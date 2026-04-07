package D6_25011;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class Solution {
	private static BufferedReader br;
    private static UserSolution userSolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_ADD = 200;
    private final static int CMD_REMOVE = 300;
    private final static int CMD_FINDWORD = 400;
    private final static int CMD_FINDPAGE = 500;
    
    private static boolean run() throws Exception {
    	boolean okay = false;
    	StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
    	
        int Q = Integer.parseInt(stdin.nextToken());
	    String mWordList[] = new String[10000];

	    for (int q = 0; q < Q; q++)
	    {
            stdin = new StringTokenizer(br.readLine(), " ");
	        int cmd = Integer.parseInt(stdin.nextToken());

	        if (cmd == CMD_INIT)
	        {
	            int N = Integer.parseInt(stdin.nextToken());
	            int mWordSize = Integer.parseInt(stdin.nextToken());
	            
	            for(int i=0;i<mWordSize;i++)
	            {
	            	mWordList[i] = stdin.nextToken();
	            }
	            userSolution.init(N, mWordList, mWordSize);
	            
	            okay = true;
	        }
	        else if (cmd == CMD_ADD)
	        {
	        	int mWordSize = Integer.parseInt(stdin.nextToken());
	        	for(int i=0;i<mWordSize;i++)
	        		mWordList[i] = stdin.nextToken();

	            userSolution.addWord(mWordList, mWordSize);
	        }
	        else if (cmd == CMD_REMOVE)
	        {
	        	int mWordSize = Integer.parseInt(stdin.nextToken());
	        	for(int i=0;i<mWordSize;i++)
	        		mWordList[i] = stdin.nextToken();

	            userSolution.removeWord(mWordList, mWordSize);
	        	
	        }
	        else if(cmd == CMD_FINDWORD)
	        {
	        	int mPageNum = Integer.parseInt(stdin.nextToken());
	        	
	        	String userAns = userSolution.findWord(mPageNum);
	        	String correctAns = stdin.nextToken();
	        	
	        	if(userAns.compareTo(correctAns) != 0)
	        	{
	        		okay = false;
	        	}
	        }
	        else if(cmd == CMD_FINDPAGE)
	        {
	        	String mWord = stdin.nextToken();
	        	
	        	int userAns = userSolution.findPage(mWord);
	        	int correctAns = Integer.parseInt(stdin.nextToken());
	        	
	        	if(userAns != correctAns)
	        	{
	        		okay = false;
	        	}
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