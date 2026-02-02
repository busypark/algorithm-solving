import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class D4_1868 {
	static Scanner sc = new Scanner(System.in);

	static int N;
	static int[][] map = null; // map[r][c] == 0(empty), 1(border), 2(mine)

	static int count;
	static Set<Point> zeroPoints, borderPoints;
	
	public static void main(String[] args) {		
		final int T = sc.nextInt();
		for (int t=1; t<=T; t++) {
			N = sc.nextInt();
			zeroPoints = new HashSet<Point>();
			borderPoints = new HashSet<Point>();
			count = N*N;

			sc.nextLine();
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				String rowInput = sc.nextLine();
				for (int c=0; c<N; c++) {
					if (rowInput.charAt(c) == '*') {
						bomb(r, c);
						count--;
					} else {
						if (map[r][c] == 0) {
							zeroPoints.add(new Point(r, c));
						}
					}
				}
			}

			// at this time, count == N*N - #mine == (basement)
			int answer = 0;
			while (!zeroPoints.isEmpty()) {
				Point anyZeroPoint = zeroPoints.iterator().next();
				zeroClick(anyZeroPoint.r, anyZeroPoint.c);
				answer++;
			}
			
			// at this time, zeroPoints is empty
			// answer == minimum #click for killing all zeros and their borders
			// borderPoints has border points not killed by zeroClick
			
			answer += borderPoints.size();

			System.out.println("#"+t+" "+answer);
		}
	}
	
	static void zeroClick(int r, int c) {
		if (!isValid(r, c) || map[r][c] != 0) return;
		
		Point p = new Point(r, c);
		if (zeroPoints.contains(p))
			zeroPoints.remove(p);
		
		for (int dr=-1; dr<=1; dr++) {
			for (int dc=-1; dc<=1; dc++) {
				int tr = r+dr;
				int tc = c+dc;
				
				if (!isValid(tr, tc) || (tr == r && tc == c)) continue;
				
				Point tp = new Point(tr, tc);
				if (map[tr][tc] == 0 && zeroPoints.contains(tp)) {
					zeroClick(tr, tc); 
				} else {
					if (borderPoints.contains(tp))
						borderPoints.remove(tp);
				}
			}
		}
	}
	
	static void bomb(int r, int c) {
		for (int dr=-1; dr<=1; dr++) {
			for (int dc=-1; dc<=1; dc++) {
				int tr = r+dr;
				int tc = c+dc;
				
				if (isValid(tr, tc) && map[tr][tc] != 2) {
					map[tr][tc] = 1;
					
					Point borderPoint = new Point(tr, tc);
					zeroPoints.remove(borderPoint);
					borderPoints.add(borderPoint);
				}
			}
		}
		
		map[r][c] = 2;
		borderPoints.remove(new Point(r, c));
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r) && (r<N) && (0<=c) && (c<N);
	}
}

class Point {
	int r, c;
	
	Point(int r, int c) {
		this.r = r;
		this.c = c;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.r, this.c);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point p) {
			return (p.r == this.r) && (p.c == this.c);
		}
		
		return false;
	}
}