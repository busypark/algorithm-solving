import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/16236
// 260303(화) 명예 A형

public class G3_16236 {
	static int N;
	static int rShark, cShark;
	static int sizeShark = 2;
	static int eatCount = 0;

	static List<Integer> rFish= new ArrayList<>();
	static List<Integer> cFish= new ArrayList<>();
	static List<Integer> sizeFish= new ArrayList<>();
		
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N and map -> transform
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			
			for (int c=0; c<N; c++) {
				final int num = Integer.parseInt(st.nextToken());
				
				switch (num) {
				case 9:
					rShark = r;
					cShark = c;
					break;
				case 0:
					break;
				default:
					rFish.add(r);
					cFish.add(c);
					sizeFish.add(num);
				}
			}
		}
		
		// step forward with time
		int t = 0;
		while (!sizeFish.isEmpty()) {
			// validity : any accessible fish? (bfs)
			Status tgFish = targetFish();
			if (tgFish == null) {
				// no accessible fish
				break;
			}
			
			t += tgFish.t;
			int fIdx = idxFish(tgFish.r, tgFish.c);
			if (sizeFish.get(fIdx) < sizeShark) {
				// eat
				rFish.remove(fIdx);
				cFish.remove(fIdx);
				sizeFish.remove(fIdx);
				
				eatCount++;
				
				if (eatCount == sizeShark) {
					// upgrade weight and reset eatCount
					sizeShark ++;
					eatCount = 0;
				}
			}
			
			rShark = tgFish.r;
			cShark = tgFish.c;
		}
		
		// print t
		System.out.println(t);
	}
	
	static Status targetFish() {
		Deque<Status> q = new ArrayDeque<>();
		Status init = new Status(rShark, cShark, 0);
		q.add(init);
		
		while (!q.isEmpty()) {
			Status here = q.pop();
			// check if it's a fish less than the shark size -> target found = return
			int fIdx = idxFish(here.r, here.c);
			if (fIdx != -1 && sizeFish.get(fIdx) < sizeShark) {
				return here;
			}
			
			// check if it's invalid -> continue
			if (fIdx != -1 && sizeFish.get(fIdx) > sizeShark) {
				continue;
			}
			
			// lookup for adjacent paths
			int adjR, adjC;
			
			// up
			adjR = here.r - 1;
			adjC = here.c;
			if (valid(adjR, adjC) && !here.path.contains(new Point(adjR, adjC))) {
				// valid and not visited -> visit adj in the next loop
				Status s = new Status(adjR, adjC, here.t+1);
				s.path = new ArrayList<>(here.path);
				s.path.add(new Point(here.r, here.c));
				
				q.add(s);
			}
			
			// down
			adjR = here.r + 1;
			adjC = here.c;
			if (valid(adjR, adjC) && !here.path.contains(new Point(adjR, adjC))) {
				// valid and not visited -> visit adj in the next loop
				Status s = new Status(adjR, adjC, here.t+1);
				s.path = new ArrayList<>(here.path);
				s.path.add(new Point(here.r, here.c));
				
				q.add(s);
			}
			
			// left
			adjR = here.r;
			adjC = here.c - 1;
			if (valid(adjR, adjC) && !here.path.contains(new Point(adjR, adjC))) {
				// valid and not visited -> visit adj in the next loop
				Status s = new Status(adjR, adjC, here.t+1);
				s.path = new ArrayList<>(here.path);
				s.path.add(new Point(here.r, here.c));
				
				q.add(s);
			}
			
			// right
			adjR = here.r;
			adjC = here.c + 1;
			if (valid(adjR, adjC) && !here.path.contains(new Point(adjR, adjC))) {
				// valid and not visited -> visit adj in the next loop
				Status s = new Status(adjR, adjC, here.t+1);
				s.path = new ArrayList<>(here.path);
				s.path.add(new Point(here.r, here.c));
				
				q.add(s);
			}
		}
		
		return null;
	}
	
	static int idxFish(int r, int c) {
		int fIdx = 0;
		for (; fIdx < sizeFish.size(); fIdx++) {
			if (rFish.get(fIdx) == r && cFish.get(fIdx) == c) {
				return fIdx;
			}
		}

		return -1;
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}

class Status extends Point {
	List<Point> path;
	int t = 0;
	
	Status(int r, int c, int t) {
		super(r, c);
		path = new ArrayList<>();
		this.t = t;
	}
}

class Point {
	int r, c;
	Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			return p.r == r && p.c == c;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}







