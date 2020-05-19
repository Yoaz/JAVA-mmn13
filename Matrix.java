/*
* This class represent a matrix = a 2D array of intergers range 0-255
*
* 
* Version: 20.04.19 
* Author: Yoaz Shmider
*/


/* @param _array an array of Box3D type of value
*  @param final BLACK an integar represent black int value
*  @param final WHITE an integar represent white int value
*/

public class Matrix{
	
	private int[][] _array;
	private final int BLACK = 255;
	private final int WHITE = 0;
			
		
	/* 
	* This is a constructor that construct a matrix from a 2D array, the dimenision and values
	* of the matrix will be the same as the 2D array values
	* @param array an int[] 2D array represent the Matrix and contain int range 0-255
	*/	
	public Matrix(int[][] arr){
		_array = new int[arr.length][arr[0].length];
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				_array[i][j] = arr[i][j];
			}
		}
		
	}
	
	
	/* 
	* This is a constructor that construct a matrix with dimenision of size1 X size2 and 0 as cell values
	* @param size1, size2 an int's represent the matrix dimenisions.
	*/	
	public Matrix(int size1, int size2){
		_array = new int[size1][size2];
	}
		
		
	/*
     * Display the Matrix 
     * @return the Matrix
    */
	public String toString(){
		String str = "";
		for(int i=0;i<_array.length;i++){
			 for(int j=0;j<_array[0].length;j++){
				 //if cell location is last col add line break
				 if(j == (_array[0].length-1))
				 	str += _array[i][j] + "\n";
				 //else add tab space
				 else	
				 	str += _array[i][j] + "\t";
			 }	
		}
		return str;
	}
	
	
	/* 
	* This method computes and return new Matrix with cell values opposite to original or Black - (Cell Value).
	* @return new Matrix object that represent the negative image of the matrix, if cell value is black -> white, white -> black
	* else cell value = black - (cell value).
	*/	
	public Matrix makeNegative(){
		Matrix newMatrix;
		int[][] arr = new int[_array.length][_array[0].length];
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(_array[i][j] == BLACK)
					arr[i][j] = WHITE;
				else if(_array[i][j] == WHITE)
					arr[i][j] = BLACK;
				else
					arr[i][j] = BLACK - _array[i][j];
			}
		}
		newMatrix = new Matrix(arr);
		return newMatrix;
	}
	
	
	/* 
	* This method computes and return if an accepted cell index values are valid = within matrix range
	* @return true if it is, flase otherwise
	*/
	private boolean withinGrid(int row, int col){
		if(row > _array.length || row < 0 || col > _array[0].length || col < 0)
			return false;
		return true;
	}
	
	
	/* 
	* This method computes and return new Matrix with cell values that are the average of their neighboors
	* @return new Matrix object that each cell have the average value of its neighboors
	*/
	public Matrix imageFilterAverage(){
		int counter = 0;
		int sum = 0;
		int[][] arr = new int[_array.length][_array[0].length];
		for(int k=0;k<_array.length;k++){
			for(int r=0;r<_array[0].length;r++){
				arr[k][r]=_array[k][r];
			}
		}
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				//Check for valid neighboors = in matrix range
				//looping through one row before -> one row after + one col before -> one col after
				for(int t=i-1;t<=i+1;t++){
					for(int s=j-1;s<=j+1;s++){
						//if indexes within mat range
						if(this.withinGrid(t,s)){
							sum += _array[t][s];
							counter++;
						}
					}
				}
			arr[i][j]=sum/counter;
			sum = 0;
			counter = 0;	
			}
		}
	Matrix filteredMatrix = new Matrix(arr);
	return filteredMatrix;	
	}
	
	
	/* 
	* This method computes and return new Matrix rotated 90 degree clockwise
	* @return new Matrix object that represent the rotated 90 clockwise matrix
	*/
	public Matrix rotateClockwise(){
		//new rotated matrix will have num of rows equal to num of columns of original matrix 
		//and num of columns in rotated matrix will be equal to number of rows
		int rotatedMatCols = _array.length;
		int rotatedMatRows = _array[0].length;
		int[][] arr = new int[rotatedMatRows][rotatedMatCols];
		
		for(int i=0;i<_array.length;i++){
		//for(int i=0;i<rotatedMatCols;i++)
			for(int j=0;j<_array[0].length;j++){
			//for(int j=0;j<rotatedMatRows;j++)
			arr[j][(rotatedMatCols - 1) - i] = _array[i][j];
			}
		}
		
		Matrix rotatedColckwiseMatrix = new Matrix(arr);
		return rotatedColckwiseMatrix;
		
	}
	
	
	/* 
	* This method computes and return new Matrix rotated 90 degree counter clockwise
	* @return new Matrix object that represent the rotated 90 counter clockwise matrix
	*/
	public Matrix rotateCounterClockwise(){
			//new rotated matrix will have num of rows equal to num of columns of original matrix 
		//and num of columns in rotated matrix will be equal to number of rows
		int rotatedMatCols = _array.length;
		int rotatedMatRows = _array[0].length;
		int[][] arr = new int[rotatedMatRows][rotatedMatCols];
		
		for(int i=0;i<_array.length;i++){
		//for(int i=0;i<rotatedMatCols;i++)
			for(int j=0;j<_array[0].length;j++){
			//for(int j=0;j<rotatedMatRows;j++)
			arr[(rotatedMatRows - 1) - j][i] = _array[i][j];
			}
		}
		
		Matrix rotatedColckwiseMatrix = new Matrix(arr);
		return rotatedColckwiseMatrix;
	}	
	
}