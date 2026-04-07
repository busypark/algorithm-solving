import java.io.*;
import java.util.*;

public class D4_3289 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			answer.append("#").append(t).append(" ");
			
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			
			int[] root = new int[n+1];
			for (int i=1; i<=n; i++) {
				root[i] = i;
			}
			
			for (int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int command = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				if (command == 0) {
					if (a != b) {
						int aRoot = find(root, a);
						int bRoot = find(root, b);
						
						root[bRoot] = aRoot;
					}
				} else {
					if (a == b) {
						answer.append(1);
					} else {
						int aRoot = find(root, a);
						int bRoot = find(root, b);
						
						if (aRoot == bRoot) {
							answer.append(1);
						} else {
							answer.append(0);
						}
					}
				}
			}
			
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int find(int[] root, int x) {
		if (root[x] == x) return x;
		return root[x] = find(root, root[x]);
	}
}
