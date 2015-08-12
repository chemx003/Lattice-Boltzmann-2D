/*
 * It seems as if the nodes are not interacting with each other
 */

import java.io.IOException;

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
			testLattice.set(i, 48, testBC);
		}
		for(int j=0;j<49;j++){
			BBNode testBC=new BBNode(testU, testP);
			testLattice.set(0, j, testBC);
			testLattice.set(48, j, testBC);
		}
		
		testLattice.set(22,22,hpNode);
		
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
