// https://www.acmicpc.net/problem/13549

import java.io.*;
import java.util.*;

public class G5_13549 {
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringTokenizer st = new StringTokenizer(br.readLine());
		
		final int N = Integer.parseInt(st.nextToken());
		final int K = Integer.parseInt(st.nextToken());
		
		int found = Integer.MAX_VALUE;
		Set<Integer> discovered = new HashSet<>();
		Set<Integer> confirmed = new HashSet<>();
		PriorityQueue<TimeNum> pq = new PriorityQueue<>((tn1, tn2) -> tn1.time - tn2.time);
		
		discovered.add(N);
		pq.add(new TimeNum(0, N));
		while (!pq.isEmpty()) {
			TimeNum here = pq.poll();
			if (confirmed.contains(here.num)) {
				continue;
			}
			
			if (K <= here.num) {
				found = Math.min(found, here.time + (here.num - K));
				continue;
			}
			
			if (found <= here.num) {
				break;
			}
			
			pq.add(new TimeNum(here.time, here.num * 2));
			pq.add(new TimeNum(here.time + 1, here.num - 1));
			pq.add(new TimeNum(here.time + 1, here.num + 1));
			
			
			
			confirmed.add(here.num);
		}
		
		System.out.println(found);
	}
}

class TimeNum {
	int time, num;
	TimeNum(int time, int num) {
		this.time = time;
		this.num = num;
	}
}









