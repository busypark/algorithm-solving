import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class D3_2805 {
	
	public static void main(String[] args) throws IOException {
		StringBuilder answer = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			final int h = (N-1)/2;
			
			int sum = 0;
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				String row = st.nextToken();
				final int shiftR = r-h;
				for (int c=0; c<N; c++) {
					final int val = row.charAt(c) - '0';
					final int shiftC = c-h;
					if (shiftR <= h-Math.abs(shiftC) && shiftR >= Math.abs(shiftC)-h) {
						sum += val;
					}
				}
			}
			
			answer.append("#"+t+" "+sum+"\n");
		}
		
		System.out.println(answer);
	}
	
}