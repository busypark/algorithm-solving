import java.io.*;
import java.util.*;

// 완료 (25분 소요)

public class S1_2841 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] arg) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		final int P = Integer.parseInt(st.nextToken());
		
		final List[] lines = new List[6];
		for (int i=0; i<6; i++) {
			lines[i] = new ArrayList<Integer>();
		}

		int answer = 0;
		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			final int numLine = Integer.parseInt(st.nextToken()) - 1; // shift as -1
			final int numPret = Integer.parseInt(st.nextToken());
			
			List<Integer> thisLine = lines[numLine];
			if (!thisLine.isEmpty()) {
				if (thisLine.get(thisLine.size()-1) < numPret) {
					thisLine.add(numPret);
					answer++;
				} else if (thisLine.get(thisLine.size()-1) == numPret) {
					// skip (no push)
				} else {
					while (!thisLine.isEmpty() && thisLine.get(thisLine.size()-1) > numPret) {
						thisLine.remove(thisLine.size()-1);
						answer++;
					}
					
					if (!thisLine.isEmpty()) {
						if (thisLine.get(thisLine.size()-1) < numPret) {
							thisLine.add(numPret);
							answer++;
						}
					} else {
						thisLine.add(numPret);
						answer++;
					}
				}
			} else {
				// not contained : push
				thisLine.add(numPret);
				answer++;
			}
			
			//System.out.print("## ["+numLine+"] :");
			//for (int i=0; i<thisLine.size(); i++)
			//	System.out.print(" "+thisLine.get(i));
			//System.out.println();
		}

		System.out.println(answer);
	}
}
