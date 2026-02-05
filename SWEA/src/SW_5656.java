import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo&

public class SW_5656 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				// input N ,W, H
				final int N = sc.nextInt(); // 구슬을 쏘는 횟수 (1 <= N <= 4)
				final int W = sc.nextInt(); // width (2 <= W <= 12)
				final int H = sc.nextInt(); // height (2 <= H <= 15)
				
				// input map : row = h, column = c
				final int[][] map = new int[H][W];
				for (int h=H-1; 0<=h; h--) { // h=높이를 의미하므로 거꾸로 입력받는 것 반영
					for (int c=0; c<W; c++) {
						map[h][c] = sc.nextInt();
					}
				}
				
				// dijkstra
				int minSize = Integer.MAX_VALUE;
				List<Status> unvisited = new LinkedList<>();
				Status startingStatus = new Status(W, H);
				startingStatus.copyMap(map);
				unvisited.add(startingStatus);
				while (!unvisited.isEmpty()) {
					//System.out.println("                                                                       [ loop ]");
					
					// choose a status such that the count is minimum
					unvisited.sort((s1, s2) -> (s1.count - s2.count));
					Status visitHere = unvisited.get(0);
					unvisited.remove(0);
					
					// check if it's Nth (invalid from N)
					if (visitHere.loop > N)
						continue;
					
					// update minSize
					minSize = Math.min(minSize, visitHere.count);
					
					// extract possible cases(status)
					for (int c=0; c<W; c++) {						
						// possible case : there exists any block 
						if (0 < visitHere.map[0][c]) {
							Status next = new Status(W, H);
							next.loop = visitHere.loop + 1;
							next.copyMap(visitHere.map);
							
							// search for the target h
							int h;
							for (h=0; h<H; h++) {
								if (next.map[h][c] == 0) {
									break;
								}
							}
							h--; // 천장에 도달했거나 빈칸을 만났으므로 하나 빼야 top과 같음
							
							printMap(" [before boom] c="+c+" ", next.map, H, W);
							
							// boom
							boom(next.map, h, c, H, W);

							printMap(" [after boom]", next.map, H, W);
							
							// pull down with gravity
							pullDown(next.map, H, W);
							
							printMap(" [after pull-down", next.map, H, W);
							
							// check if there's an overlap
							Status overlap = null;
							for (int i=0; i<unvisited.size(); i++) {
								boolean same = true;
								for (int checkH=0; checkH<H && same; checkH++) {
									for (int checkC=0; checkC<W && same; checkC++) {
										if (unvisited.get(i).map[checkH][checkC] != next.map[checkH][checkC]) {
											same = false;
										}
									}
								}
								
								if (same) {
									overlap = unvisited.get(i);
									break;
								}
							}
							
							// update count and accumulatedCount
							if (overlap == null) { // 중복이 없으면 next를 수정하고 등록
								next.updateCount();
								next.count = Math.min(next.count, visitHere.count);
								unvisited.add(next);
							} else { // 중복이 있으면 next는 무시하고 overlap을 수정
								overlap.updateCount();
								overlap.loop = Math.min(overlap.loop, next.loop);
								overlap.count = Math.min(overlap.count, visitHere.count);
							}
						}
					}
				}

				System.out.println("#"+t+" "+minSize);
			}
		}
	}
	
	public static void boom(int[][] map, int h, int c, int H, int W) {
		if (map[h][c] == 0) return;
		int boomMagnitude = map[h][c];
		map[h][c] = 0; // boom that point
		for (int i=1; i<boomMagnitude; i++) {
			if (isValid(h-i, c, H, W)) boom(map, h-i, c, H, W); // down
			if (isValid(h+i, c, H, W)) boom(map, h+i, c, H, W); // up
			if (isValid(h, c-i, H, W)) boom(map, h, c-i, H, W); // left
			if (isValid(h, c+i, H, W)) boom(map, h, c+i, H, W); // right			
		}
	}
	
	public static void pullDown(int[][] map, int H, int W) {
		for (int c=0; c<W; c++) {
			for (int h=0; h<H; h++) {
				if (map[h][c] == 0) { // need to pull down all things above
					int search = h+1;
					for (; search<H; search++) {
						if (map[search][c] != 0)
							break; // found something to pull down
					}
					
					if (search < H) {
						map[h][c] = map[search][c];
						map[search][c] = 0;
					}
				}
			}
		}
	}
	
	public static boolean isValid(int h, int c, int H, int W) {
		return (0<=h && h<H && 0<=c && c<W);
	}
	
	public static void printMap(String msg, int[][] map, int H, int W) {
		if (true) return;
		System.out.println("--------------"+ msg +"---------------");
		for (int h=H-1; 0<=h; h--) {
			for (int c=0; c<W; c++) {
				if (map[h][c] == 0)
					System.out.print(" .");
				else 
					System.out.print(" "+map[h][c]);
			}
			System.out.println();
		}
	}
}

class Status {
	int W, H;
	int[][] map;
	int count;
	int loop;
	//int accumulatedCount;
	
	Status(int W, int H) {
		this.W = W;
		this.H = H;
		this.map = new int[H][W];
		this.count = 0;
		this.loop = 0;
		//this.accumulatedCount = Integer.MAX_VALUE;
	}
	
	void copyMap(int[][] map) {
		this.count = 0;
		for (int h=0; h<H; h++) {
			for (int c=0; c<W; c++) {
				this.map[h][c] = map[h][c];
				if (map[h][c] != 0)
					this.count++;
			}
		}
	}
	
	void updateCount() {
		this.count = 0;
		for (int h=0; h<H; h++) {
			for (int c=0; c<W; c++) {
				if (map[h][c] != 0)
					this.count++;
			}
		}
	}
}

class Block {
	int c, h;
	int boomMagnitude;
	
	Block(int c, int h, int boomMagnitude) {
		this.c = c;
		this.h = h;
		this.boomMagnitude = boomMagnitude;
	}
}

