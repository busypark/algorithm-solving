import java.io.*;
import java.util.*;

/*
 * 나보다 이미 낮은 인접칸에 굳이 dig를 할 이유가 있나? 높은 칸에 쓰는게 낫지
 * 낮은 칸에 써봤자 그 칸의 다음 인접칸들을 줄이는 것과 같음 (최장 경로 찾는 목표에 역행함)
 * 그리고 어느 한 칸에 쓴다면 자기보다 딱 1칸만 낮아지는 것 이상으로 쓸 이유가 있나?
 * 자기보다 딱 1칸만 낮아지게 쓰는 걸로 충분함. 그 이상 쓰면 경우를 또 줄이는 거나 마찬가지
 * 자기 몸에 부딪히는 것도 안 되므로 boolean[][] visited 필요
 * 이미 dig했는지 여부도 필요하므로 boolean dig 필요
 * 백트래킹의 매력 : 굳이 모든 걸 다 저장할 필요가 없다. 원래의 흐름이 계속 살아 있으므로 여부만 저장해도 됨
 * maxHeight에 대해 먼저 dig를 할 때에는 해당 칸의 주변보다 1이 작은 것들로 줄일 수 있는지
 * 그런데 maxHeight를 건드리면 전체의 maxHeight가 변하므로 또다시 조사해야 함..
 * 
 */

public class SW_1949 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int N, K;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// initialize N, K, map
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			int maxHeight = 0;
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					maxHeight = Math.max(maxHeight, map[r][c]);
				}
			}
			
			// initialize before backtracks
			maxDist = 0;
			
			/* initialize curR, curC and backtrack */
			// 1. k=0 for the max heights
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if (map[r][c] == maxHeight) {
						curDist = 0;
						visited = new boolean[N][N];
						digged = false;
						curR = r;
						curC = c;
						backtrack();
					}
				}
			}
			
			// 2. k>0 for the max heights
			
			
			// print maxDist
			answer.append("#").append(t).append(" ").append(maxDist).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static int maxDist, curDist;
	static boolean[][] visited;
	static boolean digged;
	static int curR, curC;
	static void backtrack() {
		// how are there many (adjacent) options?
		int nOptions = 0;
		// down
		nOptions += visit(curR+1, curC);
		// up
		nOptions += visit(curR-1, curC);
		// right
		nOptions += visit(curR, curC+1);
		// left
		nOptions += visit(curR, curC-1);
		
		// no option -> dead-end (a solution found)
		if (nOptions == 0) {
			// process the solution
			maxDist = Math.max(maxDist, curDist);
		}
	}
	
	static int visit(int visR, int visC) {
		visited[curR][curC] = true;
		
		int nOptions = 0;
		if (valid(visR, visC) && !visited[visR][visC]) {
			nOptions++;
			if (map[visR][visC] < map[curR][curC]) {
				// 바로 갈 수 있음
				curDist++;
				int tempR = curR;
				int tempC = curC;
				curR = visR;
				curC = visC;
				
				backtrack();
				
				curC = tempC;
				curR = tempR;
				curDist--;
			} else {
				// 파야 됨 -> 팔 수 있나? 그리고 K로 충분한가?
				if (!digged && (map[visR][visC] - (map[curR][curC] - 1)) <= K) {
					digged = true;
					int temp = map[visR][visC];
					map[visR][visC] = map[curR][curC] - 1;
					curDist++;
					int tempR = curR;
					int tempC = curC;
					curR = visR;
					curC = visC;
					
					backtrack();
					
					curC = tempC;
					curR = tempR;
					curDist--;
					map[visR][visC] = temp;
					digged = false;
				}
			}
		}

		visited[curR][curC] = false;
		
		return nOptions;
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}




