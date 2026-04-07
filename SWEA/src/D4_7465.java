import java.io.*;
import java.util.*;

// 아는 관계여야만 묶어서 '무리'라고 하는 줄 알았는데, 혼자여도 무리라고 치는 문제임..

public class D4_7465 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int[] root = new int[N+1];
			for (int i=1; i<=N; i++) {
				root[i] = i;
			}
			
			for (int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				int aRoot = find(root, a);
				int bRoot = find(root, b);
				
				int min = Math.min(aRoot, bRoot);
				int max = Math.max(aRoot, bRoot);
				
				root[max] = min;
			}
			
			Set<Integer> distinct = new HashSet<>();
			for (int i=1; i<=N; i++) {
				int r = find(root, i);
				if (r != i) {
					distinct.add(r);
				}
			}
			int countGroup = distinct.size();
			
			answer.append("#").append(t).append(" ").append(countGroup).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int find(int[] root, int x) {
		if (root[x] == x) return x;
		return root[x] = find(root, root[x]);
	}
}
