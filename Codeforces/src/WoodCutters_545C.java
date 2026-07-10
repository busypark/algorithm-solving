import java.io.*;
import java.util.*;

public class WoodCutters_545C {
	enum State {
		left, right, no;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		State[] dp = new State[n];
		dp[0] = State.left;
		
		st = new StringTokenizer(br.readLine());
		int xPrev = Integer.parseInt(st.nextToken());
		int hPrev = Integer.parseInt(st.nextToken());
		
		int count = 1;
		for (int i=1; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			
			if (dp[i-1] == State.right) {
				if (xPrev + hPrev < x - h) {
					dp[i] = State.left;
				} else if (xPrev + hPrev < x) {
					dp[i] = State.right;
				} else {
					// infeasible
					dp[i-1] = State.no;
					count --;
					
					if (xPrev < x-h) {
						dp[i] = State.left;
					} else {
						dp[i] = State.right;
					}
				}
			} else {
				if (xPrev < x-h) {
					dp[i] = State.left;
				} else {
					dp[i] = State.right;
				}
			}
			
			if (dp[i] != State.no) {
				count ++;
			}
			
			xPrev = x;
			hPrev = h;
		}
		
		System.out.println(count);
	}
}
