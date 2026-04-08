import java.io.*;
import java.util.*;

// 벨만포드 쓰임새 1 : 특정 시작점 -> 도착점까지 최단경로(음수 간선 포함)
// 벨만포드 쓰임새 2 : (이 문제) 아무 시작점 -> 도착점까지 음수 사이클 존재여부 검사.
//                dist[] = 0으로 초기화하면 가상의 root(0)을 두고 모든 노드와 이어지게 한 후 간선 값을 0으로 해놓는 것과 같음(모든 노드로 시작)
// 클래스 만드는게 배열(int[3])보다 훨씬 빠름

public class G3_1865 {
    // 1. A 코드의 장점: 메모리 접근이 빠르고 가독성이 좋은 Edge 객체 활용
    static class Edge {
        int u;
        int v;
        int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 2. B 코드의 장점: I/O 병목을 제거하기 위한 StringBuilder 활용
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            // 간선의 총 개수는 도로(양방향) 2*M + 웜홀(단방향) W
            Edge[] edges = new Edge[2 * M + W];
            int idx = 0;

            // 도로 정보 입력 (양방향이므로 두 번 넣음)
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                edges[idx++] = new Edge(u, v, w);
                edges[idx++] = new Edge(v, u, w);
            }

            // 웜홀 정보 입력 (단방향이며, 가중치는 음수로 변환)
            for (int i = 0; i < W; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                edges[idx++] = new Edge(u, v, -w);
            }

            // 벨만-포드 알고리즘 실행 후 결과 저장
            if (hasNegativeCycle(N, edges)) {
                sb.append("YES\n");
            } else {
                sb.append("NO\n");
            }
        }
        
        // 모아둔 정답을 단 한 번만 출력!
        System.out.print(sb);
    }

    // 3. 논리적 오류가 수정된 완벽한 벨만-포드 음수 사이클 판별기
    static boolean hasNegativeCycle(int N, Edge[] edges) {
        // 자바에서 int 배열은 선언과 동시에 0으로 초기화됩니다. 
        // 시작점을 특정하지 않고 모두 0으로 둔다는 것은, 모든 노드를 동시에 출발시킨다는 의미입니다! (단절된 그래프 문제 해결)
        int[] dist = new int[N + 1]; 
        
        // [단계 1] 최단 거리 완화 (V-1 번 반복)
        for (int i = 1; i < N; i++) {
            boolean isUpdated = false; // 조기 종료를 위한 플래그
            
            for (Edge edge : edges) {
                // dist != INF 조건을 지웠으므로, 모든 간선이 처음부터 완화 대상이 됩니다.
                if (dist[edge.v] > dist[edge.u] + edge.w) {
                    dist[edge.v] = dist[edge.u] + edge.w; // 냅다 종료하지 않고 값을 '갱신'합니다.
                    isUpdated = true;
                }
            }
            
            // 💡 추가된 최적화: 만약 V-1번을 다 돌기도 전에 갱신이 멈췄다면? 
            // 이미 안정 상태에 도달한 것이므로 무의미한 공회전을 막고 즉시 빠져나옵니다.
            if (!isUpdated) break; 
        }

        // [단계 2] 음수 사이클 검증 (V 번째 반복)
        for (Edge edge : edges) {
            // V-1번의 완화를 거쳐 안정화되어야 정상인데, 또 갱신이 발생한다면?
            if (dist[edge.v] > dist[edge.u] + edge.w) {
                return true; // 🚨 음수 사이클(웜홀 무한동력) 확정!
            }
        }

        return false; // 무사히 통과했다면 음수 사이클 없음.
    }
}