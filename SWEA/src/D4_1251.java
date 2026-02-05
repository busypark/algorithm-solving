import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

/*
 모든 섬 N개를 2개씩 잇는 선분은 NC2 = N(N-1)/2개
 그 중에 모든 것을 연결하려면 N-1개의 선분이 필요함 (초과하면 낭비고, 미만이면 연결이 안 됨)
 따라서 총 (NC2) C (N-1) 가지 경우의 수인데, N=1000이 되면 단위가 10^3000이 넘어가므로 모두 고려하는 건 말이 안 됨
 
 동적인 버전의 Dijkstra 아닐까 싶음. 1249번은 위치가 모두 정적이었는데 이번 문제는 어떤 섬을 거치느냐에 따라 그 다음 후보들이 달라진다는 점이 다름
 그러나 전반적인 흐름은 Dijkstra와 동일하다고 보임. 다른 섬으로 가는 비용이 모두 양수라는 점까지
 그런데 기본적인 요소를 각 섬이 아니라 link로 봐야할 것 같다. 그래야 비용도 정의가 되고, 또다시 방문할 일이 없다(섬은 재방문할 수 있지만 링크는 굳이 다시 방문 X)
 */

public class D4_1251 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int T = sc.nextInt();
			for (int t=1; t<=T; t++) {
				final int N = sc.nextInt(); // 1 <= N <= 1000
				
				// input N islands
				final Island[] islands = new Island[N];
				for (int i=0; i<N; i++) {
					final int r = sc.nextInt(); // 0 <= r <= 1,000,000
					final int c = sc.nextInt(); // 0 <= c <= 1,000,000
					islands[i] = new Island(r, c);
				}
				final Double E = sc.nextDouble(); // tax rate(E)
				
				// derive all links and costs
				final Link[] links = new Link[N*(N-1)/2];
				int idx = 0;
				for (int i=0; i<N; i++) {
					for (int j=i+1; j<N; j++) {
						Link theLink = new Link(idx, Link.calculateCost(links[i], Links[j], E));
					}
				}
				
				// dijkstra
				final List<accumulatedLink> accumulatedLinks = new LinkedList<>();
				
			}
		}
	}
}

class Island {
	int r, c;
	Island(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

class Link {
	int idx;
	double cost; // E(tax rate) * L^2(length squared)
	
	Link (int idx, double cost) {
		this.idx = idx;
		this.cost = 0.0D;
	}
	
	public static double calculateCost(Island i1, Island i2, double E) {
		return E*((i1.r - i2.r)*(i1.r - i2.r)) + E*((i1.c - i2.c)*(i1.c - i2.c));
	}
}

class accumulatedLink {
	int idx;
	double accumulatedCost;
	
	accumulatedLink (int idx, double accumulatedCost) {
		this.idx = idx;
		this.accumulatedCost = Double.MAX_VALUE;
	}
}