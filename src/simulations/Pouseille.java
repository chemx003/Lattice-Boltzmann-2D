package simulations;

import java.io.IOException;

import lattices.Lattice;
import nodes.BBNode;
import nodes.LatticeNode;

public class Pouseille {
	
	public static void main(String[] args) throws IOException{
		Lattice lattice=new Lattice(40,32);
		double[] velocity={0.0,0.0};
		double[] u={0.0,0.0};
		double rho=1.025;
		
		lattice.populate(velocity,rho);
		
		for(int j=0;j<32;j++){
			LatticeNode aNode=new LatticeNode(u, 1.05);
			lattice.set(0, j, aNode);
			aNode=new LatticeNode(velocity, 1.00);
			lattice.set(39, j, aNode);
		}
		
		for(int i=0;i<40;i++){
			BBNode aNode=new BBNode(velocity,rho);
			lattice.set(i, 0, aNode);
			aNode=new BBNode(velocity,rho);
			lattice.set(i, 31, aNode);
		}
		
//		lattice.printLattice();
		lattice.linkNodes();
		lattice.verticalPBounds();
		lattice.initialize();
		
		for(int i=0; i<2000;i++){
		lattice.stream();
		lattice.collide();
		if(i%5==0){
		System.out.println("Writing data file at time step "+ i);
		lattice.writeVMag("v"+i+".dat");}
		}
	}
	
}
