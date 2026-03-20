import java.io.*;
import java.util.*;

/*
 * 자릿수가 서로 다른, 세 자리수, 0을 포함하지 않는 수.. 체크를 안 함 (강의때문에 집중 안 된 탓도 있음)
 * 다른 코드 : 실행시간은 거의 최선, 메모리는 15,000KB -> 14,000KB 정도로 약간 아낄 수 있는듯?
 * ball 구하기 위해 Set을 안 쓰고, 그냥 공통인 전체 개수를 구한 후 strike 개수를 뺐음 (차집합)
 */

public class S3_2503 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		List<Integer> candidates = new ArrayList<>();
		for (int i=102; i<=987; i++) {
			int[] sep = separate(i);
			if (sep[0] != sep[1] && sep[1] != sep[2] && sep[2] != sep[0]
			&&  sep[0] > 0 && sep[1] > 0 && sep[2] > 0)
				candidates.add(i);
		}
		
		int N = Integer.parseInt(br.readLine());
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			int[] numSep = separate(num);
			
			int nStri = Integer.parseInt(st.nextToken());
			int nBall = Integer.parseInt(st.nextToken());
			
			List<Integer> newCandidates = new ArrayList<>();
			for (int cand : candidates) {
				int[] candSep = separate(cand);
				int curStri = 0;
				Set<Integer> ball1 = new HashSet<>();
				Set<Integer> ball2 = new HashSet<>();
				
				for (int j=0; j<3; j++) {
					if (numSep[j] == candSep[j]) {
						curStri ++;
					} else {
						ball1.add(numSep[j]);
						ball2.add(candSep[j]);
					}
					
					if (nStri < curStri) break;
				}
				
				if (nStri != curStri) {
					continue;
				}
				
				ball1.retainAll(ball2);
				if (ball1.size() == nBall) {
					newCandidates.add(cand);
				}
			}
			
			candidates = newCandidates;
		}
		
		System.out.println(candidates.size());
	}
	
	static int[] separate(int n) {
		return new int[] {n / 100, (n / 10) % 10, n % 10};
	}
}
