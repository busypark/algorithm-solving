import java.io.*;
import java.util.*;

// A형 1번 : 선수과목이 주어질 때, 그것을 모두 수강하기 위해 필요한 최소 학기 수는?
// 전략 : visited 배열 외에 '후수 과목'의 count를 처음에 센 후 0이 되도록 깎아나간 것이 핵심적이었음
/*
 * 어려움
 * 1. A형 시험장에서는 이런 풀이가 안 떠올랐고, 후수 과목을 굳이 전부 나열하려 했음
 * 1-1. 정보를 일일이 파편화시키는 건 좋았지만, 그것에서 진짜 필요한 정보만 압축하는 능력이 부족했음
 * 1*. 일단 문제가 돌아가는 걸 이해했으면, 반복되는 원소들은 부분적으로 개수화해서 차원을 줄여보기
 * 2. 어이없는 실수. 변수 하나로 다 처리해보려고 이상하게 코드 작성했다가 ++가 for 안에도 있다는 걸 잊었음
 * 2-1. 그러나 이런 실수는 언제든 나올 수 있음.
 * 2*. 실수를 발견하려면 결국 한 구절마다 다시 생각해야 함(눈으로만 읽으면 절대 못 잡음)
 * 3. 후수 과목 개수 배열을 백업 버전을 써야 한다는 개념을 바로 생각해내진 못 했음
 * 3-1. 데이터의 시점에 따른 상태에 대해 면밀하게 생각하지 않았음
 * 3*. 한 번의 작업이 어디까지 영향을 미치는지 등을 천천히 생각해봐야 할 듯
 * 4. 굳이 종이/패드에 모든 걸 다 적을 필요는 없음
 * 4*. 주석이 더 빠를 때가 많음. 그림 그려야 할 때에만 종이/패드 사용
 */

public class SW_A_260219_1 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int N;
	static int[][] preNumbers;
	
	public static void main(String args[]) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			preNumbers = new int[N][];
			postCount = new int[N];
			
			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());
				final int countPrerequisites = Integer.parseInt(st.nextToken());
				preNumbers[n] = new int[countPrerequisites];
				for (int i=0; i<countPrerequisites; i++) {
					// shift 1~N to 0~N-1
					final int preNum = Integer.parseInt(st.nextToken()) - 1;
					preNumbers[n][i] = preNum;
					postCount[preNum] ++;
				}
			}
			
			numSemester = 0;
			visited = new boolean[N];
			calculateSemester();
			
			// append to answer
			answer.append("#").append(t).append(" ").append(numSemester).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static int numSemester;
	static int[] postCount;
	static boolean[] visited;
	static void calculateSemester() {
		/*
		for (int i=0; i<N; i++) {
			System.out.print(" "+postCount[i] + "(" + (visited[i]) + " )");
		}
		System.out.println("");
		*/
		
		// reached to the valid solution
		int solutionCount = 0;
		for (int i=0; i<N; i++) {
			if (visited[i] && postCount[i] == 0) {
				solutionCount++;
			} else break;
		}
		
		if (solutionCount == N) {
			return;
		}
		
		// NOT YET -> continue to search
		int count = 0;
		int toGo = 0;
		
		int[] newPost = new int[N];
		for (int i=0; i<N; i++) {
			newPost[i] = postCount[i];
		}
		
		for (int i=0; i<N; i++) {
			if (postCount[i] > 0)
				toGo++;
			
			if (!visited[i] && postCount[i] == 0) {
				count++;
				
				visited[i] = true;
				//System.out.println("preNumbers["+i+"].length = "+preNumbers[i].length);
				for (int j=0; j<preNumbers[i].length; j++) {
					newPost[preNumbers[i][j]]--;
				}
			}
		}

		numSemester++;
		postCount = newPost;
		
		if (count == 0 && toGo > 0) { 
			numSemester = -1;
			return;
		}
		
		calculateSemester();
	}
}

/*

4    
4
1 3
2 1 3
1 4
0
2
1 2
1 1
4
1 2
1 3
1 4
0
4
1 2
1 3
1 1
0

# 출력 : 4 -1 4 -1

*/

/* Gemini 테스트케이스

5
5
1 2
1 3
1 4
1 5
0
6
2 2 3
1 4
1 4
1 5
1 6
0
5
1 2
1 3
1 1
0
0
7
1 2
1 3
1 4
1 1
0
0
2 5 6
3
0
0
1 2

# 출력 : 5 5 -1 -1 2
*/
