import java.util.Scanner;

public class D3_7102 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt();
				final int M = sc.nextInt();
				
				final int lower = Math.min(N, M);
				final int higher = Math.max(N, M);
				
				System.out.print("#"+t);
				for (int i=1+lower; i<=1+higher; i++) {
					System.out.print(" "+i);
				}
				
				System.out.println();
			}
		}
	}
}
