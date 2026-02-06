import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class D4_1868 {
	static Scanner sc = new Scanner(System.in);

	static int N;
	static int[][] map = null; // map[r][c] == 0(empty), 1(border), 2(mine)

	static int count;
	static Set<Point_D4_1868> zeroPoint_D4_1868s, borderPoint_D4_1868s;
	
	public static void main(String[] args) {		
		final int T = sc.nextInt();
		for (int t=1; t<=T; t++) {
			N = sc.nextInt();
			zeroPoint_D4_1868s = new HashSet<Point_D4_1868>();
			borderPoint_D4_1868s = new HashSet<Point_D4_1868>();
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
							zeroPoint_D4_1868s.add(new Point_D4_1868(r, c));
						}
					}
				}
			}

			// at this time, count == N*N - #mine == (basement)
			int answer = 0;
			while (!zeroPoint_D4_1868s.isEmpty()) {
				Point_D4_1868 anyZeroPoint_D4_1868 = zeroPoint_D4_1868s.iterator().next();
				zeroClick(anyZeroPoint_D4_1868.r, anyZeroPoint_D4_1868.c);
				answer++;
			}
			
			// at this time, zeroPoint_D4_1868s is empty
			// answer == minimum #click for killing all zeros and their borders
			// borderPoint_D4_1868s has border Point_D4_1868s not killed by zeroClick
			
			answer += borderPoint_D4_1868s.size();

			System.out.println("#"+t+" "+answer);
		}
	}
	
	static void zeroClick(int r, int c) {
		if (!isValid(r, c) || map[r][c] != 0) return;
		
		Point_D4_1868 p = new Point_D4_1868(r, c);
		if (zeroPoint_D4_1868s.contains(p))
			zeroPoint_D4_1868s.remove(p);
		
		for (int dr=-1; dr<=1; dr++) {
			for (int dc=-1; dc<=1; dc++) {
				int tr = r+dr;
				int tc = c+dc;
				
				if (!isValid(tr, tc) || (tr == r && tc == c)) continue;
				
				Point_D4_1868 tp = new Point_D4_1868(tr, tc);
				if (map[tr][tc] == 0 && zeroPoint_D4_1868s.contains(tp)) {
					zeroClick(tr, tc); 
				} else {
					if (borderPoint_D4_1868s.contains(tp))
						borderPoint_D4_1868s.remove(tp);
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
					
					Point_D4_1868 borderPoint_D4_1868 = new Point_D4_1868(tr, tc);
					zeroPoint_D4_1868s.remove(borderPoint_D4_1868);
					borderPoint_D4_1868s.add(borderPoint_D4_1868);
				}
			}
		}
		
		map[r][c] = 2;
		borderPoint_D4_1868s.remove(new Point_D4_1868(r, c));
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r) && (r<N) && (0<=c) && (c<N);
	}
}

class Point_D4_1868 {
	int r, c;
	
	Point_D4_1868(int r, int c) {
		this.r = r;
		this.c = c;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.r, this.c);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point_D4_1868 p) {
			return (p.r == this.r) && (p.c == this.c);
		}
		
		return false;
	}
}