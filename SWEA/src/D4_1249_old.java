import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

/*
 Gemini와의 대화를 통해 맹점을 찾았음
 문제에서 분명 '경로'에 집착할 필요 없다고 했는데, 나는 계속 시작점->도착점으로 닿을 수 있는 경로에만 집착했음
 오히려 도착점이 아니라 각 지점까지 도달하기 위한 최소 비용을 계산하고 저장해놓으면 그것은 불변한다는 걸 이용하는 게 나았을 것
 S에서 오른쪽 아래 대각선 방향으로 벽을 밀어붙이면서 앞에 올 벽의 각 칸에 대한 최소비용을 현재 벽의 칸으로부터의 최소 거리로 정의
 이렇게 하면 기껏해야 1~2개 후보 중 최소값을 현재 비용에 더하는 식으로만 해도 됨
 특히 알고리즘 문제들은 각 경우가 서로 완전 독립인 경우는 없는 듯.
 내가 했던 방식은 하나의 경로를 파낸 결과물이 다른 경로를 파낼 때 도움이 되질 않음. 오직 각자의 세션에서 각자가 최선을 찾아감
 그리고 왜 하필 오른쪽 위 대각선 방향으로 생긴 벽인가?
 그것은 S->G로의 방향에 수직이면서 동시에 map이 정사각형이기에 행/열로 그 대각선을 표현하기가 알맞기 때문
 약간 더 기울어진 대각선을 쓰면 두 가지 문제가 발생
  1) 각 대각선을 모두 합해도 중간에 빈 칸이 생겨 최소비용이 표현되지 않는 지점이 생김
  2) 행/열로 대각선을 표현할 수는 있으나 딱 맞아떨어지진 않음
  
 수정 : dijkstra 알고리즘으로 쉽게 풀린다고 해서 배움
 dijkstra 알고리즘 : 미방문 점 중 누적비용이 가장 적은 것을 택하여 그것의 인접 미방문 점들의 누적비용을 갱신하는 방식
 */
public class D4_1249_old {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt();
				final int[][] map = new int[N][N];
				sc.nextLine();
				for (int r=0; r<N; r++) {
					final String row = sc.nextLine();
					for (int c=0; c<N; c++) {
						map[r][c] = row.charAt(c) - '0';
					}
				}
				
				final int[][] costMap = new int[N][N];
				final List<Pos_1249> unvisited = new LinkedList<>();
				for (int r=0; r<N; r++) {
					for (int c=0; c<N; c++) {
						Pos_1249 currentPos_1249 = new Pos_1249(r, c);
						if (0 < r || 0 < c) {
							costMap[r][c] = Integer.MAX_VALUE;
						} else {
							currentPos_1249.cumulatedCost = 0;
						}
						
						unvisited.add(currentPos_1249);
					}
				}
				
				// dijkstra
				int answer = 0;
				while (0 < unvisited.size()) {
					// 미방문 노드 중 cumulatedCost가 가장 낮은 것을 thisTurn으로 고르고, 미방문 리스트에서는 지움
					unvisited.sort((p1, p2) -> Integer.compare(p1.cumulatedCost, p2.cumulatedCost));
					Pos_1249 thisTurn = unvisited.get(0);
					unvisited.remove(0);
					
					// 지금 방문할 노드(thisTurn)가 곧 최종 노드라면 그냥 종료
					if (thisTurn.r == N-1 && thisTurn.c == N-1) {
						answer = thisTurn.cumulatedCost;
						break;
					}
					
					// 해당 노드(thisTurn) 사방에 미방문 노드를 조사하여 그곳의 cumulatedCost 갱신
					int r, c, idx;
					
					// up
					r = thisTurn.r - 1;
					c = thisTurn.c;
					if (isValid(r, c, N)) {
						idx = unvisited.indexOf(new Pos_1249(r, c));
						if (idx != -1) {
							Pos_1249 next = unvisited.get(idx);
							next.cumulatedCost = Math.min(next.cumulatedCost, thisTurn.cumulatedCost + map[next.r][next.c]);
						}
					}
					
					// down
					r = thisTurn.r + 1;
					c = thisTurn.c;
					if (isValid(r, c, N)) {
						idx = unvisited.indexOf(new Pos_1249(r, c));
						if (idx != -1) {
							Pos_1249 next = unvisited.get(idx);
							next.cumulatedCost = Math.min(next.cumulatedCost, thisTurn.cumulatedCost + map[next.r][next.c]);
						}
					}
					
					// left
					r = thisTurn.r;
					c = thisTurn.c - 1;
					if (isValid(r, c, N)) {
						idx = unvisited.indexOf(new Pos_1249(r, c));
						if (idx != -1) {
							Pos_1249 next = unvisited.get(idx);
							next.cumulatedCost = Math.min(next.cumulatedCost, thisTurn.cumulatedCost + map[next.r][next.c]);
						}
					}

					// right
					r = thisTurn.r;
					c = thisTurn.c + 1;
					if (isValid(r, c, N)) {
						idx = unvisited.indexOf(new Pos_1249(r, c));
						if (idx != -1) {
							Pos_1249 next = unvisited.get(idx);
							next.cumulatedCost = Math.min(next.cumulatedCost, thisTurn.cumulatedCost + map[next.r][next.c]);
						}
					}
				}
				
				//printMap(N, map, costMap);
				System.out.println("#"+t+" "+answer);
			}
		}
	}
	
	public static boolean isValid(int r, int c, int N) {
		return (0<=r && r<N && 0<=c && c<N);
	}
	
	public static void printMap(int N, int[][] map, int[][] costMap) {
		System.out.println("-------------------------------");
		for (int r=0; r<N; r++) {
			System.out.printf(" ## row[%2d] ", r);
			for (int c=0; c<N; c++) {
				System.out.printf("%4d(%4d)", costMap[r][c], map[r][c]);
			}
			System.out.println();
		}
	}
}

class Pos_1249 {
	int r, c, cumulatedCost;
	Pos_1249(int r, int c) {
		this.r = r;
		this.c = c;
		this.cumulatedCost = Integer.MAX_VALUE;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Pos_1249) {
			Pos_1249 p = (Pos_1249) o;
			return p.r == this.r && p.c == this.c;
		}
		return false;
	}
}
