import java.io.*;
import java.util.*;

// A형 1번 : 선수/후수과목 

public class SW_A_260219_1 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	
	static int[][] preNumbers;
	static List<Integer>[] postNumbers;
	static List<Integer> entries, finals;
	
	public static void main(String args[]) throws IOException {
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			final int N = Integer.parseInt(st.nextToken());
			preNumbers = new int[N][];
			postNumbers = new List[N];
			entries = new ArrayList<Integer>();
			finals = new ArrayList<Integer>();

			for (int n=0; n<N; n++) {
				postNumbers[n] = new ArrayList<Integer>();
			}
			
			for (int n=0; n<N; n++) {
				st = new StringTokenizer(br.readLine());
				final int countPrerequisites = Integer.parseInt(st.nextToken());
				preNumbers[n] = new int[countPrerequisites];
				for (int i=0; i<countPrerequisites; i++) {
					// shift 1~N to 0~N-1
					final int preNum = Integer.parseInt(st.nextToken()) - 1;
					preNumbers[n][i] = preNum;
					postNumbers[preNum].add(n);
				}
			}
			
			int maxSemester = -1;
			
			// append to answer
			answer.append("#").append(t).append(" ").append(maxSemester).append("\n");
		}
		
		System.out.println(answer);
	}
}

/*

4    
4
1 3
2 1 3
1 4
0
2
1 2
1 1
4
1 2
1 3
1 4
0
4
1 2
1 3
1 1
0

# 출력 -> 4 -1 4 -1

*/