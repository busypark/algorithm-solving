import java.util.Scanner;

public class D2_1928 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			sc.nextLine();
			
			for (int t=1; t<=T; t++) {
				String s = sc.nextLine();
				StringBuilder decode = new StringBuilder();
				for (int i=0; i<s.length(); i+=4) {
					int byte3 = 0;
					for (int j=0; j<4; j++) {
						char thisByte = s.charAt(i+j);
						int thisValue = 0;
						if ('A'<=thisByte && thisByte<='Z') {
							thisValue = thisByte - 'A' + 0;
						} else if ('a'<=thisByte && thisByte<='z') {
							thisValue = thisByte - 'a' + 26;
						} else if ('0'<=thisByte && thisByte<='9') {
							thisValue = thisByte - '0' + 52;
						} else if (thisByte == '+') {
							thisValue = 62;
						} else {
							thisValue = 63;
						}
						
						byte3 = byte3 << 6;
						byte3 += thisValue;
					}
					
					char[] char3 = new char[3];
					int ascii1 = byte3 >> 16;
					int ascii2 = (byte3 >> 8) % 256;
					int ascii3 = byte3 % 256;
					
					decode.append((char)ascii1);
					decode.append((char)ascii2);
					decode.append((char)ascii3);
				}
				
				System.out.println("#" + t + " " + decode);
			}
		}
	}
}
