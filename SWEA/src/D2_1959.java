import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class D2_1959 {
	
	public static void main(String[] args) throws IOException {
		StringBuilder answer = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			final int M = Integer.parseInt(st.nextToken());
			
			final int lenShort, lenLong;
			final int[] shorter, longer;
			if (N <= M) {
				shorter = new int[N];
				lenShort = N;
				longer = new int[M];
				lenLong = M;
				
				st = new StringTokenizer(br.readLine());
				for (int i=0; i<N; i++)
					shorter[i] = Integer.parseInt(st.nextToken());

				st = new StringTokenizer(br.readLine());
				for (int i=0; i<M; i++)
					longer[i] = Integer.parseInt(st.nextToken());
			} else {
				longer = new int[N];
				lenLong = N;
				shorter = new int[M];
				lenShort = M;
				
				st = new StringTokenizer(br.readLine());
				for (int i=0; i<N; i++)
					longer[i] = Integer.parseInt(st.nextToken());

				st = new StringTokenizer(br.readLine());
				for (int i=0; i<M; i++)
					shorter[i] = Integer.parseInt(st.nextToken());
			}
			
			int maxSum = 0;
			for (int i=0; i<lenLong-lenShort+1; i++) {
				int currentSum = 0;
				for (int j=0; j<lenShort; j++) {
					currentSum += shorter[j] * longer[i+j];
				}
				
				maxSum = Math.max(maxSum, currentSum);
			}
			
			answer.append("#"+t+" "+maxSum+"\n");
		}
		
		System.out.println(answer);
	}
	
}