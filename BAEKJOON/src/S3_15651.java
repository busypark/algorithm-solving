import java.util.Scanner;

public class S3_15651 {
	static int N;
	static int M;
	static final StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			
			arr = new int[M];
			repPerm(0);
			
			System.out.println(answer);
		}
	}
	
	static int[] arr;
	static void repPerm(int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(arr[i] + " ");
			}
			answer.append("\n");
		} else {
			for (int i=1; i<=N; i++) {
				arr[depth] = i;
				repPerm(depth+1);
			}
		}
	}
}
