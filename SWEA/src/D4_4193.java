import java.util.Scanner;

public class D4_4193 {
	static Scanner sc = new Scanner(System.in);
	static int[][] map; // map[r][c] == 0, 1, 2
	
	public static void main(String[] args) {
		final int T = sc.nextInt();
		for (int t=1; t<=T; t++) {
			int N = sc.nextInt();
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					map[r][c] = sc.nextInt();
				}
			}
			
			int answer = 0;
			System.out.println("#"+t+" "+answer);
		}
	}
}
