import java.util.Scanner;

// https://www.acmicpc.net/workbook/view/2052

public class S3_15650 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int N = sc.nextInt();
			final int M = sc.nextInt();
			
			arr = new int[M];
			perm(N, M, 0, 0);
		}
	}
	
	static int[] arr;
	static void perm(int N, int M, int num, int depth) {
		if (depth == M) {
			for (int i=0; i<M; i++) {
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		} else {
			for (int i=num+1; i<=N; i++) {
				arr[depth] = i;
				perm(N, M, i, depth+1);
			}
		}
	}
}
