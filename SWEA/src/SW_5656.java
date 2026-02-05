import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo&

public class SW_5656 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 구슬을 쏘는 횟수 (1 <= N <= 4)
				final int W = sc.nextInt(); // width (2 <= W <= 12)
				final int H = sc.nextInt(); // height (2 <= H <= 15)
				
				final List<List<Integer>> map = new ArrayList<>(); // List 대신 배열 쓰고 싶었지만..
				final int[][] tempMap = new int[H][W]; // 주의! H가 깊이이므로 행임
				for (int h=0; h<H; h++) {
					for (int w=0; w<W; w++) {
						tempMap[h][w] = sc.nextInt();
					}
				}
				
				// transform tempMap to map
				for (int w=0; w<W; w++) {
					List<Integer> column = new ArrayList<>();
					for (int h=H-1; 0<=h; h--) {
						if (tempMap[h][w] == 0) break; // column의 상단은 0으로 채워져 있으므로 더이상 추가할 필요가 없음
						
						column.add(tempMap[h][w]); // head = bottom, tail = top
					}
					
					map.add(column);
				}
				
				// Start Simulation
				for (int n=0; n<N; n++) {
					printMap(map, W);
					
					// choose the column which has maximum top
					// 각 열에 점수를 매김. 높이 * boom 번호의 총합으로. 그게 가장 높은 열로 maxC 선정
					// 이유? 그냥 높이에 더 큰 값 있으면 좋으니까
					
					int maxC = -1;
					for (int c=0; c<W; c++) // initialize maxC -> 아무 것도 없는 열에 쏠 필요는 없음
						if (map.get(c).size() > 0)
							maxC = c;
					if (maxC == -1) break; // 모든 열에 아무 것도 없으면 시뮬레이션 더 할 필요도 없음
					
					int maxFormula = getValue(map.get(maxC));
					for (int c=0; c<W; c++) {
						if (map.get(c).size() > 0) {
							int formula = getValue(map.get(c));
							if (maxFormula < formula) {
								maxC = c;
								maxFormula = getValue(map.get(maxC));
							}
						}
					}
					
					
					/* 기존 maxC 찾기 (그냥 가장 위의 boom 기준)
					for (int c=1; c<W; c++) {
						if (map.get(c).size() == 0) continue;
						
						int top = map.get(c).get(map.get(c).size()-1);
						if (map.get(maxC).get(map.get(maxC).size()-1) < top) {
							maxC = c;
						}
					}
					*/
					
					// boom (해당 열은 boomMagnitude만큼 pop, 좌우 열은 높이가 충족되는 것만 1번씩 pop)
					// pop이 아니라 removeLast였고, removeLast가 아니라 중간에 있는 걸 골라서 remove해야 함
					// 해당 열은 좌우 열 하고 난 다음에 해야 함
					int boomMagnitude = map.get(maxC).get(map.get(maxC).size()-1);
					System.out.println("[boomMagnitude = "+boomMagnitude+"]");
					
					for (int i=1; i<=boomMagnitude-1; i++) {
						int c;
						// boom left columns
						c = maxC - i;
						if (isValid(c, W) && map.get(maxC).size() <= map.get(c).size()) {
							map.get(c).remove(map.get(maxC).size()-1);
						}
						
						// boom right columns
						c = maxC + i;
						if (isValid(c, W) && map.get(maxC).size() <= map.get(c).size()) {
							System.out.println("[boom right : idx="+(map.get(maxC).size()-1)+"]");
							map.get(c).remove(map.get(maxC).size()-1);
						}
					}
					
					// boom the very column
					// 해당 열은 removeLast해도 됨. 어차피 가장 위의 아이템이므로
					for (int i=0; i<boomMagnitude; i++)
						if (0 < map.get(maxC).size())
							map.get(maxC).remove(map.get(maxC).size()-1);
				}
				

				printMap(map, W);
				
				// answer = sum of the lengths of the columns
				int answer = 0;
				for (int c=0; c<W; c++) {
					answer += map.get(c).size();
					//System.out.println("size of column["+c+"] = "+map.get(c).size());
				}
				
				System.out.println("#"+t+" "+answer);
			}
		}
	}
	
	public static boolean isValid(int c, int W) {
		return (0<=c && c<W);
	}
	
	public static int getValue(List<Integer> column) {
		int result = 0;
		for (int h=0; h<column.size(); h++) {
			result += (h+1) * column.get(h) * column.get(h);
		}
		
		return result;
	}
	
	public static void printMap(List<List<Integer>> map, int W) {
		System.out.println("---------------- print, horizontally! ----------------");
		for (int c=0; c<W; c++) {
			System.out.print("column["+c+"] : ");
			for (int i=0; i<map.get(c).size(); i++) {
				System.out.print(" "+map.get(c).get(i));
			}
			System.out.println();
		}
	}
}
