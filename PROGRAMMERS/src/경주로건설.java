import java.util.*;

public class 경주로건설 {
	static enum Kind { // where 'now' came from
		starting, horizontal, vertical;
	}
	
	static class Coord {
		int r, c, sumCost;
		Kind k;
		Coord(int r, int c, int sumCost, Kind k) {
			this.r = r;
			this.c = c;
			this.sumCost = sumCost;
			this.k = k;
		}
	}
	
    public static int solution(int[][] board) {
    	int n = board.length;
        
    	// initialize for dijkstra
    	int[][] cost = new int[n][n];
        for (int r=0; r<n; r++) {
        	for (int c=0; c<n; c++) {
        		cost[r][c] = Integer.MAX_VALUE;
        	}
        }
        cost[0][0] = 0;
        
        PriorityQueue<Coord> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.sumCost, n2.sumCost));
        pq.offer(new Coord(0, 0, 0, Kind.starting));
        
        // tracing
        Coord[][] trace = new Coord[n][n];
        
        // dijkstra
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!pq.isEmpty()) {
        	Coord now = pq.poll();
        	if (now.r == n-1 && now.c == n-1) break;
        	
        	if (now.k == Kind.starting) {
        		// vertical
            	for (int i=0; i<2; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 100;
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.vertical));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
            	
            	// horizontal
            	for (int i=2; i<4; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 100;
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.horizontal));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
    		} else if (now.k == Kind.horizontal) {
        		// vertical
            	for (int i=0; i<2; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 500 + 100; // corner
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.vertical));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
            	
            	// horizontal
            	for (int i=2; i<4; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 100;
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.horizontal));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
    		} else {
        		// vertical
            	for (int i=0; i<2; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 100;
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.vertical));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
            	
            	// horizontal
            	for (int i=2; i<4; i++) {
            		int nr = now.r + dr[i];
            		int nc = now.c + dc[i];
            		
            		if (nr < 0 || n <= nr || nc < 0 || n <= nc || board[nr][nc] == 1) continue;
            		
            		int nCost = cost[now.r][now.c] + 500 + 100; // corner
            		if (nCost < cost[nr][nc]) {
            			cost[nr][nc] = nCost;
            			pq.offer(new Coord(nr, nc, nCost, Kind.horizontal));
            			trace[nr][nc] = new Coord(now.r, now.c, 0, Kind.starting);
            		}
            	}
    		}
        }
        
        printTrace(n, trace);
    	
        return cost[n-1][n-1];
    }
    
    static void printTrace(int n, Coord[][] trace) {
    	char[][] rows = new char[n][n];
    	for (int r=0; r<n; r++) {
    		for (int c=0; c<n; c++)
    			rows[r][c] = '*';
    	}
    	
    	Coord now = trace[n-1][n-1];
    	rows[n-1][n-1] = '-';
    	while (now != null) {
    		rows[now.r][now.c] = '-';
    		now = trace[now.r][now.c];
    	}
    	
    	for (int r=0; r<n; r++) {
    		System.out.println(rows[r]);
    	}
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(new int[][] {{0,0,0},{0,0,0},{0,0,0}}));
    	// 900
    	
    	System.out.println(solution(new int[][] {{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,1,0,0,0},{0,0,0,1,0,0,0,1},{0,0,1,0,0,0,1,0},{0,1,0,0,0,1,0,0},{1,0,0,0,0,0,0,0}}));
    	// 3800

    	System.out.println(solution(new int[][] {{0,0,1,0},{0,0,0,0},{0,1,0,1},{1,0,0,0}}));
    	// 2100
    	
    	System.out.println(solution(new int[][] {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}}));
    	// 3200
    	
    }
}
