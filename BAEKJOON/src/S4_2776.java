import java.io.*;
import java.util.*;

// 퇴근 길에 손으로 쭉 코드 적고 집에서 그대로 냈는데 바로 정답
// 메모리/시간 둘 다 거의 최적인 것 같긴 한데, 둘 다 낮은 사람도 있음
// 그냥 거의 똑같은 거 같은데 왜 더 낮은지...

public class S4_2776 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int t=0; t<T; t++) {
			int N = Integer.parseInt(br.readLine());
			
			st = new StringTokenizer(br.readLine());
			int[] note = new int[N];
			for (int i=0; i<N; i++)
				note[i]	= Integer.parseInt(st.nextToken());
			
			Arrays.sort(note);
			
			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<M; i++) {
				int search = Integer.parseInt(st.nextToken());
				boolean searched = binarySearch(note, search);
				answer.append((searched)? 1:0).append("\n");
			}
		}
		
		System.out.println(answer);
	}
	
	static boolean binarySearch(int[] note, int search) {
		int left = 0;
		int right = note.length - 1;
		
		while (left <= right) {
			int mid = (left + right) / 2;
			if (note[mid] == search)
				return true;
			
			if (search < note[mid])
				right = mid - 1;
			else
				left = mid + 1;
		}
		
		return false;
	}
}
