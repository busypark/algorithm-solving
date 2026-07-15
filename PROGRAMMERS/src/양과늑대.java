import java.util.*;

public class 양과늑대 {
	// 이진트리 각 노드 정의 : 값 들고 있고, 자식들로의 포인터 두 개 갖고 있음
	// 왜 부모는 안 갖고, 자식만 있냐? 자식 방향으로만 순회하면 될 것 같아서 
	static class Node {
		int id;
		Node left, right; // 왼쪽부터 채우기로 고정
		
		Node(int id) {
			this.id = id;
		}
	}
	
	public static int solution(int[] info, int[][] edges) {
		// transform edges into Binary Tree
		int n = info.length;
		Node[] nodes = new Node[n]; // 이진트리를 객체 배열로 정의
		for (int[] edge : edges) { // (부모, 자식) 쌍을 객체 배열 형식으로 변환
			int parent = edge[0];
			int child = edge[1];
			
			if (nodes[parent] == null) {
				nodes[parent] = new Node(parent);
				nodes[parent].left = new Node(child);
				nodes[child] = nodes[parent].left;
			} else {
				nodes[parent].right = new Node(child);
				nodes[child] = nodes[parent].right;
			}
		}
		
		// swirl into cycle : 기본정보 + 시작 지점(id) 및 현 상태(following~) 전달
		cycle(info, nodes, 0, 0, 0);
        
        return 0;
    }
	
	// 다음 늑대들 구하고 ~ 그것들의 순열 구하고 ~ 그 안에서 순열대로 실행하도록 다시 돌아오기
	static void cycle(int[] info, Node[] nodes, int id, int followingSheeps, int followingWolves) {
		// extract boundary wolves
		List<Integer> wolves = new ArrayList<>();
		int nSheep = extractBoundaryWolves(info, nodes, id, wolves, 0);
		
		// generate permutation by wolves -> visit along it
		backtrack(info, nodes, id, wolves, followingSheeps + nSheep, followingWolves, 0, new int[wolves.size()], new boolean[wolves.size()]);
		
		
	}
	
	static void backtrack(int[] info, Node[] nodes, int id, List<Integer> wolves, int followingSheeps, int followingWolves, int depth, int[] seq, boolean[] visited) {
		if (depth == seq.length) {
			// sequence generated -> visit along it (try backtrack)
			for (int sid : seq) {
				int wolfId = wolves.get(sid);
				
			}
			
			return;
		}
		
		
	}
	
	// 늑대 만날 때까지 양 마리 수 더하면서 순회 -> 다음 늑대들 목록 + 소영역 내 양 마리 수만 구함(root 포함)
	static int extractBoundaryWolves(int[] info, Node[] nodes, int id, List<Integer> wolves, int nSheep) {
		Node now = nodes[id];
		if (info[id] == 1) {
			// wolf itself
			wolves.add(id);
			return nSheep;
		}
		
		int retnSheep = nSheep + 1;
		
		if (now.left != null) {
			retnSheep = extractBoundaryWolves(info, nodes, now.left.id, wolves, retnSheep);
			
			if (now.right != null) {
				retnSheep = extractBoundaryWolves(info, nodes, now.right.id, wolves, retnSheep);
			}
		}
		
		return retnSheep;
	}
	
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[] {0,0,1,1,1,0,1,0,1,0,1,1}, new int[][] {{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}}));
    	// 5
    	
    	System.out.println(solution(new int[] {0,1,0,1,1,0,1,0,0,1,0}, new int[][] {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}}));
    	// 5
    }
}
