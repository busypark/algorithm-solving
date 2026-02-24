import java.io.*;
import java.util.*;

/*
 * 직관적으로 2차원 배열로 높이값을 저장할 수 있음
 * 최대 깊이 K는 무조건 그만큼 팔 필요가 없음. 어느 한 곳에 1만 파도 되고, 3만 파도 됨
 * 그럼 각 칸에 대해 k=0~K까지 다 파보고 다 들어가봐야 하나? pruning 안 됨?
 * 매번 각 칸을 방문할 때마다 그곳의 십자가 방향으로 '갈 수 있는지'를 계속 검사해야 함
 * 그러지 말고, 0~K에 대해 경우를 미리 뽑아놓으면 굳이 계속 검사 안 해도 되지 않나?
 * 벡터 표현이 마음에 듦. r축/c축을 기준으로 보면 down-right-up-left로 갈 수록 각도가 커짐(0-90-180-270)
 * 너무 차원이 높지 않나? 적어도 3차원 이상인데
 * 어차피 가장 높은 지점들부터 탐색하면 됨
 * 생각보다 탐색 경우의 수가 많지 않을 수 있음. 이미 깎은 등산로는 다음 길보다 무조건 높아서 길을 스스로 막는 효과가 있음
 * 단 한 곳만 깎아야 하므로, 이전에 깎은 적이 있는지 기록해야 하며,
 * 이미 깎이지 않았다면 이번 기회에 '깎은 경우'와 '안 깎은 경우' 모두 경험해야 함
 * 경우가 많지 않을 것 같으니 BFS(Queue)로 처리해보기
 * Queue에 initial points(the highest + k=1~K일 때 the highest) 추가 후 루프
 * initial points는 무조건 k=0인가? 아닐 수도 있음
 * 어느 point에 대해 굳이 0~K를 모두 고려해야 하나? 예를 들어 내가 4인데 주변이 다 1이면..
 * 이 경우 k=0~2까지는 모두 같은 거임
 * 주변 것들이 나보다 모두 크거나 같다? 길 끝
 * 진짜 k 값은 후보 뽑을 때에만 상관있고, 저장할 필요는 없이 '깎았냐 아니냐'만 고려하면 됨
 * 각 루프에서 하려니 머리가 좀 아픔.. 진짜 미리 저장해놓는게 나을듯
 * 2차원 배열 자체를 통으로 list로 만들어보자. 만들 때에 각 2차원 배열의 최대값을 따로, 미리 저장
 * maxHeight 구하는게 맞나?
 * 
 * 소요 시간 : 강의 들으면서 45분 + 
 */

public class SW_1949 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int N, K;
	static int[][] map;
	static List<int[][]> kMap;
	static List<Integer> maxHeights;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input 't'th test case
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			// initialize every counts for k=0 ~ K
			kMap = new ArrayList<>();
			maxHeights = new ArrayList<>();
			initializekMap();
			
			// 
			
			answer.append("#").append(t).append(" ").append("").append("\n");
		}
		
		System.out.println(answer);
	}
	
	static void initializekMap() {
		for (int k=0; k<=K; k++) {
			int[][] kM = new int[N][N];
			int maxHeight = 0;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					int rr, cc;
					
					// down
					rr = r+1;
					cc = c;
					if (isValid(rr, cc) && map[rr][cc] < map[r][c] - k) {
						kM[r][c]++;
					}
					
					// up
					rr = r-1;
					cc = c;
					if (isValid(rr, cc) && map[rr][cc] < map[r][c] - k) {
						kM[r][c]++;
					}
					
					// right
					rr = r;
					cc = c+1;
					if (isValid(rr, cc) && map[rr][cc] < map[r][c] - k) {
						kM[r][c]++;
					}

					// left
					rr = r;
					cc = c-1;
					if (isValid(rr, cc) && map[rr][cc] < map[r][c] - k) {
						kM[r][c]++;
					}
				}
			}
			
			kMap.add(kM);
			maxHeights.add(maxHeight);
		}
	}
	
	static boolean isValid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}




