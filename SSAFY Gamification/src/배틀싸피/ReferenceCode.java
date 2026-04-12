package 배틀싸피;

import com.example.libraries.Bridge;
import java.util.*;

public class ReferenceCode {
    private static String NICKNAME = "대전4반_김규리일_Refactored";
    
    // 게임 상태 데이터
    private static String[][] mapData;
    private static Map<String, String[]> allies = new HashMap<>();
    private static Map<String, String[]> enemies = new HashMap<>();
    private static String[] codes;
    
    // 내 탱크 상태
    private static int myRow, myCol;
    private static int normalBomb, tankBomb, enemyTurretCount, numTop;

    // 상하좌우 이동을 위한 델타 배열 (U, D, L, R 순서가 아닌, 논리적 탐색 순서)
    // 인덱스: 0(상), 1(하), 2(좌), 3(우)
    private static final int[] dRow = {-1, 1, 0, 0}; 
    private static final int[] dCol = {0, 0, -1, 1};
    private static final String[] DIR_CMD = {"U ", "D ", "L ", "R "}; // 이동/방향 커맨드 접두사

    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        String gameData = bridge.init(NICKNAME);

        while (gameData.length() > 0) {
            parseData(gameData);
            updateMyStatus(); // 내 탱크의 현재 위치와 포탄 갯수 갱신

            // 1. 초기 커맨드: 가만히 있기 (S)
            String output = "S"; 

            // 2. 1순위 전략: 근접 교전 및 상호작용 (Immediate Action)
            // 내 주변 상하좌우 1칸을 스캔하여 즉각적인 행동을 취합니다.
            boolean actionTaken = false;
            
            for (int d = 0; d < 4; d++) {
                int nextRow = myRow + dRow[d];
                int nextCol = myCol + dCol[d];

                // 맵 범위를 벗어나지 않는다면
                if (isValidPosition(nextRow, nextCol)) {
                    String target = mapData[nextRow][nextCol];

                    // [룰] X는 적군 포탑. 일반 포탄(Normal Bomb)으로 파괴 가능
                    if (target.equals("X") && normalBomb > 0) {
                        output = DIR_CMD[d] + "F M"; // 해당 방향을 보고(DIR_CMD) 일반 포탄 발사(F M)
                        actionTaken = true;
                        break;
                    } 
                    // [룰] E는 적군 탱크. 강력한 대전차 포탄(Tank Bomb)이 필요함
                    else if (target.contains("E") && tankBomb > 0) {
                        output = DIR_CMD[d] + "F S"; // 대전차 포탄 발사(F S)
                        actionTaken = true;
                        break;
                    }
                    // [룰] F는 보급 시설. 대전차 포탄이 적의 수보다 적을 때만 파밍 시도
                    else if (target.equals("F") && tankBomb < enemies.size()) {
                        String decodedPassword = decodeCaesarCipher(codes[0]);
                        output = "G " + decodedPassword; // 암호 해독 제출
                        actionTaken = true;
                        break;
                    }
                }
            }

            // 3. 2순위 전략: 타겟 탐색 및 이동 (Pathfinding)
            // 내 주변에 당장 쏘거나 먹을 것이 없다면, BFS로 다음 목표를 향해 1칸 이동합니다.
            if (!actionTaken) {
                output = getNextMoveCommand();
            }

            // 결정된 커맨드를 서버로 전송
            gameData = bridge.submit(output);
        }
        bridge.close();
    }

    /**
     * [전략] 목표를 향한 최단경로(BFS)를 탐색하고, 당장 내딛어야 할 '첫 걸음' 커맨드를 반환합니다.
     */
    private static String getNextMoveCommand() {
        boolean[][] visited = new boolean[mapData.length][mapData[0].length];
        Queue<int[]> queue = new LinkedList<>();
        
        // 큐 구조: {현재 행, 현재 열, 첫 발자국 방향 인덱스, 이동 거리}
        queue.offer(new int[]{myRow, myCol, -1, 0});
        visited[myRow][myCol] = true;

        int firstStepDir = -1; // 내가 나아가야 할 첫 방향

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cRow = cur[0], cCol = cur[1], firstDir = cur[2], dist = cur[3];

            for (int d = 0; d < 4; d++) {
                int nRow = cRow + dRow[d];
                int nCol = cCol + dCol[d];

                if (isValidPosition(nRow, nCol) && !visited[nRow][nCol]) {
                    visited[nRow][nCol] = true;
                    String target = mapData[nRow][nCol];

                    // 첫 발자국 방향을 계속 유지하며 큐에 전달
                    int nextFirstDir = (dist == 0) ? d : firstDir; 

                    // [룰] G(잔디)와 T(나무)는 지나갈 수 있는 길로 판단
                    if (target.equals("G") || target.equals("T")) {
                        queue.offer(new int[]{nRow, nCol, nextFirstDir, dist + 1});
                    } 
                    // 목표 1: 포탄이 부족할 때 보급 시설(F)을 발견하면 그곳으로 향함
                    else if (target.equals("F") && tankBomb < enemies.size() - 1) {
                        firstStepDir = nextFirstDir;
                        queue.clear(); break; // 탐색 즉시 종료
                    } 
                    // 목표 2: 적 포탑(X)을 발견하면 그곳으로 향함
                    else if (target.equals("X")) {
                        firstStepDir = nextFirstDir;
                        queue.clear(); break; // 탐색 즉시 종료
                    }
                }
            }
        }

        // BFS 탐색 결과 갈 곳이 정해졌다면 해당 방향으로 이동(A: Advance) 커맨드 반환
        if (firstStepDir != -1) {
            return DIR_CMD[firstStepDir] + "A";
        }
        return "S"; // 갈 곳이 없으면 가만히 대기
    }

    /**
     * [기믹] 보급 시설을 열기 위한 암호 해독 로직
     * 알파벳을 9칸 뒤로 미는(Shift) 카이사르 암호 알고리즘을 사용합니다.
     */
    private static String decodeCaesarCipher(String cipherText) {
        StringBuilder trans = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            trans.append((char) ('A' + (cipherText.charAt(i) - 'A' + 9) % 26));
        }
        return trans.toString();
    }

    // 맵 범위 체크 유틸리티
    private static boolean isValidPosition(int r, int c) {
        return r >= 0 && r < mapData.length && c >= 0 && c < mapData[0].length;
    }

    // (기존 parseData 로직과 동일하여 생략, 실제 실행시에는 포함 필요)
	static void parseData(String gameData) {
		// 입력 데이터를 행으로 나누기
		String[] rows = gameData.split("\n");
		int rowIndex = 0;

		// 첫 번째 행 데이터 읽기
		String[] header = rows[rowIndex].split(" ");
		int mapHeight = Integer.parseInt(header[0]); // 맵의 세로 크기
		int mapWidth = Integer.parseInt(header[1]); // 맵의 가로 크기
		int numOfAllies = Integer.parseInt(header[2]); // 아군의 수
		int numOfEnemies = Integer.parseInt(header[3]); // 적군의 수
		int numOfCodes = Integer.parseInt(header[4]); // 암호문의 수
		rowIndex++;

		// 기존의 맵 정보를 초기화하고 다시 읽어오기
		mapData = new String[mapHeight][mapWidth];
		numTop = 0;
		for (int i = 0; i < mapHeight; i++) {
			mapData[i] = rows[rowIndex + i].split(" ");
			for (int j = 0; j < mapWidth; j++) {
				if (mapData[i][j].equals("X")) {
					numTop++;
				}
			}
		}
		rowIndex += mapHeight;

		// 기존의 아군 정보를 초기화하고 다시 읽어오기
		allies.clear();
		for (int i = 0; i < numOfAllies; i++) {
			String[] allyData = rows[rowIndex + i].split(" ");
			String allyName = allyData[0];
			String[] allyInfo = Arrays.copyOfRange(allyData, 1, allyData.length);
			allies.put(allyName, allyInfo);
		}
		rowIndex += numOfAllies;

		// 기존의 적군 정보를 초기화하고 다시 읽어오기
		enemies.clear();
		for (int i = 0; i < numOfEnemies; i++) {
			String[] enemyData = rows[rowIndex + i].split(" ");
			String enemyName = enemyData[0];
			String[] enemyInfo = Arrays.copyOfRange(enemyData, 1, enemyData.length);
			enemies.put(enemyName, enemyInfo);
		}
		rowIndex += numOfEnemies;

		// 기존의 암호문 정보를 초기화하고 다시 읽어오기
		
		codes = new String[numOfCodes];
		for (int i = 0; i < numOfCodes; i++) {
			codes[i] = rows[rowIndex + i];
		}
	}

    // 내 위치와 상태를 동기화하는 로직 분리
    private static void updateMyStatus() {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                if (mapData[i][j].equals("A")) {
                    myRow = i; myCol = j;
                    String[] myInfo = allies.get("A");
                    if (myInfo != null) {
                        normalBomb = Integer.parseInt(myInfo[2]);
                        tankBomb = Integer.parseInt(myInfo[3]);
                    }
                    return;
                }
            }
        }
    }
}