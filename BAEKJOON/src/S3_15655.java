import java.util.Scanner;
import java.util.Arrays;

// https://www.acmicpc.net/problem/15655

public class S3_15655 {
	static int N, M;
	static final StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			arr = new int[N];
			answerArr = new int[M];
			
			for (int n=0; n<N; n++) {
				final int num = sc.nextInt();
				arr[n] = num;
			}
			
			Arrays.sort(arr);
			permMAscending(0, -1);
			
			System.out.println(answer);
		}
	}
	
	static int[] arr, answerArr;
	static void permMAscending(int depth, int idx) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			
			answer.append("\n");
		} else {
			for (int i=idx+1; i<N; i++) {
				answerArr[depth] = arr[i];
				permMAscending(depth+1, i);
			}
		}
	}
}
