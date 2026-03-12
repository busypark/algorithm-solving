import java.io.*;
import java.util.*;

// 피드백 : 백트래킹으로 안 하고 다른 그리디같은 방법 있는지 알아보다 너무 오래 시간 걸렸음
//        백트래킹도 아직 완전 능숙하게는 못 했음
//        실행시간/메모리도 각각 2배 정도
// 더 나은 사람은 리스트 대신 배열[M][0,1,2] 처리(l,r,s)
// 그리고 

public class D4_8275 {
	static int N, X, M;
	static List<Condition> conditions = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// input N, X, M
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// input (l, r, s) conditions for M iterations
			conditions.clear();
			for (int m=0; m<M; m++) {
				st = new StringTokenizer(br.readLine());
				final int l = Integer.parseInt(st.nextToken());;
				final int r = Integer.parseInt(st.nextToken());;
				final int s = Integer.parseInt(st.nextToken());;
				conditions.add(new Condition(l-1, r-1, s));
			}
			
			// search for answer
			found = false;
			hamsters = new int[N];
			maxHamsters = new int[N];
			curSum = 0;
			maxSum = -1;
			search(0);
			
			// add to StringBuilder
			answer.append("#").append(t).append(" ");
			if (!found) {
				answer.append(-1);
			} else {
				for (int i=0; i<N; i++)
					answer.append(maxHamsters[i]+" ");
			}
			
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
	
	static boolean found;
	static int[] hamsters, maxHamsters;
	static int curSum, maxSum;
	static void search(int idx) {		
		if (idx == N) {
			// done -> inspect every condition fulfilled (s==0)
			for (Condition cond : conditions) {
				if (cond.s != 0) {
					return;
				}
			}
			
			// fulfilled -> update
			found = true;
			if (maxSum < curSum) {
				maxSum = curSum;
				maxHamsters = Arrays.copyOf(hamsters, N);
			}
			
			return;
		}
		
		// NOT YET
		for (int vol=0; vol<=X; vol++) {
			// inspect feasibility
			int count = 0;
			for (Condition cond : conditions) {
				if (cond.l <= idx && idx <= cond.r) {
					count++;
					if (cond.s < vol) {
						// vol is increasing -> pruning
						return;
					}
				}
			}
			
			// some index are NOT covered -> fix them MAX(X)
			if (count == 0) {
				hamsters[idx] = X;
				curSum += X;
				search(idx+1);
				curSum -= X; // 왜 해줘야되지? -> 이게 끝나고 호출한 함수로 돌아갔을 때 curSum이 돌아가 있어야 함
				break;
			} else {
				// covered by some conditions -> backtracking
				hamsters[idx] = vol;
				curSum += vol;
				for (Condition cond : conditions) {
					if (cond.l <= idx && idx <= cond.r) {
						cond.s -= vol;
					}
				}
				
				search(idx+1);
				curSum -= vol;
				for (Condition cond : conditions) {
					if (cond.l <= idx && idx <= cond.r) {
						cond.s += vol;
					}
				}
			}
		}
	}
}

class Condition {
	int l, r, s;
	Condition(int l, int r, int s) {
		this.l = l;
		this.r = r;
		this.s = s;
	}
}
