import java.util.Scanner;

public class D5_1248 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int V = sc.nextInt(); // 정점의 개수, 10~10000
				final int E = sc.nextInt(); // 간선의 개수
				final int A = sc.nextInt(); // 정점 1
				final int B = sc.nextInt(); // 정점 2
				
				// 전략 : 이진트리이므로 배열로 입력 받음. number(1~N)가 인덱스, 값이 위치(1 ~ 2^V-1)
				// 그런데 V가 최대 10000이므로 2^10000은 말이 안 됨..
				// tree[num] = positionNumber; // num 노드가 positionNumber 위치에 있다 -> 배정을 하는 행위 자체가 연결까지 하는 것과 같음
				int[] tree = new int[V+1];
				
				for (int i=0; i<E; i++) {
					int parent = sc.nextInt();
					int child = sc.nextInt();
					
					if (tree[parent] == 0 && tree[child] == 0) {
						tree[parent] = 1;
						tree[child] = 2;
					} else if (tree[parent] == 0) {
						tree[parent] = getParentPosition(tree[child]);
					} else if (tree[child] == 0) {
						if (tree[getLeftChildPosition(tree[parent])] == 0) {
							tree[child] = getLeftChildPosition(tree[parent]);
						} else {
							tree[child] = getRightChildPosition(tree[parent]);
						}
					}
				}
				
				for (int i=1; i<=V; i++)
					System.out.print(tree[i]+" ");
				
				break;
			}
		}
	}
	
	public static int getParentPosition(int childPosition) {
		// left or right
		// left : 2 4 6 ... even
		// right : 3 5 7 ... odd
		return childPosition / 2;
	}
	
	public static int getLeftChildPosition(int parentPosition) {
		return parentPosition*2;
	}
	
	public static int getRightChildPosition(int parentPosition) {
		return parentPosition*2+1;
	}
}
