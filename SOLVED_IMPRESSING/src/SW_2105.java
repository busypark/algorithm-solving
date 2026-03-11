import java.io.*;
import java.util.*;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu
// 피드백 : 문제 이해 잘못해서 좀 헤맸음.. 아무 형태의 경로가 아니라 '사각형'이므로 오히려 내가 어렵게 이해한 것
//        그리고 실행시간도 2배정도고 메모리는 3배 이상 썼음.. 다른 코드 봐야됨
//        형순형 코드보니 dfs로 풀어서 시간/메모리 좋은데, 내 거는 이미 불가능한 루트를 또 검사하는 비효율이 있음.. 다음에는 dfs로

public class SW_2105 {
	static int N;
	static int[][] map;
	static int[] drArr = {-1, -1, 1, 1};
	static int[] dcArr = {-1, 1, -1, 1};
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input N and map[N][N]
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			int maxDessert = -1;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					final int ldMax = c;
					final int rdMax = N-1-c;
					
					if (1 <= ldMax && 1 <= rdMax) {
						for (int ld = 1; ld <= ldMax; ld++) {
							loopPair:
							for (int rd = 1; rd <= rdMax; rd++) {
								// a pair of (ld, rd)
								Set<Integer> pathIncluded = new HashSet<>();
								pathIncluded.add(map[r][c]);
								int curDessert = 1;
								
								// direction of ld
								for (int i=1; i<=ld; i++) {
									final int nr = r+i;
									final int nc = c-i;
									
									if (!valid(nr, nc)) {
										continue loopPair;
									}
									
									if (pathIncluded.contains(map[nr][nc])) {
										// overlapped -> invalid path
										continue loopPair;
									} else {
										// valid until now
										pathIncluded.add(map[nr][nc]);
										curDessert ++;
									}
								}

								// direction of rd
								for (int i=1; i<=rd; i++) {
									final int nr = r+i;
									final int nc = c+i;
									
									if (!valid(nr, nc)) {
										continue loopPair;
									}
									
									if (pathIncluded.contains(map[nr][nc])) {
										// overlapped -> invalid path
										continue loopPair;
									} else {
										// valid until now
										pathIncluded.add(map[nr][nc]);
										curDessert ++;
									}
								}
								
								// direction of ld-rd
								for (int i=1; i<=rd; i++) {
									final int nr = r+ld+i;
									final int nc = c-ld+i;
									
									if (!valid(nr, nc)) {
										continue loopPair;
									}
									
									if (pathIncluded.contains(map[nr][nc])) {
										// overlapped -> invalid path
										continue loopPair;
									} else {
										// valid until now
										pathIncluded.add(map[nr][nc]);
										curDessert ++;
									}
								}
								
								// direction of rd-ld (bottom corner already calculated!!)
								for (int i=1; i<ld; i++) {
									final int nr = r+rd+i;
									final int nc = c+rd-i;
									
									if (!valid(nr, nc)) {
										continue loopPair;
									}
									
									if (pathIncluded.contains(map[nr][nc])) {
										// overlapped -> invalid path
										continue loopPair;
									} else {
										// valid until now
										pathIncluded.add(map[nr][nc]);
										curDessert ++;
									}
								}
								
								// valid path confirmed -> update Max
								maxDessert = Math.max(maxDessert, curDessert);
							}
						}
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(maxDessert).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}







