import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

// https://www.acmicpc.net/problem/14499

public class G4_14499 {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {			
			final int H = sc.nextInt(); // 1 <= H <= 20
			final int W = sc.nextInt(); // 1 <= W <= 20
			final int rDice = sc.nextInt(); // 0 <= rDice <= H-1
			final int cDice = sc.nextInt(); // 0 <= cDice <= W-1
			final int K = sc.nextInt(); // number of commands. 1 <= K <= 1000
			
			final int[][] map = new int[H][W];
			for (int r=0; r<H; r++) {
				for (int c=0; c<W; c++) {
					map[r][c] = sc.nextInt();
				}
			}
			
			Dice dice = new Dice();
			Vector3D dicePosition = new Vector3D(rDice, cDice, 0);
			final Vector3D up = new Vector3D(0, 0, 1);
			
			for (int k=0; k<K; k++) {
				final int command = sc.nextInt(); // 1234 (동서북남)
				final Vector3D inc = new Vector3D(command); // command to Vector3D
				
				final Vector3D diceNext = new Vector3D(dicePosition);
				diceNext.add(inc);
				
				//System.out.println("----------------------------------");
				//System.out.println("Before command="+command);
				//System.out.println(" dicePosition : r="+dicePosition.r + " c="+dicePosition.c);
				//dice.print();
				
				if (diceNext.r < 0 || H <= diceNext.r || diceNext.c < 0 || W <= diceNext.c) {
					continue; // satisfy invalidity
				}
				
				// valid position
				dicePosition = diceNext;
				
				// roll the dice and search for the floor and ceiling face
				Vector3D floor = null;
				Vector3D ceiling = null;
				Vector3D rollingAxis = Vector3D.outerProduct(up, inc);
				Iterator<Vector3D> iter = dice.faces.iterator();
				while (iter.hasNext()) {					
					Vector3D face = iter.next();
					Vector3D newFace = Vector3D.outerProduct(rollingAxis, face);
					
					// update face only if outerProduct is NOT 0
					if (newFace.r != 0 || newFace.c != 0 || newFace.z != 0)
						face.copy(newFace);
					
					// search for the new floor
					if (face.z == -1) {
						floor = face;
					}
					
					// search for the new ceiling
					if (face.z == 1) {
						ceiling = face;
					}
				}
				
				// stick to the rule
				if (map[dicePosition.r][dicePosition.c] == 0) {
					map[dicePosition.r][dicePosition.c] = floor.magnitude;
					
					//System.out.println("copy to the map : "+floor.magnitude);
				} else {
					floor.magnitude = map[dicePosition.r][dicePosition.c];
					map[dicePosition.r][dicePosition.c] = 0;
					
					//System.out.println("move to the floor");
				}
				
				//System.out.println("After command="+command);
				//System.out.println(" dicePosition : r="+dicePosition.r + " c="+dicePosition.c);
				//dice.print();
				
				// print the top of the dice
				System.out.println(ceiling.magnitude);
			}
		}
	}
}

class Dice { 
	final Set<Vector3D> faces = new HashSet<Vector3D>();
	Dice() {
		faces.add(new Vector3D(0, 0, 1));
		faces.add(new Vector3D(0, 0, -1));
		faces.add(new Vector3D(0, 1, 0));
		faces.add(new Vector3D(0, -1, 0));
		faces.add(new Vector3D(1, 0, 0));
		faces.add(new Vector3D(-1, 0, 0));
	}
	
	void print() {
		final Iterator<Vector3D> iter = faces.iterator();
		final Map<String, Integer> facesWithName = new HashMap<>();
		while (iter.hasNext()) {
			Vector3D n = iter.next();
			if (n.r ==  1) facesWithName.put("south", n.magnitude);
			if (n.r == -1) facesWithName.put("north", n.magnitude);
			if (n.c ==  1) facesWithName.put("east", n.magnitude);
			if (n.c == -1) facesWithName.put("west", n.magnitude);
			if (n.z ==  1) facesWithName.put("ceiling", n.magnitude);
			if (n.z == -1) facesWithName.put("floor", n.magnitude);
		}
		
		System.out.println("--------------------");
		System.out.println("  "+facesWithName.get("north"));
		System.out.println(facesWithName.get("west")+" "+facesWithName.get("ceiling")+" "+facesWithName.get("east"));
		System.out.println("  "+facesWithName.get("south"));
		System.out.println("  "+facesWithName.get("floor"));
	}
}

class Vector3D {
	int r, c, z;
	int magnitude = 0;
	
	Vector3D(int r, int c, int z) {
		this.r = r;
		this.c = c;
		this.z = z;
	}
	
	Vector3D(Vector3D v) {
		this.r = v.r;
		this.c = v.c;
		this.z = v.z;
	}
	
	Vector3D(int command) {
		this.r = 0;
		this.c = 0;
		this.z = 0;
		
		switch (command) {
		case 1:
			this.c = 1;
			break;
		case 2:
			this.c = -1;
			break;
		case 3:
			this.r = -1;
			break;
		case 4:
			this.r = 1;
			break;
		}
	}
	
	void copy(Vector3D v) {
		this.r = v.r;
		this.c = v.c;
		this.z = v.z;
	}
	
	void add(int incR, int incC, int incZ) {
		this.r += incR;
		this.c += incC;
		this.z += incZ;
	}
	
	void add(Vector3D v) {
		this.r += v.r;
		this.c += v.c;
		this.z += v.z;
	}
	
	static Vector3D outerProduct(Vector3D u, Vector3D v) {
		return new Vector3D(u.c * v.z - u.z * v.c, u.z * v.r - u.r * v.z, u.r * v.c - v.r * u.c);
	}
}