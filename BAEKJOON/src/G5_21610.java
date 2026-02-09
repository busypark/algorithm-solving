import java.util.Scanner;
//import java.util.List;
//import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
//import java.util.Collections;

// https://www.acmicpc.net/problem/21610

public class G5_21610 {
	static int N;
	static int[][] mapWater;
	static Set<Vector2D> clouds;
	//static List<Vector2D> clouds;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			final int M = sc.nextInt();
			
			mapWater = new int[N][N];
			//clouds = new ArrayList<>(); 
			clouds = new HashSet<>();
			
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
		//printMap();
		
		//System.out.println("--------------------------------");
		//System.out.println("direction="+direction+" distance="+distance);
		
		Vector2D inc = new Vector2D();
		inc.setByCommand(direction, distance);
		
		//System.out.println("inc.r="+inc.r + " inc.c="+inc.c);
		//System.out.println("1번/2번 - 이동 및 물 증가");
		
		// 1번 : Move
		Iterator<Vector2D> iterCloud = clouds.iterator();
		Set<Vector2D> movedClouds = new HashSet<>();
		while (iterCloud.hasNext()) {
			Vector2D thisCloud = iterCloud.next();
			thisCloud.add(inc.r, inc.c);
			thisCloud.r = Math.floorMod(thisCloud.r, N); // 경계선 처리
			thisCloud.c = Math.floorMod(thisCloud.c, N); // 경계선 처리
			movedClouds.add(thisCloud); // hash 갱신!
			
 			mapWater[thisCloud.r][thisCloud.c]++; // 2번 : 이동한 곳의 물 1 증가
 			
 			//System.out.println(" 2번 - 물 증가 : r="+thisCloud.r+" c="+thisCloud.c+" mapWater[][]="+mapWater[thisCloud.r][thisCloud.c]);
		}
		clouds = movedClouds;
		
		//printMap();
		
		// (3번) 여기서 구름이 사라져야 하지만, 4, 5번을 위해서 남겨둠
		iterCloud = clouds.iterator();
		while (iterCloud.hasNext()) {
			Vector2D thisCloud = iterCloud.next();
			
			// 4번 : 대각선 기준 물복사
			int incWater = ((isValid(thisCloud.r - 1, thisCloud.c - 1) && mapWater[thisCloud.r - 1][thisCloud.c - 1] > 0) ? 1 : 0)
						+  ((isValid(thisCloud.r - 1, thisCloud.c + 1) && mapWater[thisCloud.r - 1][thisCloud.c + 1] > 0) ? 1 : 0)
						+  ((isValid(thisCloud.r + 1, thisCloud.c - 1) && mapWater[thisCloud.r + 1][thisCloud.c - 1] > 0) ? 1 : 0)
						+  ((isValid(thisCloud.r + 1, thisCloud.c + 1) && mapWater[thisCloud.r + 1][thisCloud.c + 1] > 0) ? 1 : 0);
			mapWater[thisCloud.r][thisCloud.c] += incWater;
			
			//System.out.println(" 4번 - 대각선 물복사 : r="+thisCloud.r+" c="+thisCloud.c+" incWater="+incWater);
		}
		
		//printMap();
		// clouds 출력
		//System.out.println("Clouds 출력");
		iterCloud = clouds.iterator();
		while (iterCloud.hasNext()) {
			Vector2D thisCloud = iterCloud.next();
			
			//System.out.println(" thisCloud : r="+thisCloud.r+" c="+thisCloud.c);
		}
		
		// 5번 : 새로운 구름으로 갱신 & 물 -2
		Set<Vector2D> newClouds = new HashSet<>();
		//List<Vector2D> newClouds = new ArrayList<>();
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if (mapWater[r][c] >= 2) {					
					Vector2D thisCloud = new Vector2D(r, c);
				
					
					//System.out.println("Contains thisCloud? : r="+thisCloud.r + " c="+thisCloud.c);
					if (!clouds.contains(thisCloud)) {
						newClouds.add(thisCloud);
						mapWater[r][c] -= 2;
						
						//System.out.println(" TEST : clouds.size() = "+clouds.size());
						//System.out.println(" 5번 - 물의 양 2 감소 : r="+r+" c="+c+" mapWater[][]="+mapWater[r][c]);
						//System.out.println(" 5번 - 새로운 구름 추가 : r="+thisCloud.r+" c="+thisCloud.c);
					} else {
						//System.out.println("TEST");
					}
				}
			}
		}
		
		//System.out.println("newClouds.size() : "+newClouds.size());
		clouds = newClouds;
	}
	
	static boolean isValid(int r, int c) {
		return (0 <= r && r < N && 0 <= c && c < N);
	}
	
	static int sumWater() {
		int sum = 0;
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				sum += mapWater[r][c];
			}
		}
		
		return sum;
	}
	
	static void printMap() {
		System.out.println("      <printMap>");
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.print(" "+mapWater[r][c]);
			}
			System.out.println();
		}
	}
}

class Vector2D {
	public int r, c;
	Vector2D() {
		r = 0;
		c = 0;
	}
	
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
		return Objects.hash(r, c);
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
