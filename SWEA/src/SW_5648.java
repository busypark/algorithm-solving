import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.stream.Collectors;

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
				List<Double> timeLine = new ArrayList<>();
				List<Collision> collisions = new ArrayList<>();
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
							
							// 왜 여기서는 CD가 
							//System.out.println((char)('A'+A.id) + ", "+(char)('A'+B.id) + " = x("+candidate.coord.x+") y("+candidate.coord.y+")");
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
							if (A.d == 2 && ((B.d == 0 && A.x - B.x == A.y - B.y) || (B.d == 1 && A.x - B.x == B.y - A.y)) && B.x < A.x) {
								candidate = new Collision();
								candidate.coord.x = B.x;
								candidate.coord.y = A.y;
								candidate.t = A.x - B.x;
								
								//System.out.println("[error] " + candidate.t);
							}
							
							// right & (up or down)
							if (A.d == 3 && ((B.d == 0 && B.x - A.x == A.y - B.y) || (B.d == 1 && B.x - A.x == B.y - A.y)) && A.x < B.x) {
								candidate = new Collision();
								candidate.coord.x = B.x;
								candidate.coord.y = A.y;
								candidate.t = B.x - A.x;
							}
						}
						
						// (up or down) & (left or right)
						if ((A.d == 0 || A.d == 1) && (B.d == 2 || B.d == 3)) {
							// up & (left or right)
							if (A.d == 0 && ((B.d == 2 && B.y - A.y == B.x - A.x) || (B.d == 3 && B.y - A.y == A.x - B.x)) && A.y < B.y) {
								candidate = new Collision();
								candidate.coord.x = A.x;
								candidate.coord.y = B.y;
								candidate.t = B.y - A.y;
							}
								
							// down & (left or right)
							if (A.d == 1 && ((B.d == 2 && A.y - B.y == B.x - A.x) || (B.d == 3 && A.y - B.y == A.x - B.x)) && B.y < A.y) {
								candidate = new Collision();
								candidate.coord.x = A.x;
								candidate.coord.y = B.y;
								candidate.t = A.y - B.y;	
							}
						}
						
						if (candidate != null) {
							candidate.id1 = A.id;
							candidate.id2 = B.id;
							
							collisions.add(candidate); // 시간/좌표 등 어떤 것도 고려하지 않고 일단 추가
							if (!timeLine.contains(candidate.t)) { // 시간대 중복 없도록 추가
								timeLine.add(candidate.t);
							}

							//System.out.println("[new Candidate : x="+candidate.coord.x + " y="+candidate.coord.y+"]");
							//System.out.println(" id1 = "+A.id + " : id2 = "+B.id);
						}
					}
				}

				// timeLine 시간 순으로 정렬
				timeLine.sort(null);
				
				// 시간 순으로 순회 -> 동일 좌표에 해당하는 충돌만 뽑음. 이미 터진 ID는 제외하고 해당 좌표에 2개 이상의 ID가 연관되어 있어야 유효함
				int answer = 0;
				Set<Integer> passedIDs = new HashSet<>();
				for (double time : timeLine) {
					List<Collision> currentCollisions = collisions.stream()
														.filter(col -> (col.t == time)).collect(Collectors.toList());
					
					/*
					// 생각해보니 딱히 정렬할 필요가 없음. 이미 Set으로 관리하기 때문에 각 좌표는 1번씩만 방문하므로
					currentCollisions.sort(new Comparator<Collision>() {
						@Override
						public int compare(Collision c1, Collision c2) {
							return c1.hashCode() - c2.hashCode();
						}
					});
					*/
					
					Set<Point> distinctCoords = new HashSet<>();
					for (Collision col : currentCollisions) {
						if (!distinctCoords.contains(col.coord)) {
							distinctCoords.add(col.coord);
							
							// 여기 아랫줄에서 필터링이 잘 안 된 것 같다는 의심이 됨 -> 맞음을 확인했음. 왜? 무조건 equals가 true가 나오나? 그럼 x, y를 각각 비교하면?
							// equals는 안 되는데 x, y 따로 비교하면 됨. 왜???
							List<Collision> collocatedCollisions = currentCollisions.stream()
																	.filter(eachCol -> (eachCol.coord.x == col.coord.x && eachCol.coord.y == col.coord.y)).collect(Collectors.toList());
							Set<Integer> collocatedIDs = new HashSet<>();
							for (Collision collocated : collocatedCollisions) {
								//System.out.println(" collocated.coord x="+collocated.coord.x + " y="+collocated.coord.y);
								
								if (!passedIDs.contains(collocated.id1))
									collocatedIDs.add(collocated.id1);
								
								if (!passedIDs.contains(collocated.id2))
									collocatedIDs.add(collocated.id2);
							}
							
							if (2 <= collocatedIDs.size()) {
								//System.out.println(" -- t="+time+" x="+col.coord.x + " y="+col.coord.y +" --");
								for (int colID : collocatedIDs) {
									passedIDs.add(colID);
									answer += points.get(colID).K;
									
									//System.out.println(" "+(char)('A'+colID)+"(energy="+points.get(colID).K+")");
								}
							}
						}
					}
				}

				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}

class Collision {
	Point coord;
	//PointInfo p1, p2;
	double t;
	int id1, id2;
	
	Collision() {
		this.coord = new PointInfo(0, 0, 0, 0, 0);
		this.t = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Collision) {
			Collision c = (Collision) o;
			return t == c.t && coord.equals(c.coord);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(t, coord.x, coord.y);
	}
}

class PointInfo extends Point {
	int id;
	int d, K;
	
	PointInfo(int id, Point p, int d, int K) {
		this(id, p.x, p.y, d, K);
	}
	
	PointInfo(int id, double x, double y, int d, int K) {
		super(x, y);
		this.id = id;
		this.d = d;
		this.K = K;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PointInfo) {
			PointInfo p = (PointInfo) o;
			return p.id == id;
		}
		
		return false;
	}
}

class Point {
	double x, y;
	
	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return p.x == this.x && p.y == this.y;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
