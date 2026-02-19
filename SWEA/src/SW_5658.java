import java.io.*;
import java.util.*;

public class SW_5658 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input N, M, numText
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			final int M = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			final String numText = st.nextToken();
			
			final int nCount = N/4;
			Set<Long> numbers = new HashSet<>();
			for (int i=0; i<numText.length(); i++) {
				StringBuilder newNumberText = new StringBuilder();
				for (int j=0; j<nCount; j++) {
					newNumberText.append(numText.charAt((i+j) % N));
				}
				
				long newNumber = Integer.parseInt(newNumberText.toString(), 16);
				numbers.add(newNumber);
			}
			
			List<Long> convertList = new ArrayList<>(numbers);
			convertList.sort(Comparator.reverseOrder());
			
			answer.append("#"+t+" "+convertList.get(M-1)+"\n");
		}
		
		System.out.println(answer);
	}
	
}
