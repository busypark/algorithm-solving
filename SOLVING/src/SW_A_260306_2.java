// https://www.acmicpc.net/problem/17472
// G1_17472 (A형에서는 섬 형태가 직사각형이었으나 원본은 ㄱ, ㄴ 형태도 가능)

import java.io.*;
import java.util.*;

public class SW_A_260306_2 {
	static int H, W;
	static boolean[][] map;
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N(H), M(W), and map[H+2][W+2] -> padding
		st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		map = new boolean[H+2][W+2];
		for (int r=1; r<=H; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=1; c<=W; c++) {
				final int TF = Integer.parseInt(st.nextToken());
				map[r][c] = (TF == 1);
			}
		}
		
		// search for islands(nodes) and their edges
		search();
		
		
	}
	
	static boolean[][] discovered; // whether (r, c) enrollled as an island or a blank
	static void search() {
		discovered = new boolean[H+2][W+2];
		
		for (int r=1; r<=H; r++) {
			for (int c=1; c<=H; c++) {
				if (!discovered[r][c] && !map[r][c-1] && map[r][c]) {
					// undiscovered island starting position found -> discover with recursive function
					discover(r, c);
				} else {
					// blank found -> discovered
					discovered[r][c] = true;
				}
			}
		}
	}
	
	static void discover(int iR, int iC) {
		discovered[iR][iC] = true;
		
		// is this on the bottom border?
		if (!map[iR+1][iC]) {
			// shoot for the south
			for (int r=iR+2; ; r++) {
				if (r == H) {
					// wall detected -> return
					return;
				}
				
				if ()
			}
		}
	}
}















