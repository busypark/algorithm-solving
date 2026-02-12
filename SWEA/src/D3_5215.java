import java.util.Scanner;

public class D3_5215 {
	static int N, L;
	static int maxScore;
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				N = sc.nextInt(); // 재료의 수 
				L = sc.nextInt(); // 제한 칼로리
				
				scores = new int[N];
				calories = new int[N];
				for (int n=0; n<N; n++) {
					scores[n] = sc.nextInt(); // 점수
					calories[n] = sc.nextInt(); // 칼로리
				}
				
				maxScore = 0;
				getScore(0, 0, 0);
				
				System.out.println("#"+t+" "+maxScore);
			}
		}
	}
	
	static int[] scores;
	static int[] calories;
	static void getScore(int depth, int sumCalorie, int sumScore) {
		if (depth == N) {
			maxScore = Math.max(maxScore, sumScore);
		} else {
			if (sumCalorie + calories[depth] <= L)
				getScore(depth+1, sumCalorie + calories[depth], sumScore + scores[depth]);
			
			if (sumCalorie <= L)
				getScore(depth+1, sumCalorie, sumScore);
		}
	}
}
