import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

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
		
		answerArr = new int[M];
		visited = new boolean[N+1];
		repPermM(0);
		
		System.out.println(answer);
	}
	
	static int[] givenArr, answerArr;
	static int end = 0;
	static boolean[] visited;
	static void repPermM(int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			answer.append("\n");
		} else {		
			int last = -1;
			for (int i=0; i<N; i++) {
				if (last == givenArr[i]) continue;
				if (!visited[i]) {
					visited[i] = true;
					answerArr[depth] = givenArr[i];
					last = givenArr[i];
					repPermM(depth+1);
					visited[i] = false;
				}
			}
		}
	}
}
