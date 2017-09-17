package packages;
import java.lang.Math;
import packages.Pair;

public class State implements Cloneable {
	public int a[][] = new int[3][3];
	public int f, g, h_sum;
	
	//int heuristic[][] = new int[3][3];
	
	/*public void resetHeuristic(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				heuristic[i][j] = 0;
			}
		}
	}*/
	

	public State(State current) {
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				this.a[i][j] = current.a[i][j];
			}
		}
		this.f = current.f;
		this.g = current.g;
		this.h_sum = current.h_sum;
	}

	public State() {
		for(int i=0;i<3;i++){
			for(int j=0; j<3; j++){
				this.a[i][j] = 0;
			}
		}
		this.f = 0;
		this.g = 0;
		this.h_sum = 0;
		
	}

	public void setArray(int[][] array){
		this.a = array;
	}
	
	private int getZeroIndex_i(int[][] a2) {
		//int i;
		for(int a = 0 ; a <3; a++){
			for(int b = 0; b<3; b++){
				if(a2[a][b] == 0){
					return a;
				}
			}
		}
		return -1;
	}

	private int getZeroIndex_j(int[][] a2) {
		//int j;
		for(int a = 0 ; a <3; a++){
			for(int b = 0; b<3; b++){
				if(a2[a][b] == 0){
					return b;
				}
			}
		}
		return -1;
	}

	public State moveLeft(){
		//State leftState = new State();
		int i  = getZeroIndex_i(this.a);
		int j  = getZeroIndex_j(this.a);
		if(j==0){
			return null;
		}
		State leftState =this;
		leftState.a[i][j] = leftState.a[i][j-1];
		leftState.a[i][j-1] = 0;
		return leftState;
	}
	
	public State moveRight(){
		State rightState = new State();
		int i  = getZeroIndex_i(this.a);
		int j  = getZeroIndex_j(this.a);
		if(j==2){
			return null;
		}
		rightState = this;
		rightState.a[i][j] = rightState.a[i][j+1];
		rightState.a[i][j+1] = 0;
		
		return rightState;
	}
	
	public State moveUp(){
		State upState = new State();
		int i  = getZeroIndex_i(this.a);
		int j  = getZeroIndex_j(this.a);
		if(i==0){
			return null;
		}
		upState = this;
		upState.a[i][j] = upState.a[i-1][j];
		upState.a[i-1][j] = 0;
		return upState;
	}
	
	public State moveDown(){
		State downState = new State();
		int i  = getZeroIndex_i(this.a);
		int j  = getZeroIndex_j(this.a);
		if(i==2){
			return null;
		}
		downState = this;
		downState.a[i][j] = downState.a[i+1][j];
		downState.a[i+1][j] = 0;
		return downState;
	}
	
	public int getHeuristic(){
		this.h_sum = calculateHeuristic(this);
		return this.h_sum;
	}
	
	public void setG(int depth){
		this.g = depth;
	}
	private int calculateHeuristic(State state) {
		int i, j, heuristic=0;
		
		//resetHeuristic();
		for(i=0;i<3;i++){
			for(j=0;j<3;j++){
				/*if((this.a[i][j]==0 && (i!=2 || j!=2))){
					heuristic = heuristic + Math.abs(i-2) + Math.abs(j-2);
				}
				else */
				if((this.a[i][j]==1 && (i!=0 || j!=0))){
					heuristic = heuristic + Math.abs(i-0) + Math.abs(j-0);
				}
				else if((this.a[i][j]==2 && (i!=0 || j!=1))){
					heuristic = heuristic + Math.abs(i-0) + Math.abs(j-1);
				}
				else if((this.a[i][j]==3 && (i!=0 || j!=2))){
					heuristic = heuristic + Math.abs(i-0) + Math.abs(j-2);
				}
				else if((this.a[i][j]==4 && (i!=1 || j!=0))){
					heuristic = heuristic + Math.abs(i-1) + Math.abs(j-0);
				}
				else if((this.a[i][j]==5 && (i!=1 || j!=1))){
					heuristic = heuristic + Math.abs(i-1) + Math.abs(j-1);
				}
				else if((this.a[i][j]==6 && (i!=1 || j!=2))){
					heuristic = heuristic + Math.abs(i-1) + Math.abs(j-2);
				}
				else if((this.a[i][j]==7 && (i!=2 || j!=0))){
					heuristic = heuristic + Math.abs(i-2) + Math.abs(j-0);
				}
				else if((this.a[i][j]==8 && (i!=2 || j!=1))){
					heuristic = heuristic + Math.abs(i-2) + Math.abs(j-1);
				}
				
				/*if(!((this.a[i][j]==(i*3)+j+1)&&a[i][j]!=0)){
					heuristic = heuristic;
				}
				if(this.a[i][j] == 0){
					heuristic = heuristic;
				}*/
			}
		}
		return heuristic;
	}
	 public Object clone() throws CloneNotSupportedException
		{
		return super.clone();
		}

	public void printMatrix() {
		for(int i=0; i<3; i++){

				System.out.println("{"+this.a[i][0]+" "+this.a[i][1]+" "+ this.a[i][2]+"}");
		}
		
	}
	
	
}
