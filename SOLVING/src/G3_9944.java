import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/9944
// 260306(금) 명예 A형

public class G3_9944 {
	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final StringBuilder answer = new StringBuilder();
	static StringTokenizer st;
	
	static int N, M, nBlank;
	static char[][] map;
	public static void main(String[] args) throws IOException {
		int tc = 1;
		for (;;) {
			// readLine
			System.out.println("readline");
			String line = br.readLine();
			if (line == null || line.isEmpty() || line.isBlank()) break;
			System.out.println("after readline");
			
			// input N, M, and map
			st = new StringTokenizer(line);
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new char[N][M];
			nBlank = 0;
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				line = st.nextToken();
				for (int c=0; c<M; c++) {
					map[r][c] = line.charAt(c);
					
					if (map[r][c] == '.')
						nBlank ++;
				}
			}
			
			int score = -1;
			searchForScore:
			for (int r=0; r<N; r++) {
				for (int c=0; c<M; c++) {
					// game for (r, c) and (dr, dc)
					score = game(r, c, -1, 0);
					if (score != -1) {
						break searchForScore;
					}

					score = game(r, c, 1, 0);
					if (score != -1) {
						break searchForScore;
					}

					score = game(r, c, 0, -1);
					if (score != -1) {
						break searchForScore;
					}

					score = game(r, c, 0, 1);
					if (score != -1) {
						break searchForScore;
					}
				}
			}
			
			answer.append("Case "+(tc++)+": "+score+"\n");
		}
		
		System.out.print(answer);
	}
	
	static int game(int r, int c, int dr, int dc) {
		Deque<QStatus> q = new ArrayDeque<>();
		q.add(new QStatus(r, c, dr, dc));
		mainGame:
		while (!q.isEmpty()) {
			QStatus stat = q.pop();
			Coord last = stat.path.get(stat.path.size()-1);
			System.out.println("last = "+last.r+" "+last.c + " map = "+map[last.r][last.c]);
			
			// check invalidity
			if (!valid(last.r, last.c) || map[last.r][last.c] == '*') {
				System.out.println("continue, q.size = "+q.size());
				continue mainGame;
			}
			
			// check if done
			if (stat.path.size() == nBlank) {
				System.out.println("return "+stat.score);
				return stat.score;
			}
			
			System.out.println("before go");
			// go as given dir
			Coord go = new Coord(last.r, last.c);
			while (valid(go.r + stat.direction.r, go.c + stat.direction.c)
				   && map[go.r + stat.direction.r][go.c + stat.direction.c] == '.') {
				System.out.println(go.r+" "+go.c);
				go.r += stat.direction.r;
				go.c += stat.direction.c;
				if (stat.path.contains(go)) {
					// already
					continue mainGame;
				}
				
				stat.path.add(new Coord(go.r, go.c));
			}
			
			// investigate adjacency
			if (!(stat.direction.r == 1 && stat.direction.c == 0))
				q.add(new QStatus(stat, -1, 0));

			if (!(stat.direction.r == -1 && stat.direction.c == 0))
				q.add(new QStatus(stat,  1, 0));
			
			if (!(stat.direction.r == 0 && stat.direction.c == 1))
				q.add(new QStatus(stat,  0, -1));
			
			if (!(stat.direction.r == 0 && stat.direction.c == -1))
				q.add(new QStatus(stat,  0, 1));
			
			break;
		}
		
		System.out.println("[q empty] return -1");
		return -1;
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
}

class QStatus {
	Coord direction;
	List<Coord> path;
	int score = 1;
	
	QStatus(int r, int c, int dr, int dc) {
		direction = new Coord(dr, dc);
		path = new ArrayList<>();
		path.add(new Coord(r, c));
	}
	
	QStatus(QStatus pre, int dr, int dc) {
		direction = new Coord(dr, dc);
		path = new ArrayList<>(pre.path);
	}
}

//class Coord {
	int r, c;
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord coo = (Coord) o;
			return (coo.r == r && coo.c == c);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}







