import java.io.*;
import java.util.*;

public class D3_26009 {
	static final long N = 998244353L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder answer = new StringBuilder();
	
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			final long a = Integer.parseInt(st.nextToken());
			final long b = Integer.parseInt(st.nextToken());
			final long c = Integer.parseInt(st.nextToken());
			
			final long remA = sum(a) % N;
			final long remB = sum(b) % N;
			final long remC = sum(c) % N;
			
			final long r = (remA*remB*remC) % N;
			answer.append(r).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static long sum(long n) {
		if (n % 2 == 0) {
			return (((n+1)%N)*(n/2)) % N;
		} else {
			return (((n+1)/2)%N + ((n+1)%N)*(n/2)) % N;
		}
	}
}
