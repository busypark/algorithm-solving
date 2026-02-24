import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/2468
// 47분 소요 -> 1회차 정답

public class S1_2468 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N;
	static int[][] map;
	static List<Integer> heights;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		heights = new ArrayList<Integer>();
		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if (!heights.contains(map[r][c])) {
					heights.add(map[r][c]);
				}
			}
		}
		
		heights.sort(null);
		
		int maxArea = 1;
		for (int h : heights) {
			int count = 0;
			visited = new boolean[N][N];
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if (map[r][c] > h && !visited[r][c]) {
						count++;
						paint(r, c, h);
					}
				}
			}
			
			maxArea = Math.max(maxArea, count);
		}
		
		System.out.println(maxArea);
	}
	
	static boolean[][] visited;
	static void paint(int r, int c, int level) {
		visited[r][c] = true;
		
		int rr, cc;
		
		rr = r-1;
		cc = c;
		if (isValid(rr, cc) && map[rr][cc] > level && !visited[rr][cc]) {
			paint(rr, cc, level);
		}

		rr = r+1;
		cc = c;
		if (isValid(rr, cc) && map[rr][cc] > level && !visited[rr][cc]) {
			paint(rr, cc, level);
		}

		rr = r;
		cc = c-1;
		if (isValid(rr, cc) && map[rr][cc] > level && !visited[rr][cc]) {
			paint(rr, cc, level);
		}

		rr = r;
		cc = c+1;
		if (isValid(rr, cc) && map[rr][cc] > level && !visited[rr][cc]) {
			paint(rr, cc, level);
		}
	}
	
	static boolean isValid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}
