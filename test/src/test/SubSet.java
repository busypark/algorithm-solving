package test;

import java.util.Arrays;

public class SubSet {
	public static void main(String[] args) {
		for (int i=2; i<=5; i++) {
			N = i;
			result = new int[N];
			
			//Arrays.fill(result, Integer.MAX_VALUE);

			System.out.println("\nN="+i);
			//subSet(0, 0);
			subsetSlot(0, 0);
		}
	}
	
	static int N;
	static int[] result; // 길이 N, 초기에는 모든 값이 Integer.MAX_VALUE
	static void subSet(int depth, int start) {
		if (start == N) {
			for (int ele : result) {
				if (ele == Integer.MAX_VALUE)
					break;
				
				System.out.print(ele + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i=start; i<N; i++) {
			result[depth] = i;
			subSet(depth+1, i+1);
			
			result[depth] = Integer.MAX_VALUE;
			subSet(depth, i+1);
		}
	}
	
	static void subsetSlot(int depth, int start) {
	    // ★ 마법의 순간: 함수에 들어오자마자 현재까지 채워진(depth) 배열을 출력하면
	    // 그것이 곧 하나의 부분집합입니다! (별도의 return 조건이 없습니다)
	    
	    System.out.print("[ ");
	    for (int i = 0; i < depth; i++) {
	        System.out.print(result[i] + " ");
	    }
	    System.out.println("]");

	    // 다음 자리를 채우기 위한 루프 (start부터 N-1까지)
	    for (int i = start; i < N; i++) {
	        result[depth] = i;             // 현재 자리(depth)에 i를 채워 넣음
	        subsetSlot(depth + 1, i + 1);  // 다음 자리를 채우러 깊숙이 들어감
	    }
	}
}
