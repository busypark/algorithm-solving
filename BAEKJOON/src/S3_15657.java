import java.util.Scanner;
import java.util.Arrays;

public class S3_15657 {
	static StringBuilder answer = new StringBuilder();
	static int N, M;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		givenArr = new int[N];
		for (int i=0; i<N; i++) {
			givenArr[i] = sc.nextInt();
		}
		
		Arrays.sort(givenArr);
		
		answerArr = new int[M];
		repPermAscending(0, 0);
		System.out.println(answer);
		
		sc.close();
	}
	
	static int[] givenArr, answerArr;
	static void repPermAscending(int depth, int idx) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(answerArr[i] + " ");
			}
			answer.append("\n");
		} else {
			for (int i=idx; i<N; i++) {
				answerArr[depth] = givenArr[i];
				repPermAscending(depth+1, i);
			}
		}
	}
}
