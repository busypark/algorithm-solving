import java.io.*;
import java.util.*;

public class G4_17144 {
	static int R, C, T;
	static int[][] map;
	static int upper = -1;
	static int lower = -1;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input R, C, T, and map
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		map = new int[R][C];
		for (int r=0; r<R; r++) {
			st = new StringTokenizer(br.readLine());
			
			for (int c=0; c<C; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if (map[r][c] == -1) {
					if (upper == -1)
						upper = r;
					else 
						lower = r;
				}
			}
		}
		
		// time goes by 
		for (int t=0; t<T; t++) {
			diffuse();
			circulate();
		}
		
		// count dust
		int count = 0;
		for (int r=0; r<R; r++) {			
			for (int c=0; c<C; c++) {	
				if (map[r][c] != -1) {
					count += map[r][c];
				}
			}
		}
		
		System.out.println(count);
	}
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static void diffuse() {
		int[][] newMap = new int[R][C];
		newMap[upper][0] = -1;
		newMap[lower][0] = -1;
		
		// diffuse
		for (int r=0; r<R; r++) {
			for (int c=0; c<C; c++) {
				if (map[r][c] != -1) {
					int count = 0;
					for (int i=0; i<4; i++) {
						final int nr = r + dr[i];
						final int nc = c + dc[i];
						
						if (valid(nr, nc) && map[nr][nc] != -1) {
							count ++;
						}
					}
					
					final int send = map[r][c] / 5;
					final int remain = map[r][c] - count*send;
					
					newMap[r][c] += remain;
					for (int i=0; i<4; i++) {
						final int nr = r + dr[i];
						final int nc = c + dc[i];
						
						if (valid(nr, nc) && map[nr][nc] != -1) {
							newMap[nr][nc] += send;
						}
					}
				}
			}
		}
		
		// copy
		for (int r=0; r<R; r++) {
			map[r] = Arrays.copyOf(newMap[r], C);
		}
	}
	
	static void circulate() {
		/* upper */
		// left-top
		for (int r=upper-1; 1<=r; r--) {
			map[r][0] = map[r-1][0];
		}
		
		// top
		for (int c=1; c<=C-1; c++) {
			map[0][c-1] = map[0][c];
		}
		
		// right-top
		for (int r=0; r<upper; r++) {
			map[r][C-1] = map[r+1][C-1];
		}
		
		// bottom
		for (int c=C-1; 2<=c; c--) {
			map[upper][c] = map[upper][c-1];
		}
		
		map[upper][1] = 0; // fresh air
		
		/* lower */
		// left-bottom
		for (int r=lower+1; r<R-1; r++) {
			map[r][0] = map[r+1][0];
		}
		
		// bottom
		for (int c=0; c<C-1; c++) {
			map[R-1][c] = map[R-1][c+1];
		}
		
		// bottom-right
		for (int r=R-1; lower+1<=r; r--) {
			map[r][C-1] = map[r-1][C-1];
		}
		
		// top
		for (int c=C-1; 2<=c; c--) {
			map[lower][c] = map[lower][c-1];
		}
		
		map[lower][1] = 0; // fresh air
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
}
