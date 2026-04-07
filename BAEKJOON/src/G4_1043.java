import java.io.*;
import java.util.*;

// 옛날에 푼 거(old)보다 코드 길이는 1/3 수준인데 실행시간은 오히려 108ms < 112ms
// 대체 옛날에 어떻게 푼 거지?
// 유니온 파인드로 해석하니 쉽긴 쉬웠음. 근데 host와 prophet 중 하나의 root만 비교하면 된다는 개념을 떠올리는 데 오래 걸림

public class G4_1043 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int[] parent = new int[N+1];
		for (int i=0; i<N; i++) {
			parent[i] = i;
		}
		
		int M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int nProphet = Integer.parseInt(st.nextToken());
		int prevProphet = 0;
		for (int i=0; i<nProphet; i++) {
			int ph = Integer.parseInt(st.nextToken());
			if (1 <= i) {
				union(parent, prevProphet, ph);
			}
			prevProphet = ph;
		}
		
		int[] hosts = new int[M];
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int nAttend = Integer.parseInt(st.nextToken());
			int host = Integer.parseInt(st.nextToken());
			hosts[i] = host;
			for (int j=1; j<nAttend; j++) {
				union(parent, host, Integer.parseInt(st.nextToken()));
			}
		}

		int count = M;
		if (nProphet > 0) {
			int rootProphet = find(parent, prevProphet);
			for (int i=0; i<M; i++) {
				if (rootProphet == find(parent, hosts[i])) {
					count --;
				}
			}
		}
		
		System.out.println(count);
	}
	
	static int find(int[] parent, int node) {
		if (parent[node] == node) return node;
		return parent[node] = find(parent, parent[node]);
	}
	
	static void union(int[] parent, int a, int b) {
		parent[find(parent, b)] = find(parent, a);
	}
}
