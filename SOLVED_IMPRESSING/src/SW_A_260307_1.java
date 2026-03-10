// https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV4suNtaXFEDFAUf
// SW_1767
// 03.07(금) A형 1번 -> 1시간 50분 걸려 맞힘.. 멘탈 관리, 명상 필요

import java.io.*;
import java.util.*;
 
public class SW_A_260307_1 {
    static final int[] drArr = {1, -1, 0, 0};
    static final int[] dcArr = {0, 0, 1, -1};
         
    static int N;
    static int[][] map;
    static List<Coord_260307> cores;
    static int nCore;
     
    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final StringBuilder answer = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        final int T = Integer.parseInt(st.nextToken());
        for (int t=1; t<=T; t++) {
            // input N, map -> cores[0~N-1] = Coord_260307(r, c)
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            cores = new ArrayList<>();
            
            for (int r=0; r<N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c=0; c<N; c++) {
                    final int num = Integer.parseInt(st.nextToken());
                    map[r][c] = num;
                     
                    if (map[r][c] == 1)
                        cores.add(new Coord_260307(r, c));
                }
            }
            
            nCore = cores.size();
            
            argMax = -1;
            minLengthSum = Integer.MAX_VALUE;
            coreWired = new boolean[nCore];
            mapWired = new boolean[N][N];
            dfs(-1);
            
            answer.append("#").append(t).append(" ").append(minLengthSum).append("\n");
        }
        
        System.out.print(answer);
    }
    
    static int minLengthSum;
    static int argMax;
    static int curConnections, curLengthSum;
    static Map<Integer, Integer> minLengthSums;
    
    static boolean[] coreWired;
    static boolean[][] mapWired; // core / blank = 0, ONLY wired = 1
    static void dfs(int depth) {
        if (depth == nCore - 1) {
            // all cores are searched -> update minLengthSums
            if (argMax == -1) {
                argMax = curConnections;
                minLengthSum = curLengthSum;
            } else {
                if (argMax < curConnections) {
                    argMax = curConnections;
                    minLengthSum = curLengthSum;
                } else if (argMax == curConnections) {
                    minLengthSum = Math.min(minLengthSum, curLengthSum);
                }
            }
             
            return;
        }
         
        // NOT YET
        for (int i = depth + 1; i < nCore; i ++) {
            if (!coreWired[i]) {
                Coord_260307 core = cores.get(i);
                for (int j = 0; j < 4; j ++) {
                    coreWired[i] = true;    
                     
                    if (argMax != -1 && nCore - i + curConnections < argMax) {
                        // just skip dfs..
                    } else {
                        int inspect = inspectWall(core.r, core.c, drArr[j], dcArr[j]);
                        if (inspect != -1) {
                            wiring(core.r, core.c, drArr[j], dcArr[j]);
                            curConnections ++;
                            curLengthSum += inspect;
                             
                            //System.out.println("dfs r="+core.r+" c="+core.c+" depth="+depth);
                            dfs(i);
                             
                            unwiring(core.r, core.c, drArr[j], dcArr[j]);
                            curConnections --;
                            curLengthSum -= inspect;
                        } else {
                            dfs(i);
                        }
                    }
                     
                    coreWired[i] = false;
                }
            }
        }
    }
    
    static int inspectWall(int r, int c, int dr, int dc) {
        int length = 0;
        while (true) {
            length ++;
             
            r += dr;
            c += dc;
             
            if (isWall(r, c)) return length - 1;
            if (map[r][c] == 1 || mapWired[r][c]) return -1;
        }
    }
    
    static void wiring(int r, int c, int dr, int dc) {
        while (!isWall(r, c)) {
            r += dr;
            c += dc;
            if (isWall(r, c)) break;
 
            mapWired[r][c] = true;
        }
    }
    
    static void unwiring(int r, int c, int dr, int dc) {
        while (!isWall(r, c)) {
            r += dr;
            c += dc;
            if (isWall(r, c)) break;
             
            mapWired[r][c] = false;
        }
    }
    
    static boolean isWall(int r, int c) {
        return r == -1 || r == N || c == -1 || c == N;
    }
}
 
class Coord_260307 {
    int r, c;
    Coord_260307(int r, int c) {
        this.r = r;
        this.c = c;
    }
}