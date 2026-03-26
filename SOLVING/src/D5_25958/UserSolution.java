package D5_25958;

import java.util.*;

class UserSolution {
	int P, T;
	List<Integer> reload;
	
	int[][] tmpP;
	List<Integer>[] attackedBy;
	void init(int N, int mMap[][])
	{
		P = 0;
		int r, c;
		for (r=0; r<N; r++) {
			for (c=0; c<N; c++) {
				if (mMap[r][c] == 2) {
					break;
				}
			}
		}
		
		
		
		attackedBy = new List[P];
		
	}

	void addTower(int mRow, int mCol, int mInterval)
	{
		
	}

	void runSimulation(int M, int mInterval, int mHP, int mRetTs[], int mRetHP[])
	{
		
	}
}








