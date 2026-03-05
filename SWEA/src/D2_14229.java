import java.io.*;
import java.util.*;

public class D2_14229 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		final int mil = 1000000;
		final PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder()); // 최대 힙
		final PriorityQueue<Integer> higher = new PriorityQueue<>(); // 최소 힙
		int mid = Integer.parseInt(st.nextToken());
		
		for (int i=1; i<mil; i++) {
			final int num = Integer.parseInt(st.nextToken());
			if (num < mid) {
				lower.offer(num);
			} else if (mid < num) {
				higher.offer(num);
			} else {
				if (lower.size() < higher.size()) {
					lower.offer(num);
				} else {
					higher.offer(num);
				}
			}
			
			if (lower.size()+1 < higher.size()) {
				lower.offer(mid);
				mid = higher.poll();
			} else if (lower.size() > higher.size()+1) {
				higher.offer(mid);
				mid = lower.poll();
			}
		}
		
		System.out.println(higher.peek());
	}
}
