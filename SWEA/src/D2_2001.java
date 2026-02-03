import java.util.Scanner;

public class D2_2001 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt();
				final int[][] map = new int[N][N];

				final int M = sc.nextInt();
				for (int r=0; r<N; r++) {
					for (int c=0; c<N; c++) {
						map[r][c] = sc.nextInt();
					}
				}
				
				int max = 0;
				for (int r=0; r<=N-M; r++) {
					for (int c=0; c<=N-M; c++) {
						int current = 0;
						for (int dr=0; dr<M; dr++) {
							for (int dc=0; dc<M; dc++) {
								current += map[r+dr][c+dc];
							}
						}
						max = Math.max(max, current);
					}
				}
				
				System.out.println("#"+t+" "+max);
			}
		}
	}
}
