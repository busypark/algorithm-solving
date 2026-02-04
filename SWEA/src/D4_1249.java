import java.util.Scanner;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class D4_1249 {
	public static int N;
	public static int[][] map;
	public static List<Deque<Pos>> cases;
	public static int minTotal;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				N = sc.nextInt(); // 2~100
				sc.nextLine();
				map = new int[N][N];
				for (int r=0; r<N; r++) {
					String row = sc.nextLine();
					for (int c=0; c<N; c++) {
						map[r][c] = row.charAt(c) - '0';
					}
				}
				
				// 전략 : 2중 Deque를 만듦. 직전 index(경우)를 하나 pop함에 따라 다음 index의 경우들이 만들어짐
				//       경로 중간에 이미 minTotal을 넘어버리면 탐색 중단(더 가봤자 minTotal보다 못하므로)
				//       경로가 성립하지 않는 경우는 돌아가면 됨
				//       경로가 하나 완성된 경우는 기존의 minTotal과 비교해 갱신
				//       1차원 : 각 step
				//       2차원 : 각 step에 대한 경우의 수
				/* 수정 : 굳이 2차원으로 할 필요가 없는 것 같다
						 Deque<Pos>로 처리하고 'pop -> 처리' 반복해도 될듯
				   수정 : pop을 하면 기존 log가 날라가서 다음 갈 곳 후보를 뽑을 때 왔던 데로 돌아갈 수 있으므로 peek으로 변경
				   		 peek -> 처리 -> pop(버리기) 하면 한 번 들어갔다가 나온 다음 버리는 거니까 맞음
				   		 stepLog에 의해서만 지나온 여부를 검사하면 상관 없는 다른 경우의 길까지 고려하게 돼서 틀림. 별도의 passed 필요
				   		 그런데 pop을 안 하고 딱 맞게 로직을 짜기가 힘듦. pop을 하고 별도의 passed에 저장하는게 나을듯
				   		 가장 기초적인 경로 하나는 정상적으로 찾음. 근데 거기서 프로그램이 끝나버림. 다음 경로들이 스택에 있는데?
				   초기화--
				*/
				
				/*
				 스택 방식으로 풀어내면 잘 될 줄 알았는데, 각 Step에서 경우의 수를 뽑아내는 건 쉽지만 G에 도착하거나 dead-end인 경우를 처리하는 것이 깔끔하지 못함
				 좀 더 직관적인 해석 필요..
				 직관적인 걸로 치면 제일 처음의 2중 Deque가 더 직관적이었는데..
				 2중 Deque 방식으로 하려면 깊게 파는 방식만 가능함. 
				 그러니까, 각 i번째 요소(1차원 Deque)들은 i-1번째 Deque의 특정한 item(후보)으로 이동한 1가지 경우의 또다른 후보들을 표현하는 것만 가능
				 2중 deque보다는 List<Deque<Pos>>가 나을 듯. 각 Step은 번호로 접근하는 게 더 편함
				*/
				
				cases = new ArrayList<>();
				cases.add(new ArrayDeque<Pos>());
				cases.get(0).add(new Pos(0, 0));
				
				minTotal = Integer.MAX_VALUE;
				searchPath(0); // 로직은 괜찮은 것 같은데, 재귀함수라서 StackOverFlow 뜨는 것 같음. 반복문으로 바꿔야..
				
				int answer = minTotal;
				System.out.println("#"+t+" "+answer);
				
				//break;
			}
		}
	}
	
	static void searchPath(int cost) {
		printPath();
		
		if (cases.size() == 0) { // 아무 Step도 없으면 종료
			System.out.println("[ No More Step -> return ]");
			return;
		}
		
		if (cases.get(cases.size()-1).size() == 0) { // 마지막 Step에 후보가 더 없으므로 해당 Step은 지우고 그 직전의 Head도 pop
			cases.remove(cases.size()-1); // 해당 Step 지움
			System.out.println("[ this step is dead-end. removing.. ]");
			if (0 < cases.size()) { // S 말고 더 남았으면 이미 탐색한 후보 1개 지우고 다시 재귀. S뿐이면 이대로 재귀 종료
				Pos head = cases.get(cases.size()-1).pop();
				System.out.println("[ removing the head. searching another head.. ]");
				searchPath(cost - map[head.r][head.c]);
			}
		} else { // 마지막 Step에 후보가 더 있으므로 그 중 Head에 대해 4방으로 살피되 기존 경로(기존 Step들의 Head) 피해서 다음 Step 추가
			Pos head = cases.get(cases.size()-1).peekFirst(); // 후보들 중 선택된 좌표
			if (head.r == N-1 && head.c == N-1) { // 선택된 좌표가 곧 종점(G)일 경우, minTotal 갱신 후 재귀
				cases.get(cases.size()-1).pop(); // G는 제거
				minTotal = Math.min(minTotal, cost); // minTotal 갱신
				System.out.println("[Reached to G]");
				System.out.println("  currentTotal = " + cost);
				System.out.println("  minTotal = "+minTotal);
				searchPath(cost);
			} else { // 선택된 좌표가 종점(G) 아닌 경우, 4방인데 기존 경로 중복 안 되도록 Step 추가 후 재귀 (cost 누적)
				Deque<Pos> newStep = new ArrayDeque<>();
				
				Pos up = newCandidate(head.r - 1, head.c);
				if (up != null) newStep.add(up);

				Pos down = newCandidate(head.r + 1, head.c);
				if (down != null) newStep.add(down);

				Pos left = newCandidate(head.r, head.c - 1);
				if (left != null) newStep.add(left);

				Pos right = newCandidate(head.r, head.c + 1);
				if (right!= null) newStep.add(right);
				
				cases.add(newStep);
				searchPath(cost + map[head.r][head.c]);
			}
		}
	}
	
	static void printPath() {
		int[][] pathMap = new int[N][N];
		for (int i=0; i<cases.size(); i++) {
			Pos p = cases.get(i).peekFirst();
			if (p != null)
				pathMap[p.r][p.c] = i+1;
		}

		System.out.println("--------------");
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.printf("%2d ", pathMap[r][c]);
			}
			System.out.println();
		}
	}

	static Pos newCandidate(int r, int c) {
		if (isValid(r, c)) {
			boolean passed = false;
			for (int i=0; i<cases.size(); i++) {
				Pos p = cases.get(i).peekFirst();
				if (p.r == r && p.c == c) {
					passed = true;
					break;
				}
			}
			
			if (!passed) {
				return new Pos(r, c);
			}
		}
		return null;
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
}

class Pos {
	int r, c;
	Pos(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pos) {
			Pos p = (Pos) o;
			return (p.r == r && p.c == c);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}
