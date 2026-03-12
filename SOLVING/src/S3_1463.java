import java.util.*;

public class S3_1463 {
	static final int[] count = new int[1000001];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Arrays.fill(count, 1, N+1, Integer.MAX_VALUE);
		count[N] = 0;
		recursive(N);
		
		System.out.println(count[1]);
	}
	
	static int[] re = new int[3];
	static void recursive(int N) {
		if (N == 1) return;
		
		Arrays.fill(re, Integer.MAX_VALUE);
		int i = 0;
		if (N % 3 == 0) re[i++] = (N / 3);
		if (N % 3 == 0) re[i++] = (N / 2);
		re[i++] = (N - 1);
		
		for (int ele : re) {
			if (ele == Integer.MAX_VALUE) break;
			count[ele] = Math.min(count[ele], count[N]+1);
		}
		
		for (int ele : re) {
			if (ele == Integer.MAX_VALUE) break;
			recursive(ele);
		}
	}
}
