import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class D5_1248 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int V = sc.nextInt(); // 정점의 개수, 10~10000
				final int E = sc.nextInt(); // 간선의 개수
				final int A = sc.nextInt(); // 정점 1
				final int B = sc.nextInt(); // 정점 2
				
				// 전략 : 자식 노드를 정의하고 parent 숫자 및 자신의 level(root=1에서 자식일 수록 1씩 커짐) 저장
				//       또한 모든 노드를 Set에 담아서 iterate 가능하도록
				Set<TreeNode> nodes = new HashSet<>();
				for (int i=0; i<E; i++) {
					int numParent = sc.nextInt();
					int numChild = sc.nextInt();
					
					
				}
			}
		}
	}
}

class TreeNode {
	int parent = -1;
	int level = -1;
	
	TreeNode(int parent, int level) {
		this.parent = parent;
		this.level = level;
	}
}
