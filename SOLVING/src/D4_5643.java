import java.io.*;
import java.util.*;

public class D4_5643 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc=1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			
			Map<Integer, Integer> edges = new HashMap<>();
			for (int i=0; i<M; i++) {
				// undirected roads
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				
				int hash1 = hash(s, e);
				int hash2 = hash(e, s);
				int get1 = (edges.containsKey(hash1)) ? edges.get(hash1) : Integer.MAX_VALUE;
				int put1 = Math.min(get1, t);
				
				edges.put(hash1, put1);
				edges.put(hash2, put1);
			}
			
			for (int i=0; i<W; i++) {
				// directed worm holes
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				
				int hash1 = hash(s, e);
				int get1 = (edges.containsKey(hash1)) ? edges.get(hash1) : Integer.MAX_VALUE;
				int put1 = Math.min(get1, -t); // negative t
				
				edges.put(hash1, put1);
			}
			
			boolean flag = false;
			bellmanLoop:
			for (int i=1; i<=N; i++) {
				for (int j=1; j<=N; j++) {
					if (i != j) {
						if (bellmanford(N, edges, i, j) + bellmanford(N, edges, j, i) < 0) {
							flag = true;
							break bellmanLoop;
						}
					}
				}
			}
			
			answer.append(flag ? "YES" : "NO").append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int hash(int x, int y) {
		return (x << 9) + y;
	}
	
	static int[] unhash(int h) {
		return new int[] {h >> 9, h % 512};
	}
	
	static int bellmanford(int N, Map<Integer, Integer> edges, int start, int end) {
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[start] = 0;
		
		Set<Integer> keys = edges.keySet();
		for (int i=1; i<=N-1; i++) {
			for (int key : keys) {
				int[] uh = unhash(key);
				int s = uh[0];
				int e = uh[1];
				
				if (dist[s] != Integer.MAX_VALUE && dist[s] + edges.get(key) < dist[e]) {
					dist[e] = dist[s] + edges.get(key);
				}
			}
		}
		System.out.println(start+" "+end+" : "+dist[end]);
		return dist[end];
	}
}
