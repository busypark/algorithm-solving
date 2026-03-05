// https://www.acmicpc.net/problem/13549
// 260304(수) 명예 A형

import java.io.*;
import java.util.*;

public class G5_13549 {
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringTokenizer st = new StringTokenizer(br.readLine());
		
		final int N = Integer.parseInt(st.nextToken());
		final int K = Integer.parseInt(st.nextToken());
		
		int found = Integer.MAX_VALUE;
		int[] dist = new int[200001];
		Set<Integer> discovered = new HashSet<>();
		Set<Integer> confirmed = new HashSet<>();
		PriorityQueue<Integer> pq = new PriorityQueue<>((tn1, tn2) -> dist[tn1] - dist[tn2]);
		
		discovered.add(N);
		pq.add(N);
		dist[N] = 0;
		while (!pq.isEmpty()) {
			int here = pq.poll();
			
			if (confirmed.contains(here)) {
				if (here == K) {
					found = dist[here];
					break;
				}
				continue;
			}
			
			if (K <= here) {
				dist[here] = dist[here] + here - K;
				confirmed.add(here);
				continue;
			}
			
			pq.add(here * 2);
			dist[here * 2] = dist[here];
			pq.add(here + 1);
			dist[here + 1] = dist[here] + 1;
			
			if (1 <= here) {
				pq.add(here - 1);
				dist[here - 1] = dist[here] + 1;
			}
			
			confirmed.add(here);
		}
		
		System.out.println(found);
	}
}









