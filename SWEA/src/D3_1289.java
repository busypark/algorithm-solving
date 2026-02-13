import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class D3_1289 {
	
	public static void main(String[] args) throws IOException {
		StringBuilder answer = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			String s = "0"+st.nextToken();
			int count = 0;
			for (int i=s.length()-1; 1<=i; i--) {
				if (s.charAt(i) != s.charAt(i-1))
					count++;
			}
			
			answer.append("#"+t+" "+count+"\n");
		}
		
		System.out.println(answer);
	}
	
}