import java.io.*;
import java.util.*;

public class D4_3000 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		final int denom = 20171109;
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int mid = Integer.parseInt(st.nextToken());
			PriorityQueue<Integer> upper = new PriorityQueue<>();
			PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder());
			
			int modular = 0;
			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());
				int num1 = Integer.parseInt(st.nextToken());
				int num2 = Integer.parseInt(st.nextToken());
				
				int lw = Math.min(num1, num2);
				int up = Math.max(num1, num2);
				
				if (up <= mid) {
					System.out.println("up <= mid " + mid);
					
					lower.offer(lw);
					upper.offer(mid);
					mid = up;
					
				} else if (mid <= lw) {
					System.out.println("mid <= lw " + mid);
					lower.offer(mid);
					upper.offer(up);
					mid = lw;
					
				} else {
					System.out.println("else " + mid);
					lower.offer(lw);
					upper.offer(up);
				}
				
				System.out.println(" - mod = " + mid % denom);
				modular += (mid % denom);
				modular %= denom;
			}
			
			answer.append("#").append(t).append(" ").append(modular).append("\n");
		}
		
		System.out.print(answer);
	}
}
