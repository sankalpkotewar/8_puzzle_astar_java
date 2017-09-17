package packages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//A*//
public class Main {
	
	//public static BiHashMap<Integer,Integer,Integer> elementsMap = new BiHashMap<Integer,Integer,Integer>();
	public static void main(String[] args) throws CloneNotSupportedException {
		//BiHashMap<Integer,Integer,Integer> elementsMap = new BiHashMap<Integer,Integer,Integer>();
		//HashMap<Map<Integer,Integer>, Integer> elementsMap = new HashMap<Map<Integer,Integer>, Integer>();
		/*elementsMap.put(0, 0, 1);
		elementsMap.put(0, 1, 2);
		elementsMap.put(0, 2, 3);
		elementsMap.put(1, 0, 4);
		elementsMap.put(1, 1, 5);
		elementsMap.put(1, 2, 6);
		elementsMap.put(2, 0, 7);
		elementsMap.put(2, 1, 8);
		elementsMap.put(2, 2, 0);*/
		
		ArrayList<State> list = new ArrayList<State>();
		State init = new State();
		//State goal = new State();
		System.out.println("Enter the states from 1 to 8, put zero for blank");
		Scanner sc = new Scanner(System.in);
		int initial[][] = {{0,0,0},{0,0,0},{0,0,0}};
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				initial[i][j] = sc.nextInt();
			}
		}
		
		if(!validState(initial)){
			System.out.println("The input matrix is invalid, program will exit.");
			
		}
		else if(isSolvable(initial)){
		//int initial[][] = {{0,1,3},{4,2,5},{7,8,6}};
		
			int finalGoal[][] = {{1,2,3},{4,5,6},{7,8,0}};
			
			init.setArray(initial);
			init.setG(0);
			int h = init.h_sum = init.getHeuristic();
			System.out.println(h);
			init.f = init.g + init.h_sum;
			int f = init.f;
			list.add(init);
			int iterations = 0;
			State current = init;
			//int t=3;
			//while(!(compare(current.a , finalGoal))){ //doubt
			while((zeroInversion(current.a)!=0)){ //doubt
				System.out.println("\nCURRENT STATE: ");
				current.printMatrix();
				State left = new State(current);
				left = left.moveLeft();
				if(left!=null){
					left.setG(current.g+1);
					left.f = left.g+left.getHeuristic();
					list.add(left);
					//System.out.println("Left matrix");
					//left.printMatrix();
					//System.out.println(left.f);
					//System.out.println(left.g);
					//System.out.println(left.h_sum);
				}
				
				State right = new State(current);
				right = right.moveRight();
				if(right!=null){
					right.setG(current.g+1);
					right.f =  right.g+right.getHeuristic();
					list.add(right);
					//System.out.println("Right matrix");
					//right.printMatrix();
					//System.out.println(right.g);
	
				}
				
				State up = new State(current);
				up = up.moveUp();
				if(up!=null){
					up.setG(current.g+1);
					up.f = up.g+up.getHeuristic();
					list.add(up);
					//System.out.println("UP");
					//up.printMatrix();
	
				}
				State down = new State(current);
				down = down.moveDown();
				if(down!=null){
					down.setG(current.g+1);
					down.f = down.g+down.getHeuristic();
					list.add(down);
					//System.out.println("down");
					//down.printMatrix();
				}
				//System.out.println("Old print matrix");
				//current.printMatrix();
				list.remove(current);
				current = getMinState(list);
				f = current.f;
				//System.out.println("New print matrix");
				//current.printMatrix();
				iterations++;
				System.out.println("Number of elements in fringe list = " + list.size());
				System.out.println("The states in the fringe list are as follows:");
				for(int i=0; i<list.size();i++){
					list.get(i).printMatrix();
					System.out.println("Inversion Count:" + zeroInversion(list.get(i).a));
					System.out.println("F = " + list.get(i).f);
				}
			}
			System.out.println("The total number of iterations required to reach the goal state = "+iterations);
			current.printMatrix();
		}
		else{
			System.out.println("The entered puzzle is not solvable");
		}
	}

	private static State getMinState1(ArrayList<State> list) {
		int mininversion = 10000000;
		int minIndex = -1;
		for(int i=0; i<list.size(); i++){
			if(mininversion > zeroInversion(list.get(i).a)){
				minIndex = i;
				mininversion = zeroInversion(list.get(i).a); //minHeuristic stores the index of State having least h value
			}
		}
		
		return list.get(minIndex);
	}


	private static int zeroInversion(int[][] a) {
		int inversionCount = 0;
		int [] temp = new int[9];
		for(int i=0;i<3;i++){
			for(int j=0; j<3; j++){
				temp[(i*3)+j] = a[i][j];
			}
		}
		for(int i=0;i<8;++i){
			for(int j=i+1;j<9;++j){
				if(temp[i]>temp[j]){
					inversionCount++;
				}
			}
		}
		/*if(inversionCount==0){
			return true;
		}
		return false;*/
		return inversionCount;
	}

	private static boolean isSolvable(int[][] initial) {
		int inversionCount = 0;
		int [] temp = new int[9];
		for(int i=0;i<3;i++){
			for(int j=0; j<3; j++){
				temp[(i*3)+j] = initial[i][j];
			}
		}
		for(int i=0;i<8;++i){
			for(int j=i+1;j<9;++j){
				if((temp[i]>0) && (temp[j]>0) && temp[i]>temp[j]){
					inversionCount++;
				}
			}
		}
		return (inversionCount%2==0);
	}

	private static boolean validState(int[][] initial) {
		ArrayList<Integer> flag = new ArrayList<Integer>();
		for(int i=0;i<9;i++){
			flag.add(0);
		}
		for(int i=0; i<3;i++){
			for(int j=0; j<3; j++){
				flag.set(initial[i][j], 1); 
			}
		}
		if(flag.contains(0)){
			return false;
		}
		return true;
	}

	private static boolean compare(int[][] a, int[][] finalGoal) {
		for(int i =0; i<3;i++){
			for(int j=0; j<3; j++){
				if(a[i][j]!=finalGoal[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	//f = g+h, h is found by calculations, g is number of actions previously taken to reach the state.
	private static State getMinState(ArrayList<State> list) {
		int minHeuristic = 10000000;
		int minIndex = -1;
		for(int i=0; i<list.size(); i++){
			if(minHeuristic > list.get(i).f){
				minIndex = i;
				minHeuristic = list.get(i).f; //minHeuristic stores the index of State having least h value
			}
		}
		
		return list.get(minIndex);
	}
	
}
