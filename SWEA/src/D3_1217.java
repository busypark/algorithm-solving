import java.util.Scanner;

public class D3_1217 {
	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		for (int t=1; t<=10; t++) {
			final int testNum = sc.nextInt();
			final int N = sc.nextInt();
			final int M = sc.nextInt();
			
			int answer = pow(N, M);
			System.out.println("#"+testNum+" "+answer);
		}
	}
	
	static int pow(int base, int exp) {
		if (exp == 0) return 1;
		return base * pow(base, exp-1);
	}
}
