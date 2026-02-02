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
			
			while (true) {
				int maxIndex = heights.indexOf(maxHeight);
				if (maxIndex == -1) break;
				heights.remove(maxIndex);
			}
			
			for (int i=0; i<heights.size(); i++) {
				heights.set(i, maxHeight - heights.get(i));
			}
			
			// Validation
			//if (t != 2) continue;
			
			
			Comparator<Integer> evenComparator = new Comparator<>() {
				@Override
				public int compare(Integer i1, Integer i2) {
					final int ascendingReturn;
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
					
					return -ascendingReturn;
				}
			};
			
			Comparator<Integer> oddComparator = new Comparator<>() {
				@Override
				public int compare(Integer i1, Integer i2) {
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
					
					return -ascendingReturn;
				}
			};

			int s = 1; // switch between 1 and 2
			int answer = 0;
			while (0 < heights.size()) {
				//print(answer, s); //
				
				if (heights.size() == 1) {
					if (heights.get(0) == 2 && s == 1) {
						s = 2 - (s+1) % 2;
						answer++;
						continue;
					}
				}
				
				// sort : heights[0] must be the most proper
				if (s == 1) heights.sort(oddComparator);
				else heights.sort(evenComparator);
				
				if (heights.get(0) <= 0) {
					System.out.println("#error occured");
					print(); //
					break;
				}
				
				int newValue = heights.get(0) - s;
				if (newValue == 0) {
					heights.remove(0);
				} else if (newValue > 0) {
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
