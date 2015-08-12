/*
 * The Lattice object will hold LattceNode and BBNode objects. It will have methods to initialize the LatticeNodes at 
 * locations with a global velocity and density. Will also need methods to change the properties of nodes in a specific area.
 * I'm thinking that I should go back and a constructor with no arguments for the LatticeNode.
 * 
 * I will either have to deal with generics or the BBNode objects will extend the lattice node objects
 */

import java.io.*;

public class Lattice {
	
	public Lattice(int lenX, int lenY){
		lattice=new LatticeNode[lenX][lenY];
		this.lenX=lenX;
		this.lenY=lenY;
	}
	
	/*
	 * Populates entire lattice with a default node
	 */
	public void populate(){
		for(int i=0;i<lenX;i++){
			for(int j=0;j<lenY;j++){
				LatticeNode defaultNode=new LatticeNode();
				lattice[i][j]=defaultNode;
			}
		}
	}
	
	/*
	 * Populates entire lattice with a node of specific velocity and density
	 */
	public void populate(double[] velocity, double density){
		for(int i=0;i<lenX;i++){
			for(int j=0;j<lenY;j++){
				LatticeNode defaultNode=new LatticeNode(velocity,density);
				lattice[i][j]=defaultNode;
			}
		}
	}
	
	/*
	 * Links the nodes together. DO NOT CALL UNTIL BOUNDARIES SET.
	 * 
	 * ALSO: recall that nodes[][] does not include to 0 basis need to shift things over
	 */
	public void linkNodes(){
		
		for(int j=0;j<lenY;j++){
			for(int i=0;i<lenX-1;i++){
					lattice[i][j].setNode(0, lattice[i+1][j]);
					lattice[i+1][j].setNode(2, lattice[i][j]);
			}
		}//horiz
		
		for(int i=0;i<lenX; i++){
			for(int j=0;j<lenY-1;j++){
				lattice[i][j].setNode(1, lattice[i][j+1]);
				lattice[i][j+1].setNode(3, lattice[i][j]);
			}
		}//vertical
		
		for(int i=0;i<lenX-1;i++){
			for(int j=0;j<lenY-1;j++){
				lattice[i][j].setNode(4, lattice[i+1][j+1]);
				lattice[i+1][j+1].setNode(6, lattice[i][j]);
			}
		}//diag up
		
		for(int i=0;i<lenX-1;i++){
			for(int j=1;j<lenY;j++){
				lattice[i][j].setNode(7, lattice[i+1][j-1]);
				lattice[i+1][j-1].setNode(5, lattice[i][j]);
			}
		}//diag down
	}
	
	/*
	 * Populates a single location on the lattice with a node
	 */
	public void set(int xPos, int yPos, LatticeNode newNode){
		if(xPos<lenX && yPos<lenY){
			lattice[xPos][yPos]=newNode;
		}
	}
	
	public void initialize(){
		for(int i=0;i<lenX;i++){
			for(int j=0;j<lenY;j++){
				lattice[i][j].calcDist();
			}
		}
	}
	
	public void stream(){
		for(int i=0;i<lenX;i++){
			for(int j=0;j<lenY;j++){
				lattice[i][j].getDist();
				lattice[i][j].calcRho();
				lattice[i][j].calcU();
			}
		}
	}
	
	public void collide(){
		for(int i=0;i<lenX;i++){
			for(int j=0;j<lenY;j++){
				lattice[i][j].calcDist();
			}
		}
	}
	
	public void writeDensities(String filename) throws IOException{
		PrintWriter out=null;
		
		try{
			out=new PrintWriter(new FileWriter(filename));
			out.println("#"+"\t" + "x" + "\t" +"y"+"\t"+"pressure");
			for(int i=0;i<lenX;i++){
				for(int j=0;j<lenY;j++){
					out.println(""+i+"\t"+j+"\t"+lattice[i][j].getRho());
				}
			}
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	public void writeDist(int i, int j, String filename) throws IOException{
		PrintWriter out=null;
		
		try{
			out=new PrintWriter(new FileWriter(filename));
			for(int x=0;x<9;x++){
				out.println(lattice[i][j].giveDist(x));
			}
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	public void writeFStar(int i, int j, String filename) throws IOException{
		PrintWriter out=null;
		
		try{
			out=new PrintWriter(new FileWriter(filename));
			for(int x=0;x<9;x++){
				out.println(lattice[i][j].giveFStar(x));
			}
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	public void printLattice(){
		for(int j=lenY-1;j>=0;j--){	
			for(int i=0;i<lenX;i++){
				if(lattice[i][j]!=null){
					if(lattice[i][j].getType()=='L'){
						System.out.print("L ");
					}
					else if(lattice[i][j].getType()=='B'){
						System.out.print("B ");
					}
					else{
						System.out.print("U ");
					}
				}
				else{
					System.out.print("F ");
				}
			}
			System.out.println();
		}
	}
	
	private LatticeNode[][] lattice;
	private int lenX;
	private int lenY;
}
