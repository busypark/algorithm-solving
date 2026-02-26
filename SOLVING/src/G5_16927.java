/*
 * kernel 개수 = (min(W, H) + 1) / 2
 * (r, c) => (k) : 가까운 변과의 거리 (최소 0~최대 kernel 개수-1)
 * 				 : min(min(r, H-1-r), min(c, W-1-c))
 * (k) => kernelSize : k kernel에 포함된 원소 개수는?
 * 					 = 2*(W-2*k)+2*(H-2*k)-4
 * (r, c) => which corner or which edge (총 8가지)
 ** 이것에 따라 kernel별 배열의 알맞은 자리(0 ~ kernelSize-1)에 채움
 * kernel이 모두 입력된 후에야 다음의 newKernel 구성 가능
 * newKernel[k][0 ~ kernelSize-1] : 새로운 kernel 구성
 *  : newKernel[k][i] = kernel[k][i + R % kernelSize]
 * 이제 출력만 하면 됨. 근데 newKernel index 기준이 아니라 입력때처럼 (r, c) 기준 
 * 어차피 위에서 (r, c) => which corner or which edge (총 8가지) 구했음
 ** 각 위치에 맞는 kernel에 접근해서 값을 뽑아와서 출력하면 끝
 */

// https://www.acmicpc.net/problem/16927

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class G5_16927 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int H = Integer.parseInt(st.nextToken());
		final int W = Integer.parseInt(st.nextToken());
		final int R = Integer.parseInt(st.nextToken());
		
		final int nKernel = (Math.min(W,  H) + 1) / 2;
		Function<Integer, Integer> kernelSize = (k) -> 2*(W-2*k)+2*(H-2*k)-4;
		
		final int[][] map = new int[H][W];
		
		for (int r=0; r<H; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<W; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
	}
}




