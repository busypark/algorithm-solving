import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class D4_1219 {
	public static Map<Integer, List<Integer>> links;
	public static boolean[] passed;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			for (int t=1; t<=10; t++) {
				final int tc = sc.nextInt();
				final int n = sc.nextInt();
				
				// 전략 : {finish:start} pair로 구성된 Map을 만들어서 B(99)부터 역으로 타고 올라가며 A(0)가 나오는 지 경로 탐색
				//       이때, 무한루프도 존재할 수 있으므로 지나친 노드를 Set으로 저장. A 도착 전에 무한루프 봉착하는 순간 그건 실패임
				// 맹점 : 각 자식노드의 부모 수는 1개 이상일 수 있으므로 모든 경우 검토해야 함 -> Map으로 하려면 Value가 리스트여야 함
				// 수정 : 지나친 노드를 Set 말고 boolean 배열로 선언, Map도 전역으로 관리하여 재귀함수 가능하도록
				links = new HashMap<>();
				for (int i=0; i<n; i++) {
					final int start = sc.nextInt();
					final int finish = sc.nextInt();
					
					if (links.get(finish) == null) {
						links.put(finish, new ArrayList<>());
					} 
					
					links.get(finish).add(start);
				}
				
				// initialize all passed[i] as false
				passed = new boolean[100];
				
				final int answer = searchResult(99); // start with B(99)
				System.out.println("#"+tc+" "+answer);
			}
		}
	}
	
	public static int searchResult(int child) {
		if (passed[child]) return 0; // already passed this way (no need to get here again)
		passed[child] = true;
		
		if (child == 0) return 1; // reached A(0)
		if (!links.containsKey(child)) return 0; // reached a dead-end
		
		int max = 0;
		for (int i=0; i<links.get(child).size(); i++) {
			max = Math.max(max, searchResult(links.get(child).get(i)));
		}
		
		return max;
	}
}
