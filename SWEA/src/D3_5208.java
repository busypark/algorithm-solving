import java.util.Scanner;

// 파이썬 전용이라 제출은 못했으나 Test Case 3개 모두 정답

public class D3_5208 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 3 <= N <= 100
				final int[] charges = new int[N-1]; // 0 < charges[i] < N
				for (int i=0; i<N-1; i++)
					charges[i] = sc.nextInt();
				
				/*
				 기본적으로 가능한 모든 경우의 수는 2^(N-2)가지임 (시점/종점 제외한 정류장에서 충전하냐/마냐)
				 N <= 100이므로 최대 경우의 수는 2^8 = 256가지 -> 모든 경우 탐색해도 그리 오래 안 걸릴 것
				 시점의 충전은 횟수로 안 침
				 모든 경우는 optimal & feasible(횟수가 최소인 경우), not optimal & feasible(최소는 아니지만 종점 도달), infeasible(종점 도달 불가)로 나뉨
				 전략 : 0부터 2^(N-2)-1까지 1씩 올리면서 2진수로 경우 표현하면서 충전(1)/비충전(0) 적용하고 infeasible하면 빨리 거름. optimal은 그냥 최소값으로 판단
				 */
				
				int numCase; // 0 ~ 2^(N-2)-1 -> 주의! 1의 자리는 idx=1, 2의 자리는 idx=2, ..., n의 자리는 idx=n의 충전 여부
				int minChargeCount = Integer.MAX_VALUE;
				final int lastCase = pow(2, N-2) - 1; // binary 1111...111
				for (numCase = 0; numCase <= lastCase; numCase++) {
					//System.out.println("#"+t+" caseNum(binary) : "+Integer.toBinaryString(numCase));
					
					int currentChargeCount = 0;
					int charge = charges[0]; // initial charge
					int i = 1;
					boolean reached = false; // is Feasible?
					for (; i<N; i++) {
						if (minChargeCount <= currentChargeCount) break; // no need to search this way more
						
						charge--; // move 1 step
						if (charge < 0) break;
						if (i == N-1) {
							reached = true;
							break;
						}
						
						if ((numCase >> (i-1)) % 2 == 1) {
							charge += charges[i];
							currentChargeCount++;
						}
					}
					
					if (reached) {
						minChargeCount = Math.min(minChargeCount, currentChargeCount);
					}
				}
				
				System.out.println("#"+t+" "+minChargeCount);
			}
		}
	}
	
	public static int pow(int base, int exp) {
		return (int)Math.pow(base, exp);
	}
}
