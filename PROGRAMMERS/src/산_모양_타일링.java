// 수가 너무 커져서 중간에 나머지를 계속 해줘야 함. 나머지의 특성상 더하기/곱하기는 결합법칙 성립
// DP는 결국 완탐으로 하기에 경우의 수가 너무 큰 경우 -> 변수 기준 점화식으로 해보는 것

public class 산_모양_타일링 {
	public int solution(int n, int[] tops) {
        long rT = 1, rF = 2 + tops[0];
		long new_rT, new_rF;
        
        for (int i=1; i<n; i++) {
        	new_rT = rT + rF;
        	new_rF = rT * (1 + tops[i]) + rF * (2 + tops[i]);
        	
        	rT = new_rT % 10007L;
        	rF = new_rF % 10007L;
        }
        
		int answer = (int)((rT % 10007L + rF % 10007L) % 10007L);
        return answer;
    }
}
