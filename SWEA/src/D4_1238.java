import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class D4_1238 {
	static List[] edges;
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int N = sc.nextInt() / 2; // 간선의 개수
				final int start = sc.nextInt();

				edges = new List[101]; // 1~100번 인덱스 사용
				for (int n=0; n<N; n++) {
					final int from = sc.nextInt();
					final int to = sc.nextInt();
					
					if (edges[from] == null) {
						edges[from] = new ArrayList<Integer>();
					} 
					
					if (!edges[from].contains(to))
						edges[from].add(to);
				}
				
				boolean[] visited = new boolean[101];
				List<Integer> currentNodes = new ArrayList<>();
				int maxNode = 0;
				currentNodes.add(start);
				while (!currentNodes.isEmpty()) {
					maxNode = Collections.max(currentNodes);
					List<Integer> nextNodes = new ArrayList<>();
					for (int eachNode : currentNodes) {
						if (edges[eachNode] != null) {
							for (Object eachNextNode : edges[eachNode]) {
								if (eachNextNode instanceof Integer) {
									int nextNode = (Integer) eachNextNode;
									if (!visited[nextNode]) {
										nextNodes.add(nextNode);
										visited[nextNode] = true;
									}
								}
							}
						}
					}
					
					currentNodes = nextNodes;
				}
				
				System.out.println("#"+t+" "+maxNode);
			}
		}
	}
}
