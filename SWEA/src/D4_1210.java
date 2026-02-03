import java.util.Scanner;

public class D4_1210 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				sc.nextInt();
				final int n = 100;
				int[][] map = new int[n][n];
				
				int exitColumn = -1; // exitRow = n-1 = 99
				for (int r=0; r<n; r++) {
					for (int c=0; c<n; c++) {
						map[r][c] = sc.nextInt(); // map[r][c] == 0 or 1 or 2 (once)
						if (map[r][c] == 2) {
							exitColumn = c;
						}
					}
				}
				
				int R = 98; // above exit point
				int C = exitColumn;
				int direction = 0; // 0=above, -1=left, +1=right
				
				//int testCount = 10;
				
				while (0 < R) {
					//System.out.println("position : [r="+R+", c="+C+"]");
					if (direction == 0) {
						if (0 < C && map[R][C-1] == 1) {
							C--;
							direction = -1; //System.out.println("A");
						} else if (C < n-1 && map[R][C+1] == 1) {
							C++;
							direction = 1; //System.out.println("B");
						} else {
							R--;
						}
					} else if (direction == -1) {
						if (map[R-1][C] == 0) {
							C--; //System.out.println("C");
						} else {
							R--;
							direction = 0; //System.out.println("D");
						}
					} else {
						if (map[R+1][C] == 0) {
							C++; //System.out.println("E");
						} else {
							R--;
							direction = 0; //System.out.println("F");
						}
					}
					
					//if (testCount-- <= 0) break;
				}
				
				System.out.println("#"+t+" "+C);
			}
		}
	}
}
