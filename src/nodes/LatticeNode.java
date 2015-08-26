package nodes;
/*
 * The theory of the LatticeNode class is mostly derived from Yuanxun Bill Bao's 
 * Lattice Boltzmann Method for Fluid Simulations
 */

public class LatticeNode {
	
	/*
	 * Constructor for the lattice node. The lattice node carries the distribution functions 
	 * for each of the velocities at the node, the distribution functions most recently streamed
	 * to it, an array of pointers to it's nearest neighbors, the lattice speed, and the 
	 * relaxation time.
	 * 
	 * @param 	velocity	velocity vector of the fluid at the node
	 * @param 	density		density of fluid at the node	
	 */
	public LatticeNode(double[] velocity, double density){
		u=velocity;
		rho=density;
	}
	
	/*
	 * Default constructor for the lattice node leaves velocity vector as {0.0,0.0} at the node
	 * and the density as 0 at the node
	 */
	public LatticeNode(){}
	
	/*
	 * CalcF() calculates the distribution function at the point using the equilibrium distribution
	 * functions and the distribution functions streamed from other nodes according to the Lattice 
	 * Boltzmann equation
	 */
	public void calcDist(){
		double[] fEQ=calcEQDist();
		if(fStar==null){
			fStar=new double[9];
			for(int i=0; i<9; i++){
				f[i]=fEQ[i];
			}
		}
		else{
			for(int i=0; i<9; i++){
				f[i]=fStar[i]-(1/tau)*(fStar[i]-fEQ[i]);
			}
		}
	}
	
	/*
	 * Calculates equilibrium functions for each basis using the velocity and density calculated using 
	 * distribution functions streamed from neighboring nodes
	 * 
	 * @return 				An array of equilibrium functions for each basis
	 */
	public double[] calcEQDist(){
		double[] fEQ=new double[9];
		for(int i=0;i<9;i++){
			double wi=w(i);
			double[] b=b(i);
			double eqDist=wi*rho+rho*wi*( (3*(b[0]*u[0]+b[1]*u[1])/c) 
					+ (4.5*Math.pow(b[0]*u[0]+b[1]*u[1], 2)/Math.pow(c, 2)) 
					- (1.5*(u[0]*u[0]+u[1]*u[1])/Math.pow(c, 2)) );
			fEQ[i]=eqDist;
		}
		return fEQ;
	}
	
	/*
	 * Calculates the density at the node from the most recently streamed distributions functions
	 */
	public void calcRho(){
		double density=0;
		for(int i=0;i<9;i++){
			density+=fStar[i];
		}
		rho=density;
	}//calculates density in similar manner to u
	
	/*
	 * Calculates the velocity as the sum of the distribution functions in the direction of all basis 
	 * at the node multiplied by the lattice speed and divided by the density
	 */
	public void calcU(){
		double[] velocity={0.0,0.0};
		double[] basis;
		for(int i=0;i<9;i++){
			basis=b(i);
			velocity[0]+=(c/rho)*fStar[i]*basis[0];
			velocity[1]+=(c/rho)*fStar[i]*basis[1];
		}
		u=velocity;
	}
	
	/*
	 * Getter method for f
	 * 
	 * @return				f value at a specified index
	 */
	public double giveDist(int index){
		if(index<9 && index>=0)
			return f[index];
		else 
			return 0;
	}

	/*
	 * Getter method for f*
	 * 
	 * @return				f* value at specified index
	 */
	public double giveFStar(int index){
		if(index<9 && index>=0)
			return fStar[index];
		else 
			return 0;
	}//returns fistar

	
	/*
	 * getDist() retrieves the fi from the surrounding nodes and places them in fStar in the 
	 * streaming process. It will have to account for the boundary conditions. 
	 */
	public void getDist(){
		for(int i=0;i<9;i++){
			switch(i) {
			case 0: fStar[0]=f[0]; break;
			case 1: fStar[1]=nodes[2].giveDist(1); break;
			case 2: fStar[2]=nodes[3].giveDist(2); break;
			case 3: fStar[3]=nodes[0].giveDist(3); break;
			case 4: fStar[4]=nodes[1].giveDist(4); break;
			case 5: fStar[5]=nodes[6].giveDist(5); break;
			case 6: fStar[6]=nodes[7].giveDist(6); break;
			case 7: fStar[7]=nodes[4].giveDist(7); break;
			case 8: fStar[8]=nodes[5].giveDist(8); break;
			}
		}
	}
	
	/*
	 * @return				the lattice speed at the node
	 */
	public double getC(){
		return c;
	}

	/*
	 * @return	u			velocity vector at the node as an array
	 */
	public double[] getU(){
		if(u!=null)
			return u;
		else
			return null;
	}
	
	/*
	 * @return	vMag		velocity magnitude at the node
	 */
	public double getVMag(){
		double vMag=0.0;
		if(u!=null){
			vMag=Math.sqrt(u[0]*u[0]+u[1]*u[1]);
			return vMag;
		}
		else{
			return vMag;
		}
	}
	
	public double getVxMag(){
		double vxMag=0.0;
		if(u!=null){
			vxMag=u[0];
			return vxMag;
		}
		else{
			return vxMag;
		}
	}
	
	public double getVyMag(){
		double vyMag=0.0;
		if(u!=null){
			vyMag=u[1];
			return vyMag;
		}
		else{
			return vyMag;
		}
	}
	
	/*
	 * @return				relaxation constant at the node
	 */
	public double getTau(){
		return tau;
	}
	
	/*
	 * @return				density at node
	 */
	public double getRho(){
		return rho;
	}
	
	/*
	 * @return				LatticeNode object at specified index in nodes
	 */
	public LatticeNode getNode(int index){
		if(index<8 && index>=0)
			return nodes[index];
		else
			return null;
	}
	
	public char getType(){
		return type;
	}
	
	public void setType(char c){
		type=c;
	}
	
	/*
	 * Places a LatticeNode object in nodes at a given index
	 * 
	 * @param	index		index in nodes
	 * @param 	n			LatticeNode object to be placed in nodes
	 */
	public void setNode(int index, LatticeNode n){
		nodes[index]=n;
	}
	
	public void setRho(double density){
		rho=density;
	}
	
	/*
	 * Sets the distribution function in f at a given index
	 * 
	 * @param	index		index in f
	 * @param	fi			distribution function to be placed in f
	 */
	public void setDist(int index, double fi){
		f[index]=fi;
	}
	
	/*
	 * Sets the distribution function in fStar
	 * 
	 * @param index			index in fStar
	 * @param fistar		distribution function to be placed in fStar
	 */
	public void setFStar(int index, double fiStar){
		fStar[index]=fiStar;
	}
	
	/*
	 * @param	i			new lattice speed for the node
	 */
	public void setC(double i){
		c=i;
	}
	
	/*
	 * @param	i			new relaxation constant for the node
	 */
	public void setTau(double i){
		tau=i;
	}
	
	/*
	 * w method returns weights necessary to calculate the distribution function for a basis
	 * 
	 * @param	i			basis index
	 * @return 				weight 
	 */
	public double w(int i){
		double weight;
		if(i==0){
			weight=4.0/9.0;}
		else if(i>0 && i<=4){
			weight=1.0/9.0;}
		else if(i>4 && i<=8){
			weight=1.0/36.0;}
		else{
			weight=0;}
		return weight;
	}
	
	/*
	 * b method returns basis vectors each of the velocities
	 * 
	 * @param	i			basis index
	 * @return				basis in an array
	 */
	public double[] b(int i){
		double[] basis=new double[2];
		switch (i) {
		case 0: basis[0]=0; basis[1]=0; break; 
		case 1: basis[0]=1; basis[1]=0; break; 
		case 2: basis[0]=0; basis[1]=1; break; 
		case 3: basis[0]=-1; basis[1]=0; break; 
		case 4: basis[0]=0; basis[1]=-1; break; 
		case 5: basis[0]=1; basis[1]=1; break; 
		case 6: basis[0]=-1; basis[1]=1; break; 
		case 7: basis[0]=-1; basis[1]=-1; break; 
		case 8: basis[0]=1; basis[1]=-1; break; 
		}
		return basis;
	}
	
	public void bounceback(){};
	
	protected double[] f=new double[9];						//Array holding distribution fcns for each basis
	protected double[] fStar=null;							//Array holding dist. fcns streamed from other nodes
	protected LatticeNode[] nodes=new LatticeNode[8];		//Array holding pointers to other nodes
	protected double[] u={0.0,0.0};							//Current velocity at the node
	protected double rho=1.0;									//Current density at the node
	protected double c=1.0;									//Lattice speed at the node
	protected double tau=1;									//Relaxation constant at the node
	protected char type='L';
}
