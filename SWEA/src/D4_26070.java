import java.io.*;
import java.util.*;

// dfs, bfs도 아니고 그냥 반복문으로 해결 가능
// https://swexpertacademy.com/main/code/userProblem/userProblemDetail.do?contestProbId=AZwmBfua3q3HBIT3&categoryId=AZwmBfua3q3HBIT3&categoryType=CODE&&&

public class D4_26070 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N, M;
	static int[][] map;
	static Jewelry_D4_26070[] jewelries; // Coord_D4_26070inates of jewelries(1~M) -> (r, c)
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		final StringBuilder answer = new StringBuilder();
		for (int t=1; t<=T; t++) {
			// initialize N, map
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			
			jewelries = new Jewelry_D4_26070[11]; // M <= 10
			M = 0; // number of jewelries (named as 1 ~ M)
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					if (map[r][c] > 0) {
						M++;
						jewelries[map[r][c]] = new Jewelry_D4_26070(r, c, map[r][c]);
						//System.out.println("M="+map[r][c]+" r="+r+ " c="+c);
					}
				}
			}
			
			// sort by index (1~M)
			Arrays.sort(jewelries, 1, M+1, (j1, j2) -> j1.index - j2.index);
			
			// initialize current state
			Player_D4_26070 ply = new Player_D4_26070(0, 1, 0, 1);
			//System.out.println("-----------t="+t+"-------------");
			// count the minimum turn count
			int minTurnCount = 0;
			for (int m=1; m<=M; m++) {
				Jewelry_D4_26070 target = jewelries[m];
				double cos = ply.cos(target);
				int outer = ply.outer(target);
				

				//System.out.println("M="+target.index+" r="+target.r+ " c="+target.c);
				
				if (cos == 1) {
					// 정면에 있는 경우 -> 회전 없이 이동
					//System.out.println("[정면] += 0");
				} else if (0 <= cos) {
					if (outer < 0) {
						// 오른쪽 정면 / 완전 오른쪽에 있는 경우 -> 1번 회전 후 이동
						ply.turn90();
						minTurnCount ++;
						//System.out.println("[오른쪽 앞 or 완전 오른쪽] += 1");
					} else {
						// 왼쪽 정면에 있는 경우 -> 3번 회전
						minTurnCount += 3;
						ply.turn90inverse();
						//System.out.println("[왼쪽 정면] += 3");
					}
				} else {
					if (outer < 0) {
						// 오른쪽 뒤에 있는 경우 -> 2번 회전 후 이동
						ply.turn90();
						ply.turn90();
						minTurnCount += 2;
						//System.out.println("[오른쪽 뒤] += 2");
					} else {
						// 왼쪽 뒤에 혹은 바로 뒤에 있는 경우 -> 3번 회전 = 1번 역회전과 같음
						ply.turn90inverse();
						minTurnCount += 3;
						//System.out.println("[왼쪽 뒤 or 완전 뒤] += 3");
					}
				}
				
				// 모든 경우에 이동은 반드시 수행함
				ply.r = target.r;
				ply.c = target.c;
			}

			answer.append("#"+t+" "+minTurnCount+"\n");
		}
		
		System.out.println(answer);
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
}

class Player_D4_26070 extends Coord_D4_26070 {
	int dr, dc;
	Player_D4_26070(int r, int c, int dr, int dc) {
		super(r, c);
		this.dr = dr;
		this.dc = dc;
	}
	
	Coord_D4_26070 getPositionVector(Coord_D4_26070 target) {
		return new Coord_D4_26070(target.r - this.r, target.c - this.c);
	}
	
	double cos(Coord_D4_26070 target) {
		Coord_D4_26070 pV = getPositionVector(target);
		return (dr*pV.r + dc*pV.c)
			 / (0.0 + Math.pow(pV.r*pV.r + pV.c*pV.c, 0.5));
	}
	
	int outer(Coord_D4_26070 target) {
		Coord_D4_26070 pV = getPositionVector(target);
		return dr*pV.c - dc*pV.r;
	}
	
	void turn90() {
		// (dr, dc) -> (dc, -dr)
		int temp = dr;
		dr = dc;
		dc = -temp;
	}
	
	void turn90inverse() {
		// (dr, dc) -> (-dc, dr)
		int temp = dr;
		dr = -dc;
		dc = temp;
	}
}

class Jewelry_D4_26070 extends Coord_D4_26070 {
	int index; // 1 ~ M
	Jewelry_D4_26070(int r, int c, int index) {
		super(r, c);
		this.index = index;
	}
}

class Coord_D4_26070 {
	int r, c;
	Coord_D4_26070(int r, int c) {
		this.r = r;
		this.c = c;
	}
}













