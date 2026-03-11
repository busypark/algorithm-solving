// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PpLlKAQ4DFAUq
// 피드백 : 문제는 잘 해석했지만 로직에서 nLocation(총 장소 수)를 증가시키는 시점을 잘못 이해해서 오래 헤맸음
//        메모리는 괜찮은데 실행시간이 약 2배
//        

import java.io.*;
import java.util.*;

public class SW_1953 {
	static int N, M, L;
	static int holeR, holeC;
	static int[][] map;
	
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	static final int[][] directions = {null, {0,1,2,3}, {0,1}, {2,3}, {0,3}, {1,3}, {1,2}, {0,2}};
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		final int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			// input N, M, R, C, L, and map
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			holeR = Integer.parseInt(st.nextToken());
			holeC = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			map = new int[N][M];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<M; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			// inspect #location with bfs
			int[][] dist = new int[N][M];
			for (int r=0; r<N; r++) {
				Arrays.fill(dist[r], Integer.MAX_VALUE);
			}
			
			//Queue<Coord> q = new LinkedList<>();
			PriorityQueue<Coord> q = new PriorityQueue<Coord>((c1, c2) -> (c1.step - c2.step));
			q.add(new Coord(holeR, holeC, 1));
			while (!q.isEmpty()) {
				final Coord here = q.poll();
				final int r = here.r;
				final int c = here.c;
				if (here.step > L) continue;
				
				dist[r][c] = Math.min(dist[r][c], here.step);
				for (int i=0; i<directions[map[r][c]].length; i++) {
					final int dirId = directions[map[r][c]][i]; // 0~3
					final int nr = r + dr[dirId];
					final int nc = c + dc[dirId];
					
					if (valid(nr, nc) && map[nr][nc] > 0 && here.step + 1 < dist[nr][nc]) {
						for (int j=0; j<directions[map[nr][nc]].length; j++) {
							final int nDirId = directions[map[nr][nc]][j];
							if (Math.abs(dirId - nDirId) == 1 && dirId + nDirId != 3) {
								q.add(new Coord(nr, nc, here.step + 1));
								break;
							}
						}
					}
				}
			}

			int nLocation = 0;
			for (int r=0; r<N; r++) {
				for (int c=0; c<M; c++) {
					if (dist[r][c] <= L)
						nLocation ++;
				}
			}
			
			answer.append("#").append(t).append(" ").append(nLocation).append("\n");
		}
		
		System.out.print(answer);
	}

	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
}

class Coord {
	int r, c, step;
	Coord(int r, int c, int step) {
		this.r = r;
		this.c = c;
		this.step = step;
	}
}


/* 신기한 코드
import java.io.*;
import java.util.*;
 
public class Solution {
    // IO Handler
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st = null;
    // types
    // constants
    static int SIZE = 50;
    // variables
    static int N, M, R, C, L;
    static int[][] mat = new int[SIZE][SIZE];
     
    static boolean inRange(int y, int x) { return 0 <= y && y < N && 0 <= x && x < M; }
     
     
    static boolean offer(Deque<int[]> q, int ny, int nx, int join) {
        if(inRange(ny, nx) && (mat[ny][nx] & join) != 0) {
            q.offerLast(new int[] {ny, nx, mat[ny][nx]});
            mat[ny][nx] = 0;
            return true;
        }
        return false;
    }
     
    static int solution() {     
        int cnt = 0;
         
        Deque<int[]> q = new ArrayDeque<>();
        offer(q, R, C, 0x0F);
        ++cnt;
         
        for(int i = 1; i < L && !q.isEmpty(); ++i) {
            for(int j = 0, size = q.size(); j < size; ++j) {
                int[] u = q.pollFirst();
                int y = u[0], x = u[1], m = u[2];
                 
                if((m & 0x01) != 0 && offer(q, y - 1, x, 0x02)) ++cnt;
                if((m & 0x02) != 0 && offer(q, y + 1, x, 0x01)) ++cnt;
                if((m & 0x04) != 0 && offer(q, y, x - 1, 0x08)) ++cnt;
                if((m & 0x08) != 0 && offer(q, y, x + 1, 0x04)) ++cnt;
            }
        }
        return cnt;
    }
     
    public static void main(String[] args) throws IOException {
        int T = readInt();
        for(int t = 1; t <= T; ++t) {
            N = readInt();
            M = readInt();
            R = readInt();
            C = readInt();
            L = readInt();
             
            for(int y = 0; y < N; ++y) {
                for(int x = 0; x < M; ++x) {
                    switch(readInt()) {
                    // 우, 좌, 하, 상
                    case 1: mat[y][x] = 0b1111; break;
                    case 2: mat[y][x] = 0b0011; break;
                    case 3: mat[y][x] = 0b1100; break;
                    case 4: mat[y][x] = 0b1001; break;
                    case 5: mat[y][x] = 0b1010; break;
                    case 6: mat[y][x] = 0b0110; break;
                    case 7: mat[y][x] = 0b0101; break;
                    default: mat[y][x] = 0b0000; break;
                    }
                }
            }
             
            sb.append('#').append(t).append(' ').append(solution()).append('\n');
        }
        System.out.println(sb);
         
    }
    static int readInt() throws IOException {
        int c, n = 0;
        while((c = System.in.read()) >= 0x30) n = (n << 3) + (n << 1) + (c & 0x0F);
        if(c == '\r') System.in.read();
        return n;
    }
}

*/