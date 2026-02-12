import java.util.Scanner;
import java.util.Arrays;

public class S3_15654 {
	static int N, M;
	static int[] arr, answerArr;
	static final StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			
			arr = new int[N];
			answerArr = new int[M];
			visited = new boolean[N];
			for (int n=0; n<N; n++) {
				arr[n] = sc.nextInt();
			}
			Arrays.sort(arr);
			
			getPermM(0);
			
			System.out.println(answer);
		}
	}
	
	static boolean[] visited;
	static void getPermM(int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			answer.append("\n");
		} else {
			for (int i=0; i<N; i++) {
				if (!visited[i]) {
					visited[i] = true;
					answerArr[depth] = arr[i];
					getPermM(depth+1);
					visited[i] = false;
				}
			}
		}
	}
}
