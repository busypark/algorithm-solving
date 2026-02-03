import java.util.Scanner;

public class D3_1216 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				sc.nextInt();
				sc.nextLine();
				final int n=100;
				final char[][] map = new char[n][n];
				for (int r=0; r<n; r++) {
					String row = sc.nextLine();
					for (int c=0; c<n; c++) {
						map[r][c] = row.charAt(c);
					}
				}
				
				int max = -1; // maximum length of pal (회문)
				// check horizontally
				for (int r=0; r<n; r++) {
					for (int c=0; c<n; c++) {
						for (int p=n-1; c<=p; p--) {
							boolean isPal = true;
							for (int i=0; i<=(p-c)/2; i++) {
								if (map[r][c+i] != map[r][p-i]) {
									isPal = false;
								}
							}
							
							if (isPal)
								max = Math.max(max, p-c+1);
						}
					}
				}
				
				// check vertically
				for (int c=0; c<n; c++) {
					for (int r=0; r<n; r++) {
						for (int p=n-1; r<=p; p--) {
							boolean isPal = true;
							for (int i=0; i<=(p-r)/2; i++) {
								if (map[r+i][c] != map[p-i][c]) {
									isPal = false;
								}
							}
							
							if (isPal)
								max = Math.max(max, p-r+1);
						}
					}
				}
				
				System.out.println("#"+t+" "+max);
			}
		}
	}
}
