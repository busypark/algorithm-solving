import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 조합인데 dfs 안에서 0부터 시작해서 시간초과됐다가 헛발질 계속..

public class SW_4128 {
	static int N, totalSum;
	static int[][] synergies;
	
	public static void main(String[] args) throws IOException {
		StringBuilder answer = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		totalSum = 0;
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			synergies = new int[N][];
			for (int i=0; i<N; i++) {
				synergies[i] = new int[N];
				st = new StringTokenizer(br.readLine());
				for (int j=0; j<N; j++) {
					final int val = Integer.parseInt(st.nextToken());
					synergies[i][j] = val;
					totalSum += val;
				}
			}
			
			minDiff = Integer.MAX_VALUE;
			comb = new int[N/2];
			unComb = new int[N/2];
			visited = new boolean[N];
			getMinComb(0, -1);
			
			answer.append("#"+t+" "+minDiff+"\n");
		}
		
		System.out.println(answer);
	}
	
	static int minDiff;
	static int[] comb, unComb;
	static boolean[] visited;
	static void getMinComb(int depth, int currIdx) {
		if (depth == N/2) {
			int sumA = 0;
			for (int i=0; i<N/2; i++) {
				for (int j=i+1; j<N/2; j++) {
					sumA += synergies[comb[i]][comb[j]] + synergies[comb[j]][comb[i]];
				}
			}
			
			// extract unComb
			int idx = 0;
			for (int i=0; i<N; i++) {
				if (!visited[i]) {
					unComb[idx++] = i;
				}
			}
			
			int sumB = 0;
			for (int i=0; i<N/2; i++) {
				for (int j=i+1; j<N/2; j++) {
					sumB += synergies[unComb[i]][unComb[j]] + synergies[unComb[j]][unComb[i]];
				}
			}
			
			int currDiff = Math.abs(sumA - sumB);
			minDiff = Math.min(currDiff, minDiff);
		} else {
			for (int i=currIdx+1; i<N; i++) {
				if (!visited[i]) {
					visited[i] = true;
					comb[depth] = i;
					getMinComb(depth+1, i);
					visited[i] = false;
				}
			}
		}
	}
}