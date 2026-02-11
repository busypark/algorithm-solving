import java.util.Scanner;

public class D3_1234 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int n = sc.nextInt();
				String word = sc.next();
				StringBuilder password = new StringBuilder();
				for (int i=0; i<n; i++) {
					char thisChar = word.charAt(i);
					
					if (i == 0) {
						password.append(thisChar);
					} else {
						final int pwdLength = password.length();
						if (pwdLength > 0 && thisChar == password.charAt(pwdLength-1)) {
							password.delete(pwdLength-1, pwdLength);
						} else {
							password.append(thisChar);
						}
					}
				}
				
				System.out.println("#"+t+" "+password);
			}
		}
	}
}
