// https://www.acmicpc.net/problem/17472
// G1_17472 (A형에서는 섬 형태가 직사각형이었으나 원본은 ㄱ, ㄴ 형태도 가능)

import java.io.*;
import java.util.*;

public class SW_A_260306_2 {
	static int H, W;
	static boolean[][] map;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N(H), M(W), and map[H+2][W+2] -> padding
		st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		map = new boolean[H+2][W+2];
		for (int r=1; r<=H; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=1; c<=W; c++) {
				final int TF = Integer.parseInt(st.nextToken());
				map[r][c] = (TF == 1);
			}
		}
		
		// search for islands(nodes) and their edges
		islands = new int[H+2][W+2];
		final int nIsland = search();

		// construct bridges (initialized as MAX_VALUE while it will be updated as minimum)
		bridges = new int[nIsland+1][nIsland+1];
		for (int i=1; i<=nIsland; i++) {
			Arrays.fill(bridges[i], 1, nIsland+1, Integer.MAX_VALUE);
		}
		constructBridges();
		
		
	}
	
	static int[][] bridges;
	static void constructBridges() {
		// minimum length = 2
		int islandId = 1;
		for (int r=1; r<=H; r++) {
			for (int c=1; c<=H; c++) {
				if (islands[r][c-1] == 0 && islands[r][c] == islandId) {
					// island starting position found -> march and shoot with recursive function
					march(r, c, islandId);
					
					islandId ++;
				}
			}
		}
	}
	
	static void march(int iR, int iC, int islandId) {
		for (int i=0; i<4; i++) {
			final int nr = iR+drArr[i];
			final int nc = iC+dcArr[i];
			
			if (valid(nr, nc)) {
				if (!map[nr][nc]) {
					// reached to any border -> shoot
					final int[] shootInfo = shoot(nr, nc, drArr[i], dcArr[i], islandId);
					if (shootInfo != null) {
						// any target found
						final int targetId = shootInfo[0];
						final int dist = shootInfo[1];
						
						// update as min distance
						bridges[targetId][islandId] = Math.min(bridges[targetId][islandId], dist);
						bridges[islandId][targetId] = Math.min(bridges[islandId][targetId], dist);
					}
				} else {
					// yet interior -> march
					march(nr, nc, islandId);
				}
			}
		}
	}
	
	static int[] shoot(int iR, int iC, int dr, int dc, int islandId) {
		int[] result = new int[2]; // target id, distance
		int r = iR;
		int c = iC;
		while (valid(r, c)) {
			if (islands[r][c] == islandId) {
				// shot itself -> invalid == wall
				return null;
			}
			
			if (map[r][c]) {
				// didn't shot itself but there's an island -> another island found = target found
				result[0] = islands[r][c];
				result[1] = Math.abs(r - iR) + Math.abs(c - iC);
				
				if (result[1] >= 2) {
					return result;
				} else {
					return null;
				}
			}
			
			r += dr;
			c += dc;
		}
		
		// wall -> no target found
		return null;
	}
	
	static int[][] islands;
	static List<Island> islandEntries;
	static int search() {
		// inspect islands (how many, where are they)
		islandEntries = new ArrayList<>();
		int islandId = 1;
		for (int r=1; r<=H; r++) {
			for (int c=1; c<=H; c++) {
				if (islands[r][c] == 0 && !map[r][c-1] && map[r][c]) {
					// undiscovered island starting position found -> discover with recursive function
					discover(r, c, islandId++);
					islandEntries.add(new Island(r, c, islandId));
				}
			}
		}
		
		return islandId;
	}
	
	static final int[] drArr = {-1, -1, 1, 1};
	static final int[] dcArr = {-1, 1, -1, 1};
	static void discover(int iR, int iC, int islandId) {
		islands[iR][iC] = islandId;
		for (int i=0; i<4; i++) {
			final int nr = iR+drArr[i];
			final int nc = iC+dcArr[i];
			
			if (valid(nr, nc) && map[nr][nc] && islands[nr][nc] == 0) {
				// expand this island for adjacent area (which is not included yet)
				discover(nr, nc, islandId);
			}
		}
	}
	
	static boolean valid(int r, int c) {
		return 1<=r && r<=H && 1<=c && c<=W;
	}
}


class Island {
	int r, c, id;
	Island(int r, int c, int id) {
		this.r = r;
		this.c = c;
		this.id = id;
	}
}





