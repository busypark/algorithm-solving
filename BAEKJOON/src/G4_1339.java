import java.io.*;
import java.util.*;

// 피드백 : 긴장을 안 해서 그런가 발상이 좋았던 것 같음
// 		  근데 Arrays.sort(int[])는 되지만 뒤에 Collections.reverseOrder()를 붙이면 Integer[] 여야 한다는 게 신기..

public class G4_1339 {
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		final Integer[] compose = new Integer[26];
		for (int i=0; i<26; i++)
			compose[i] = 0;
		
		final boolean[] use = new boolean[26];
		int useCount = 0;
		
		st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			final String word = st.nextToken();
			
			final int len = word.length();
			for (int c=0; c<len; c++) {
				final char ch = word.charAt(c);
				final int idx = ch - 'A';
				final int digit = (int)Math.pow(10, len - 1 - c);
				
				compose[idx] += digit;
				if (!use[idx]) useCount++;
				use[idx] = true;
			}
		}
		
		Arrays.sort(compose, Collections.reverseOrder());
		int sum = 0;
		for (int i=0; i<useCount; i++) {
			sum += (9-i) * compose[i];
		}
		
		System.out.println(sum);
	}
}
