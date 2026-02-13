import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1018

public class S3_1018 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static int[][] map;
	static int[] maskers = new int[2];
	
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		for (int i=0; i<M/2; i++) {
			maskers[0] += 1*(int)Math.pow(2, i*2);
			maskers[1] += 1*(int)Math.pow(2, i*2+1);
		}
		
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
		
		minCount = Integer.MAX_VALUE;
		mapSearch();
	}
	
	static int minCount;
	static void mapSearch() {
		
	}
	
	static boolean completeMap() {
		int s;
		if (map[0][0] == 0) { // 'B'로 시작
			s = 0;
		} else { // 'W'로 시작
			s = 1;
		}
		
		for (int r=0; r<N; r++) {
			for (int c=0; c<M; c++) {
				if ((maskers[s] & (1 << c)) > 0) {
					
				}
			}
			
			s = 1 - s;
		}
	
		return true;
	}
}
