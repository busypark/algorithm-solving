import java.util.*;

public class S3_1463 {
	static final int[] count = new int[1000001];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Arrays.fill(count, 2, N+1, Integer.MAX_VALUE);

		
		
		System.out.println(count[N]);
		sc.close();
	}
}
