import java.util.Scanner;

public class D1_2068 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				int max = -1;
				
				for (int i=0; i<10; i++) {
					int n = sc.nextInt();
					if (max < n) max = n;
				}
				
				System.out.println("#"+t+" "+max);
			}
		}
	}
}
