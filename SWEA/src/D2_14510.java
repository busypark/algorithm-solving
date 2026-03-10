import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;

public class D2_14510 {
	public static Scanner sc = new Scanner(System.in);
	public static List<Integer> heights = new LinkedList<>();
	
	public static void main(String[] args) {
		final int T = sc.nextInt();
		for (int t=1; t<=T; t++) {
			int N = sc.nextInt();
			heights.clear();
			
			int maxHeight = 0;
			for (int i=0; i<N; i++) {
				heights.add(sc.nextInt()); // 1 <= heights.get(i) <= 120
				maxHeight = Math.max(maxHeight, heights.get(i));
			}
			
			// 최대값에는 물 줄 필요가 없으니 있는 거 다 제거
			while (true) {
				int maxIndex = heights.indexOf(maxHeight);
				if (maxIndex == -1) break;
				heights.remove(maxIndex);
			}
			
			// 최대값을 향해 물을 준다는 개념이 아니라, 최대값까지 남은 물의 양을 점점 깎아서 모두 0으로 만든다는 개념으로 전환
			// 굳이 필요한가?
			for (int i=0; i<heights.size(); i++) {
				heights.set(i, maxHeight - heights.get(i));
			}
			
			// Validation
			//if (t != 2) continue;
			
			
			Comparator<Integer> evenComparator = new Comparator<>() {
				@Override
				public int compare(Integer i1, Integer i2) {
					// 2만큼 깎아야 하면 기왕이면 짝수인 거 깎고, 홀홀/짝짝이면 더 큰 쪽을 깎도록
					final int ascendingReturn;
					if (i1 % 2 == 0) {
						if (i2 % 2 == 0) {
							ascendingReturn = i2 - i1;
						} else {
							ascendingReturn = -1;
						}
					} else {
						if (i2 % 2 == 0) {
							ascendingReturn = 1;
						} else {
							ascendingReturn = i2 - i1;
						}
					}
					
					return ascendingReturn;
				}
			};
			
			// 1만큼 물 주는 날에 어디에 줄건지 결정 -> 더 물을 줘야하는 곳일수록 index -> 0
			Comparator<Integer> oddComparator = new Comparator<>() {
				@Override
				public int compare(Integer i1, Integer i2) {
					final int ascendingReturn;
					// 짝짝/홀홀이면 더 작은 쪽에 빨리 1만큼 물 줘서 소진시킴
					// 1은 아무데나 줘도 되는데, 이왕이면 2만큼 주는 턴을 버틸 것을 남겨두는 방식
					// 홀짝/짝홀이면 홀인 쪽이 더 1을 받도록 함. 짝수가 홀수보다 더 귀하기 때문
					if (i1 % 2 == 0) {
						if (i2 % 2 == 0) {
							ascendingReturn = i1 - i2;
						} else {
							ascendingReturn = 1;
						}
					} else {
						if (i2 % 2 == 0) {
							ascendingReturn = -1;
						} else {
							ascendingReturn = i1 - i2;
						}
					}
					
					return ascendingReturn;
				}
			};

			int s = 1; // switch between 1 and 2
			int answer = 0;
			while (0 < heights.size()) {
				//print(answer, s); //
				
				if (heights.size() == 1) {
					if (heights.get(0) == 2 && s == 1) {
						// 물 줄 거 하나 남았는데 그게 2인데 지금 1 물 줘야 하면 무조건 한 턴 쉬기
						s = 2 - (s+1) % 2;
						answer++;
						continue;
					}
				}
				
				// sort : heights[0] must be the most proper
				// 이미 여기서 무슨 나무에 물 줄지 결정이 되므로 Comparator가 코드의 핵심임
				if (s == 1) heights.sort(oddComparator);
				else heights.sort(evenComparator);
				
				if (heights.get(0) <= 0) {
					System.out.println("#error occured");
					print(); //
					break;
				}
				
				// 물 주기
				int newValue = heights.get(0) - s;
				if (newValue == 0) {
					// 물 줬더니 딱 알맞으면 더 손 쓸 필요도 없으니 제거
					heights.remove(0);
				} else if (newValue > 0) {
					// 아직 물 더 줘야하면 값만 대체
					heights.set(0, newValue);
				} else {
				}
				
				s = 2 - (s+1) % 2;
				answer++;
			}
			
			System.out.println("#"+t+" "+answer);
		}
	}
	
	static void print() {
		System.out.print("heights : ");
		for (int i=0; i<heights.size(); i++)
			System.out.print(heights.get(i)+" ");
		System.out.println("");
	}
	
	static void print(int iter, int s) {
		System.out.print("iter: "+iter+", heights["+s+"] : ");
		for (int i=0; i<heights.size(); i++)
			System.out.print(heights.get(i)+" ");
		System.out.println("");
	}
}
