package D5_25958;

import java.util.*;

class UserSolution {
	int N, P, T;
	List<Integer> reload;
	
	int[][] tmpP;
	List<Integer>[] attackedBy;
	void init(int N, int mMap[][])
	{
		this.N = N;
		
		int r = 0, c = 0;
		lookForStart:
		for (r = 0; r<N; r++) {
			for (c = 0; c<N; c++) {
				if (mMap[r][c] == 2) {
					break lookForStart;
				}
			}
		}
		
		
		tmpP = new int[N][N];
		P = 1;
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		while (mMap[r][c] != 3) {
			tmpP[r][c] = P ++;
			
			for (int i=0; i<4; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];
				
				if (valid(nr, nc) && tmpP[nr][nc] == 0 && mMap[nr][nc] > 0) {
					r = nr;
					c = nc;
					break;
				}
			}
		}
		tmpP[r][c] = P;
		
		attackedBy = new List[P];
		for (int i=0; i<P; i++)
			attackedBy[i] = new ArrayList<>();
		
		T = 0;
		reload = new ArrayList<>();
	}
	
	boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}

	void addTower(int mRow, int mCol, int mInterval)
	{
		for (int dr=-3; dr<=3; dr++) {
			for (int dc=Math.abs(dr)-3; dc<=3-Math.abs(dr); dc++) {
				int r = mRow + dr;
				int c = mCol + dc;
				
				if (valid(r, c)) {
					int p = tmpP[r][c];
					if (p > 0) {
						attackedBy[p-1].add(T);
					}
				}
			}
		}
		
		reload.add(mInterval);
		T++;
	}

	void runSimulation(int M, int mInterval, int mHP, int mRetTs[], int mRetHP[])
	{				
		int[] head = {0, -1};
		
		int[] lastT = new int[T];
		int[] preserveLastT = new int[T];
		
		Arrays.fill(lastT, Integer.MIN_VALUE);
		
		Arrays.fill(mRetTs, -1);
		Arrays.fill(mRetHP, mHP);
		
		int t = 0;
		int DoE = 0;
		int lastBorn = -1;
		boolean[] targets = new boolean[T];
		int[] attackedHP = new int[M];
		PriorityQueue<Integer> mpq = new PriorityQueue<>((a, b) -> ((mRetHP[a] == mRetHP[b]) ? (a - b) : (mRetHP[a] - mRetHP[b])));
		PriorityQueue<Integer> subpq = new PriorityQueue<>((a, b) -> ((mRetHP[a] == mRetHP[b]) ? (a - b) : (mRetHP[a] - mRetHP[b])));
		while (DoE < M) {
			Arrays.fill(targets, false);
			Arrays.fill(attackedHP, 0);
			for (int i=0; i<T; i++)
				preserveLastT[i] = lastT[i];
			
			if (head[1] != -1) {
				//int whom = head[0];
				//for (int where = head[1]; 0 <= where && whom < M; where--) {
				while (!mpq.isEmpty()) {
					int whom = mpq.poll();
					int where = head[1] - (whom - head[0]);
					
					for (int twr : attackedBy[where]) {						
						if (mRetTs[whom] == -1 && !targets[twr] && lastT[twr] + reload.get(twr) <= t) {
							attackedHP[whom] ++;
							preserveLastT[twr] = t;
							targets[twr] = true;
						}
					}
					
					subpq.offer(whom);
					whom ++;
				}
			}

			for (int i=0; i<T; i++)
				lastT[i] = preserveLastT[i];
			
			//for (int m=0; m<M; m++) {
			while (!subpq.isEmpty()) {
				//if (mRetTs[m] == -1) {
					int m = subpq.poll();
					int newHP = mRetHP[m] - attackedHP[m];
					if (newHP <= 0) {
						mRetHP[m] = 0;
						mRetTs[m] = t;
						
						head[0] ++;
						head[1] --;
						DoE ++;
					} else {
						mRetHP[m] = newHP;
						
						if (0 < t && t % mInterval == 0) {
							if (head[1] + 1 == P-1 && head[0] == m) {
								continue;
							}
						}
						
						mpq.offer(m);
					}
				//}
			}
			
			if (0 < t && t % mInterval == 0) {
				head[1] ++;
				if (head[1] == P-1) {
					mRetTs[head[0]] = t;
					
					head[0] ++;
					head[1] --;
					DoE ++;
				}
				
				if (lastBorn < M-1) {
					lastBorn ++;
					mpq.offer(lastBorn);
				}
			}
			
			if (t <= 15) {
			//if (false) {
				System.out.println("------------");
				print("t", t);
				print("head", head);
				print("lastT", lastT);
				print("DoE", DoE);
				print("mRetTs", mRetTs);
				print("mRetHP", mRetHP);
				print("targets", targets);
				print("attackedHP", attackedHP);
			}
			
			t++;
		}
	}
	
	void print(String name, int[] arr) {
		System.out.print(name+" ");
		for (int ele : arr) {
			System.out.print(ele+" ");
		}
		
		System.out.println();
	}
	
	void print(String name, boolean[] arr) {
		System.out.print(name+" ");
		for (boolean ele : arr) {
			System.out.print((ele)? "T " : "F ");
		}
		
		System.out.println();
	}
	
	void print(String name, int val) {
		System.out.println(name+" "+val);
	}
}


/*

1 100
5
0 0 0 0 0
0 0 0 0 0
2 1 1 1 3
0 0 0 0 0
0 0 0 0 0
3
200 0 1 3
200 3 3 3
300 3 3 3 0 0 0 0 0 0

 */





