import java.util.Scanner;

public class D3_1206 {
	public static Scanner sc = new Scanner(System.in);
	public static int N;
	public static int[] heights; // 0 <= heights[i] <= 255
	
	public static void main(String[] args) {
		final int T = 10;
		for (int t=1; t<=T; t++) {
			/* input heights */
			N = sc.nextInt(); // 4 <= N <= 1000
			heights = new int[N];
			for (int i=0; i<N; i++) {
				heights[i] = sc.nextInt();
			}
			
			/* count */
			int count = 0;
			for (int i=0; i<N; i++) {
				for (int h=1; h<=heights[i]; h++) {
					count += (
							   (isValid(i-1) && heights[i-1] < h)
							&& (isValid(i-2) && heights[i-2] < h)
							&& (isValid(i+1) && heights[i+1] < h)
							&& (isValid(i+2) && heights[i+2] < h)
							) ? 1 : 0;
				}
			}
			
			System.out.println("#"+t+" "+count);
		}
	}
	
	public static boolean isValid(int idx) {
		return (0<=idx && idx<N);
	}
}
