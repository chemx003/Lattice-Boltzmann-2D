package nodes;

/*
 * To start i'll implement "on-grid" bounce back. The difference between the BBNode and the LatticeNode is 
 * the way fStar values are collected. Otherwise everything else seems the same. 
 */

public class BBNode extends LatticeNode {
	
	public BBNode(double[] velocity, double density){
		u=velocity;
		rho=density;
	}
	
	public BBNode(){};
	
	public void getDist(){
		for(int i=0;i<9;i++){
			switch(i) {
			case 0: fStar[0]=f[0]; break;
			
			case 1: if(nodes[2]==null){
						fStar[1]=f[3];
					}
					else{
						fStar[1]=nodes[2].giveDist(1); 
					}
					break;
			
			case 2: if(nodes[3]==null){
						fStar[2]=f[4];
					}
					else{
						fStar[2]=nodes[3].giveDist(2); 
					}
					break;
					
			case 3: if(nodes[0]==null){
						fStar[3]=f[1];
					}
					else{
						fStar[3]=nodes[0].giveDist(3); 
					}
					break;
					
			case 4: if(nodes[1]==null){
						fStar[4]=f[2];
					}
					else{
						fStar[4]=nodes[1].giveDist(4); 
					}
					break;
					
			case 5: if(nodes[6]==null){
						fStar[5]=f[7];
					}
					else{
						fStar[5]=nodes[6].giveDist(5); 
					}
					break;
			
			case 6: if(nodes[7]==null){
						fStar[6]=f[8];
					}
					else{
						fStar[6]=nodes[7].giveDist(6); 
					}
					break;
			
			case 7: if(nodes[4]==null){
						fStar[7]=f[5];
					}
					else{
						fStar[7]=nodes[4].giveDist(7); 
					}
					break;
			
			case 8: if(nodes[5]==null){
						fStar[8]=f[6];
					}
					else{
						fStar[8]=nodes[5].giveDist(8); 
					}
					break;
			}
		}
	}
	
	public char getType(){
		return 'B';
	}
}
