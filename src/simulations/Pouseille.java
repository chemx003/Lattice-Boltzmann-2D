package simulations;

import java.io.IOException;

import lattices.Lattice;
import nodes.BBNode;
import nodes.LatticeNode;
import data_tools.FieldTool;

public class Pouseille {
	
	public static void main(String[] args) throws IOException{
		Lattice lattice=new Lattice(32,16);
		FieldTool f=new FieldTool(lattice);
		double[] velocity={0.0,0.0};
		double[] u={0.0,0.0};
		double rho=1.025;
		
		lattice.populate(velocity,rho);
		
		for(int j=0;j<lattice.getLenY();j++){
			LatticeNode aNode=new LatticeNode(u, 1.05);
			lattice.set(0, j, aNode);
			aNode=new LatticeNode(velocity, 1.0);
			lattice.set(lattice.getLenX()-1, j, aNode);
		}
		
		for(int i=0;i<lattice.getLenX();i++){
			BBNode aNode=new BBNode(velocity,rho);
			lattice.set(i, 0, aNode);
			aNode=new BBNode(velocity,rho);
			lattice.set(i, lattice.getLenY()-1, aNode);
		}
		
		
		lattice.linkNodes();
		lattice.verticalPBounds();
		lattice.printLattice();
		lattice.initialize();
		
		for(int i=0; i<10001;i++){
		lattice.stream();
		lattice.collide();
		if(i%100==0){
		System.out.println("Writing data file at time step "+ i);
		f.writeVxMag("v"+i+".dat");}
		}
	}
	
}
