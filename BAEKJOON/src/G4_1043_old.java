
// https://www.acmicpc.net/problem/1043


/*
 * 진실을 아는 사람들이 오면 진실 얘기해야 하고
 * 누구든지 여러 파티에 동시에 참여한다면 똑같이 얘기해야 한다
 * 그럼 진실을 아는 사람이 아무도 없는데 두 파티에서 똑같은 거짓말을 하면? 그건 거짓말쟁이가 아닌가? 그렇다.
 * solution : 파티 개수의 최대값 (0<=solution<=M)
 * <입력>
 * N M
 * 진실을 아는 사람의 수 -> 그들의 번호들
 * 1번 파티의 참여자 : 번호들
 * 2번 파티의 참여자 : 번호들
 * ...
 * M번 파티의 참여자 : 번호들
 * 
 * 가장 쉬운 건 일단 List[]로 파티별 참여자를 저장하고, 진실을 아는 사람들은 배열로 저장하는 거임
 * N, M은 최대 50
 * 
 * 케이스 1 : 진실을 아무도 몰라서 전부 거짓말 -> solution = M
 * 케이스 2 : 1번이 진실을 아는데 한 번뿐인 파티에 1번이 와서 거짓말 불가 -> solution = 0
 * 케이스 3 : 파티는 한 번인데 아무도 진실을 모름 -> solution = 1 (케이스 1과 동일)
 * 케이스 4 : 1번이 진실을 아니까 1, 4번 파티는 X이며 4번 파티에 포함된 4번에 의해 3번 파티도 X라서 1, 2번만 해당 -> solution = 2
 * 케이스 5 : 진실을 아는 1, 2, 3, 4에 의해 그들과 같은 파티에 참석하는 5, 10도 진실을 알게 되는 것과 같음 -> solution = 4
 * 케이스 6 : 1, 2, 7이 진실을 알아도 아무도 파티에 참석 안해서 solution = M
 * 케이스 7 : 3만 진실을 알지만, 그와 함께 참석하는 1, 2도 진실을 알게 되어 solution = 0
 * 
 * 문제가 재귀적임. 처음 주어진 '진실을 아는 사람들'에 의해 점점 진실이 퍼져나감
 * 진실을 아는지 여부 boolean[N+1]
 * 이미 고려한 파티인지 여부 boolean[M+1]
 * 
 * 로직을 어떻게 짜면 좋지?
 * 일단 파티 순서대로 보면 안 됨. 첫째줄 봤더니 진실 아는 사람 없으면 다음으로 그냥 넘어가는 거니까.
 * 그런데 알고보니 다른 사람에 의해 영향 받을수도
 * 각 knowTruth에 의해 영향을 먼저 다 끼치고, 그 다음 사람에 의해 또 영향을 다 끼치고.. 그래야 할듯
 * 
 * 피드백 : 분명 쉬운 문제였지만 그에 비해 너무 오래 걸렸음.. 남과의 비교에 의해 심리적 긴장이 컸음
 * 예전엔 이런 문제를 보면 설렜는데.. 그런데 두 번째 문제 풀 때에는 좀 더 내려놓은 듯. 강의 들으면서 하니까 긴장이 안 됐음
 * 왜일까? 왜 설렘보다 긴장이 앞섰을까
 * 
 * 이거 유니온 파인드 문제라는데... 다시 풀어보기
 */

import java.io.*;
import java.util.*;

public class G4_1043_old {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M;
	static List<Integer> knowTruth;
	static int[][] partyAttendants;
	static boolean[] infectedParty;
	
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		knowTruth = new ArrayList<>();
		infectedParty = new boolean[M+1];
		
		st = new StringTokenizer(br.readLine());
		final int countKnowTruth = Integer.parseInt(st.nextToken());
		for (int i=0; i<countKnowTruth; i++) {
			final int attendant = Integer.parseInt(st.nextToken());
			knowTruth.add(attendant);
		}
		
		partyAttendants = new int[M+1][];
		for (int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			final int countAttendants = Integer.parseInt(st.nextToken());
			partyAttendants[i] = new int[countAttendants];
			for (int j=0; j<countAttendants; j++) {
				partyAttendants[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		infection();
		
		int maxParty = 0;
		for (int m=1; m<=M; m++) {
			if (!infectedParty[m]) {
				maxParty++;
			}
		}
		
		System.out.println(maxParty);
	}

	static void infection() {
		for (int i=0; i<knowTruth.size(); i++) {
			for (int m=1; m<=M; m++) {
				boolean hasKnow = false;
				for (int a=0; a<partyAttendants[m].length; a++) {
					if (partyAttendants[m][a] == knowTruth.get(i)) {
						hasKnow = true;
						infectedParty[m] = true;
						break;
					}
				}
				
				if (hasKnow) {
					for (int a=0; a<partyAttendants[m].length; a++) {
						if (!knowTruth.contains(partyAttendants[m][a])) {
							knowTruth.add(partyAttendants[m][a]);
						}
					}
				}
			}
		}
	}
}



















