package D5_25958;

import java.util.*;

class UserSolution {
	int P, T;
	
	List<Integer> tmpPathRows, tmpPathCols;
	List<Integer> tmpTowerRows, tmpTowerCols;
	List<Integer> tmpReloadTimes;
	void init(int N, int mMap[][])
	{
		tmpPathRows = new ArrayList<>();
		tmpPathCols = new ArrayList<>();
	
		int r = 0, c = 0;
		for (; r<N; r++) {
			for (; c<N; c++) {
				if (mMap[r][c] == 2) {
					break;
				}
			}
		}
		
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		while (mMap[r][c] != 3) {
			tmpPathRows.add(r);
			tmpPathCols.add(c);
			
			for (int i=0; i<4; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];
				
				if (nr < 0 || nc < 0 || N <= nr || N <= nc)
					continue;
				
				if (mMap[nr][nc] > 0) {
					int n = tmpPathRows.size();
					if (n == 1) {
						r = nr;
						c = nc;
						
						break;
					} else if (tmpPathRows.get(n-1) != nr && tmpPathCols.get(n-1) != nc) {
						r = nr;
						c = nc;
						
						break;
					}
				}
			}
		}
		tmpPathRows.add(r);
		tmpPathCols.add(c);
		P = tmpPathRows.size();
		T = 0;
		
		tmpTowerRows = new ArrayList<>();
		tmpTowerCols = new ArrayList<>();
		tmpReloadTimes = new ArrayList<>();
	}

	void addTower(int mRow, int mCol, int mInterval)
	{
		tmpTowerRows.add(mRow);
		tmpTowerCols.add(mCol);
		tmpReloadTimes.add(mInterval);
		
		T ++;
	}

	int[] twrRecentAttacks;
	int[] twrRecentTimes;
	List<Integer>[] pathCharges;
	void runSimulation(int M, int mInterval, int mHP, int mRetTs[], int mRetHP[])
	{
		mRetTs = new int[M];
		mRetHP = new int[M];
		
		twrRecentAttacks = new int[P];
		twrRecentTimes = new int[P];
		pathCharges = new List[P-1];
		
		Arrays.fill(mRetHP, mHP);
		Arrays.fill(twrRecentAttacks, -1);
		for (int i=1; i<P-1; i++) {
			List<Integer> charges = new ArrayList<>();
			for (int twr=0; twr<T; twr++) {
				int d = dist(i, twr);
				if (d <= 3) {
					charges.add(twr);
				}
			}
			
			pathCharges[i] = charges;
		}
		
		LinkedList runner = new LinkedList<Integer>();
		int headPath = -1;
		int t = 0;
		int DoE = 0;
		
		while (DoE < M) {
			for (int i=runner.size()-1; 0<=i; i--) {
				
			}
			
			t ++;
		}
	}
	
	int dist(int path, int twr) {
		return Math.abs(tmpPathRows.get(path) - tmpTowerRows.get(twr))
			+  Math.abs(tmpPathCols.get(path) - tmpTowerCols.get(twr));
	}
}








