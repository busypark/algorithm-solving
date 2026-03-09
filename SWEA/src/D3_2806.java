import java.io.*;
import java.util.*;

// https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AZwbxqzq0vrHBIPa&contestProbId=AV7GKs06AU0DFAXB&probBoxId=AZzP9Fmaqg3HBIRj+&type=PROBLEM&problemBoxTitle=Day14_%EB%B0%B1%ED%8A%B8%EB%9E%98%ED%82%B9&problemBoxCnt=++3+

public class D3_2806 {
	static int N;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			
			count = 0;
			row = new int[N];
			Arrays.fill(row, Integer.MIN_VALUE);
			backtrack(-1);
			
			answer.append("#").append(t).append(" ").append(count).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static int count = 0;
	static int[] row;
	static void backtrack(int depth) {
		if (depth == N-1) {
			// filled every depth = 0 ~ N-1 -> count ++;
			count ++;
			
			return;
		}
		
		// NOT YET -> Look for promising option -> Selection
		// (initialize) construct candidates for row[depth+1].
		boolean[] candidates = new boolean[N];
		Arrays.fill(candidates, true);
		
		// prune
		for (int i=0; i<=depth; i++) {
			// prune same columns
			candidates[row[i]] = false;
			
			// prune diagonals
			int left = row[i] - (depth + 1 - i);
			if (valid(left)) {
				candidates[left] = false;
			}
			
			int right = row[i] + (depth + 1 - i);
			if (valid(right)) {
				candidates[right] = false;
			}
		}
		
		// selection
		for (int i=0; i<N; i++) {
			if (candidates[i]) {
				// promising -> selection
				row[depth + 1] = i;
				backtrack(depth + 1);
				row[depth + 1] = Integer.MIN_VALUE;
			}
		}
	}
	
	static boolean valid(int col) {
		return 0 <= col && col < N;
	}
}
