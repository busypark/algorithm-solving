import java.io.*;
import java.util.*;

// https://school.programmers.co.kr/learn/courses/30/lessons/42860?language=java

public class PROG_42860 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws IOException {
		// input
		st = new StringTokenizer(br.readLine());
		final String s = st.nextToken();
		
		// create AAAA..
		final int n = s.length();
		final StringBuilder AA = new StringBuilder();
		for (int i=0; i<n; i++) AA.append("A");
		
		// loop
		int p = 0;
		int nControl = 0;
		while (!s.equals(AA.toString())) {
			// process
			final int minusInc = 27 - (s.charAt(p) - 'A' + 1);
			final int plusInc = s.charAt(p) - 'A';
			AA.setCharAt(p, s.charAt(p));
			nControl += Math.min(minusInc, plusInc);
			
			// break if the condition satisfied
			if (s.equals(AA.toString())) break;
			
			// search for next (right)
			int nextP = (p + 1) % n;
			int nextCount = 1;
			for (; nextCount <= n ; nextCount++) {
				if (s.charAt(nextP) != 'A') {
					break;
				}
				
				nextP = (nextP + 1) % n;
			}
			
			// search for prev (left)
			int prevP = (p - 1 + n) % n;
			int prevCount = 1;
			for (; prevCount <= n; prevCount++) {
				if (s.charAt(prevP) != 'A') {
					break;
				}
				
				prevP = (prevP - 1 + n) % n;
			}
			
			// update p
			if (nextCount < prevCount) {
				p = nextP;
				nControl += nextCount;
			} else {
				p = prevP;
				nControl += prevCount;
			}
		}
		
		System.out.println(nControl);
	}
}
