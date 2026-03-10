import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1253
// 피드백 : 어느 수를 만들 수 있는 조합에 대해 생각하질 않아서 헤맸음

public class G4_1253 {
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		final long[] arr = new long[N];
		final Map<Long, List<Pair>> map = new HashMap<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			final long num = Long.parseLong(st.nextToken());
			arr[i] = num;
			for (int j=0; j<i; j++) {
				final long sum = arr[i] + arr[j];
				if (map.containsKey(sum)) {
					List<Pair> lstPair = map.get(sum);
					lstPair.add(new Pair(i, j));
				} else {
					List<Pair> lstPair = new ArrayList<>();
					lstPair.add(new Pair(i, j));
					map.put(sum, lstPair);
				}
			}
		}
		
		int goods = 0;
		for (int i=0; i<N; i++) {
			if (map.containsKey(arr[i])) {
				for (Pair pair : map.get(arr[i])) {
					if (pair.a != i && pair.b != i) {
						goods ++;
						break;
					}
				}
			}
				
		}
		
		System.out.println(goods);
	}
}

class Pair {
	int a, b;
	Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}
}
