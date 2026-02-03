import java.util.Scanner;

public class D5_1247 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 2<=N<=10
				
				final Position company = new Position(sc.nextInt(), sc.nextInt());
				final Position house = new Position(sc.nextInt(), sc.nextInt());
				
				final Position[] customers = new Position[N];
				for (int i=0; i<N; i++) {
					customers[i] = new Position(sc.nextInt(), sc.nextInt());
				}
				
				// 전략 : customers에 대한 인덱스 배열(0, 1, ..., N-1)에 대한 가능한 모든 순열(N!가지)을 순회하여 minimum distance 계산
				int[] idxArr = new int[N];
				for (int i=0; i<N; i++) idxArr[i] = i;
				
				int minDistance = Integer.MAX_VALUE;
				do {
					int currentDistance = Position.getDistance(company, customers[idxArr[0]])
										+ Position.getDistance(customers[idxArr[N-1]], house);
					for (int i=0; i<N-1; i++) {
						currentDistance += Position.getDistance(customers[idxArr[i]], customers[idxArr[i+1]]);
					}
					
					minDistance = Math.min(minDistance, currentDistance);
				} while (nextPermutation(idxArr));
				
				System.out.println("#"+t+" "+minDistance);
			}
		}
	}
	
	public static boolean nextPermutation(int[] arr) {
		int pivot;
		for (pivot = arr.length-2; 0 <= pivot; pivot--) {
			if (arr[pivot] < arr[pivot+1]) break;
		}
		
		if (pivot == -1) return false;
		
		int swap;
		for (swap = arr.length-1; pivot < swap; swap--) {
			if (arr[pivot] < arr[swap]) {
				break;
			}
		}
		
		int temp1 = arr[pivot];
		arr[pivot] = arr[swap];
		arr[swap] = temp1;
		
		for (int i=0; i<(arr.length-1-pivot)/2; i++) {
			int temp2 = arr[pivot+1+i];
			arr[pivot+1+i] = arr[arr.length-1-i];
			arr[arr.length-1-i] = temp2;
		}
		
		return true;
	}
}

class Position {
	int r, c;
	Position(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	static int getDistance(Position p1, Position p2) {
		return Math.abs(p1.r - p2.r) + Math.abs(p1.c - p2.c);
	}
}
