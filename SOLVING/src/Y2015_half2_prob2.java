import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Y2015_half2_prob2 {	
	public static void main(String[] args) {
		Map map = new Map();
		map.inputMap();
		
		
		
		int answer = -1; // -1 0 1 2 ... 10
		
		for (int count = 1; count <= 10; count++) {
			
		}
	}
}

//class Map {	
	public int N;
	public int M;
	public char[][] map;
	public StaticPoint origin = null;
	public DynamicPoint red = null;
	public DynamicPoint blue = null;
	public Set<Point> mapSet;
	
	void inputSize() {
		try (Scanner sc = new Scanner(System.in)) {
			this.N = sc.nextInt();
			this.M = sc.nextInt();
		}
	}
	
	void inputMap() {
		try (Scanner sc = new Scanner(System.in)) {
			mapSet = new HashSet<>();
			for (int r=0; r<N; r++) {
				String row = sc.nextLine();
				for (int c=0; c<M; c++) {
					map[r][c] = row.charAt(c); // map[r][c] == . # B R O
					if (map[r][c] == 'O')
						origin = new OriginPoint(r, c);
					if (map[r][c] == 'R');
						red = new RedPoint(r, c);
					if (map[r][c] == 'B')
						blue = new BluePoint(r, c);
					if (map[r][c] == '*')
						mapSet.add(new WallPoint(r, c));
				}
			}
		}
	}
	
	boolean cline(int dR, int dC) {
		boolean colRed = false;
		boolean colBlue = false;
		boolean exitRed = false;
		boolean exitBlue = false;
		
		while (!colRed || !colBlue) {
			if (colRed == true) {
				blue.r += dR;
				blue.c += dC;
				
				if (!isValid(blue.r, blue.c) || (!exitRed && blue.equals(red)) || mapSet.contains(blue)) {
					colBlue = true;
					blue.r -= dR;
					blue.c -= dC;
				} else if (blue.equals(origin)) {
					colBlue = true;
					exitBlue = true;
				}
			} else if (colBlue == true) {
				red.r += dR;
				red.c += dC;
				
				if (!isValid(red.r, red.c) || (!exitBlue && red.equals(blue)) || mapSet.contains(red)) {
					colRed = true;
					red.r -= dR;
					red.c -= dC;
				} else if (red.equals(origin)) {
					colRed = true;
					exitRed = true;
				}
			} else {
				blue.r += dR;
				blue.c += dC;
				red.r += dR;
				red.c += dC;
				
				if ()
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
		this.red = new Point(red.r, red.c);
		this.blue = new Point(blue.r, blue.c);
	}
	
	Status(int redR, int redC, int blueR, int blueC) {
		this.red = new Point(redR, redC);
		this.blue = new Point(blueR, blueC);
	}
}

class WallPoint extends StaticPoint {
	WallPoint(int r, int c) { super(r, c); }
}

class OriginPoint extends StaticPoint {
	OriginPoint(int r, int c) { super(r, c); }
}

class StaticPoint extends Point {
	StaticPoint(int r, int c) { super(r, c); }
}

class BluePoint extends DynamicPoint {
	BluePoint(int r, int c) { super(r, c); }
}

class RedPoint extends DynamicPoint {
	RedPoint(int r, int c) { super(r, c); }
}

class DynamicPoint extends Point {
	boolean out; // already reached exit
	DynamicPoint(int r, int c) { 
		super(r, c); 
		this.out = false;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}