import java.io.*;
import java.util.*;

public class SW_4013 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int K;
	static List<Integer>[] magnetics;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			K = Integer.parseInt(st.nextToken());
			
			magnetics = new List[4];
			for (int i=0; i<4; i++) {
				magnetics[i] = new LinkedList<Integer>();

				st = new StringTokenizer(br.readLine());
				for (int j=0; j<8; j++) {
					magnetics[i].addLast(Integer.parseInt(st.nextToken()));
				}
			}
			
			for (int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());
				final int idxMagnetic = Integer.parseInt(st.nextToken()) - 1; // shift 1~4 to 0~3
				final int dir = Integer.parseInt(st.nextToken()); // 1(clockwise) or -1(counter-)
				
				/* extract rotate candidates */
				final int[] directions = new int[4];
				directions[idxMagnetic] = dir;
				// right wheels
				for (int i=idxMagnetic+1; i<4; i++) {
					List<Integer> left = magnetics[i-1];
					List<Integer> right = magnetics[i];
					if (left.get(2) == right.get(6)) {
						break;
					}
					
					directions[i] = -directions[i-1];
				}
				
				// left wheels
				for (int i=idxMagnetic-1; 0<=i; i--) {
					List<Integer> left = magnetics[i];
					List<Integer> right = magnetics[i+1];
					if (left.get(2) == right.get(6)) {
						break;
					}
					
					directions[i] = -directions[i+1];
				}
				
				/* rotate */
				for (int i=0; i<4; i++) {
					List<Integer> mag = magnetics[i];
					switch (directions[i]) {
					case 1:
						mag.addFirst(mag.removeLast());
						break;
					case -1:
						mag.addLast(mag.removeFirst());
						break;
					}
				}
				
				//answer.append("<k=").append(k).append("> magIdx=").append(idxMagnetic).append(" dir=").append(dir).append("\n");
				//printMagnetics();
			}
			
			int score = 0;
			for (int i=0; i<4; i++) {				
				score += (int)magnetics[i].get(0) * (int)Math.pow(2, i);
			}
			
			answer.append("#").append(t).append(" ").append(score).append("\n");
		}
		
		System.out.println(answer);
	}
	
	static void printMagnetics() {
		for (int i=0; i<4; i++) {
			answer.append(" ").append(i).append(": ");
			for (int j=0; j<8; j++) 
				answer.append(" ").append(magnetics[i].get(j));
			answer.append("\n");
		}
	}
}
