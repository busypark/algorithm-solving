import java.io.*;

public class D2_1970 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			answer.append("#").append(t).append("\n");
			
			int N = Integer.parseInt(br.readLine());
			int denom = 50000;
			int factor = 2;
			
			for (; denom >= 10; denom /= factor) {
				answer.append(N / denom).append(" ");
				N %= denom;
				
				if (factor == 5) factor = 2;
				else factor = 5;
			}
			
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
}
