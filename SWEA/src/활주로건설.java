import java.io.*;
import java.util.*;

public class 활주로건설 {
	static boolean canConstruct(int[] row, int X) {
		boolean[] constructed = new boolean[row.length];
		for (int i=1; i<row.length; i++) {
			int diff = row[i] - row[i-1];
			if (Math.abs(diff) >= 2) return false;
			
			if (diff == 1) {
				for (int j=1; j<=X; j++) {
					// j = constructing length
					int p = i-j;
					if (p < 0) return false; // violate left boundary
					if (row[p] != row[i-1]) return false; // violate flatness
					if (constructed[p]) return false; // violate atomicity
					
					// able to construct one part
					constructed[p] = true;
				}
			} else if (diff == -1) {
				for (int j=1; j<=X; j++) {
					// j = constructing length
					int p = i+j-1;
					if (row.length <= p) return false; // violate left boundary
					if (row[p] != row[i]) return false; // violate flatness
					if (constructed[p]) return false; // violate atomicity
					
					// able to construct one part
					constructed[p] = true;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			final int X = Integer.parseInt(st.nextToken());
			
			// input matrix
			int[][] mat = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					mat[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			int count = 0;
			// count for rows
			for (int r=0; r<N; r++) {
				count += (canConstruct(mat[r], X)) ? 1 : 0;
			}
			
			// count for cols
			for (int c=0; c<N; c++) {
				int[] row = new int[N];
				for (int r=0; r<N; r++) {
					row[r] = mat[r][c];
				}

				count += (canConstruct(row, X)) ? 1 : 0;
			}
			
			answer.append("#").append(t).append(" ").append(count).append("\n");
		}
		
		System.out.print(answer);
	}
}
