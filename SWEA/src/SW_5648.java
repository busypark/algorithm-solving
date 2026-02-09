import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRFInKex8DFAUo

public class SW_5648 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 1 <= N <= 1000
				
				List<Atom> atoms = new ArrayList<>();
				for (int n=0; n<N; n++) {
					final int x = sc.nextInt(); // -1000 <= x <= 1000
					final int y = sc.nextInt(); // -1000 <= y <= 1000
					final int d = sc.nextInt(); // direction (up 0, 1 down, 2 left, 3 right)
					final int K = sc.nextInt(); // 1 <= K <= 100
					
					atoms.add(new Atom(n, x, y, d, K));
				}
				
				/*
				 수직/수평의 충돌 양상이 서로 다름
				 수직이면 무조건 격자점에서 충돌, 수평이면 서로 마주볼 때에만/격자점 두 개의 중간 지점에서도 충돌 가능
				 전략 : Collision이 발생할 수 있는 좌표+시간 후보들을 도출한 후 동일한 좌표에 여러 Collision이 있으면 시간이 가장 작은 것만 택함
				 */
				
				double[] timeLine = new double[N];
				for (int i=0; i<N; i++) {
					for (int j=i+1; j<N; j++) {
						Atom A = atoms.get(i);
						Atom B = atoms.get(j);
						
						double time = 0D;
						
						// 주의! up/down/left/right는 '방향'이므로 위치는 반대로 봐야 함
						/* case 1 : facing -> 100% candidate */
						// up & down && same column(x) && row diff 
						if (A.d == 0 && B.d == 1 && A.x == B.x && A.y < B.y) {
							time = (B.y - A.y) / 2.0;
						}
						
						// down & up && same column(x) && row diff
						if (A.d == 1 && B.d == 0 && A.x == B.x && B.y < A.y) {
							time = (A.y - B.y) / 2.0;
						}
						
						// right & left && same row(y) && column diff
						if (A.d == 3 && B.d == 2 && A.y == B.y && A.x < B.x) {
							time = (B.x - A.x) / 2.0;
						}
						
						// left & right && same row(y) && column diff
						if (A.d == 2 && B.d == 3 && A.y == B.y && B.x < A.x) {
							time = (A.x - B.x) / 2.0;
						}
						
						/* case 2 : orthogonal NOT 100% candidate */
						// (left or right) & (up or down)
						if ((A.d == 2 || A.d == 3) && (B.d == 0 || B.d == 1)) {
							// left & (up or down)
							if (A.d == 2 && ((B.d == 0 && A.x - B.x == A.y - B.y) || (B.d == 1 && A.x - B.x == B.y - A.y))) {
								time = A.x - B.x;
							}
							
							// right & (up or down)
							if (A.d == 3 && ((B.d == 0 && B.x - A.x == A.y - B.y) || (B.d == 1 && B.x - A.x == B.y - A.y))) {
								time = B.x - A.x;
							}
						}
						
						// (up or down) & (left or right)
						if ((A.d == 0 || A.d == 1) && (B.d == 2 || B.d == 3)) {
							// up & (left or right)
							if (A.d == 0 && ((B.d == 2 && B.y - A.y == B.x - A.x) || (B.d == 3 && B.y - A.y == A.x - B.x))) {
								time = B.y - A.y;
							}
								
							// down & (left or right)
							if (A.d == 1 && ((B.d == 2 && A.y - B.y == B.x - A.x) || (B.d == 3 && A.y - B.y == A.x - B.x))) {
								time = A.y - B.y;	
							}
						}
						
						if (time != 0D) {
							if (time < timeLine[i]) {
								
							}
						}
					}
				}
				
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
}

class Atom extends Point {
	int id;
	int d, K;
	
	Atom(int id, double x, double y, int d, int K) {
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
		return (int)(x * 31 + y);
	}
}