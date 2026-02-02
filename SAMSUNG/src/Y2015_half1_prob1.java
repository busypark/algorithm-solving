/*
 바이러스의 확산을 막기 위해 총 n개의 식당에 있는 고객들의 체온을 측정하고자 합니다.
 체온을 측정하는 검사자는 검사팀장과 검사팀원으로 나뉘어집니다.
 팀장과 팀원이 검사할 수 있는 고객의 수가 다르며, 한 가게당 팀장은 오직 한 명, 팀원은 여러명 있을 수 있습니다.
 하지만 가게당 팀장 한 명은 무조건 필요합니다. 
 가게에 검사팀원만 존재하는 경우는 있을 수 없습니다.
 팀장이든 팀원이든 담당한 가게에 대해서만 검사합니다.
 n개의 식당 고객들의 체온을 측정하기 위해 필요한 검사자 수의 최솟값을 구하는 프로그램을 작성해주세요.
*/

// feedback : answer에 대한 제한은 없었기 때문에 웬만하면 long으로 선언해야.

import java.util.Scanner;

public class Y2015_half1_prob1 {
	public static Scanner sc = new Scanner(System.in);
	public static int[] customers;
	
	public static void main(String[] args) {
		final int n = sc.nextInt();
		customers = new int[n];
		for (int i=0; i<n; i++)
			customers[i] = sc.nextInt();
		final int eachLeader = sc.nextInt();
		final int eachMember = sc.nextInt();
		
		long answer = n;
		for (int i=0; i<n; i++) {
			customers[i] -= eachLeader;
			if (0 < customers[i]) {
				answer += customers[i] / eachMember;
				if (customers[i] % eachMember > 0)
					answer ++;
			}
		}
		
		System.out.println(answer);
	}
}
