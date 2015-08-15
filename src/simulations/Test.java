package simulations;
/*
 * This is a simple simulation that places LatticeNodes in a square grid and BBNodes at the boundaries
 */

import java.io.IOException;
import lattices.Lattice;
import nodes.BBNode;
import nodes.LatticeNode;

public class Test {
	
	public static void main(String[] args) throws IOException{
		Lattice testLattice=new Lattice(49,49);
		double[] testU={0.0,0.0};
		double testP=1.0;
		LatticeNode hpNode=new LatticeNode(testU, testP+.01);
		
		testLattice.populate(testU, testP);
		
		for(int i=0;i<49;i++){
			BBNode testBC=new BBNode(testU, testP);
			testLattice.set(i, 0, testBC);
			testBC=new BBNode(testU, testP);
			testLattice.set(i, 48, testBC);
		}
		for(int j=0;j<49;j++){
			BBNode testBC=new BBNode(testU, testP);
			testLattice.set(0, j, testBC);
			testBC=new BBNode(testU, testP);
			testLattice.set(48, j, testBC);
		}
		
		testLattice.set(25,25,hpNode);
		
		testLattice.linkNodes();
		
//		testLattice.printLattice();
				
		testLattice.initialize();
		
		for(int i=0; i<200;i++){
		testLattice.stream();
		testLattice.collide();
		System.out.println("Writing data file at time step "+ i);
		testLattice.writeDensities("p"+i+".dat");
		}
		
	}
}
