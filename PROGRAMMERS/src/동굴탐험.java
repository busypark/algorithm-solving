import java.util.*;

class 동굴탐험 {
    public boolean solution(int n, int[][] path, int[][] order) {
        // transform order list to HashSet<k>, HashSet<q>, and HashMap<k, q>
        Set<Integer> ks = new HashSet<>();
        Set<Integer> qs = new HashSet<>();
        Map<Integer, Integer> kq = new HashMap<>();
        for (int[] ord : order) {
            int k = ord[0];
            int q = ord[1];
            
            ks.add(k);
            qs.add(q);
            kq.put(k, q);
        }
        
        // transform path to adjacency list (no weight)
        List<Integer>[] adjList = new List[n];
        for (int i=0; i<n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] pth : path) {
            int a = pth[0];
            int b = pth[1];
            
            adjList[a].add(b);
            adjList[b].add(a);
        }
        
        // loop for resolve toresolveQ
        Queue<Integer> toresolveQ = new LinkedList<Integer>();
        toresolveQ.add(0);
        
        Set<Integer> unresolvedQ = new HashSet<>();
        List<Integer> havingk = new ArrayList<>();
        boolean[] visited = new boolean[n];
        while (!toresolveQ.isEmpty()) {
            int qroot = toresolveQ.poll();
            
            // dfs
            visited[qroot] = true;
            dfs(qroot, adjList, ks, qs, kq, havingk, unresolvedQ, visited);
            
            // any k can resolve q?
            int lenK = havingk.size();
            for (int i=0; i<lenK; ) {
                int k = havingk.get(i);
                int q = kq.get(k);
                if (unresolvedQ.contains(q)) {
                    // can resolve
                    toresolveQ.add(q);
                    
                    // remove from candidates
                    unresolvedQ.remove(q);
                    havingk.remove(i);
                    lenK --;
                } else {
                    // no.. skip
                    i ++;
                }
            }
        }
                
        return unresolvedQ.isEmpty();
    }
    
    static void dfs(int root, 
                    List<Integer>[] adjList,
                    Set<Integer> ks, Set<Integer> qs, Map<Integer, Integer> kq,
                    List<Integer> havingk, Set<Integer> unresolvedQ,
                    boolean[] visited) {
        if (ks.contains(root)) {
            havingk.add(root);
        }
        
        if (qs.contains(root)) {
            unresolvedQ.add(root);
        }
        
        for (int next : adjList[root]) {
            if (!visited[next]) {
                visited[next] = true; // protect from infinite loop
                dfs(next, adjList, ks, qs, kq, havingk, unresolvedQ, visited);
            }
        }
    }
}