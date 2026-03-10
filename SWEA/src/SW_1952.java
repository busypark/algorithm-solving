import java.io.*;
import java.util.*;

public class SW_1952 {
	static int p1day, p1month, p3month;
	static int[] plan = new int[13];
	static int minCost;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input fines and plan
			st = new StringTokenizer(br.readLine());
			p1day = Integer.parseInt(st.nextToken());
			p1month = Integer.parseInt(st.nextToken());
			p3month = Integer.parseInt(st.nextToken());
			minCost = Integer.parseInt(st.nextToken());
			
			int dayCount = 0;
			st = new StringTokenizer(br.readLine());
			for (int i=1; i<=12; i++) {
				plan[i] = Integer.parseInt(st.nextToken());
				dayCount += plan[i];
			}
			
			minCost = Math.min(minCost, dayCount * p1day);
			
			// backtrack
			curCost = 0;
			backtrack(-1);
			
			answer.append("#").append(t).append(" ").append(minCost).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int curCost;
	static void backtrack(int depth) {
		if (minCost <= curCost) {
			// prune
			return;
		}
		
		if (12 <= depth) {
			// every month calculated
			minCost = Math.min(minCost, curCost);
			return;
		}
		
		final int depthNow = depth + 1;
		final int planNow = plan[depthNow];
		
		// NOT YET
		if (planNow == 0) {
			// no plan for now -> skip
			backtrack(depthNow);
			return;
		}
		
		// try 1 day plan for depth+1
		curCost += plan[depthNow] * p1day;
		backtrack(depthNow);
		curCost -= plan[depthNow] * p1day;
		
		// try 1 month plan for depth+1
		curCost += p1month;
		backtrack(depthNow);
		curCost -= p1month;
		
		// try 3 months plan for depth+1
		curCost += p3month;
		backtrack(depth + 3);
		curCost -= p3month;
	}
}













