import java.util.Scanner;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

// https://www.acmicpc.net/problem/15686
// 시간초과 -> N과 M부터 공부!

public class G5_15686 {
	static int N, M;
	static Map<Integer, List<Edge>> edgeMap;
	static Map<Integer, Point> houseMap, chickenMap;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			N = sc.nextInt();
			M = sc.nextInt();
			
			// houseId -> List<Edge>
			edgeMap = new HashMap<>();
			// houseMap
			houseMap = new HashMap<>();
			// chickenMap
			chickenMap = new HashMap<>();
			
			int currentHouseId = 0;
			int currentChickenId = 0;
			
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					final int input = sc.nextInt();
					switch (input) {
					case 1: // 집
						edgeMap.put(currentHouseId, new ArrayList<>());
						houseMap.put(currentHouseId++, new Point(r, c));
						break;
					case 2: // 치킨
						chickenMap.put(currentChickenId++, new Point(r, c));
						break;
					}
				}
			}
			
			// edgeMap 계산 (house - chicken 대응(1:N) 및 거리 계산)
			for (Integer curHouseId : edgeMap.keySet()) {
				Point house = houseMap.get(curHouseId);
				for (Integer curChickenId : chickenMap.keySet()) {
					Point chicken = chickenMap.get(curChickenId);
					List<Edge> edgeList = edgeMap.get(curHouseId);
					edgeList.add(new Edge(curChickenId, Math.abs(house.r - chicken.r) + Math.abs(house.c - chicken.c)));
				}
			}
			
			// M개의 chicken 뽑아서 조합 경우들 도출하면서 최소 치킨거리 갱신
			for (Integer chickenId : chickenMap.keySet())
				currentChickens.add(chickenId);
			int minChickenDistance = minChickenDistance(Integer.MAX_VALUE);
			
			System.out.println(minChickenDistance);
		}
	}
	
	static List<Integer> currentChickens = new ArrayList<>();
	
	
	static int minChickenDistance(int currentMin) {
		if (currentChickens.size() == M) { // M개 chicken 뽑은 경우 -> 치킨거리 계산 -> 최소값 갱신
			int currentDistance = 0;
			for (List<Edge> curEdges : edgeMap.values()) {
				int currentEdgeLength = Integer.MAX_VALUE;
				for (Edge curEdge : curEdges) {
					if (currentChickens.contains(curEdge.chickenId)) { // M개 chicken 중 하나라면
						currentEdgeLength = Math.min(currentEdgeLength, curEdge.distance);
					}
				}
				
				currentDistance += currentEdgeLength;
			}
			
			return Math.min(currentMin, currentDistance);
		} else { // M개 뽑기 전 -> 더 뽑기
			int min = currentMin;
			for (int i=0; i<currentChickens.size(); i++) {
				int chickenId = currentChickens.get(i);
				currentChickens.remove(i);
				min = Math.min(min, minChickenDistance(currentMin));
				currentChickens.add(chickenId);
			}
			
			return min;
		}
	}
}

//class Edge {
	int chickenId, distance;
	Edge(int chickenId, int distance) {
		this.chickenId = chickenId;
		this.distance = distance;
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
			return (p.r == this.r && p.c == this.c);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}