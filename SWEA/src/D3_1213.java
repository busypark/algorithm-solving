import java.util.Scanner;

public class D3_1213 {
	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		for (int t=1; t<=10; t++) {
			final int testNum = sc.nextInt();
			sc.nextLine();
			
			String s = sc.nextLine();
			String sentence = sc.nextLine();
			
			int count = 0;
			for (int i=0; i<sentence.length();) {
				int p = sentence.indexOf(s, i);
				if (p == -1) break;
				else {
					count++;
					i = p+1;
				}
			}
			
			System.out.println("#"+testNum+" "+count);
		}
	}
}
