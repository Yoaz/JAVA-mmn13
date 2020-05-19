/*
* This class represent a collection of 3-dimensional boxes 
*
* 
* Version: 20.04.19 
* Author: Yoaz Shmider
*/


/* @param _boxes an array of Box3D type of value
*  @param _noOfBoxes the number of boxes in Point3D array
*  @param final MAX_NUM_BOXES the max Box3D variables in the class
*/
public class Collection{

	private Box3D[] _boxes;
	private int _noOfBoxes;
	private final int MAX_NUM_BOXES = 100;
	
	
	/* default constructor setting class values to max possible
	*/
	public Collection(){
		_noOfBoxes = 0;
		_boxes = new Box3D[MAX_NUM_BOXES];
	}
	
	
	/* This method accepts Box3D values and add the box based on the values recieved to the correct index in the
	* class array based on the volume value of the box.
	* @param base a Point3D object of the base point of the accpeted box
	* @param length the length of the accepted box
	* @param width the width of the accepted box
	* @param height the height of the accepted box
	* @return true if succeded to add the box and there's was space for it, false otherwise.
	*/
	public boolean addBox(Point3D base, int length, int width, int height){
		
		//Checking _boxes array isnt full, @return false if it is.
		if(_boxes[MAX_NUM_BOXES - 1] != null)
			return false;
			
		Box3D newBox = new Box3D(base, length, width, height);
		
		//Inserting the newBox into correct index into _boxes based on volume value
		//if _boxes is empty. inserting new box to index 0
		if(_noOfBoxes < 1){
			_boxes[0] = new Box3D(newBox);
			_noOfBoxes++;
			return true;
		}
		else{
			for(int i=0; i<_noOfBoxes; i++){
				if(newBox.getVolume() <= _boxes[i].getVolume()){
					for(int j=_noOfBoxes; j>i; j--){
						_boxes[j] = new Box3D(_boxes[j-1]); 
					}
					_boxes[i] = new Box3D(newBox);
					_noOfBoxes ++;
					return true;
				}
			}
			//newBox has the heights volume of all current boxes in array
			//Inserting the newBox one index after the last box in _boxes
			_boxes[_noOfBoxes] = new Box3D(newBox);
			_noOfBoxes++;
		}
		return true;
	}
	
	
	/* Checks which 3D box have the most upper base corner from all boxes in the _boxes array class property
	* @return Box3D object of the box with the most upper corner from all, if there are several with same max base, the first one
	* in the array will be the returned one, if array is empty, return NULL
	*/
	public Box3D mostUpperBaseCorner(){
		if(_boxes[0] == null)
			return null;
		Box3D mostUpBox = new Box3D(_boxes[0]);
		for(int i=0; i<_noOfBoxes; i++){
			//if current mostUpBox has equal base point z index as next box in array or next box in array has lower 
			//base point then current box -> i++, mostUpBox not changed
			if(mostUpBox.getCenter().getZ() == _boxes[i].getCenter().getZ() || _boxes[i].getCenter().isUnder(mostUpBox.getCenter())) 
				i++;
			//else whats left is that next box in array is above mostUpBox -> next box is set to be the mostUpBox
			else 
				mostUpBox = new Box3D(_boxes[i]);
		}	
		return mostUpBox;
	}
	
	
	/* 
	* @return the total surface area of all the Boxes in the array
	*/
	public double totalSurfaceArea(){
		double totalSurfaceArea = 0;
		for(int i=0;i<_noOfBoxes;i++){
			totalSurfaceArea += _boxes[i].getSurfaceArea();
		}
		return totalSurfaceArea;
	}
	
	
	/* 
	* @return the longest distance between 2 boxes in the array
	* if array contains less then 2 boxes, @return 0;
	*/
	public double longestDistance(){
		if(_noOfBoxes < 2)
			return 0;
		double longestDistance = 0.0;
		for(int i=0;i<_noOfBoxes;i++){
			for(int j=i+1;j<_noOfBoxes;j++){
			if(_boxes[i].distance(_boxes[j]) >= longestDistance)
				longestDistance = _boxes[i].distance(_boxes[j]);
			}
		}
		return longestDistance;
	}
	
	
	/* 
	* @return the number of boxes who can contain the accepted box
	* @param box a Box3D object to be checked how many boxes contain it
	*/
	public int howManyContains(Box3D box){
		int counter = 0;
		for(int i=0;i<_noOfBoxes;i++){
			if(_boxes[i].contains(box))
				counter ++;
		}
		return counter;
	}
	
	
	/*
	* @return the volume value of the smallest box that can contain all boxes in 
	* the collection between 2 accepted index values
	* @param i,j an int index range
	*/
	public int volumeOfSmallestBox(int i, int j){
		//making sure the index range is valid
		//@return 0 if not
		if(i<0 || j<0 || j>=_noOfBoxes || i>=_noOfBoxes)
			return 0;
		
		//making sure i index var will be the smallest index and j is the biggest
		if(i>j){
			int tempIndex = i;
			i = j;
			j = tempIndex;
		}
		
		//making sure there is boxes in all index range value
		//@return 0 if not
		for(int s=i;s<=j;s++){
			if(_boxes[s] == null)
				return 0;
		}
		
		//if range valid computes which box in range contains all the other boxes in range
		//means have the biggest volume
		Box3D smallestVolumeBox = new Box3D(_boxes[i]);
		for(int k=i+1;k<=j;k++){
			if(smallestVolumeBox.contains(_boxes[k]) || smallestVolumeBox.equals(_boxes[k]))
				k++;		
			else
				smallestVolumeBox = new Box3D(_boxes[k]);	
		}
		return (smallestVolumeBox.getLength()+1) * (smallestVolumeBox.getWidth()+1) * (smallestVolumeBox.getHeight()+1);
	}
	
	
	/*
	* @return a new size full array that contain all boxes
	*/
	public Box3D[] getBoxes(){
		Box3D[] arrayBoxes = new Box3D[_noOfBoxes];
		for(int i=0;i<_noOfBoxes;i++){
			arrayBoxes[i] = new Box3D(_boxes[i]);
		}
		return arrayBoxes;
	}
	
	
	/*
	* @return the number of boxes in the array
	*/
	public int getNumOfBoxes(){
		return _noOfBoxes;
	}
	
	
	/*
     * Display the Box3D collection
     * @return the Box3D collection
    */
	public String toString(){
		String boxString = "";
		for(int i=0;i<_noOfBoxes;i++){
			/*if(i == (_noOfBoxes-1))
				boxString += "Box no. " + (i+1) + ": " + _boxes[i].toString();	
			else */
				boxString += "Box no. " + (i+1) + ": " + _boxes[i].toString() + "\n";
		}
		return boxString;
	}


}