import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRFInKex8DFAUo

public class SW_5648 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 1 <= N <= 1000
				
				List<PointInfo> points = new ArrayList<>();
				for (int n=0; n<N; n++) {
					final int x = sc.nextInt(); // -1000 <= x <= 1000
					final int y = sc.nextInt(); // -1000 <= y <= 1000
					final int d = sc.nextInt(); // direction (up 0, 1 down, 2 left, 3 right)
					final int K = sc.nextInt(); // 1 <= K <= 100
					
					points.add(new PointInfo(n, x, y, d, K));
				}
				
				/*
				 수직/수평의 충돌 양상이 서로 다름
				 수직이면 무조건 격자점에서 충돌, 수평이면 서로 마주볼 때에만/격자점 두 개의 중간 지점에서도 충돌 가능
				 전략 : Collision이 발생할 수 있는 좌표+시간 후보들을 도출한 후 동일한 좌표에 여러 Collision이 있으면 시간이 가장 작은 것만 택함
				 */
				
				// choose any two points. extract Collision candidates
				List<Collision> collisions = new ArrayList<Collision>();
				for (int i=0; i<N; i++) {
					for (int j=i+1; j<N; j++) {
						PointInfo A = points.get(i);
						PointInfo B = points.get(j);
						
						Collision candidate = null;
						// 주의! up/down/left/right는 '방향'이므로 위치는 반대로 봐야 함
						/* case 1 : facing -> 100% candidate */
						// up & down && same column(x) && row diff 
						if (A.d == 0 && B.d == 1 && A.x == B.x && A.y < B.y) {
							candidate = new Collision();
							candidate.coord.x = A.x;
							candidate.coord.y = (A.y + B.y) / 2.0;
							candidate.t = (B.y - A.y) / 2.0;
						}
						
						// down & up && same column(x) && row diff
						if (A.d == 1 && B.d == 0 && A.x == B.x && B.y < A.y) {
							candidate = new Collision();
							candidate.coord.x = A.x;
							candidate.coord.y = (A.y + B.y) / 2.0;
							candidate.t = (A.y - B.y) / 2.0;
						}
						
						// right & left && same row(y) && column diff
						if (A.d == 3 && B.d == 2 && A.y == B.y && A.x < B.x) {
							candidate = new Collision();
							candidate.coord.x = (A.x + B.x) / 2.0; 
							candidate.coord.y = A.y;
							candidate.t = (B.x - A.x) / 2.0;
						}
						
						// left & right && same row(y) && column diff
						if (A.d == 2 && B.d == 3 && A.y == B.y && B.x < A.x) {
							candidate = new Collision();
							candidate.coord.x = (A.x + B.x) / 2.0; 
							candidate.coord.y = A.y;
							candidate.t = (A.x - B.x) / 2.0;
						}
						
						/* case 2 : orthogonal NOT 100% candidate */
						// (left or right) & (up or down)
						if ((A.d == 2 || A.d == 3) && (B.d == 0 || B.d == 1)) {
							// left & (up or down)
							if (A.d == 2 && ((B.d == 0 && A.x - B.x == A.y - B.y) || (B.d == 1 && A.x - B.x == B.y - A.y))) {
								candidate = new Collision();
								candidate.coord.x = B.x;
								candidate.coord.y = A.y;
								candidate.t = A.x - B.x;
							}
							
							// right & (up or down)
							if (A.d == 3 && ((B.d == 0 && B.x - A.x == A.y - B.y) || (B.d == 1 && B.x - A.x == B.y - A.y))) {
								candidate = new Collision();
								candidate.coord.x = B.x;
								candidate.coord.y = A.y;
								candidate.t = B.x - A.x;
							}
						}
						
						// (up or down) & (left or right)
						if ((A.d == 0 || A.d == 1) && (B.d == 2 || B.d == 3)) {
							// up & (left or right)
							if (A.d == 0 && ((B.d == 2 && B.y - A.y == B.x - A.x) || (B.d == 3 && B.y - A.y == A.x - B.x))) {
								candidate = new Collision();
								candidate.coord.x = A.x;
								candidate.coord.y = B.y;
								candidate.t = B.y - A.y;
							}
								
							// down & (left or right)
							if (A.d == 1 && ((B.d == 2 && A.y - B.y == B.x - A.x) || (B.d == 3 && A.y - B.y == A.x - B.x))) {
								candidate = new Collision();
								candidate.coord.x = A.x;
								candidate.coord.y = B.y;
								candidate.t = A.y - B.y;	
							}
						}
						
						// 세 개의 점이 모여도 처리가 되나? 셋 다 터져야 함
						// 여기서는 어쨌든 collision을 두 개의 점으로 정의하고 있음. 여러 개의 점이 한 점으로 모이는 걸 어떻게 봐야 할까?
						// collision마다 total energy를 미리 계산할 필요가 없음. 그냥 각 정보들을 들고 있으면 됨 Point -> PointInfo
						// t에 따라 시간 순으로 정렬. 같은 t인 collision들은 서로 PointInfo가 distinct하도록 제거하면 됨
						if (candidate != null) {
							candidate.p1 = A;
							candidate.p2 = B;
							collisions.add(candidate);

							//System.out.println("[new Candidate : x="+candidate.coord.x + " y="+candidate.coord.y+"]");
							//System.out.println(" id1 = "+A.id + " : id2 = "+B.id);
						}
					}
				}
				
				// t는 double이지만 소숫점 아래가 0.0 혹은 0.5인 경우 뿐이니 2를 곱하면 유일성 보존
				// 뒤에서 어차피 key 정렬할 거니 미리 할 필요 없음
				// collisions.sort((c1, c2) -> (int)(c1.t * 2 - c2.t * 2));

				// 시간(t)별 collision 리스트 구성
				Map<Double, List<Collision>> colTimes = new HashMap<>();
				for (int i=0; i<collisions.size(); i++) {
					Collision thisCol = collisions.get(i);
					double thisT = thisCol.t;
					if (colTimes.containsKey(thisT)) {
						List<Collision> thisList = colTimes.get(thisT);
						if (thisList == null) {
							thisList = new ArrayList<Collision>();
							colTimes.put(thisT, thisList);
						}
						
						thisList.add(thisCol);
					} else {
						List<Collision> thisList = new ArrayList<Collision>();
						colTimes.put(thisT, thisList);
						thisList.add(thisCol);
					}
				}
				
				// 고려하지 못한 점! 만약 한 점이 다른 두 점과 모두 collision을 일으킨다고 본다면? 먼저 충돌한 걸 제거해야 함
				// 예를 들어 1개의 수평 점과 2개의 수직 점이 각각 collision 일으킨다고 판정되었다 (다른 시점에)
				// 그럼 수평 점과 먼저 충돌하는 수직 점은 각각 passedID에 들어가지만 나중에 충돌하는 수직 점은 이전에 그것의 쌍(수평 점)이 이미 없어졌으므로 collision 자체가 성립이 안 됨
				
				// 시간(t)별 collision 리스트에서 distinct points를 이용해 total energy(answer) 계산
				int answer = 0;
				Set<Integer> passedIDs = new HashSet<>();
				List<Double> sortedKeys = new ArrayList<>(colTimes.keySet());
				sortedKeys.sort((d1, d2) -> (int)(d1*2 - d2*2));
				
				for (Double key : sortedKeys) {
					List<Collision> colList = colTimes.get(key);
					//System.out.println("\n# t="+key);
					Set<Integer> distinctIDs = new HashSet<>();
					for (Collision col : colList) {
						// 단순히 collision 자체에 포함되었는지 여부가 아니라, 상대방이 포함되었는지도 봐야 함
						// 그러니까 and 조건이어야 함 -> 이거 챙기니까 테스트 케이스 만족 증가
						// 근데 이러면 홀수개의 동시 충돌이 여전히 안 챙겨짐. 하나가 남아버림
						// 그러니까 같은 시간 내의 처리와 다른 시간들 사이의 처리가 논리가 좀 다름
						// 같은 시간 내에서는 distinct id들만 고르면 되는데 다른 시간으로 따지면 짝 중에 하나라도 passed에 포함되어 있어선 안 됨(collision 성립X)
						// 둘 중에 어떤 조건이 더 먼저? 일단 외부 조건이 먼저 -> 그 다음 distinct 따져야 함. 애초에 collision 성립 안 되면 안 되니까
						// 3개가 충돌하는 경우에 두 개가 이미 들어가 있으므로 하나가 이 조건에 포함이 안 됨.. distinct id 먼저 해야 함
						// 이렇게 하니까 오히려 테스트 케이스 만족 감소. 왜?
						
						final int id1 = col.p1.id;
						final int id2 = col.p2.id;
						if (!distinctIDs.contains(id1) || !distinctIDs.contains(id2)) {
							if (!distinctIDs.contains(id1) && !passedIDs.contains(id1)) {
								answer += col.p1.K;
								passedIDs.add(id1);
								

								//System.out.println("(id1) id1="+id1+" id2="+id2);
							}
							
							if (!distinctIDs.contains(id2) && !passedIDs.contains(id2)) {
								answer += col.p2.K;
								passedIDs.add(id2);
								

								//System.out.println("(id2) id1="+id1+" id2="+id2);
							}
								
							distinctIDs.add(id1);
							distinctIDs.add(id2);
						}
						
						
						/*
						if (!passedIDs.contains(col.p1.id) && !passedIDs.contains(col.p2.id)) {
							// 일단 collision 성립하므로, distinct id에 대해 에너지만 누적하면 됨
							if (!distinctIDs.contains(col.p1.id)) {
								answer += col.p1.K;
								distinctIDs.add(col.p1.id);
							}
							
							if (!distinctIDs.contains(col.p2.id)) {
								answer += col.p2.K;
								distinctIDs.add(col.p2.id);
							}
							
							passedIDs.add(col.p1.id);
							passedIDs.add(col.p2.id);
						}
						*/
						
						/*
						if (!passedIDs.contains(col.p1.id) && !passedIDs.contains(col.p2.id)) {
							if (!passedIDs.contains(col.p1.id)) {
								answer += col.p1.K;
								passedIDs.add(col.p1.id);
							}
							
							if (!passedIDs.contains(col.p2.id)) {
								answer += col.p2.K;
								passedIDs.add(col.p2.id);
							}
						}
						*/
					}
				}
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}

class Collision {
	Point coord;
	PointInfo p1, p2;
	double t;
	
	Collision() {
		this.coord = new PointInfo(0, 0, 0, 0, 0);
		this.t = 0;
	}
}

class PointInfo extends Point {
	int id;
	int d, K;
	PointInfo(int id, double x, double y, int d, int K) {
		super(x, y);
		this.id = id;
		this.d = d;
		this.K = K;
	}
}

class Point {
	double x, y;
	
	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	boolean isEqual(Point p) {
		return p.x == this.x && p.y == this.y;
	}
	*/
}