import java.io.*;
import java.util.*;

// 은근 자료형에 약한 듯? 중간을 막 long으로 바꿨는데 결국 출력할 때 (int)로 해서 틀린 거였음..
// 43번줄 nEdge < N-1에서 등호 붙이는 걸로 착각해서 좀 헤맸음. N-1개까지 만들어야 하므로 N-1이 되기 전까지만 루프를 돌려야 함

public class D4_1251 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			int N = Integer.parseInt(br.readLine());
			long[] x = new long[N];
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<N; i++) {
				x[i] = Long.parseLong(st.nextToken());
			}
			long[] y = new long[N];
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<N; i++) {
				y[i] = Long.parseLong(st.nextToken());
			}
			double e = Double.parseDouble(br.readLine());
			
			PriorityQueue<Edge> edges = new PriorityQueue<>((e1, e2) -> Double.compare(e1.cost, e2.cost));
			for (int i=0; i<N; i++) {
				for (int j=i+1; j<N; j++) {
					long dx = x[i] - x[j];
					long dy = y[i] - y[j];
					edges.offer(new Edge(i, j, (e*dx)*dx + (e*dy)*dy));
				}
			}
			
			int[] parent = new int[N];
			for (int i=0; i<N; i++) {
				parent[i] = i;
			}
			int nEdge = 0;
			double minCost = 0.0D;
			while (nEdge < N-1) {
				Edge edge = edges.poll();
				int rootA = find(parent, edge.A);
				int rootB = find(parent, edge.B);
				if (rootA == rootB) continue;
				
				parent[rootB] = rootA;
				nEdge ++;
				minCost += edge.cost;
			}
			
			answer.append("#").append(t).append(" ").append((long)Math.round(minCost)).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int find(int[] parent, int node) {
		if (parent[node] == node) return node;
		return parent[node] = find(parent, parent[node]);
	}
	
	static class Edge {
		int A, B;
		double cost;
		
		Edge(int A, int B, double cost) {
			this.A = A;
			this.B = B;
			this.cost = cost;
		}
	}
}
