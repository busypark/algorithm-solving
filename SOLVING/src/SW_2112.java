import java.io.*;
import java.util.*;

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
			
			inject = new char[D];
			currInject = 0;
			minInject = K;
			backtrack(D, W, K, map);
			
			answer.append("#").append(t).append(" ").append(minInject).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static char[] inject;
	static int currInject, minInject;
	static void backtrack(int D, int W, int K, boolean[][] map) {
		// prune
		if (minInject <= currInject || K < currInject) return;
		
		// passed?
		boolean passed = true;
		for (int c=0; c<W; c++) {			
			int barA = 0;
			int barB = 0;
			boolean colpassed = false;
			for (int r=0; r<D; r++) {
				if (map[r][c]) { // B
					barA = 0;
					barB ++;
				} else { // A
					barB = 0;
					barA ++;
				}
				
				if (barA >= K || barB >= K) {
					colpassed = true;
					break;
				}
			}
			
			if (!colpassed) {
				passed = false;
				break;
			}
		}
		
		if (passed) {
			// update the answer
			minInject = Math.min(minInject, currInject);
		} else {
			// backtrack 0 -> A & B
			boolean[] backup_row;
			for (int r=0; r<D; r++) {
				if (inject[r] == 'B') continue;
				
				char backup_inject = inject[r];
				backup_row = Arrays.copyOf(map[r], W);
				
				if (inject[r] == 0) {
					// backtrack A
					inject[r] = 'A';
					Arrays.fill(map[r], false);
					currInject ++;
					
					backtrack(D, W, K, map);
					
					inject[r] = backup_inject;
					for (int c=0; c<W; c++) {
						map[r][c] = backup_row[c];
					}
					currInject --;
				}
				
				if (inject[r] == 0 || inject[r] == 'A') {
					// backtrack B
					inject[r] = 'B';
					Arrays.fill(map[r], true);
					currInject ++;
					
					backtrack(D, W, K, map);
					
					inject[r] = backup_inject;
					for (int c=0; c<W; c++) {
						map[r][c] = backup_row[c];
					}
					currInject --;
				}
			}
		}
	}
}














