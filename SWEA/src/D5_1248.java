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
				
				// 전략 : 자식 노드를 정의하고 parent 숫자 및 자신의 level(root=1에서 자식일 수록 1씩 커짐. 처음엔 root=1이지만 나중에는 더 낮아질 수도) 저장
				// 맹점 : 연결할 때 자손들의 level을 모두 reset해야 함
				TreeNode[] nodes = new TreeNode[V+1];
				TreeNode nodeA = null;
				TreeNode nodeB = null;
				
				for (int i=0; i<E; i++) {
					int numParent = sc.nextInt();
					int numChild = sc.nextInt();
					
					if (nodes[numParent] == null && nodes[numChild] == null) { // 둘 다 없는 경우 노드 생성 & 연결
						nodes[numParent] = new TreeNode(numParent, -1, 1, numChild, -1);
						nodes[numChild]  = new TreeNode(numChild, numParent, 2, -1, -1);
					} else if (nodes[numParent] == null) { // parent만 원래 없었다면 생성 & 연결
						nodes[numParent] = new TreeNode(numParent, -1, nodes[numChild].level-1, numChild, -1);
						nodes[numChild].parent = numParent;
					} else if (nodes[numChild] == null) { // child만 원래 없었다면 생성 & 연결
						nodes[numChild] = new TreeNode(numChild, numParent, nodes[numParent].level+1, -1, -1);
						if (nodes[numParent].leftChild == -1)
							nodes[numParent].leftChild = numChild;
						else 
							nodes[numParent].rightChild = numChild;
					} else { // 둘 다 있는 경우 연결만 해줌 -> 이때, 각자가 이미 완성된 상태이므로 자식들은 부모 기준으로 모두 level 리셋
						nodes[numChild].parent = numParent;
						if (nodes[numParent].leftChild == -1)
							nodes[numParent].leftChild = numChild;
						else 
							nodes[numParent].rightChild = numChild;
						
						resetChildLevels(nodes, nodes[numParent]);
					}

					if (numParent == A) nodeA = nodes[numParent];
					if (numParent == B) nodeB = nodes[numParent];
					if (numChild == A) nodeA = nodes[numChild];
					if (numChild == B) nodeB = nodes[numChild];
				}
				
				// search for the common ancestor of A and B
				// 전략 : A, B 중 레벨이 더 높은 (트리에서 더 낮은 곳에 있는) 것을 나머지 것과 level이 동일해질 때까지 순회
				// 		 만약 순회한 결과가 나머지 것과 다르다면 둘을 동시에 하나씩 level-- 하면서 순회. 루트까지 올라가면서 같아지는 최초의 공통조상 찾기
				TreeNode needToUp = (nodeA.level > nodeB.level) ? nodeA : nodeB;
				TreeNode rest = (nodeA.level > nodeB.level) ? nodeB : nodeA;

				//System.out.println("#"+t+" needToUp = "+needToUp.num);
				//System.out.println("#"+t+" rest = "+rest.num);
				while (needToUp.level > rest.level) {
					needToUp = nodes[needToUp.parent];
				}
				
				//System.out.println("#"+t+" [after] needToUp = "+needToUp.num);
				
				while (true) {
					if (needToUp.num == rest.num) {
						break;
					}
					
					needToUp = nodes[needToUp.parent];
					rest = nodes[rest.parent];
				}
				final TreeNode commonAncestor = needToUp;
				
				// commonAncestor를 포함한 그것의 모든 자식의 개수 세기
				int count = countChildNodes(nodes, commonAncestor);
				
				System.out.println("#"+t+" "+commonAncestor.num+" "+count);
			}
		}
	}
	
	public static void resetChildLevels(TreeNode[] nodes, TreeNode parent) {
		if (parent == null) return;
		if (parent.leftChild != -1) {
			nodes[parent.leftChild].level = parent.level + 1;
			resetChildLevels(nodes, nodes[parent.leftChild]);
		}
		if (parent.rightChild != -1) {
			nodes[parent.rightChild].level = parent.level + 1;
			resetChildLevels(nodes, nodes[parent.rightChild]);
		}
	}
	
	public static int countChildNodes(TreeNode[] nodes, TreeNode parent) {
		if (parent == null) return 0;
		int countLeftChildNodes = (parent.leftChild != -1)? countChildNodes(nodes, nodes[parent.leftChild]) : 0;
		int countRightChildNodes = (parent.rightChild != -1)? countChildNodes(nodes, nodes[parent.rightChild]) : 0;
		
		return 1 + countLeftChildNodes + countRightChildNodes;
	}
}

class TreeNode {
	int num = -1;
	int parent = -1;
	int level = -1;
	int leftChild = -1;
	int rightChild = -1;
	
	TreeNode(int num, int parent, int level, int leftChild, int rightChild) {
		this.num = num;
		this.parent = parent;
		this.level = level;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
}
