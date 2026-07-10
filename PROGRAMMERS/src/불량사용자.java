import java.util.*;

public class 불량사용자 {
    public static int solution(String[] user_id, String[] banned_id) {
        count = 0;
        bannedIdx = 0;
        visited = new HashMap<>();
        for (int i=0; i<user_id.length; i++) {
        	visited.put(user_id[i], false);
        }
        
        checks = new Map[banned_id.length];
        for (int i=0; i<banned_id.length; i++) {
        	Map<String, Boolean> row = new HashMap<>();
        	for (String userId : user_id) {
        		row.put(userId, check(banned_id[i], userId));
        	}
        	
        	checks[i] = row;
        }
        
        combs = new HashSet<>();
        currentComb = 0;
        dfs(user_id, banned_id);
    	
        return combs.size();
    }
    
    static int count;
    static int currentComb;
    static int bannedIdx; // < user_id.length, < banned_id
    static Map<String, Boolean>[] checks;
    static Map<String, Boolean> visited;
    static Set<Integer> combs;
    static void dfs(String[] user_id, String[] banned_id) {
    	if (bannedIdx == banned_id.length) {
    		// correct one found
    		count ++;
    		combs.add(currentComb);
    		//System.out.println(Integer.toBinaryString(currentComb));
    		//System.out.println();
    		return;
    	}
    	
    	for (int i=0; i<user_id.length; i++) {
    		if (!visited.get(user_id[i]) && checks[bannedIdx].get(user_id[i])) {
    			visited.put(user_id[i], true);
    			currentComb |= 1 << i;
    			bannedIdx++;
    			//System.out.print(user_id[i]+ " ");
    			dfs(user_id, banned_id);
    			bannedIdx--;
    			currentComb -= 1 << i;
    			visited.put(user_id[i], false);
    		}
    	}
    }
    
    static boolean check(String banned, String user) {
    	if (banned.length() != user.length()) return false;
    	
    	for (int i=0; i<banned.length(); i++) {
    		if (banned.charAt(i) != '*') {
    			if (banned.charAt(i) != user.charAt(i))
    				return false;
    		}
    	}
    	
    	return true;
    }
    
    public static void main(String[] args) {
    	System.out.println(solution(
    			new String[] {"frodo", "fradi", "crodo", "abc123", "frodoc"}, 
    			new String[] {"fr*d*", "abc1**"}));
    	// 2
    	
    	System.out.println(solution(
    			new String[] {"frodo", "fradi", "crodo", "abc123", "frodoc"},
    			new String[] {"*rodo", "*rodo", "******"}));
    	// 2
    	
    	System.out.println(solution(
    			new String[] {"frodo", "fradi", "crodo", "abc123", "frodoc"},
    			new String[] {"fr*d*", "*rodo", "******", "******"}));
    	// 3
    }
}
