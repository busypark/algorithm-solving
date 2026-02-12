import java.util.Scanner;
import java.util.Arrays;

public class S3_15656 {
	static int N, M;
	static final StringBuilder answer = new StringBuilder();
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			
			givenArr = new int[N];
			for (int n=0; n<N; n++) {
				givenArr[n] = sc.nextInt();
			}
			
			Arrays.sort(givenArr);
			
			answerArr = new int[M];
			repPermM(0);
			System.out.println(answer);
		}
	}
	
	static int[] givenArr, answerArr;
	static void repPermM(int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			answer.append("\n");
		} else {
			for (int i=0; i<N; i++) {
				answerArr[depth] = givenArr[i];
				repPermM(depth+1);
			}
		}
	}
}
