import java.io.*;
import java.util.*;

// 피드백 : 문제는 쉬웠는데 이번에도 습관때문에 틀림.. dr, dc를 생각없이 기억대로 적어서 상하좌우 말고 대각선 방향 이동(디저트 카페) 적음..
//        실행속도가 2배 정도.. 명석이 코드 보니 dfs 썼음. 하나만 발견하면 되어서 그런가?
//        하긴, '최소 경로' 구하는 문제가 아니라 그냥 연결된 길이 있는지 여부만 조사하면 되니..

public class D4_1226 {
	static final int n = 16;
	static boolean[][] wall;
	static int startR, startC, finishR, finishC;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		
		for (int t=1; t<=10; t++) {
			// input tc and walls. update start/finish locations
			final int tc = Integer.parseInt(br.readLine());
			wall = new boolean[n][n];
			for (int r=0; r<n; r++) {
				final String row = br.readLine();
				for (int c=0; c<n; c++) {
					char desc = row.charAt(c);
					wall[r][c] = (desc == '1');
					if (desc == '2') {
						startR = r;
						startC = c;
					}
					
					if (desc == '3') {
						finishR = r;
						finishC = c;
					}
				}
			}
			
			// bfs
			int reached = 0;
			final int[] dr = {-1, 1, 0, 0};
			final int[] dc = {0, 0, -1, 1};
			boolean[][] visited = new boolean[n][n];
			
			Queue<Coord> q = new LinkedList<>();
			q.add(new Coord(startR, startC));
			visited[startR][startC] = true;
			while (!q.isEmpty()) {
				Coord here = q.poll();
				if (here.r == finishR && here.c == finishC) {
					reached = 1;
					break;
				}
				
				for (int i=0; i<4; i++) {
					final int nr = here.r + dr[i];
					final int nc = here.c + dc[i];	
					
					if (valid(nr, nc) && !visited[nr][nc] && !wall[nr][nc]) {
						visited[nr][nc] = true;
						q.add(new Coord(nr, nc));
					}
				}
			}
			
			answer.append("#").append(tc).append(" ").append(reached).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<n && 0<=c && c<n;
	}
}

class Coord { 
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
