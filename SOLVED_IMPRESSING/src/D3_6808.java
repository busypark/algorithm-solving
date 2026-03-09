import java.io.*;
import java.util.*;

// 피드백 : pruning하는 경우 나머지 경우에 대한 count는 1개가 아님! 
// tip : 나의 경우 절반을 넘어야(86<=score) pruning하는데,
//       재영님은 각 플레이어에게 '남은 점수'라는 개념을 도입해서 '남은 점수까지 다 더해도 안 되는 경우'를 pruning하여 더 빠름

public class D3_6808 {
	static final int sum = 362880; // 9!
	static final int n = 9;
	static int[] kyu = new int[n];
	static int[] iny = new int[n];
	static boolean[] kyuFilled = new boolean[n*2+1];
	static final int[] factorials = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			Arrays.fill(kyuFilled, false);
			for (int i=0; i<n; i++) {
				kyu[i] = Integer.parseInt(st.nextToken());
				kyuFilled[kyu[i]] = true;
			}
			
			int p = 0;
			for (int i=1; i<=n*2; i++) {
				if (!kyuFilled[i]) {
					iny[p++] = i;
				}
			}
			
			winCount = 0;
			kyuScore = 0;
			Arrays.fill(visited, false);
			backtrack(-1);
			
			answer.append("#").append(t).append(" ").append(winCount).append(" ").append(sum - winCount).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static int kyuScore;
	static int winCount;
	static boolean[] visited = new boolean[n];
	static void backtrack(int depth) {
		// already kyu ate over-half of the sum -> win (promising -> pruning)
		if (86 <= kyuScore) {
			winCount += factorials[n-1-depth];

			return;
		}
		
		if (depth == n-1) {
			// every game done -> already counted win / lose beforehand
			
			return;
		}
		
		// NOT YET
		for (int i=0; i<n; i++) {
			if (!visited[i]) {
				visited[i] = true;
				if (kyu[depth + 1] > iny[i]) {
					kyuScore += kyu[depth + 1] + iny[i];
					backtrack(depth + 1);
					kyuScore -= kyu[depth + 1] + iny[i];
				} else {
					backtrack(depth + 1);
				}
				visited[i] = false;
			}
		}
	}
}
