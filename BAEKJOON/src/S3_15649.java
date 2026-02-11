import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// https://www.acmicpc.net/workbook/view/2052
// 전략 : 

public class S3_15649 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int N = sc.nextInt();
			final int M = sc.nextInt();
			
			final int[] arr = new int[N];
			
			// 전략 : 1~N 중 M개를 뽑는 순열을 구하기 위해 1~N 중 하나씩 제거하는 시행을 N-M번 재귀적으로 수행하다가 N-M번에 도달하면 출력하고 다시 제거한 것 넣기
			
			removeAndPrint(arr, N, M, 1);
		}
	}
	
	static void removeAndPrint(int[] arr, int N, int M, int count) {
		if (M == count - 1) {
			List<Element_S3_15649> Element_S3_15649s = new ArrayList<>();
			for (int i=0; i<N; i++) {
				if (arr[i] > 0)
					Element_S3_15649s.add(new Element_S3_15649(i, arr[i]));
			}
			
			Element_S3_15649s.sort(new Comparator<Element_S3_15649>() {
				@Override
				public int compare(Element_S3_15649 e1, Element_S3_15649 e2) {
					return e1.value - e2.value;
				}
			});
			
			for (int i=0; i<M; i++) {
				System.out.print((Element_S3_15649s.get(i).idx+1)+" ");
			}
			
			System.out.println();
		} else {
			for (int i=0; i<N; i++) {
				if (arr[i] == 0) {
					arr[i] = count;
					removeAndPrint(arr, N, M, count+1);
					arr[i] = 0;
				}
			}
		}
	}
}

class Element_S3_15649 {
	int idx, value;
	Element_S3_15649(int idx, int value) {
		this.idx = idx;
		this.value = value;
	}
}
