import java.util.Scanner;

public class D4_1222 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				sc.nextInt();
				sc.nextLine();
				
				String f = sc.nextLine();
				int answer = 0;
				
				for (int i=0; i<f.length(); i++)
					if (f.charAt(i) != '+') {
						answer += f.charAt(i) - '0';
					}
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
