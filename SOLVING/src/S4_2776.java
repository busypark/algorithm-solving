import java.io.*;
import java.util.*;

public class S4_2776 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int t=0; t<T; t++) {
			int N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			
			boolean[] note1 = new boolean[N+1];
			for (int i=0; i<N; i++) {
				int num = Integer.parseInt(st.nextToken());
				note1[num] = true;
			}
			
			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<M; i++) {
				int check = Integer.parseInt(st.nextToken());
				answer.append((0 <= check && check <= N && note1[check]) ? 1 : 0).append("\n");
			}
		}
		
		System.out.println(answer);
	}
}
