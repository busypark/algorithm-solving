import java.io.*;

/*
 * 왜 이게 Greedy인지?
 * Optimal Substructure인 건 알겠는데, Greedy Choice Property는 모르겠음
 * 
 * 피드백 : long 입력은 Long.parseLong -> Integer.parseInt 런타임 에러
 * 피드백 : 너무 감정적이었음. 더 고민해볼걸
 */
 

public class D5_6782 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			final long N = Long.parseLong(br.readLine());
			answer.append("#").append(t).append(" ").append(Ctrl(N)).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static long Ctrl(long N) {
		long ctrl = 0;
        while (N > 2) {
            long root = (long) Math.ceil(Math.sqrt(N)); // 이게 핵심
            long right = root * root;
            
            ctrl += (right - N + 1);
            N = root;
        }
        return ctrl;
	}
}
