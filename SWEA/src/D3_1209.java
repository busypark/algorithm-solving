import java.util.Scanner;

public class D3_1209 {
	public static Scanner sc = new Scanner(System.in);
	static int[][] map = new int[100][100];
	
	public static void main(String[] args) {
		for (int t=1; t<=10; t++) {
			final int testNum = sc.nextInt();
			final int n = 100;
			long max = Long.MIN_VALUE;
			// rows
			for (int r=0; r<n; r++) {
				long rowSum = 0L;
				for (int c=0; c<n; c++) {
					map[r][c] = sc.nextInt();
					rowSum += map[r][c];
				}
				if (max < rowSum) max = rowSum;
			}
			
			// columns
			for (int c=0; c<n; c++) {
				long colSum = 0L;
				for (int r=0; r<n; r++) {
					colSum += map[r][c];
				}
				if (max < colSum) max = colSum;
			}
			
			// diagonal (right-up)
			long diagSum1 = 0L;
			for (int i=0; i<n; i++) {
				diagSum1 += map[n-i-1][i];
			}
			if (max < diagSum1) max = diagSum1;
			
			// diagonal (right-down)
			long diagSum2 = 0L;
			for (int i=0; i<n; i++) {
				diagSum2 += map[i][i];
			}
			if (max < diagSum2) max = diagSum2;
			
			System.out.println("#"+testNum+" "+max);
		}
	}
}
