import java.io.*;
import java.util.*;

public class G4_14502 {
	static int H, W;
	static int[][] map;
	static List<Coord> virus = new ArrayList<>();
	static List<Coord> wall = new ArrayList<>();
	static List<Coord> blank = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		map = new int[H][W];
		
		for (int r=0; r<H; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<W; c++) {
				final int block = Integer.parseInt(st.nextToken());
				map[r][c] = block;
				switch (block) {
				case 0:
					blank.add(new Coord(r, c));
					break;
				case 1:
					wall.add(new Coord(r, c));
					break;
				case 2:
					virus.add(new Coord(r, c));
					break;
				}
			}
		}
		
		// nP3
		visited = new boolean[blank.size()];
		Arrays.fill(perm, Integer.MAX_VALUE);
		curZones = 0;
		maxZones = 0;
		permutation(-1, -1);
		
		System.out.println(maxZones);
	}
	
	static boolean[] visited;
	static int[] perm = new int[3];
	static int curZones, maxZones;
	static void permutation(int depth, int idx) {
		if (depth == 2) {
			// done (nP3) -> diffuse virus
			curZones = sumSafetyArea(diffuse());
			maxZones = Math.max(maxZones, curZones);
			
			return;
		}
		
		// NOT YET
		for (int i=idx+1; i<blank.size(); i++) {
			if (!visited[i]) {
				visited[i] = true;
				perm[depth+1] = i;
				permutation(depth + 1, i);
				visited[i] = false;
			}
		}
	}
	
	static int[][] diffuse() {
		// copy map and experiment diffusion
		int[][] copyMap = new int[H][W];
		for (int r=0; r<H; r++) {
			copyMap[r] = Arrays.copyOf(map[r], W);
		}
		
		// new 3 walls
		for (int i=0; i<3; i++) {
			Coord bl = blank.get(perm[i]);
			copyMap[bl.r][bl.c] = 3;
		}
		
		// dfsDiffuse
		for (int r=0; r<H; r++) {
			for (int c=0; c<W; c++) {
				if (copyMap[r][c] == 2) {
					// virus detected -> diffusion
					dfsDiffuse(copyMap, r, c);
				}
			}
		}
		
		return copyMap;
	}
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static void dfsDiffuse(int[][] copyMap, int vr, int vc) {
		copyMap[vr][vc] = 2;
		for (int i=0; i<4; i++) {
			final int nr = vr + dr[i];
			final int nc = vc + dc[i];
			
			if (valid(nr, nc) && copyMap[nr][nc] == 0) {
				dfsDiffuse(copyMap, nr, nc);
			}
		}
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<H && 0<=c && c<W;
	}
	
	static int sumSafetyArea(int[][] m) {
		int count = 0;
		
		for (int r=0; r<H; r++) {
			for (int c=0; c<W; c++) {
				if (m[r][c] == 0)
					count ++;
			}
		}
		
		return count;
	}
}

class Coord {
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
