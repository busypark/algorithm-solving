// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRF8s6ezEDFAUo&
// 런타임 에러 뜸.. 테스트 케이스 다 맞는데

import java.io.*;
import java.util.*;

public class SW_5650 {
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// input N and map
			final int N = Integer.parseInt(br.readLine());
			int[][] map = new int[N][N];
			
			HashMap<Integer, Coord[]> wormHoles = new HashMap<>(); 
			for (int i=6; i<=10; i++) {
				wormHoles.put(i, new Coord[2]);
			}
			
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					
					// input worm hole pairs
					if (6 <= map[r][c] && map[r][c] <= 10) {
						if (wormHoles.get(map[r][c])[0] == null) {
							wormHoles.get(map[r][c])[0] = new Coord(r, c);
						} else {
							wormHoles.get(map[r][c])[1] = new Coord(r, c);
						}
					}
				}
			}
			
			int maxScore = 0;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if (map[r][c] != 0) continue;
					
					// (r, c) : starting location for this iteration
					maxScore = Math.max(maxScore, game(N, map, wormHoles, r, c, -1, 0));
					maxScore = Math.max(maxScore, game(N, map, wormHoles, r, c, 1, 0));
					maxScore = Math.max(maxScore, game(N, map, wormHoles, r, c, 0, -1));
					maxScore = Math.max(maxScore, game(N, map, wormHoles, r, c, 0, 1));
				}
			}
			
			answer.append("#").append(t).append(" ").append(maxScore).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int game(int N, int[][] map, HashMap<Integer, Coord[]> wormHoles, int rStart, int cStart, int dr, int dc) {
		int score = 0;
		int r = rStart;
		int c = cStart;
		boolean initialized = false;
		
		for (;;) {
			if (0<=r && r<N && 0<=c && c<N && map[r][c] == -1) break;
				
			// move
			r += dr;
			c += dc;
			
			if (!initialized) {
				map[rStart][cStart] = -1;
				initialized = true;
			}
			
			if (r<0 || N<=r || c<0 || N<=c) {
				// wall
				score ++;
				
				// reverse direction
				dr *= -1;
				dc *= -1;
			} else if (map[r][c] == 5) {
				// square block (same as above)
				score ++;
				
				// reverse direction
				dr *= -1;
				dc *= -1;
			} else if (1 <= map[r][c] && map[r][c] <= 4) {
				// diagonal block
				int[] v = {dr, dc, 0};
				int[] norm = dirToNormal(map[r][c]);
				
				score ++;
				if (isBackCollision(v, norm)) {
					// same as wall
					// reverse direction
					dr *= -1;
					dc *= -1;
				} else {
					// diagonally collided
					int[] resultant = outerProduct(outerProduct(v, norm), v);
					dr = resultant[0];
					dc = resultant[1];
				}
			} else if (6 <= map[r][c] && map[r][c] <= 10) {
				// worm hole
				Coord wH = wormHoles.get(map[r][c])[0];
				if (wH.r == r && wH.c == c) {
					wH = wormHoles.get(map[r][c])[1];
				}
				
				r = wH.r;
				c = wH.c;
			}
		}
		
		map[rStart][cStart] = 0;
		
		return score;
	}
	
	static int[] dirToNormal(int dir) {
		double angle = Math.toRadians(90*dir - 45);
		
		int[] result = new int[3];
		result[0] = (int)(Math.round(-Math.cos(angle)));
		result[1] = (int)(Math.round(Math.sin(angle)));
		result[2] = 0;
		
		return result;
	}
	
	static boolean isBackCollision(int[] v, int[] n) {
		return (v[0]*n[0] + v[1]*n[1]) > 0;
	}
	
	static int[] outerProduct(int[] a, int[] b) {
		int[] resultant = {a[1]*b[2]-b[1]*a[2], a[2]*b[0]-a[0]*b[2], a[0]*b[1]-a[1]*b[0]};
		return resultant;
	}
}

class Coord {
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
