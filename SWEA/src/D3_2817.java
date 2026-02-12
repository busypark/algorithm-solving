import java.util.Scanner;

public class D3_2817 {
	static int count;
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt();
				final int K = sc.nextInt();
				final int[] arr = new int[N];
				for (int n=0; n<N; n++) {
					arr[n] = sc.nextInt();
				}
				
				count = 0;
				getCount(arr, N, K, 0, 0);
				System.out.println("#" + t + " " + count);
			}
		}
	}
	
	static void getCount(int[] arr, int N, int K, int sum, int depth) {
		if (depth == N) {
			if (sum == K) count++;
		} else {
			if (sum+arr[depth] <= K)
				getCount(arr, N, K, sum+arr[depth], depth+1);
			
			if (sum <= K)
				getCount(arr, N, K, sum, depth+1);
		}
	}
}
