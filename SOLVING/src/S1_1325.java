import java.util.*;

public class S1_1325 {
	public static void main(String[] args) {
		final Scanner sc = new Scanner(System.in);
		
		// input N and M
		final int N = sc.nextInt();
		final int M = sc.nextInt();
		
		// initialize edges
		final List<Integer>[] edges = new List[N+1];
		for (int i=1; i<=N; i++) {
			edges[i] = new ArrayList<>();
		}
		
		// add edges into the directed graph
		for (int i=0; i<M; i++) {
			final int trust = sc.nextInt();
			final int trusted = sc.nextInt();
			
			edges[trusted].add(trust);
		}
		
		// bfs with optimal substructure
		int[] scores = new int[N+1];
		Map<Integer, List<Integer>> scoreBoard = new HashMap<>();
		for (int i=1; i<=N; i++) {
			boolean[] visited = new boolean[N+1];
			visited[i] = true;
			Queue<Integer> q = new LinkedList<>();
			q.add(i);
			
			int curScore = 1; // visited node = i
			while (!q.isEmpty()) {
				int here = q.poll();
				if (scores[here] > 0) {
					curScore += scores[here] - 1;
					continue;
				}
				
				for (int edge : edges[i]) {
					if (!visited[edge]) {
						visited[edge] = true;
						curScore ++;
						q.add(edge);
					}
				}
			}
			
			scores[i] = curScore;
			if (!scoreBoard.containsKey(curScore)) {
				scoreBoard.put(curScore, new ArrayList<>());
			}
			scoreBoard.get(curScore).add(i);
		}
		
		// print
		List<Integer> keys = new ArrayList<>(scoreBoard.keySet());
		keys.sort(Collections.reverseOrder());
		
		List<Integer> values = scoreBoard.get(keys.get(0));
		values.sort(null);
		
		for (int ele : values) {
			System.out.print(ele + " ");
		}
		
		System.out.println();
	}
}
