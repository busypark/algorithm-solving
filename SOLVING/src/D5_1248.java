import java.io.*;
import java.util.*;

// 가장 가까운 공통조상 찾는 문제 -> LCA(Lowest Common Ancestor, lowest = 트리 상 가장 낮은)

public class D5_1248 {
	static class Node {
		Node parent, left, right;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			Node[] nodes = new Node[V+1];
			for (int i=1; i<=V; i++) {
				nodes[i] = new Node();
			}
			
			st = new StringTokenizer(br.readLine());
			for (int i=1; i<=E; i++) {
				int pa = Integer.parseInt(st.nextToken());
				int ch = Integer.parseInt(st.nextToken());
				
				if (nodes[pa].left == null) {
					nodes[pa].left = nodes[ch];
				} else {
					nodes[pa].right = nodes[ch];
				}
				nodes[ch].parent = nodes[pa];
			}
			
			
			
			answer.append("#").append(t).append(" ").append(false).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static boolean[] ancestA, ancestB;
	static boolean commonAncest(Node a, Node b) {
		
	}
	
	static int getTreeSize(Node root, int curSize) {
		if (root.left == null) {
			return curSize;
		}
		
		int sum = getTreeSize(root.left, curSize + 1);
		if (root.right != null) {
			sum = getTreeSize(root.right, sum);
		}
		
		return sum;
	}
}
