import java.io.*;
import java.util.*;

// 중복순열 구현이 기본적인 논리. clone 없이 구현하는게 킥

public class SW_2112 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int D = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			boolean[][] map = new boolean[D][W];
			for (int r=0; r<D; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<W; c++) {
					map[r][c] = Integer.parseInt(st.nextToken()) == 1; // A == false, B == true
				}
			}
			
			/*
			mapClone = new boolean[D][W];
			for (int r=0; r<D; r++) {
				mapClone[r] = map[r].clone();
			}
			*/
			
			currInject = 0;
			minInject = Integer.MAX_VALUE;
			injected = new Boolean[D];
			dfs(D, W, K, map, 0);
			minInject = Math.min(minInject, K);
			
			answer.append("#").append(t).append(" ").append(minInject).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static Boolean[] inject = {null, false, true};
	static int minInject, currInject = 0;
	static Boolean[] injected;
	//static boolean[][] mapClone;
	static void dfs(int D, int W, int K, boolean[][] map, int depth) {	
		if (minInject <= currInject)
			return;
		
		if (depth == D) {
			boolean passed = true;
			for (int c=0; c<W; c++) {
				boolean passedCol = false;
				int countA = 0;
				int countB = 0;
				for (int r=0; r<D; r++) {
					if ((injected[r] == null && map[r][c]) || (injected[r] != null && injected[r])) {
						countA = 0;
						countB ++;
					} else {
						countA ++;
						countB = 0;
					}
					
					if (K <= countA || K <= countB) {
						passedCol = true;
						break;
					}
				}
				
				if (!passedCol) {
					passed = false;
					break;
				}
			}
			
			if (passed) {
				minInject = Math.min(minInject, currInject);
				
				for (int r=0; r<D; r++) {
					for (int c=0; c<W; c++) {
						System.out.print((map[r][c] ? "B" : "A") + " ");
					}
					System.out.println();
				}
			}
			
			return;
		}
		
		for (int i=0; i<3; i++) {
			injected[depth] = inject[i];
			if (0 < i) {
				currInject++;
				/*
				for (int c=0; c<W; c++) {
					map[depth][c] = inject[i];
				}
				*/
			}
			dfs(D, W, K, map, depth+1);
			
			if (0 < i) {
				injected[depth] = inject[0];
				currInject--;
				/*
				for (int c=0; c<W; c++) {
					map[depth][c] = mapClone[depth][c];
				}
				*/
			}
		}
	}
}














