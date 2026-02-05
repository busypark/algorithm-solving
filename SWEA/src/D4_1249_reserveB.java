import java.util.Scanner;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

/*
 일단 reserveA의 코드를 재귀->반복문으로 어떻게 고치나?
 고치기가 그리 어렵지 않았음. 근데 실행시간이 너무 오래 걸려서 겨우 N=16인 경우도 1분은 넘게 걸리는 듯
 전체 탐색하는 로직 자체는 맞음. 그러나 더 최적화 필요
 */

public class D4_1249_reserveB {
	public static int N;
	public static int[][] map;
	public static List<Deque<Pos_reserveB>> cases;
	public static int minTotal, cost;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				N = sc.nextInt();
				map = new int[N][N];
				sc.nextLine();					
				for (int r=0; r<N; r++) {
					String row = sc.nextLine();
					for (int c=0; c<N; c++) {
						map[r][c] = row.charAt(c) - '0';
					}
				}
				
				cases = new ArrayList<>();
				cases.add(new ArrayDeque<Pos_reserveB>());
				cases.get(0).add(new Pos_reserveB(0, 0));
				
				minTotal = Integer.MAX_VALUE;
				cost = 0;

				while (0 < cases.size()) {	
					//printPath();
					
					if (cases.get(cases.size()-1).size() == 0) { // 마지막 Step에 후보가 더 없으므로 해당 Step은 지우고 그 직전의 Head도 pop
						cases.remove(cases.size()-1); // 해당 Step 지움
						//System.out.println("[ this step is dead-end. removing.. ]");
						if (0 < cases.size()) { // S 말고 더 남았으면 이미 탐색한 후보 1개 지우고 다시 재귀. S뿐이면 이대로 재귀 종료
							Pos_reserveB head = cases.get(cases.size()-1).pop();
							//System.out.println("[ removing the head. searching another head.. ]");
							cost -= map[head.r][head.c];
						}
					} else { // 마지막 Step에 후보가 더 있으므로 그 중 Head에 대해 4방으로 살피되 기존 경로(기존 Step들의 Head) 피해서 다음 Step 추가
						Pos_reserveB head = cases.get(cases.size()-1).peekFirst(); // 후보들 중 선택된 좌표
						if (head.r == N-1 && head.c == N-1) { // 선택된 좌표가 곧 종점(G)일 경우, minTotal 갱신 후 재귀
							cases.get(cases.size()-1).pop(); // G는 제거
							minTotal = Math.min(minTotal, cost); // minTotal 갱신
							//System.out.println(":::::::::::::::::::::: [Reached to G] ::::::::::::::::::::::");
							System.out.println("::::::::::::::::::::::   currentTotal = " + cost + " :::::::::::::::::::::: ");
							//System.out.println("::::::::::::::::::::::   minTotal = "+minTotal + " :::::::::::::::::::::: ");
						} else { // 선택된 좌표가 종점(G) 아닌 경우, 4방인데 기존 경로 중복 안 되도록 Step 추가 후 재귀 (cost 누적)
							Deque<Pos_reserveB> newStep = new ArrayDeque<>();
							
							Pos_reserveB up = newCandidate(head.r - 1, head.c);
							if (up != null) newStep.add(up);

							Pos_reserveB down = newCandidate(head.r + 1, head.c);
							if (down != null) newStep.add(down);

							Pos_reserveB left = newCandidate(head.r, head.c - 1);
							if (left != null) newStep.add(left);

							Pos_reserveB right = newCandidate(head.r, head.c + 1);
							if (right!= null) newStep.add(right);
							
							cases.add(newStep);
							//System.out.println("[ a step including "+ newStep.size() +" cases added ]");
							cost += map[head.r][head.c];
						}
					}
				}
				
				int answer = minTotal;
				System.out.println("#"+t+" "+answer);
			}
		}
	}

	static Pos_reserveB newCandidate(int r, int c) {
		if (isValid(r, c)) {
			//System.out.println("[ newCandidate: r="+r+" c="+c+" is valid");
			if (minTotal <= cost + map[r][c]) // 현재에서 다음 지점으로 넘어가면 이미 최소 비용을 넘어버린다? 굳이 탐색할 이유 없음
				return null;
			
			boolean passed = false;
			for (int i=0; i<cases.size(); i++) {
				Pos_reserveB p = cases.get(i).peekFirst();
				if (p.r == r && p.c == c) {
					passed = true;
					break;
				}
			}
			
			if (!passed) {
				return new Pos_reserveB(r, c);
			}
		}
		return null;
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
	
	static void printPath() {
		int[][] pathMap = new int[N][N];
		for (int i=0; i<cases.size(); i++) {
			Pos_reserveB p = cases.get(i).peekFirst();
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

}

class Pos_reserveB {
	int r, c;
	Pos_reserveB(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pos_reserveB) {
			Pos_reserveB p = (Pos_reserveB) o;
			return (p.r == r && p.c == c);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}
