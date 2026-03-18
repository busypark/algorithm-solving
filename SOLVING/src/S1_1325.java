import java.util.*;

public class S1_1325 {
	static List<Integer>[] edges;
	
	public static void main(String[] args) {
		final Scanner sc = new Scanner(System.in);
		
		// input N and M
		final int N = sc.nextInt();
		final int M = sc.nextInt();
		
		// initialize edges
		edges = new List[N+1];
		for (int i=1; i<=N; i++) {
			edges[i] = new ArrayList<>();
		}
		
		// add edges into the directed graph
		for (int i=0; i<M; i++) {
			final int trust = sc.nextInt();
			final int trusted = sc.nextInt();
			
			edges[trusted].add(trust);
		}
		
		// DP
		scores = new int[N+1];
		scoreBoard = new HashMap<>();
		for (int i=1; i<=N; i++) {
			//scores[i] = DP(i);
			int score = 1;
			Queue<Integer> q = new LinkedList<>();
			q.add(i);
			while (!q.isEmpty()) {
				int node = q.poll();
				if (scores[node] > 0) {
					score += scores[node];
					continue;
				}
				
				for (int edge : edges[node]) {
					int edgeDP = DP(edge);
					scores[edge] = edgeDP;
					score += edgeDP;
				}
			}
			
			
			if (!scoreBoard.containsKey(scores[i])) {
				scoreBoard.put(scores[i], new ArrayList<>());
			}
			
			scoreBoard.get(scores[i]).add(i);
		}
		
		// print the maximum nodes
		List<Integer> keys = new ArrayList<>(scoreBoard.keySet());
		keys.sort(Collections.reverseOrder());
		
		List<Integer> values = scoreBoard.get(keys.get(0));
		values.sort(null);
		
		for (int ele : values) {
			System.out.print(ele + " ");
		}
		
		System.out.println();
	}
	
	
	static int[] scores;
	static Map<Integer, List<Integer>> scoreBoard;
	static int DP(int node) {
		if (scores[node] > 0) {
			return scores[node];
		}
		
		int sum = 1;
		for (int edge : edges[node]) {
			int edgeDP = DP(edge);
			scores[edge] = edgeDP;
			sum += edgeDP;
		}
		
		return sum;
	}
}
