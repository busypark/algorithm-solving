import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1018

public class S3_1018 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// initialize map
		map = new int[N][M];
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			String row = st.nextToken();
			for (int j=0; j<M; j++) {
				char color = row.charAt(j); // 'B' or 'W'
				map[i][j] = (color == 'B')? 0 : 1;
			}
		}
		
		// search for the minimum
		int minCount = Integer.MAX_VALUE;
		for (int r=0; r<N-8+1; r++) {
			for (int c=0; c<M-8+1; c++) {
				// (r, c) : left-top of the window
				int currCount0 = 0; // left-top : 0
				int currCount1 = 0; // left-top : 1
				for (int wr=0; wr<8; wr++) {
					for (int wc=0; wc<8; wc++) {
						if (map[r+wr][c+wc] == (wr+wc) % 2) {
							currCount1++;
						} else {
							currCount0++;
						}
					}
				}
				minCount = Math.min(minCount, Math.min(currCount0, currCount1));
			}
		}
		
		System.out.println(minCount);
	}
}
