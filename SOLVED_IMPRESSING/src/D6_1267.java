import java.io.*;
import java.util.*;

// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV18TrIqIwUCFAZN
/*
 * solution : V들의 나열 (최대 1000! 경우의 수이므로 매우 큰 수 -> efficiency, pruning)
 * 무조건 처음부터 끝까지 갈 수 있도록 주어짐(예외가 없음)
 * feasible한 경로 하나만 찾으면 됨(여러 가능한 해 중에 하나만 찾아도 정답)
 * 가장 직관적인 저장 방식은 간선의 출발점=index, 도착점=value 배열로 하는 2차원 배열
 * 결과적으로 1~V까지의 번호이므로 배열도 V+1개로 선언하는 것이 깔끔
 * A형 1번 푼 것(선수과목 문제)과 같은 전략으로 접근
 * 각 노드별 선수과목의 개수 저장(parentCount) 및 방문여부 저장(visited)
 * not visited && parentCount == 0인 노드에 대해 그것의 child의 parentCount를 하나 줄임
 * 모든 노드에 visit했다면 그것이 solution -> 출력하고 끝
 * 굳이 배열을 복제해야 하나?
 * 
 * 1차 : E*2만큼 반복문 돌렸음 -> 어차피 안에서 from, to 처리하므로 E만큼만 해도 됨
 * 2차 : StringBuilder answer를 생성 안 했음
 * 3차 : 오답 -> 한 번에 쭉 끝까지 가는 경우를 visited 배열로 거르지 않았음. 그냥 visited 없앰
 * 4차 : 오답(이전과 같아보임) -> 재귀를 안 넣어서 끝에 넣었음
 * 5차 : 에러(StackOverFlow) -> visited가 없으니 논리적으로 깔끔하지 않은 것 같음
 * 							  왜 parentCount==0만으로 감지가 안 되나?
 * 6차 : 성공 (50분 소요, 계속 강의 들으면서 풀어서 집중이 좀 안 됐음)
 * 
 * 다른 코드 참고 (실행시간 절반)
 * 1. List를 아예 안 쓰진 않아도 됨. 이 경우도 2차원 배열을 쓰니 메모리 낭비가 많았음
 * 2. 재귀함수 말고 Queue를 쓰니 visited 배열도 굳이 필요가 없었음
 */

public class D6_1267 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int V, E;
	static int[][] edges;
	static int[] nEdges;
	static final int nEdgeMax = 3000;
	
	public static void main(String[] args) throws IOException {
		for (int t=1; t<=10; t++) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			edges = new int[V+1][];
			nEdges = new int[V+1];
			parentCount = new int[V+1];
			
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<E; i++) {
				final int from = Integer.parseInt(st.nextToken());
				final int to = Integer.parseInt(st.nextToken());
				
				if (edges[from] == null) {
					edges[from] = new int[nEdgeMax];
				}
				
				edges[from][nEdges[from]++] = to;
				parentCount[to]++;
			}
			
			visited = new boolean[V+1];
			printAnswer = new StringBuilder();
			findPath();
			
			answer.append("#").append(t).append(" ").append(printAnswer).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static StringBuilder printAnswer;
	static boolean[] visited;
	static int[] parentCount;
	static void findPath() {
		// check if it's a complete solution
		int v = 1;
		for (; v < V+1; v++) {
			if (!visited[v]) break;
		}
		
		if (v == V+1) {
			// every node was visited -> complete
			return;
		}
		
		// NOT YET
		for (int i=1; i<=V; i++) {
			if (!visited[i] && parentCount[i] == 0) {
				visited[i] = true;
				printAnswer.append(i).append(" ");
				
				for (int j=0; j<nEdges[i]; j++) {
					parentCount[edges[i][j]]--;
				}
			}
		}
		
		findPath();
	}
}











