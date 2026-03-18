package D6_25938;

import java.util.Scanner;

class Solution {
	private final static int MAX_K = 5000;
	private final static int CMD_INIT = 100;
	private final static int CMD_ADD = 200;
	private final static int CMD_REMOVE = 300;
	private final static int CMD_CALC = 400;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) {
		int q = sc.nextInt();

		int n, k;
		int[] mIdArr = new int[MAX_K];
		int[] sCityArr = new int[MAX_K];
		int[] eCityArr = new int[MAX_K];
		int[] mTimeArr = new int[MAX_K];
		int mId, sCity, eCity, mTime;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			cmd = sc.nextInt();
			switch (cmd) {
				case CMD_INIT:
					okay = true;
					n = sc.nextInt();
					k = sc.nextInt();
					for (int j = 0; j < k; ++j) {
						mIdArr[j] = sc.nextInt();
						sCityArr[j] = sc.nextInt();
						eCityArr[j] = sc.nextInt();
						mTimeArr[j] = sc.nextInt();
					}
					usersolution.init(n, k, mIdArr, sCityArr, eCityArr, mTimeArr);
					break;
				case CMD_ADD:
					mId = sc.nextInt();
					sCity = sc.nextInt();
					eCity = sc.nextInt();
					mTime = sc.nextInt();
					usersolution.add(mId, sCity, eCity, mTime);
					break;
				case CMD_REMOVE:
					mId = sc.nextInt();
					usersolution.remove(mId);
					break;
				case CMD_CALC:
					sCity = sc.nextInt();
					eCity = sc.nextInt();
					ans = sc.nextInt();
					ret = usersolution.calculate(sCity, eCity);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);

		TC = sc.nextInt();
		MARK = sc.nextInt();

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
	}
}