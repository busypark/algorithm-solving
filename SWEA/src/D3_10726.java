import java.util.Scanner;

public class D3_10726 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final long N = sc.nextInt();
				final long M = sc.nextInt();
				long operand = (long)Math.pow(2,  N) - 1;
				
				String answer;
				if ((M & operand) == operand) {
					answer = "ON";
				} else {
					answer = "OFF";
				}
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
