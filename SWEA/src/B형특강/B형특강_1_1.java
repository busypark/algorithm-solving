package B형특강;
import java.io.*;

// (Solved) D2_1288 새로운 불면증 치료법

// 비트마스킹 문제인데 Set으로 처리해도 됨. O(1)이므로
// 피드백 : 각 자리수 스캔하는 것에서 좀 헷갈렸지만 20분 만에 구현까지 완료했음
// 강사님 코드 : 매번의 숫자를 문자열로 변환하는 메서드로 처리해서 간편하게 이해.
//            java에서는 String.valueOf(숫자).toCharArray();
/*
 * 강사님 설명 : 최대 count는 100회 이하일 것임을 증명할 수 있음
 * 예를 들어 N이 4자리면, 100을 곱하면 6자리임
 * 그럼 100 이하의 어떤 수를 곱했을 때 '최초로 5자리가 되는' k번은 반드시 있음
 * 그때의 5자리의 앞자리 수는 무조건 1임. 20000 - 9999 = 10001이므로
 * 4자리 두 개를 더해서 5자리의 앞이 2 이상인 수를 만들 수 없음
 * 그래서 계속 더하다보면 제일 앞자리는 1~9가 무조건 등장하므로
 * 100번까지 더하면 반드시 0~9의 모든 수를 쓸 수밖에 없음
 */

public class B형특강_1_1 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			int N = Integer.parseInt(br.readLine());
			int mask = 0; // 0 10개 ~ 1 10개가 채워질 때까지
			int target = (1 << 10) - 1; // 1 10개
			long count = 0;
			long mul = N;
			
			while (mask != target) {
				long cur = mul;
				while (cur > 0) {
					int d1 = (int)(cur % 10);
					mask = mask | (1 << d1);
					
					long next = (cur / 10);
					cur = next;
				}
				
				mul += N;
				count ++;
			}
			
			answer.append("#").append(t).append(" ").append(count * N).append("\n");
		}
		
		System.out.print(answer);
	}
}
