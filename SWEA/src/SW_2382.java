import java.io.*;
import java.util.*;

// 

public class SW_2382 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// input and initialize
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			final int M = Integer.parseInt(st.nextToken());
			final int K = Integer.parseInt(st.nextToken());
			
			List[][] candidates = new List[N][N];
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					candidates[r][c] = new ArrayList<Integer>();
				}
			}
			
			HashMap<Integer, Group> map = new HashMap<>();
			for (int id = 0; id < K; id ++) {
				st = new StringTokenizer(br.readLine());
				final int r = Integer.parseInt(st.nextToken());
				final int c = Integer.parseInt(st.nextToken());
				final int count = Integer.parseInt(st.nextToken());
				final int direction = Integer.parseInt(st.nextToken());
				
				map.put(id, new Group(r, c, count, direction));
				candidates[r][c].add(id);
			}
			
			// cycle
			for (int m = 0; m < M; m++) {
				// move
				for (int id = 0; id < K; id ++) {
					Group thisGroup = map.get(id);
					if (thisGroup.enabled) {
						final int r = thisGroup.r;
						final int c = thisGroup.c;
						
						// candidate & group move
						candidates[r][c].remove(new Integer(id));
						switch (thisGroup.direction) {
						case 1:
							candidates[r-1][c].add(id);
							thisGroup.r --;
							break;
						case 2:
							candidates[r+1][c].add(id);
							thisGroup.r ++;
							break;
						case 3:
							candidates[r][c-1].add(id);
							thisGroup.c --;
							break;
						default:
							candidates[r][c+1].add(id);
							thisGroup.c ++;
						}
					}
				}
				
				// process candidates
				boolean[] visited = new boolean[K];
				for (int id = 0; id < K; id ++) {
					Group thisGroup = map.get(id);
					if (!visited[id] && thisGroup.enabled) {
						final int r = thisGroup.r;
						final int c = thisGroup.c;
						
						if (candidates[r][c].size() >= 2) {
							// get argMax(count) = maxId, calculate sum
							int maxId = (int)candidates[r][c].get(0);
							int sum = 0;
							for (Object o : candidates[r][c]) {
								int i = (int) o;
								if (map.get(maxId).count < map.get(i).count) {
									maxId = i;
								}
								
								sum += map.get(i).count;
								map.get(i).enabled = false;
								visited[i] = true;
							}
							
							map.get(maxId).enabled = true;
							map.get(maxId).count = sum;
							candidates[r][c].clear();
							candidates[r][c].add(maxId);
						}
					}
				}
				
				// process wall
				for (int id = 0; id < K; id ++) {
					Group thisGroup = map.get(id);
					if (thisGroup.enabled) {
						final int r = thisGroup.r;
						final int c = thisGroup.c;
						
						if (r == 0 || r == N-1 || c == 0 || c == N-1) {
							thisGroup.count /= 2;
							
							if (thisGroup.count == 0) {
								thisGroup.enabled = false;
								candidates[r][c].remove(new Integer(id));
							} else {
								// reverse direction
								switch (thisGroup.direction) {
								case 1:
									thisGroup.direction = 2;
									break;
								case 2:
									thisGroup.direction = 1;
									break;
								case 3:
									thisGroup.direction = 4;
									break;
								default:
									thisGroup.direction = 3;
								}
							}
						}
					}
				}
			}
			
			// sum
			int total = 0;
			for (int id = 0; id < K; id ++) {
				Group thisGroup = map.get(id);
				if (thisGroup.enabled) {
					total += thisGroup.count;
				}
			}
			
			answer.append("#").append(t).append(" ").append(total).append("\n");
		}
		
		System.out.print(answer);
	}
}

class Group {
	int r, c, count, direction;
	boolean enabled = true;
	
	Group(int r, int c, int count, int direction) {
		this.r = r;
		this.c = c;
		this.count = count;
		this.direction = direction;
	}
}