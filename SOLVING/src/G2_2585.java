import java.io.*;
import java.util.*;

// 아이디어 : MST가 아니라, 산술평균 길이에 가장 근접한 엣지 순으로 Kruskal 하면 되지 않을까? Greedy하게

public class G2_2585 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[][] edgeMat = new int[N][N];
		for (int n=0; n<N; n++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			for (int m=0; m<n; m++) {
				
			}
		}
	}
}
