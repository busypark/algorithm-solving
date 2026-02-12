import java.util.Scanner;

// https://www.acmicpc.net/workbook/view/2052

public class S3_15652 {
	static int N, M;
	static final StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			
			arr = new int[M];
			repPermAscending(0, 1);
			
			System.out.println(answer);
		}
	}
	
	static int[] arr;
	static void repPermAscending(int depth, int num) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				answer.append(arr[i] + " ");
			}
			
			answer.append("\n");
		} else {
			for (int i=num; i<=N; i++) {
				arr[depth] = i;
				repPermAscending(depth+1, i);
			}
		}
	}
}
