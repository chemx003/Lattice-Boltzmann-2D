package simulations;
/*
 * This is a simple simulation that places LatticeNodes in a square grid and BBNodes at the boundaries
 */

import java.io.IOException;

import data_tools.FieldTool;
import lattices.Lattice;
import nodes.BBNode;
import nodes.LatticeNode;

public class Test {
	
	public static void main(String[] args) throws IOException{
		Lattice testLattice=new Lattice(21,21);
		testLattice.populate();
		FieldTool f=new FieldTool(testLattice);
		double[] velocity={0.0,0.0};
		double rho=1.0;
		
		for(int i=0;i<testLattice.getLenX();i++){
			BBNode aNode=new BBNode();
			testLattice.set(i, 0, aNode);
			aNode=new BBNode();
			testLattice.set(i, testLattice.getLenY()-1, aNode);
		}
		
//		for(int j=0;j<testLattice.getLenY();j++){
//			BBNode aNode=new BBNode();
//			testLattice.set(0, j, aNode);
//			aNode=new BBNode();
//			testLattice.set(testLattice.getLenX()-1, j, aNode);
//		}
		
		LatticeNode hNode=new LatticeNode(velocity,1.2);
		testLattice.set(10,10,hNode);
		
		testLattice.linkNodes();
		testLattice.verticalPBounds();
//		testLattice.horizontalPBounds();
		testLattice.printLattice();
		testLattice.initialize();
		
		for(int i=0; i<1000;i++){
		testLattice.stream();
		testLattice.collide();
		if(i%1==0){
		System.out.println("Writing data file at time step "+ i);
		f.writeDensities("p"+i+".dat");}
		}
	}
}
