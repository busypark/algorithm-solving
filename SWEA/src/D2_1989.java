import java.util.Scanner;

public class D2_1989 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			sc.nextLine();
			for (int t=1; t<=T; t++) {
				String s = sc.nextLine();
				boolean pal = true;
				for (int i=0; i<=s.length()/2; i++) {
					if (s.charAt(i) != s.charAt(s.length()-1-i)) {
						pal = false;
						break;
					}
				}
				
				int answer = (pal)? 1 : 0;
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}
