// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWrDOdQqRCUDFARG
// 피드백 : visited 2차원 배열을 두 번 쓰는 게 인상적이었음.
//        실행속도/메모리 괜찮은 거 같은데, 실습하는 거 보고 비교해봐야..

import java.io.*;
import java.util.*;

public class D4_7733 {
	static int N;
	static int[][] cheeseMap;
	static Map<Integer, List<Coord>> cheeseCounter = new HashMap<>();
	static boolean[][] eaten; // true == eaten == disabled!!
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// input N and cheeseMap. count per flavor(key)
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			
			cheeseCounter.clear();
			for (int i=1; i<=100; i++) {
				cheeseCounter.put(i, new ArrayList<>());
			}
			
			cheeseMap = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					cheeseMap[r][c] = Integer.parseInt(st.nextToken());
					cheeseCounter.get(cheeseMap[r][c]).add(new Coord(r, c));
				}
			}
			
			// day goes 1 to 100
			int nArea = 1;
			eaten = new boolean[N][N];
			for (int day=1; day<=100; day++) {
				if (cheeseCounter.get(day).size() > 0) {
					// there exists any cheese with flavor == day
					// eat
					for (Coord coo : cheeseCounter.get(day)) {
						eaten[coo.r][coo.c] = true;
					}
					
					// inspect #area
					int curArea = 0;
					areaInspected = new boolean[N][N];
					for (int r=0; r<N; r++) {
						for (int c=0; c<N; c++) {
							if (!eaten[r][c] && !areaInspected[r][c]) {
								areaInspected[r][c] = true;
								inspect(r, c);
								curArea ++;
							}
						}
					}
					
					nArea = Math.max(nArea, curArea);
				}
			}
			
			answer.append("#").append(t).append(" ").append(nArea).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	static boolean[][] areaInspected;
	static void inspect(int r, int c) {
		for (int i=0; i<4; i++) {
			final int nr = r+dr[i];
			final int nc = c+dc[i];
			
			if (valid(nr, nc) && !eaten[nr][nc] && !areaInspected[nr][nc]) {
				areaInspected[nr][nc] = true;
				inspect(nr, nc);
			}
		}
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}

class Coord {
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
