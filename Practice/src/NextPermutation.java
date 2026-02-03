import java.util.Scanner;

// 정수 N을 입력받고, [0, 1, 2, ..., N-1] 배열을 생성한 후 그것의 '다음 순열'을 하나씩 출력하는 메서드 작성
public class NextPermutation {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int N = sc.nextInt();
			final int[] arr = new int[N];
			for (int i=0; i<N; i++) arr[i] = i;
			
			
			int testCount = 10;
			printArr(arr);
			while (testCount > 0) {
				boolean np = nextPermutation(arr);
				if (np) {
					printArr(arr);
				} else {
					System.out.println("더 이상 다음 순열이 없습니다!");
					break;
				}
			}
		}
	}
	
	static boolean nextPermutation(int[] arr) {
		// 뒤에서부터 오름차순인 부분 찾기 (idx = 오름차순 시작되는 index = 다음 자릿수로 바뀔 곳)
		int idx;
		for (idx=arr.length-2; 0<=idx; idx--) {
			if (arr[idx] < arr[idx+1]) {
				break;
			}
		}
		
		if (idx == -1)
			return false;
		
		// 뒤에서부터 idx 자리의 숫자보다 큰 수 찾아내어 바꿈 -> 바꿔도 idx 뒷자리들은 여전히 내림차순
		int i;
		for (i=arr.length-1; 0<=i; i--) {
			if (arr[idx] < arr[i]) break;
		}
		
		int temp = arr[idx];
		arr[idx] = arr[i];
		arr[i] = temp;
		
		// idx 뒷자리들(+1)을 reverse하여 오름차순으로 바꿈
		for (int j=0; j<=(arr.length-1-idx)/2; j++) {
			int temp2 = arr[idx+1+j];
			arr[idx+1+j] = arr[arr.length-1-j];
			arr[arr.length-1-j] = temp2;
		}
			
		return true;
	}
	
	static void printArr(int[] arr) {
		for (int i=0; i<arr.length; i++)
			System.out.print(arr[i]+" ");
		System.out.println();
	}
}
