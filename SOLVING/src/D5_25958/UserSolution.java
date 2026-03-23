package D5_25958;

import java.util.*;

class UserSolution {
	int N;
	int[][] map;
	
	Coord s, e;
	List<Tower> towers;
	List<Coord> path;
	int pathLength;
	
	void init(int N, int mMap[][])
	{
		towers = new ArrayList<>();
		path = new ArrayList<>();
		
		map = new int[N][N];
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				map[r][c] = mMap[r][c];
				
				if (map[r][c] == 2) {
					s = new Coord(r, c);
				}
				
				if (map[r][c] == 3) {
					e = new Coord(r, c);
				}
			}
		}
		
		Coord go = new Coord(s.r, s.c);
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		while (!(go.r == e.r && go.c == e.c)) {
			path.add(new Coord(go.r, go.c));
			
			for (int i=0; i<4; i++) {
				int nr = go.r + dr[i];
				int nc = go.c + dc[i];
				
				if (nr < 0 || nc < 0 || N <= nr || N <= nc)
					continue;
				
				int n = path.size();
				if (2 <= n && path.get(n-2).equals(go))
					continue;
				
				if (map[nr][nc] == 1) {
					path.add(new Coord(nr, nc));
					
					go.r = nr;
					go.c = nc;
					break;
				}
			}

		}
		path.add(new Coord(e.r, e.c));
		pathLength = path.size();
	}

	void addTower(int mRow, int mCol, int mInterval)
	{
		towers.add(new Tower(mRow, mCol, mInterval));
	}

	void runSimulation(int M, int mInterval, int mHP, int mRetTs[], int mRetHP[])
	{
		mRetTs = new int[M];
		Arrays.fill(mRetTs, Integer.MAX_VALUE);
		mRetHP = new int[M];
		Arrays.fill(mRetHP, mHP);
		int[] runnerPath = new int[M];
		List<Integer> running = new LinkedList<>();
		int recentBorn = -1;
		
		int t = 0;
		int remaining = M; // all - (dead or exit)
		
		while (0 < remaining) {
			/* towers attack runners */
			towerAttack:
			for (Tower tower : towers) {
				if (tower.reload + tower.recentT <= t) {
					// passed reloading time -> able to attack
					if (tower.recentRunner != -1 && mRetTs[tower.recentRunner] != Integer.MAX_VALUE) {
						// tower has recentRunner and he is here
						if (tower.Distance(path.get(runnerPath[tower.recentRunner])) <= 3) {
							// it is nearby -> shoot again
							mRetHP[tower.recentRunner] -= 1;
							if (mRetHP[tower.recentRunner] <= 0) {
								// dead? write down
								mRetHP[tower.recentRunner] = 0;
								mRetTs[tower.recentRunner] = t;
								
								remaining --;
							}
							
							tower.recentT = t;
							continue towerAttack;
						}
					}
					
					// couldn't reuse -> find some new
					int target = -1;
					for (int run : running) {
						if (tower.Distance(path.get(runnerPath[run])) <= 3) {
							if (target == -1) {
								// first one? enroll
								target = run;
							} else {
								if (mRetHP[run] < mRetHP[target]) {
									// lower HP than now? enroll
									target = run;
								} else if (mRetHP[run] == mRetHP[target]) {
									// same HP? take earlier one
									target = Math.min(target, run);
								}
							}
						}
					}
					
					if (target != -1) {
						// target found -> shoot
						mRetHP[target] -= 1;
						if (mRetHP[target] <= 0) {
							// dead? write down
							mRetHP[target] = 0;
							mRetTs[target] = t;
							
							remaining --;
						}
						
						tower.recentRunner = target;
						tower.recentT = t;
						continue towerAttack;
					}
					
					// couldn't find any new -> recentRunner = -1 and recentT = MAX
					tower.recentRunner = -1;
					tower.recentT = Integer.MAX_VALUE;
				}
			}
			
			if (0 < t && t % mInterval == 0) {
				/* runners move (if interval passed) */
				for (int rnIdx = running.size()-1; 0 <= rnIdx; rnIdx--) {
					int id = running.get(rnIdx);
					runnerPath[id] ++;
					if (runnerPath[id] == pathLength - 1) {
						// exit
						running.remove(rnIdx);
						mRetTs[id] = t;
						
						remaining --;
					}
				}
				
				/* runners generated (if interval passed) */
				int newRunner = ++ recentBorn;
				running.add(0, newRunner);
				runnerPath[newRunner] = 0;
			}
			
			/* next turn */
			t ++;
		}
	}
}

class Coord {
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord coo = (Coord) o;
			return coo.r == r && coo.c == c;
		}
		
		return false;
	}
}

class Tower extends Coord {
	int recentRunner, recentT, reload;
	Tower(int r, int c, int reload) {
		super(r, c);
		this.recentRunner = -1;
		this.recentT = Integer.MAX_VALUE;
		this.reload = reload;
	}
	
	// Manhattan distance
	int Distance(Coord coo) {
		return Math.abs(coo.r - r) + Math.abs(coo.c - c);
	}
}
