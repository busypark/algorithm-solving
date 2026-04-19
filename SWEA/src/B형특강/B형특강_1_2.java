package B형특강;
import java.io.*;
import java.util.*;

// (Solved) D3_10726

// 피드백 : 강사님 코드랑 같음
/*
 * TMI : M | mask == M 해도 된다? 이미 mask 부분이 채워져 있었다는 뜻임
 * 포인트 : 반복문 없이도 풀 수 있다는 것
 */

public class B형특강_1_2 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int mask = (1 << N) - 1;
			boolean sw = (M & mask) == mask;
			answer.append("#").append(t).append(" ").append(sw ? "ON" : "OFF").append("\n");
		}
		
		System.out.print(answer);
	}
}
