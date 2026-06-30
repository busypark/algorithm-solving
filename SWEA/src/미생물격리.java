import java.io.*;
import java.util.*;

public class 미생물격리 {
	static class Info {
		int magnitude, direction;
		Info(int m, int d) {
			magnitude = m; // disabled if magnitude == 0
			direction = d;
		}
	}
	
	static int hashing(int r, int c) {
		return 100*r + c;
	}
	
	static int[] dr = new int[] {0, -1, 1, 0, 0};
	static int[] dc = new int[] {0, 0, 0, -1, 1};
	
	public static int solve(int N, int M, int K, int[][] info) {
		List<Info> cluster = new ArrayList<>();
		Map<Integer, List<Integer>> state = new HashMap<>();
		
		// transform into list and map
		for (int k=0; k<K; k++) {
			cluster.add(new Info(info[k][2], info[k][3]));
			state.put(hashing(info[k][0], info[k][1]), new ArrayList<>(Arrays.asList(k)));
		}
		
		// follow timestamps for M times
		for (int m=0; m<M; m++) {
			// spread for the next state
			Map<Integer, List<Integer>> stateNext = new HashMap<>();
			for (int hash : state.keySet()) {
				int r = hash / 100;
				int c = hash % 100;
				
				for (int id : state.get(hash)) {
					
					if (cluster.get(id).magnitude == 0) continue;
					
					int nr = r + dr[cluster.get(id).direction];
					int nc = c + dc[cluster.get(id).direction];
					
					int hashed = hashing(nr, nc);
					if (!stateNext.containsKey(hashed)) {
						stateNext.put(hashed, new ArrayList<>());
					}
					
					stateNext.get(hashed).add(id);
				}
			}
			
			state.clear();
			
			// merge all stateNext into state
			for (int hash : stateNext.keySet()) {
				int r = hash / 100;
				int c = hash % 100;
				List<Integer> ids = stateNext.get(hash);
				
				if (ids.size() == 1) {
					int id = ids.get(0);
					if (r == 0 || r == N-1 || c == 0 || c == N-1) {
						Info ifo = cluster.get(id);
						
						int d = ifo.direction;
						ifo.direction = (2-(d-1)%2)+2*((d-1)/2);
						ifo.magnitude /= 2;
					}
					
					state.put(hash, new ArrayList<>(Arrays.asList(id)));
				} else if (ids.size() >= 2) {
					int sum = 0;
					int maxId = -1;
					for (int id : ids) {
						int mag = cluster.get(id).magnitude;
						sum += mag;
						
						if (maxId == -1) maxId = id;
						else if (cluster.get(maxId).magnitude < mag) maxId = id;
					}
					
					for (int id : ids) {
						cluster.get(id).magnitude = 0;
					}
					
					cluster.get(maxId).magnitude = sum;
					
					state.put(hash, new ArrayList<>(Arrays.asList(maxId)));
				}
			}
		}
		
		// get the sum of all magnitudes
		int sumMag = 0;
		for (Info ifo : cluster) {
			sumMag += ifo.magnitude;
		}
		
		return sumMag;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			int[][] info = new int[K][4];
			for (int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());
				for (int i=0; i<4; i++)
					info[k][i] = Integer.parseInt(st.nextToken());
			}
			
			answer.append("#").append(t).append(" ")
				  .append(solve(N, M, K, info))
				  .append("\n");
		}
		
		System.out.print(answer);
	}
}
