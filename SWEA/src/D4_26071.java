import java.io.*;
import java.util.*;

// https://swexpertacademy.com/main/code/userProblem/userProblemDetail.do?contestProbId=AZwmCVWq3uLHBIT3&categoryId=AZwmCVWq3uLHBIT3&categoryType=CODE&&&
// DFS로 풀었는데 확실히 가장자리/중간 경우들 나누는 부분에서 착오를 많이 한 듯.. 특히, 배열기준 양 끝을 곧바로 끝이라고 생각하는 습성이 있었는데, 그걸 from/to 변수로 극복했음

public class D4_26071 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N;
	static int[] blocks;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		StringBuilder answer = new StringBuilder();
		for (int t=1; t<=T; t++) {
			// input N and blocks
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			blocks = new int[N+1];
			
			st = new StringTokenizer(br.readLine());
			for (int n=1; n<=N; n++) {
				blocks[n] = Integer.parseInt(st.nextToken());
			}
			
			// search for the maximum score
			maxScore = 0;
			currScore = 0;
			visited = new boolean[N+1];
			searchMaxScore(0);
			
			answer.append("#"+t+" "+maxScore+"\n");
		}
		System.out.println(answer);
	}
	
	static int maxScore, currScore;
	static boolean visited[];
	static void searchMaxScore(int depth) {
		if (depth == N) {
			maxScore = Math.max(maxScore, currScore);
		} else if (depth == N-1) {
			int unvisited = -1;
			for (int i=1; i<=N; i++) {
				if (!visited[i]) {
					unvisited = i;
					break;
				}
			}
			
			visited[unvisited] = true;
			currScore += blocks[unvisited]; // 1개 남은 상황이므로 그것 자체가 점수에 더해짐
			searchMaxScore(depth+1);
			visited[unvisited] = false;
			currScore -= blocks[unvisited];
			
		} else if (depth == N-2) {
			int unvisited1 = -1;
			int unvisited2 = -1;
			for (int i=1; i<=N; i++) {
				if (!visited[i]) {
					if (unvisited1 == -1) {
						unvisited1 = i;
					} else {
						unvisited2 = i;
						break;
					}
				}
			}
			
			// 2개 남으면 최종적으로 둘 중 큰 블록이 두 번 만큼 더해짐
			final int currentIncrement = 2*Math.max(blocks[unvisited1], blocks[unvisited2]);
			currScore += currentIncrement;
			searchMaxScore(depth+2);
			currScore -= currentIncrement;
		} else {
			// 3개 이상의 블록이 남은 경우 모든 경우 탐색(가장자리 or 중간)
			// 가장자리 (왼쪽)
			int from;
			for (from=1; visited[from]; from++);
			if (!visited[from]) {
				visited[from] = true;
				int a;
				for (a=from+1; visited[a]; a++);
				final int currentIncrement = blocks[a];
				currScore += currentIncrement;
				searchMaxScore(depth+1);
				currScore -= currentIncrement;
				visited[from] = false;
			}
			
			// 가장자리 (오른쪽)
			int to;
			for (to=N; visited[to]; to--);
			if (!visited[to]) {
				visited[to] = true;
				int b;
				for (b=to-1; visited[b]; b--);
				final int currentIncrement = blocks[b];
				currScore += currentIncrement;
				searchMaxScore(depth+1);
				currScore -= currentIncrement;
				visited[to] = false;
			}
			
			// 중간 : 점수계산 주의! 인접한 두 블록의 곱!
			for (int i=from+1; i<=to-1; i++) {
				if (!visited[i]) {
					visited[i] = true;
					int a, b;
					for (a=1; visited[i-a]; a++);
					for (b=1; visited[i+b]; b++);
					final int currentIncrement = blocks[i-a] * blocks[i+b];
					currScore += currentIncrement;
					searchMaxScore(depth+1);
					currScore -= currentIncrement;
					visited[i] = false;
				}
			}
		}
	}
}








