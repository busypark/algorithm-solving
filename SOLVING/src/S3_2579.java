/*
 * 점화식? An까지의 최대합 = Max(A[n-2], A[n-1]) + score[n]
 */

// https://www.acmicpc.net/problem/2579

import java.io.*;
import java.util.*;

public class S3_2579 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N;
	
	public static void main(String[] args) throws IOException {
		// input N, stair
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int[] stair = new int[N];
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			stair[i] = Integer.parseInt(st.nextToken());
		}
		
		// 
		
		System.out.println(cumScore[N-1]);
	}
}
