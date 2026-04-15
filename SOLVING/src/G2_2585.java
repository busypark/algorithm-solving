import java.io.*;
import java.util.*;

/*
 * 아이디어 : S -> M -> E 순으로 가는걸 한 단위 삼아서 계속 쪼개면? Triangle Inequality때문에 긴 변 > max(작은변1, 작은변2)
 * 근데 그게 플로이드 워셜인데.. 문제는 플로이드 워셜에 어떻게 '최대 간선 수'를 섞느냐?
 * 플로이드 워셜 자체는 거쳐온 간선 수를 저장하지 않으므로 소실됨. 거리만 저장하니까..
 * 그럼 저장을 하면 되나? 업데이트 될 때마다 하나씩 올리면..
 * 정확히는 플로이드 워셜을 약간 변형. 플로이드 워셜은 합의 최소값이고, 이건 각 간선의 최대값이므로 다름
 * 
 */

public class G2_2585 {	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[][] nodes = new int[N+2][2];
		nodes[0] = new int[] {0, 0};
		nodes[N+1] = new int[] {10000, 10000};
		
		List<int[]>[] edges = new List[N+2];
		edges[0] = new ArrayList<>();
		edges[N+1] = new ArrayList<>();

		double dist;
		int fuel;
		
		dist = Math.sqrt(Math.pow(10000, 2) + Math.pow(10000, 2));
		fuel = (int) Math.ceil(dist / 10);
		edges[0].add(new int[] {N+1, fuel});
		edges[N+1].add(new int[] {0, fuel});
		
		for (int n=1; n<N+1; n++) {
			edges[n] = new ArrayList<>();
			
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			nodes[n] = new int[] {r, c};
			
			dist = Math.sqrt(Math.pow(nodes[0][0] - nodes[n][0], 2) + Math.pow(nodes[0][1] - nodes[n][1], 2));
			fuel = (int) Math.ceil(dist / 10);
			edges[0].add(new int[] {n, fuel});
			edges[n].add(new int[] {0, fuel});
			
			dist = Math.sqrt(Math.pow(nodes[N+1][0] - nodes[n][0], 2) + Math.pow(nodes[N+1][1] - nodes[n][1], 2));
			fuel = (int) Math.ceil(dist / 10);
			edges[N+1].add(new int[] {n, fuel});
			edges[n].add(new int[] {N+1, fuel});
			
			for (int m=0; m<n; m++) {
				dist = Math.sqrt(Math.pow(nodes[m][0] - nodes[n][0], 2) + Math.pow(nodes[m][1] - nodes[n][1], 2));
				fuel = (int) Math.ceil(dist / 10);
				edges[m].add(new int[] {n, fuel});
				edges[n].add(new int[] {m, fuel});
			}
		}
		
		int[] fuels = new int[N+2];
		//int[] counts = new int[N+2];
		PriorityQueue<int[]> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(fuels[n1[0]], fuels[n2[0]]));
		pq.offer(new int[] {0, 0});
		while (!pq.isEmpty()) {
			int[] here = pq.poll();
			int node = here[0];
			
			System.out.println("[ node == "+node + " ]");
			if (node == N+1) break;
			
			for (int i=0; i<edges[node].size(); i++) {
				int next = edges[node].get(i)[0];
				int f = Math.max(fuels[node], edges[node].get(i)[1]);
				
				if (fuels[next] < f) {
					fuels[next] = f;
					//counts[next] ++;
					int count = here[1] + 1;
					
					if (count <= K+1) {
						pq.offer(new int[] {next, count});
						
						System.out.println("[ "+node+" -> "+next+" ]");
						
						//System.out.println("[ PQ : " + Arrays.toString((new ArrayList<>(pq)).toArray()) + " ]");
					}

					System.out.println("  "+Arrays.toString(fuels));
				}
			}

			System.out.println(Arrays.toString(fuels));
		}
		
		
		System.out.println(fuels[N+1]);
	}
}










