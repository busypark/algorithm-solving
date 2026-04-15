import java.io.*;
import java.util.*;

// 약 15분만에 풀이. MST인거 눈치 채니까 그냥 기본 문제였음. edge 기준으로 PQ 뽑는 Kruskal 방식
// 피드백 : 

public class G4_6497 {
	static class Edge implements Comparable<Edge> {
		int a, b, l;
		Edge(int a, int b, int l) {
			this.a = a;
			this.b = b;
			this.l = l;
		}
		
		@Override
		public int compareTo(Edge e) {
			return Integer.compare(l, e.l);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		while (true) {
			st = new StringTokenizer(br.readLine());
			int m = Integer.parseInt(st.nextToken());
			int n = Integer.parseInt(st.nextToken());
			
			if (m == 0 && n == 0) break;
			
			int total = 0;
			PriorityQueue<Edge> pq = new PriorityQueue<>();
			for (int i=0; i<n; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int l = Integer.parseInt(st.nextToken());
				
				pq.offer(new Edge(a, b, l));
				total += l;
			}
			
			int v = 0;
			int MST = 0;
			int[] parents = new int[m];
			for (int i=0; i<m; i++) {
				parents[i] = i;
			}
			
			while (!pq.isEmpty() && v <= m) {
				Edge e = pq.poll();
				
				int rA = find(parents, e.a);
				int rB = find(parents, e.b);
				
				if (rA == rB) continue;
				
				v ++;
				MST += e.l;
				parents[rB] = rA;
			}
			
			answer.append(total - MST).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int find(int[] parents, int X) {
		if (parents[X] == X) return X;
		return parents[X] = find(parents, parents[X]);
	}
}







