import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

// https://www.acmicpc.net/problem/21610

public class G5_21610 {
	static int N;
	static int[][] mapWater;
	static Set<Vector2D> clouds;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			final int M = sc.nextInt();
			
			mapWater = new int[N][N];
			cloud = new HashSet<>();
			
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					mapWater[r][c] = sc.nextInt();
				}
			}
			
			// initialize cloud on the left bottom 2x2
			clouds.add(new Vector2D(N-1, 0));
			clouds.add(new Vector2D(N-1, 1));
			clouds.add(new Vector2D(N-2, 0));
			clouds.add(new Vector2D(N-2, 1));
			
			// process M commands
			for (int m=0; m<M; m++) {
				final int direction = sc.nextInt();
				final int distance = sc.nextInt();
				
				command(direction, distance);
			}
			
			final int answer = sumWater();
			
			System.out.println(answer);
		}
	}
	
	// process a single command
	static void command(int direction, int distance) {
		Vector2D inc = new Vector2D();
		inc.setByCommand(direction, distance);
		
		// Move
		Iterator<Vector2D> iterCloud = clouds.iterator();
		while (iterCloud.hasNext()) {
			Vector2D thisCloud = iterCloud.next();
			thisCloud.add(inc.r, inc.c);
			thisCloud.r %= N; // 경계선 처리
			thisCloud.c %= N; // 경계선 처리
			
			mapWater[thisCloud.r][thisCloud.c]++; // 이동한 곳의 물 1 증가
		}
		
		// 여기서 구름이 사라져야 하지만, 4번을 위해서 남겨둠
		iterCloud = clouds.iterator(); // 이미 한 번 돌았는데 또 동작하나?
		while (iterCloud.hasNext()) {
			Vector2D thisCloud = iterCloud.next();
			
			if (isValid(thisCloud.r - 1, thisCloud.c - 1) && ) {
				
			}
		}
	}
	
	static boolean isValid(int r, int c) {
		return (0 <= r && r < N && 0 <= c && c < N);
	}
	
	static int sumWater() {
		// 바구니의 물의 총 합
	}
}

class Vector2D {
	int r, c;
	Vector2D(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	void add(int dr, int dc) {
		this.r += dr;
		this.c += dc;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector2D) {
			Vector2D v = (Vector2D) o;
			return (v.r == this.r && v.c == this.c);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return r * 31 + c;
	}
	
	void setByCommand(int direction, int distance) {
		switch (direction) {
		case 1:
			r = 0;
			c = -1;
			break;
		case 2:
			r = -1;
			c = -1;
			break;
		case 3:
			r = -1;
			c = 0;
			break;
		case 4:
			r = -1;
			c = 1;
			break;
		case 5:
			r = 0;
			c = 1;
			break;
		case 6:
			r = 1;
			c = 1;
			break;
		case 7:
			r = 1;
			c = 0;
			break;
		case 8:
			r = 1;
			c = -1;
			break;
		}
		
		r *= distance;
		c *= distance;
	}
}
