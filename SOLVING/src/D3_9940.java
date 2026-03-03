import java.io.*;
import java.util.*;

// 개 억까.. 로직 맞는데 출력 형식 마지막에 개행문자 넣었다고 틀림

public class D3_9940 {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		
		final int T = sc.nextInt();
		for (int t=1; t<=T; t++) {
			final int N = sc.nextInt();
			
			boolean result = true;
			boolean[] arr = new boolean[N+1];
			for (int i=0; i<N; i++) {
				int num = sc.nextInt();
		
				if (arr[num]) {
					result = false;
					break;
				}
                arr[num] = true;
			}
			
            String a;
            if (result) a = "Yes";
            else a = "No";
			System.out.println("#"+t+" "+a);
		}
		
	}
}
