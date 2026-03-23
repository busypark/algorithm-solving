package D5_25958;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	private static BufferedReader br;
    private static UserSolution userSolution = new UserSolution();

    private final static int MAXN = 20;
    private final static int MAXM = 300;
    
    private final static int CMD_ADD = 200;
    private final static int CMD_RUN = 300;
    
    private static int mMap[][] = new int[MAXN][MAXN];
    private static int mHP;
    private static int mRetTs[] = new int[MAXM];
    private static int mRetHP[] = new int[MAXM];
    
    private static int N;

    private static boolean run() throws Exception {
    	StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
    	
    	boolean okay = false;
    	int mRow, mCol, mInterval, N, M, Q;

        N = Integer.parseInt(stdin.nextToken());
        mMap = new int[N][N];
        
        for (int y = 0; y < N; y++)
    	{
    		stdin = new StringTokenizer(br.readLine(), " ");
    		for (int x = 0; x < N; x++)
    		{
    			mMap[y][x] = Integer.parseInt(stdin.nextToken());
    		}
    	}
    	userSolution.init(N, mMap);

    	okay = true;
    	
    	stdin = new StringTokenizer(br.readLine(), " ");
    	Q = Integer.parseInt(stdin.nextToken());

    	for (int q = 0; q < Q; ++q)
    	{
    		stdin = new StringTokenizer(br.readLine(), " ");
    		int cmd = Integer.parseInt(stdin.nextToken());
    		switch (cmd)
    		{
    		case CMD_ADD:
    			mRow = Integer.parseInt(stdin.nextToken());
    			mCol = Integer.parseInt(stdin.nextToken());
    			mInterval = Integer.parseInt(stdin.nextToken());
    			userSolution.addTower(mRow, mCol, mInterval);
    			break;
    		case CMD_RUN:
    			M = Integer.parseInt(stdin.nextToken());
    			mInterval = Integer.parseInt(stdin.nextToken());
    			mHP = Integer.parseInt(stdin.nextToken());
    			userSolution.runSimulation(M, mInterval, mHP, mRetTs, mRetHP);

    			for (int i = 0; i < M; i++)
    			{
    				int x = Integer.parseInt(stdin.nextToken());
    				if (mRetTs[i] != x)
    					okay = false;
    			}
    			for (int i = 0; i < M; i++)
    			{
    				int x = Integer.parseInt(stdin.nextToken());;
    				if (mRetHP[i] != x)
    					okay = false;
    			}
    			break;
    		default:
    			okay = false;
    			break;
    		}
    	}

        return okay;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

//        System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
}