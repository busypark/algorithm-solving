import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

// https://www.acmicpc.net/problem/21608
// 변수 명 헷갈려서 틀렸었는데.. 변수 많아질 때에는 어떻게 관리하면 좋을지?
// Set은 웬만하면 안 쓰는게 좋을 듯.. 속도라도 빠를 줄 알았는데 ArrayList가 오히려 더 빠름 -> 왜?

public class G5_21608 {
	static int N;
	static int[][] map;
	static Map<Integer, List<Integer>> favoriteMap;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			map = new int[N][N];
			
			favoriteMap = new HashMap<>();
			for (int n=0; n<N*N; n++) {
				final int stdNum = sc.nextInt();
				List<Integer> favorites = new ArrayList<>();
				for (int i=0; i<4; i++) {
					favorites.add(sc.nextInt());
				}
				
				//System.out.println("added to favoriteMap : stdNum="+stdNum+" favorites : "+favorites.get(0)+ " "+favorites.get(1)+ " "+favorites.get(2)+ " "+favorites.get(3)+ " ");
				favoriteMap.put(stdNum, favorites);
				place(stdNum, favorites);
			}
			
			final int answer = calculateTotalHappiness();
			
			System.out.println(answer);
		}
	}
	
	static void place(int stdNum, List<Integer> favorites) {
		// 1번. 빈칸 탐색 + 각 빈칸 주변의 좋아하는 학생 수 저장 및 최대값 저장
		int maxFavorites = -1;
		Set<SeatCandidate> blankSeats = new HashSet<>();
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if (map[r][c] == 0) {
					int countFavorites = 0;
					int rr, cc;
					
					// up
					rr = r-1;
					cc = c;
					if (isValid(rr, cc) && favorites.contains(map[rr][cc]))
						countFavorites++;

					// down
					rr = r+1;
					cc = c;
					if (isValid(rr, cc) && favorites.contains(map[rr][cc]))
						countFavorites++;
					
					// left
					rr = r;
					cc = c-1;
					if (isValid(rr, cc) && favorites.contains(map[rr][cc]))
						countFavorites++;

					// right
					rr = r;
					cc = c+1;
					if (isValid(rr, cc) && favorites.contains(map[rr][cc]))
						countFavorites++;
					
					blankSeats.add(new SeatCandidate(r, c, countFavorites));
					
					maxFavorites = Math.max(countFavorites, maxFavorites);
				}
			}
		}
		
		// (2번) 최대값에 해당되는 것만 따로 Set 만듦
		Set<SeatCandidate> maxFavoriteSeats = new HashSet<>();
		for (SeatCandidate s : blankSeats) {
			if (s.countFavorites == maxFavorites) {
				maxFavoriteSeats.add(s);
				
				//System.out.println(" maxFavoriteSeats : r="+s.r + " c="+s.c);
			}
		}
		
		// 딱 한 자리면 그곳으로 정함
		if (maxFavoriteSeats.size() == 1) {
			SeatCandidate givenSeat = maxFavoriteSeats.iterator().next();
			map[givenSeat.r][givenSeat.c] = stdNum;
		} else { // 2번. 후보가 여럿인 경우
			// 인접한 빈 칸의 최대값부터 구함
			int maxBlanks = -1;
			for (SeatCandidate s : maxFavoriteSeats) {
				int countBlanks = 0;
				int rr, cc;
				
				// up
				rr = s.r-1;
				cc = s.c;
				if (isValid(rr, cc) && map[rr][cc] == 0)
					countBlanks++;
				
				// down
				rr = s.r+1;
				cc = s.c;
				if (isValid(rr, cc) && map[rr][cc] == 0)
					countBlanks++;
				
				// left
				rr = s.r;
				cc = s.c-1;
				if (isValid(rr, cc) && map[rr][cc] == 0)
					countBlanks++;
				
				// right
				rr = s.r;
				cc = s.c+1;
				if (isValid(rr, cc) && map[rr][cc] == 0)
					countBlanks++;
				
				s.countBlanks = countBlanks;
				blankSeats.add(s); // 해시 갱신
				maxBlanks = Math.max(countBlanks, maxBlanks);
			}
			
			// 빈 칸의 최대값만큼의 빈 칸을 인접하게 갖고 있는 경우만 따로 목록 만듦
			Set<SeatCandidate> maxBlankSeats = new HashSet<>();
			for (SeatCandidate s : maxFavoriteSeats) {
				if (s.countBlanks == maxBlanks) {
					maxBlankSeats.add(s);
				}
			}
			
			// 인접한 칸의 수가 최대인 자리가 딱 한 자리면 그곳으로 정함
			if (maxBlankSeats.size() == 1) {
				SeatCandidate givenSeat = maxBlankSeats.iterator().next();
				map[givenSeat.r][givenSeat.c] = stdNum;				
			} else { // 3번. 후보가 여럿인 경우
				// 행의 번호가 가장 작은 칸 & 행의 번호가 같다면 그 중 열의 번호가 가장 작은 칸을 구함
				SeatCandidate currentSeat = maxBlankSeats.iterator().next();
				int minRow = currentSeat.r;
				int minCol = currentSeat.c;
				
				for (SeatCandidate s : maxBlankSeats) {
					if (s.r < minRow || (s.r == minRow && s.c < minCol)) {
						minRow = s.r;
						minCol = s.c;
					}
				}
				
				map[minRow][minCol] = stdNum;
			}
		}
		
		//print();
	}
	
	static int calculateTotalHappiness() {
		int total = 0;
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				int countFavorites = 0;
				int rr, cc;
				
				// up
				rr = r-1;
				cc = c;
				if (isValid(rr, cc) && favoriteMap.get(map[r][c]).contains(map[rr][cc]))
					countFavorites++;

				// down
				rr = r+1;
				cc = c;
				if (isValid(rr, cc) && favoriteMap.get(map[r][c]).contains(map[rr][cc]))
					countFavorites++;
				
				// left
				rr = r;
				cc = c-1;
				if (isValid(rr, cc) && favoriteMap.get(map[r][c]).contains(map[rr][cc]))
					countFavorites++;

				// right
				rr = r;
				cc = c+1;
				if (isValid(rr, cc) && favoriteMap.get(map[r][c]).contains(map[rr][cc]))
					countFavorites++;
				
				switch (countFavorites) {
				case 1:
					total += 1;
					break;
				case 2:
					total += 10;
					break;
				case 3:
					total += 100;
					break;
				case 4:
					total += 1000;
					break;
				}
			}
		}
		
		return total;
	}
	
	static void print() {
		System.out.println(" ------------ print ------------");
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.print(" " + map[r][c]);
			}
			
			System.out.println();
		}
	}
	
	static boolean isValid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
}

class SeatCandidate extends Seat {
	int countFavorites;
	int countBlanks = 0;
	SeatCandidate(int r, int c, int countFavorites) {
		super(r, c);
		this.countFavorites = countFavorites;
	}
}

class Seat {
	int r, c;
	Seat(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Seat) {
			Seat s = (Seat) o;
			return s.r == this.r && s.c == this.c;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}
