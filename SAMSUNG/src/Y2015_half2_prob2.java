import java.util.Scanner;

public class Y2015_half2_prob2 {	
	public static void main(String[] args) {
		Map map = new Map();
		map.inputMap();
		
		
		
		int answer = -1; // -1 0 1 2 ... 10
		
		for (int count = 1; count <= 10; count++) {
			
		}
	}
}

class Map {	
	public int N;
	public int M;
	public char[][] map;
	public Point origin = null;
	public Point red = null;
	public Point blue = null;
	
	void inputSize() {
		try (Scanner sc = new Scanner(System.in)) {
			this.N = sc.nextInt();
			this.M = sc.nextInt();
		}
	}
	
	void inputMap() {
		try (Scanner sc = new Scanner(System.in)) {
			for (int r=0; r<N; r++) {
				String row = sc.nextLine();
				for (int c=0; c<M; c++) {
					map[r][c] = row.charAt(c); // map[r][c] == . # B R O
					if (map[r][c] == 'O')
						origin = new Point(r, c);
					if (map[r][c] == 'R');
						red = new Point(r, c);
					if (map[r][c] == 'B')
						blue = new Point(r, c);
				}
			}
		}
	}
	
	void cline(int dR, int dC) {
		if (dC == 0) { // up or down
			if (red.c == blue.c) { // vertically same line
				if (dR == 1) { // down(+)
					if (red.r < blue.r) { // blue first
						do { // peek next point
							++blue.r; // execute at least 1 time == the point was at valid('.') point
						} while (isValid(blue.r, blue.c) && map[blue.r][blue.c] != 'O' && map[blue.r][blue.c] != '#');
						
						int next = blue.r--; // move point back and leave next to check the origin
						if (isValid(next, blue.c) && map[next][blue.c] == 'O') {
							blue.r = next; // point = next only if next is origin
						}
					} else { // red first
						do { // peek next point
							++red.r; // execute at least 1 time == the point was at valid('.') point
						} while (isValid(red.r, red.c) && map[red.r][red.c] != 'O' && map[red.r][red.c] != '#');
						
						int next = red.r--; // move point back and leave next to check the origin
						if (isValid(next, red.c) && map[next][red.c] == 'O') {
							red.r = next; // point = next only if next is origin
						}
					}
				} else { // up(-)
					if (red.r < blue.r) { // red first
						do { // peek next point
							--red.r; // execute at least 1 time == the point was at valid('.') point
						} while (isValid(red.r, red.c) && map[red.r][red.c] != 'O' && map[red.r][red.c] != '#');
						
						int next = red.r++; // move point back and leave next to check the origin
						if (isValid(next, red.c) && map[next][red.c] == 'O') {
							red.r = next; // point = next only if next is origin
						}
					} else { // blue first
						do { // peek next point
							--blue.r; // execute at least 1 time == the point was at valid('.') point
						} while (isValid(blue.r, blue.c) && map[blue.r][blue.c] != 'O' && map[blue.r][blue.c] != '#');
						
						int next = blue.r++; // move point back and leave next to check the origin
						if (isValid(next, blue.c) && map[next][blue.c] == 'O') {
							blue.r = next; // point = next only if next is origin
						}
					}
				}
			} else { // not on the same line
				
			}
		} else { // left or right
			if (red.r == blue.c) { // horizontally same line
				
			} else { // not on the same line
				
			}
		}
	}
	
	boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<M);
	}
}

class Status {
	Point red, blue;
	
	Status(Point red, Point blue) {
		this.red = red;
		this.blue = blue;
	}
	
	Status(int redR, int redC, int blueR, int blueC) {
		this.red = new Point(redR, redC);
		this.blue = new Point(blueR, blueC);
	}
}

class Point {
	int r, c;
	Point() {
		this.r = -1;
		this.c = -1;
	}
	
	Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point given = (Point) obj;
			return (given.r == this.r && given.c == this.c);
		}
		
		return false;
	}
}