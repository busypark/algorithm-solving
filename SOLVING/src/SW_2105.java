import java.io.*;
import java.util.*;

public class SW_2105 {
	static int N;
	static int[][] map;
	static int[] drArr = {-1, -1, 1, 1};
	static int[] dcArr = {-1, 1, -1, 1};
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input N and map[N][N]
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			int maxDessert = -1;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					final int ld = c;
					final int rd = N-1-c;
					
					if (1 <= ld && 1 <= rd) {
						
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(maxDessert).append("\n");
		}
		
		System.out.print(answer);
	}
}







