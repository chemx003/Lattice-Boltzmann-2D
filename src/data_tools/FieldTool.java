package data_tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import lattices.Lattice;

public class FieldTool {
	
		public FieldTool(Lattice aLattice){
			lattice=aLattice;
		}
		
		public void writeDensities(String filename) throws IOException{
			PrintWriter out=null;
			
			try{
				out=new PrintWriter(new FileWriter(filename));
				out.println("#"+"\t" + "x" + "\t" +"y"+"\t"+"pressure");
				for(int i=0;i<lattice.getLenX();i++){
					for(int j=0;j<lattice.getLenY();j++){
							out.println(""+i+"\t"+j+"\t"+lattice.getNode(i,j).getRho());}
				}
			}
			finally{
				if(out!=null){
					out.close();
				}
			}
		}
		
		public void writeVMag(String filename) throws IOException{
			PrintWriter out=null;
			
			try{
				out=new PrintWriter(new FileWriter(filename));
				out.println("#"+"\t" + "x" + "\t" +"y"+"\t"+"velocity");
				for(int i=0;i<lattice.getLenX();i++){
					for(int j=0;j<lattice.getLenY();j++){
						out.println(""+i+"\t"+j+"\t"+lattice.getNode(i, j).getVMag());
					}
				}
			}
			
			finally{
				if(out!=null){
					out.close();
				}
			}		
		}
		
		public void writeVxMag(String filename) throws IOException{
			PrintWriter out=null;
			
			try{
				out=new PrintWriter(new FileWriter(filename));
				out.println("#"+"\t" + "x" + "\t" +"y"+"\t"+"velocity");
				for(int i=0;i<lattice.getLenX();i++){
					for(int j=0;j<lattice.getLenY();j++){
						if(lattice.getNode(i,j).getType()!='B'){
						out.println(""+i+"\t"+j+"\t"+lattice.getNode(i, j).getVxMag());}
					}
				}
			}
			
			finally{
				if(out!=null){
					out.close();
				}
			}		
		}
		
		public void writeVyMag(String filename) throws IOException{
			PrintWriter out=null;
			
			try{
				out=new PrintWriter(new FileWriter(filename));
				out.println("#"+"\t" + "x" + "\t" +"y"+"\t"+"velocity");
				for(int i=0;i<lattice.getLenX();i++){
					for(int j=0;j<lattice.getLenY();j++){
						out.println(""+i+"\t"+j+"\t"+lattice.getNode(i, j).getVyMag());
					}
				}
			}
			
			finally{
				if(out!=null){
					out.close();
				}
			}		
		}
		
		private Lattice lattice=null;

}
