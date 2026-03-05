import java.util.Scanner;

public class SW_1217 {
	public static void main(String[] args) {
		final Scanner sc = new Scanner(System.in);
		for (int t=1; t<=10; t++) {
			final int tc = sc.nextInt();
			final int base = sc.nextInt();
			final int exp = sc.nextInt();
			
			final int answer = exponential(base, exp);
			
			System.out.println("#"+tc+" "+answer);
		}
	}
	
	static int exponential(int base, int exp) {
		if (exp == 0) return 1;
		if (exp == 1) return base;
		if (exp % 2 == 0) {
			final int sub = exponential(base, exp/2);
			return sub*sub;
		} else {
			final int sub = exponential(base, (exp-1)/2);
			return base*sub*sub;
		}
	}
}
