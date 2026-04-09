package test;

import java.util.*;

public class combWithRep {
    public static void main(String[] args) {
        // 10가지 (N, R) 테스트 케이스 (출력 확인을 위해 N, R은 2~5 사이로 구성)
        int[][] testCases = {
            {2, 2}, {3, 2}, {2, 3}, {3, 3}, {4, 2}, 
            {2, 4}, {4, 3}, {3, 4}, {5, 2}, {4, 4}
        };

        for (int t = 0; t < testCases.length; t++) {
            int N = testCases[t][0];
            int R = testCases[t][1];

            List<int[]> resultAsc = new ArrayList<>();
            List<int[]> resultDesc = new ArrayList<>();

            // 1. 상향식 (백트래킹 O) 호출
            combAsc(0, 0, N, R, new int[N], resultAsc);
            
            // 2. 하향식 (백트래킹 X) 호출
            combDesc(0, 0, N, R, new int[N], resultDesc);

            // 결과 집합 비교를 위한 사전순 정렬
            Comparator<int[]> lexicographicalOrder = (a, b) -> {
                for (int i = 0; i < a.length; i++) {
                    if (a[i] != b[i]) return Integer.compare(a[i], b[i]);
                }
                return 0;
            };
            //resultAsc.sort(lexicographicalOrder);
            //resultDesc.sort(lexicographicalOrder);

            // 결과 출력
            System.out.println("=========================================");
            System.out.printf("Test Case %d: N=%d, R=%d (Total: %d)\n", t + 1, N, R, resultAsc.size());
            System.out.println("<상향식> \t\t <하향식>");
            
            for (int i = 0; i < resultAsc.size(); i++) {
                String ascStr = Arrays.toString(resultAsc.get(i));
                String descStr = Arrays.toString(resultDesc.get(i));
                
                // 불일치 시 경고 표시
                String match = ascStr.equals(descStr) ? "" : " [불일치!]"; 
                System.out.printf("%s \t %s%s\n", ascStr, descStr, match);
            }
            System.out.println();
        }
    }

    // 1. 상향식 (기존 방식): 0부터 남은 횟수까지 증가시키며 탐색. 백트래킹(초기화) 필수.
    static void combAsc(int itemIdx, int totalPicked, int N, int R, int[] counts, List<int[]> res) {
        if (totalPicked == R) {
            res.add(counts.clone()); // 정답 복사
            return;
        }
        if (itemIdx == N) return;

        int maxAffordable = R - totalPicked;
        for (int k = 0; k <= maxAffordable; k++) {
            counts[itemIdx] = k;
            combAsc(itemIdx + 1, totalPicked + k, N, R, counts, res);
            counts[itemIdx] = 0; // ★ 백트래킹(상태 복구) 필수
        }
    }

    // 2. 하향식 (질문자님 아이디어): 남은 최대 횟수부터 0까지 감소시키며 탐색. 백트래킹 불필요.
    static void combDesc(int itemIdx, int totalPicked, int N, int R, int[] counts, List<int[]> res) {
        if (totalPicked == R) {
            res.add(counts.clone()); // 정답 복사
            return;
        }
        if (itemIdx == N) return;

        int maxAffordable = R - totalPicked;
        for (int k = maxAffordable; k >= 0; k--) {
            counts[itemIdx] = k;
            combDesc(itemIdx + 1, totalPicked + k, N, R, counts, res);
            // ★ 백트래킹 불필요: 하행 탐색이므로 항상 마지막 동작이 counts[itemIdx] = 0 으로 청소됨
        }
    }
}
