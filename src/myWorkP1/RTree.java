package myWorkP1;

public class RTree {
	int degree;
	MBR root;
	MBR nowPtr;
	MBR nextPtr;
	MBR lastPtr;
	float enlargePerimeter;
	Point [] tempPoint;
	MBR [] tempMBR;
	
	public RTree(int de){
		degree = de;
		root = new MBR(degree,false);
		nowPtr = new MBR(degree,true);
		root.mbrPtr[0] = nowPtr;
		root.nowDegree ++;
		nowPtr.parentPtr = root;
		enlargePerimeter = 999999999;
		tempPoint = new Point[degree+1];
		tempMBR = new MBR[degree+1];
	}
	
	public RTree(){
	}
	
	public void add(Point p){
		nowPtr = root;
		while(nowPtr.isLeaf == false){  //�]��leaf node����
			lastPtr = nowPtr;
			for(int i= 0; i<lastPtr.nowDegree ; i++){
				//System.out.println(nowPtr.nowDegree);
				nextPtr = lastPtr.mbrPtr[i];
				if(p.calculateEnPerimeter(nextPtr.minX, nextPtr.minY, nextPtr.maxX, nextPtr.maxY) < enlargePerimeter){
					enlargePerimeter = p.calculateEnPerimeter(nextPtr.minX, nextPtr.minY, nextPtr.maxX, nextPtr.maxY);
					nowPtr = nextPtr;
				}
			}
			enlargePerimeter = 999999999;
		}
		if(nowPtr.nowDegree < degree){ //����split
			nowPtr.dataPtr[nowPtr.nowDegree] = p;
			nowPtr.nowDegree++;
			updateMinMax(nowPtr,0); //��sleaf min max��
			nextPtr = nowPtr.parentPtr;
			updateToRoot(nextPtr); //nextPtr�����A�@����s��root;
		}
		else{ //��split
			System.out.println(123);
			nowPtr.dataPtr[nowPtr.nowDegree] = p;
			nowPtr.nowDegree++;
			for(int i=0;i<(degree+1);i++)
				tempPoint[i] = nowPtr.dataPtr[i];
			
			sortTempPoint();
			
			nextPtr = nowPtr.parentPtr; //����leaf��parent
			nextPtr.mbrPtr[nextPtr.nowDegree] = new MBR(degree,true);
			nextPtr.nowDegree++;
			
			lastPtr = nextPtr.mbrPtr[nextPtr.nowDegree-1]; //����new�X��child
			lastPtr.parentPtr = nextPtr; //lastPtr��parent
			
			initialMBR(nowPtr,0);	 //��l2�Ӥ���
			initialMBR(lastPtr,0);
			
			for(int i=0;i<(degree+1);i++){	//���t��2�Ӥ���
				if(i<(degree/2)){
					nowPtr.dataPtr[nowPtr.nowDegree] = tempPoint[i];
					nowPtr.nowDegree++;
				}
				else{
					lastPtr.dataPtr[lastPtr.nowDegree] = tempPoint[i];
					lastPtr.nowDegree++;
				}
			}
			
			updateMinMax(nowPtr,0);
			updateMinMax(lastPtr,0);
			
			while(nextPtr.nowDegree > degree){ //nextPtr��nowPtr�MlastPtr��parent
				
				for(int i=0;i<(degree+1);i++)
					tempMBR[i] = nextPtr.mbrPtr[i];
				
				sortTempMBR();

				if(nextPtr.parentPtr == null){ //nextPtr�Sparent�A�s�Wroot
					root = new MBR(degree,false);
					root.mbrPtr[root.nowDegree] = nextPtr;
					root.nowDegree++;
					nextPtr.parentPtr = root;
				}
				
				nowPtr = nextPtr;
				nextPtr = nextPtr.parentPtr; //�A���ʨ�W�h�ܦ�parent��new child
				nextPtr.mbrPtr[nextPtr.nowDegree] = new MBR(degree,false);
				nextPtr.nowDegree++;
				lastPtr = nextPtr.mbrPtr[nextPtr.nowDegree-1];
				lastPtr.parentPtr = nextPtr;
				
				initialMBR(nowPtr,1);	 //��l2�Ӥ���
				initialMBR(lastPtr,1);
				
				for(int i=0;i<(degree+1);i++){	//���t��2�Ӥ���
					if(i<(degree/2)){
						nowPtr.mbrPtr[nowPtr.nowDegree] = tempMBR[i];
						nowPtr.mbrPtr[nowPtr.nowDegree].parentPtr = nowPtr;
						nowPtr.nowDegree++;
					}
					else{
						lastPtr.mbrPtr[lastPtr.nowDegree] = tempMBR[i];
						lastPtr.mbrPtr[lastPtr.nowDegree].parentPtr = lastPtr;
						lastPtr.nowDegree++;
					}
				}
				
				updateMinMax(nowPtr,1);
				updateMinMax(lastPtr,1);
				
			}
			
			updateToRoot(nextPtr); //nextPtr�����A�@����s��root;
		}
	}
	
	private void sortTempPoint(){
		Point temp;
		for(int i = 0; i < degree;i++)
			for(int j=0;j < (degree-i) ;j++){
				if(tempPoint[j].getX() > tempPoint[j+1].getX()){
					temp = tempPoint[j];
					tempPoint[j] = tempPoint[j+1];
					tempPoint[j+1] = temp;
				}
			}
	}
	
	private void initialMBR(MBR m , int num){
		m.maxX = -1;
		m.maxY = -1;
		m.minX = 99999;
		m.minY = 99999;
		m.nowDegree = 0;
		
		if(num == 0){ //leaf
			for(int i=0;i<(degree+1);i++) 
				m.dataPtr[i] = null;
		}
		else{
			for(int i=0;i<(degree+1);i++) 
				m.mbrPtr[i] = null;
		}
	}
	
	private void updateMinMax(MBR m , int num){
		m.maxX = -1;
		m.maxY = -1;
		m.minX = 99999;
		m.minY = 99999;
		
		if(num == 0){
			for(int i=0;i<m.nowDegree;i++){	//��smax min��
				if(m.dataPtr[i].getX() > m.maxX)
					m.maxX = m.dataPtr[i].getX();
				if(m.dataPtr[i].getX() < m.minX)
					m.minX = m.dataPtr[i].getX();
				if(m.dataPtr[i].getY() > m.maxY)
					m.maxY = m.dataPtr[i].getY();
				if(m.dataPtr[i].getY() < m.minY)
					m.minY = m.dataPtr[i].getY();
			}
		}
		else{
			for(int i=0;i<m.nowDegree;i++){
				if(m.mbrPtr[i].maxX > m.maxX)
					m.maxX = m.mbrPtr[i].maxX;
				if(m.mbrPtr[i].minX < m.minX)
					m.minX = m.mbrPtr[i].minX;
				if(m.mbrPtr[i].maxY > m.maxY)
					m.maxY = m.mbrPtr[i].maxY;
				if(m.mbrPtr[i].minY < m.minY)
					m.minY = m.mbrPtr[i].minY;
			}
		}
	}
	
	private void sortTempMBR(){
		MBR temp;
		for(int i = 0; i < degree;i++)
			for(int j=0;j < (degree-i) ;j++){
				if( (tempMBR[j].maxX + tempMBR[j].minX) > (tempMBR[j+1].maxX + tempMBR[j+1].minX) ){
					temp = tempMBR[j];
					tempMBR[j] = tempMBR[j+1];
					tempMBR[j+1] = temp;
				}
			}
	}
	
	private void updateToRoot(MBR m){
		if(m!=null){
			updateMinMax(m,1);
			updateToRoot(m.parentPtr);
		}
	}
}
