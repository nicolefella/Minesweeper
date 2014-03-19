
import java.lang.Math;

public class Board 
{
	//cell dimensions variable (8)
	static int boardDimension = 8;
	//matrix
	public Cell[][] boardMatrix;
	//number of bombs, initialized to zero
	int numBombs = 0;
	//number of cells
	static int numCells = boardDimension*boardDimension;
	//number of revealed cells, initialized to zero
	int numRevealedCells = 0;

	
	//constructor method
	public Board(){
		//call initBoard method
		boardMatrix = new Cell[boardDimension][boardDimension];
		initBoard();
	}

	//initBoard function to assign values to matrix
	public void initBoard(){
		//initalize contents of array
		for (int i=0; i<boardDimension; i++){
			for (int j=0; j<boardDimension; j++){
				//initialize a boardMatrix full of cells
				boardMatrix[i][j] = new Cell();
				boardMatrix[i][j].setValue((int)(Math.random()*10));
			}
		}
		//calculate number of cells containing bombs
		for (int i=0; i<boardDimension; i++){
			for (int j=0; j<boardDimension; j++){
				//if value is 9, then cell contains bomb
				if (boardMatrix[i][j].getValue() == 9){
					//if cell contains bomb, update numBombs variable
					numBombs = numBombs + 1;
				}
			}
		}
	}


	//updateBoard function
	public void updateBoard(int x, int y, int value){
		//each time function is called, a move is made
		//so update numRevealedCells each time function called
		numRevealedCells = numRevealedCells + 1;
		//reveal contents of array index 
		boardMatrix[x][y].setValue(value);
	}
}
