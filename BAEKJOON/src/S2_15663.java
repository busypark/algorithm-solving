import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

// 왜 미리 givenArr를 distinct하게 뽑은 후에 하는 건 안 되고, givenArr 자체를 재귀함수 안에서 'last' 변수를 이용해 continue하는 건 되나?

public class S2_15663 {
	static int N, M;
	static StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		givenArr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			givenArr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(givenArr);
		
		distinctArr = new int[N];
		distinctArr[0] = givenArr[0];
		for (int i=0; i<N; i++) {
			if (1<=i && distinctArr[end] != givenArr[i]) {
				distinctArr[++end] = givenArr[i];
			}
		}
		
		answerArr = new int[M];
		visited = new boolean[end+1];
		repPermM(0);
		
		System.out.println(answer);
	}
	
	static int[] givenArr, distinctArr, answerArr;
	static int end = 0;
	static boolean[] visited;
	static void repPermM(int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			answer.append("\n");
		} else {		
			// 재영님 코드
			int last = -1;
			for (int i=0; i<N; i++) {
				if (last == givenArr[i]) continue;
				if (!visited[i]) {
					visited[i] = true;
					answerArr[depth] = givenArr[i];
					last = givenArr[i];
					repPermM(depth+i);
					visited[i] = false;
				}
			}
			
			/* 원래 했던 코드
			for (int i=0; i<=end; i++) {
				if (!visited[i]) {
					visited[i] = true;
					answerArr[depth] = distinctArr[i];
					repPermM(depth+1);
					visited[i] = false;
				}
			}
			*/
		}
	}
}
