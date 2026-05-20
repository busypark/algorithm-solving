import java.io.*;
import java.util.*;

public class CodeTree_Dot_N_Found {
	static class Area {
		int count, boundary;
		
		Area(int count, int boundary) {
			this.count = count;
			this.boundary = boundary;
		}
		
		double calculate() {
			return ((double) boundary) / ((double) count); 
		}
	}
	
	static class Point {
		int r, c;
		
		Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		boolean[][] ground = new boolean[N][N];
		boolean[][] visited = new boolean[N][N];
		for (int r=0; r<N; r++) {
			String row = br.readLine();
			for (int c=0; c<N; c++) {
				ground[r][c] = (row.charAt(c) == '#');
			}
		}
		
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		List<Area> areas = new ArrayList<>();
		
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if (!visited[r][c] && ground[r][c]) {
					areas.add(new Area(1, 0));
					
					// bfs
					visited[r][c] = true;
					Queue<Point> q = new LinkedList<>();
					q.offer(new Point(r, c));
					while (!q.isEmpty()) {
						Point here = q.poll();
						Area area = areas.get(areas.size()-1);
						
						for (int i=0; i<4; i++) {
							int nr = here.r + dr[i];
							int nc = here.c + dc[i];
							
							if (0 <= nr && nr < N && 0 <= nc && nc < N) {
								if (!visited[nr][nc]) {
									if (ground[nr][nc]) {
										area.count ++;
										visited[nr][nc] = true;
										q.offer(new Point(nr, nc));
									} else {
										area.boundary ++;
									}
								}
							} else {
								area.boundary ++;
							}
						}
					}
				}
			}
		}
		
		// pick minimum
		int id = 0;
		for (int i=0; i<areas.size(); i++) {
			if (areas.get(i).calculate() <= areas.get(id).calculate()) {
				id = i;
			}

			//System.out.println(areas.get(i).count + " " + areas.get(i).boundary);
		}
		
		System.out.println(areas.get(id).count + " " + areas.get(id).boundary);
	}
}
