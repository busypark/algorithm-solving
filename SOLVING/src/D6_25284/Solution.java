package D6_25284;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT   	= 0;
    private final static int CMD_ADDRADIO   = 1;
	private final static int CMD_GETPOWER 	= 2;
	
	private final static int MAX_RADIO = 100;
	
	private static int id[] = new int[MAX_RADIO];
	private static int freq[] = new int[MAX_RADIO];
	private static int my[] = new int[MAX_RADIO];
	private static int mx[] = new int[MAX_RADIO];
	
    private static boolean run(BufferedReader br) throws Exception
    {
    	int N, K, Limit;
    	int ret, ans;
 
        boolean ok = false;

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int Q = Integer.parseInt(st.nextToken());

        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());

            if (cmd == CMD_INIT) {
				N = Integer.parseInt(st.nextToken());
				Limit = Integer.parseInt(st.nextToken());
				usersolution.init(N, Limit);
                ok = true;
            } else if (cmd == CMD_ADDRADIO) {
            	K = Integer.parseInt(st.nextToken());
            	for (int i = 0; i < K; i++) {
            		st = new StringTokenizer(br.readLine(), " ");
            		id[i] = Integer.parseInt(st.nextToken());
            		freq[i] = Integer.parseInt(st.nextToken());
            		my[i] = Integer.parseInt(st.nextToken());
            		mx[i] = Integer.parseInt(st.nextToken());
            	}
				usersolution.addRadio(K, id, freq, my, mx);
			} else if (cmd == CMD_GETPOWER) {
				int mid = Integer.parseInt(st.nextToken());
        		int cnt = Integer.parseInt(st.nextToken());
        		ans = Integer.parseInt(st.nextToken());
            	
            	ret = usersolution.getMinPower(mid, cnt);
            	if (ret != ans) {
                	ok = false;
                }
			}
			else ok = false;
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int T = Integer.parseInt(line.nextToken());
        int MARK = Integer.parseInt(line.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
}