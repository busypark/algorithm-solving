import java.util.Scanner;

public class G4_14499 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {			
			final int H = sc.nextInt(); // 1 <= H <= 20
			final int W = sc.nextInt(); // 1 <= W <= 20
			int rDice = sc.nextInt(); // 0 <= rDice <= H-1
			int cDice = sc.nextInt(); // 0 <= cDice <= W-1
			final int[][] map = new int[H][W];
			for (int r=0; r<H; r++) {
				for (int c=0; c<W; c++) {
					map[r][c] = sc.nextInt();
				}
			}
			
			final int K = sc.nextInt(); // number of commands. 1 <= K <= 1000
			for (int k=0; k<K; k++) {
				final int command = sc.nextInt(); // 1234 (동서북남)
				
			}
		}
	}
	
	public static void toNorth() {
		
	}
	
	public static boolean isValid(int command, int r, int c, int H, int W) {
		switch (command) {
		case 1: // 동
			return (c+1 < W);
		case 2: // 서
			return (0 <= c-1);
		case 3: // 북
			return (0 <= r-1);
		case 4: // 남
			return (r+1 < H);
		}
		
		return false;
	}
}
