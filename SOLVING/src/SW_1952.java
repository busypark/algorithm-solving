import java.io.*;
import java.util.*;

public class SW_1952 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer;
	
	static int price1day;
	static int price1mon;
	static int price3mon;
	static int[] plan;
	static int from, to;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			price1day = Integer.parseInt(st.nextToken());
			price1mon = Integer.parseInt(st.nextToken());
			price3mon = Integer.parseInt(st.nextToken());
			minCost = Integer.parseInt(st.nextToken()); // 1년치 비용을 초기 최소 가격으로 설정
			
			from = -1;
			plan = new int[12];
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<12; i++) {
				plan[i] = Integer.parseInt(st.nextToken());
				if (from == -1 && plan[i] != 0) {
					from = i;
				}
			}
			
			to = 12;
			for (int i=11; 0<=i; i--) {
				if (to == 12 && plan[i] != 0) {
					to = i+1;
					break;
				}
			}
			
			if (0 <= from && to <= 12) {
				// from and to are valid : there's more than one plan
				final int max3mon = (to - from + 2) / 3;
				for (int mon3 = 0; mon3 <= max3mon; mon3 ++) {
					
				}
			}
			
			answer.append("#").append(t).append(" ").append(minCost).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static int minCost;
}
