import java.util.Scanner;

public class D3_1215 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				int N = sc.nextInt();
				sc.nextLine();
				final int n = 8;
				char[][] map = new char[n][n];
				for (int r=0; r<n; r++) {
					String row = sc.nextLine();
					for (int c=0; c<n; c++)
						map[r][c] = row.charAt(c);
				}
				
				int count = 0;
				
				// count horizontally
				for (int r=0; r<n; r++) {
					for (int c=0; c<=n-N; c++) {
						boolean isPal = true;
						for (int i=0; i<N/2; i++) {
							if (map[r][c+i] != map[r][c+N-1-i]) {
								isPal = false;
								break;
							}
						}
						
						if (isPal) count++;
					}
				}
				
				// count vertically
				for (int c=0; c<n; c++) {
					for (int r=0; r<=n-N; r++) {
						boolean isPal = true;
						for (int i=0; i<N/2; i++) {
							if (map[r+i][c] != map[r+N-1-i][c]) {
								isPal = false;
								break;
							}
						}
						
						if (isPal) count++;
					}
				}
				
				System.out.println("#"+t+" "+count);
			}
		}
	}
}
