import java.io.*;
import java.util.*;

public class SW_4131 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	static int N, X;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					final int h = Integer.parseInt(st.nextToken());
					map[r][c] = h;
				}
			}
			
			int cases = 0;
			// investigate for rows
			for (int r=0; r<N; r++) {
				final int[] line = new int[N];
				for (int c=0; c<N; c++) {
					line[c] = map[r][c];
				}
				
				cases += (investigateLine(line)) ? 1 : 0;
			}
			
			// investigate for columns
			for (int c=0; c<N; c++) {
				final int[] line = new int[N];
				for (int r=0; r<N; r++) {
					line[r] = map[r][c];
				}
				
				cases += (investigateLine(line)) ? 1 : 0;
			}
			
			answer.append("#"+t+" "+cases+"\n");
		}
		
		System.out.println(answer);
	}
	
	static boolean investigateLine(int[] line) {
		boolean result = true;
		boolean[] ramps = new boolean[N];
		for (int i=0; i<N-1; i++) {
			switch (line[i+1] - line[i]) {
			case 0:
				// 이번 칸과 다음 칸의 높이가 동일
				break;
			case 1:
				// i+1에서 1계단 상승
				// i까지(j=0) X회의 평지가 있어야 함 & ramp 깔기
				for (int j=0; j<X; j++) {
					if (!isValid(i-j) || line[i] != line[i-j] || ramps[i-j]) {
						result = false;
						break;
					} else {
						ramps[i-j] = true;
					}
				}
				break;
			case -1:
				// i+1에서 1계단 하락
				// i+1부터 X회의 평지가 있어야 함 & ramp 깔기
				for (int j=1; j<=X; j++) {
					if (!isValid(i+j) || line[i+1] != line[i+j] || ramps[i+j]) {
						result = false;
						break;
					} else {
						ramps[i+j] = true;
					}
				}
				break;
			default:
				// 이번 칸과 다음 칸의 높이가 2 이상 차이남 -> 경사로로 커버 안 되므로 조사 결과 거짓
				result = false;
			}
			
			if (!result) break;
		}
		
		return result;
	}
	
	static boolean isValid(int idx) {
		return 0<=idx && idx < N;
	}
}













