import java.io.*;
import java.util.*;

public class SW_4123 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int N;
	static int[] opCounts; // [0]~[3] counts for + - * /
	static int[] numbers; // [0]~[N-1]
	static int[] operators; // [0]~[N-1]
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			/* input N, counts for 4 operators, N numbers */
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			opCounts = new int[4];
			operators = new int[N-1];
			int p = 0;
			for (int i=0; i<4; i++) {
				// input 4 kinds of counts for operators
				opCounts[i] = Integer.parseInt(st.nextToken());
				
				for (int j=0; j<opCounts[i]; j++) {
					operators[p++] = i;
				}
			}

			st = new StringTokenizer(br.readLine());
			numbers = new int[N];
			for (int i=0; i<N; i++) {
				// input N numbers
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			/* initialize before processing */
			operMax = Integer.MIN_VALUE;
			operMin = Integer.MAX_VALUE;
			visited = new boolean[N-1];
			
			/* begin dfs */
			investigate(0, numbers[0]);
			
			/* adding the answer into StringBuilder */
			diffMaxMin = operMax - operMin;
			answer.append("#"+t+" "+diffMaxMin+"\n");
		}
		
		System.out.println(answer);
	}
	
	static int diffMaxMin = 0;
	static int operMax, operMin;
	static boolean[] visited; // for N-1 operators
	static void investigate(int depth, int result) {
		if (depth == N-1) {
			// N-1개의 연산자를 모두 조사한 상태이므로 결과 업데이트
			operMax = Math.max(operMax, result);
			operMin = Math.min(operMin, result);
			
			//answer.append("  result: "+result+"\n");
		} else {
			// 이 방식이 기존 방식보다 훨씬 빠르다. 왜??
			for (int i=0; i<4; i++) {
				if (opCounts[i] > 0) {
					opCounts[i] --;
					
					final int calculate;
					switch (i) {
					case 0:
						calculate = result + numbers[depth+1];
						break;
					case 1:
						calculate = result - numbers[depth+1];
						break;
					case 2:
						calculate = result * numbers[depth+1];
						break;
					default:
						calculate = result / numbers[depth+1];
						break;
					}
					
					investigate(depth+1, calculate);
					
					opCounts[i] ++;
				}
			}
			
			/* 기존 방식
			for (int i=0; i<N-1; i++) {
				if (!visited[i]) {
					visited[i] = true;
					final int calculate;
					switch (operators[i]) {
					case 0:
						calculate = result + numbers[depth+1];
						break;
					case 1:
						calculate = result - numbers[depth+1];
						break;
					case 2:
						calculate = result * numbers[depth+1];
						break;
					default:
						calculate = result / numbers[depth+1];
						break;
					}
					
					investigate(depth+1, calculate);
					visited[i] = false;
				}
			}
			*/
		}
	}
}





















