import java.util.*;

public class 양과늑대 {
	static class Node {
		int id;
		Node left, right;
		
		Node(int id) {
			this.id = id;
		}
	}
	
	public static int solution(int[] info, int[][] edges) {
		// transform edges into Binary Tree
		int n = info.length;
		Node[] nodes = new Node[n];
		for (int[] edge : edges) {
			int parent = edge[0];
			int child = edge[1];
			
			if (nodes[parent] == null) {
				nodes[parent] = new Node(parent);
			}
			
			if (nodes[child] == null) {
				nodes[child] = new Node(child);
			}
			
			if (nodes[parent].left == null) {
				nodes[parent].left = nodes[child];
			} else {
				nodes[parent].right = nodes[child];
			}
		}
		
		// backtrack
		nSheep = 1;
		nWolf = 0;
		maxSheep = 1;
		options = new boolean[n];
		backtrack(info, nodes, 0);
		
        return maxSheep;
    }
	
	static int nSheep, nWolf;
	static int maxSheep;
	static boolean[] options;
	static void backtrack(int[] info, Node[] nodes, int id) {
		if (nodes[id].left != null)
			options[nodes[id].left.id] = true;
		if (nodes[id].right != null) 
			options[nodes[id].right.id] = true;
		
		for (int i=0; i<options.length; i++) {
			if (options[i]) {
				options[i] = false;
				nSheep += 1 - info[i];
				nWolf += info[i];
				if (nWolf < nSheep) {
					maxSheep = Math.max(maxSheep, nSheep);
					backtrack(info, nodes, i);
				}
				nSheep -= 1 - info[i];
				nWolf -= info[i];
				
				options[i] = true;
			}
		}
		
		if (nodes[id].left != null)
			options[nodes[id].left.id] = false;
		if (nodes[id].right != null) 
			options[nodes[id].right.id] = false;
	}
	
	
    public static void main(String[] args) {
    	System.out.println(solution(new int[] {0,0,1,1,1,0,1,0,1,0,1,1}, new int[][] {{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}}));
    	// 5
    	
    	System.out.println(solution(new int[] {0,1,0,1,1,0,1,0,0,1,0}, new int[][] {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}}));
    	// 5
    }
}
