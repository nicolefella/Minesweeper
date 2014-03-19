
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class MinesweeperGame {
	//game board to be set and compared to
	public Board gameBoard;

	//guess board to be updated and displayed
	public Board guessBoard;

	//create variables to be updated later for user guess coordinates
	int inputX;
	int inputY;

	public MinesweeperGame(){
		//create instances of these two boards
		gameBoard = new Board();
		guessBoard = new Board();
	}

	//method initGame to set the boards
	public void initGame(){
		//call initGuess
		initGuess();
	}

	//method initGuess for initializing the guessBoard 
	public void initGuess(){
		//use for-loop to walk through guessBoard matrix and initialize cells
		for (int i=0; i<Board.boardDimension; i++){
			for (int j=0; j<Board.boardDimension; j++){
				//initialize board with random integer 404 because no cell can have 404 neighbors
				int gtg = 404;
				guessBoard.boardMatrix[i][j].setValue(gtg);
			}
		}

		//call helper function to display the board
		displayBoard();

		//call userGuess to generate user guess
		userGuess();
	}

	//helper function to display the user's guessBoard
	public void displayBoard(){
		//display current board
		System.out.println("Your current board is: ");
		//iterate through the board indexes
		for (int i=0; i<Board.boardDimension; i++){
			for (int j=0; j<Board.boardDimension; j++){
				//if content of cell still initialized value, display "[ * ]"
				if (guessBoard.boardMatrix[i][j].getValue() == 404){
					//print out rows of board matrix
					System.out.print("[ * ]");
				}
				else{
					//print out rows of board matrix
					System.out.print("[ " + guessBoard.boardMatrix[i][j].getValue() + " ]");
				}
			}
			//go to next line for the next row for the board matrix
			//this creates a grid looking structure
			System.out.println();
		}
	}

	//method userGuess which takes in user-input
	public void userGuess(){
		System.out.println("Enter coordinates in the form of '#,#,'");
		System.out.println("Ex] Entering '0,0,' gives you top left corner box");
		System.out.println("And entering '7,7,' gives you bottom right corner box");
		//variable for user input, for coordinates of selected cell
		Scanner coordinates = new Scanner(System.in).useDelimiter(",");
		//variable assignment of coordinates 
		inputX = coordinates.nextInt();
		inputY = coordinates.nextInt();

		//use coordinates to determine number of neighbors with bombs

		//call helper function getNeighbors, which returns list of neighbors
		//assign list to foundNeighbors
		List<int[]> foundNeighbors = getNeighbors(inputX, inputY);

		//call helper function to find out number of neighbors containing bombs
		//assign number of bombs to numBombs
		int numBombs = numNeighborBombs(foundNeighbors);

		//call updateGuessBoard
		updateGuessBoard(inputX, inputY, numBombs);
	}

	//helper function getNeighbors to get coordinates of neighboring cells
	public List<int[]> getNeighbors(int x, int y){
		//create list neighbors to store the coordinates of the neighboring cells
		List<int[]> neighbors = new ArrayList<int[]>();

		//iterate through all possible neighbors
		for (int i=(x-1); i<=(x+1); i++){
			for (int j=(y-1); j<=(y+1); j++){ 
				//make sure the coordinate point is in the board
				if (i>-1 && i<8 && j>-1 && j<8){
					//creates variable holding the integer coordinates
					int[] temp = {i,j};
					//add neighbor coordinate to list created
					//as long as valid neighbor isn't the point given, add to list
					int[] poop = {x,y};
					if(temp[0] != poop[0] && temp[1]!= poop[1])
						neighbors.add(temp);
				}
			}
		}
		//return the list of neighbors
		return neighbors;
	}

	//helper function to determine how many neighbors contain bombs
	public int numNeighborBombs(List<int[]> neighbors){
		//iterate through the list parameter
		Iterator<int[]> iter = neighbors.iterator();
		//create variable for number of bombs held by neighbors
		int numBombs = 0;
		//interate as long as there is another value to iterate to
		while(iter.hasNext()){
			//take next iteration
			int[] coordinates = iter.next();
			//if cell containing the coordinate contains a 9, add 1 to numBombs
			if (gameBoard.boardMatrix[coordinates[0]][coordinates[1]].getValue()==9){
				numBombs++;
			}
		}
		//return the numBombs variable
		return numBombs;
	}

	//method updateGuessBoard for updating and displaying guessBoard 
	public void updateGuessBoard(int x, int y, int bombs){
		//update the board
		guessBoard.updateBoard(x, y, bombs);

		if (gameBoard.boardMatrix[x][y].getValue()!=9){
			//display guessBoard
			displayBoard();
		}

		//call updateGame to continue or end the game
		updateGame();
	}

	//method updateGame to check if game won or lost
	public void updateGame(){
		//create variable gameResult, of type boolean
		boolean gameResult;

		//if game lost
		if (gameBoard.boardMatrix[inputX][inputY].getValue()==9){
			//create variable gameResult, false means game lost
			gameResult = false;
			//call end game function
			endGame(gameResult);
		}
		//if game won
		else if (gameBoard.numBombs + guessBoard.numRevealedCells == gameBoard.numCells){
			gameResult = true; 
			//call end game function
			endGame(gameResult);
		}
		else{
			//continue game by calling userGuess function
			userGuess();
		}
	}

	//endGame function
	public void endGame(boolean gameResultInput){
		if (gameResultInput == false){
			//trace out "YOU LOSE"
			System.out.println("YOU LOSE");
			//trace out locations of bombs

		}
		else if (gameResultInput == true){
			//trace out "YAY YOU WIN!"
			System.out.println("YAY! YOU WIN!");
			//trace out location of bombs 
		}
		//displaying gameBoard
		System.out.println("The Game Board was: ");
		for (int i=0; i<Board.boardDimension; i++){
			for (int j=0; j<Board.boardDimension; j++){
				if(gameBoard.boardMatrix[i][j].getValue()==9){
					//if bomb, keep value as 9 when displaying
					System.out.print("[ " + gameBoard.boardMatrix[i][j].getValue() + " ]");
				}
				//else, change display to '[ 0 ]'
				else{
					System.out.print("[ - ]");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args){
		MinesweeperGame blub = new MinesweeperGame();
		blub.initGame();
	}
}