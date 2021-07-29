package com.assignment;

/**
 * Class implementing Conway's GameOfLife
 *
 * http://en.wikipedia.org/wiki/Conway's_Game_of_Life
 *
 * Conventions int[][] board; // The game board is a 2D array. board[4][5] = 1;
 * // Means that the cell at (4,5) is live. board[4][5] = 0; // Means that the
 * cell at (4,5) is dead.
 *
 * Rules of the game 1. Any live cell with fewer than two live neighbours dies,
 * as if caused by under-population. 2. Any live cell with two or three live
 * Neighbors lives on to the next generation. 3. Any live cell with more than
 * three live neighbors dies, as if by over-population. 4. Any dead cell with
 * exactly three live neighbors becomes a live cell, as if by reproduction.
 */

/**
 * @author Shashwat Sharma
 *
 */
public final class Assignment {

	// The value representing a dead cell
	public final static int DEAD = 0;

	// The value representing a live cell
	public final static int LIVE = 1;

	/**
	 * Starts the GameOfLife example
	 * 
	 * @param args arguments, console arguments
	 */
	public final static void main(String[] args) {

		// test the game of life implementation in the assignment
		Assignment gameOfLife = new Assignment();
		gameOfLife.test(5);
	}

	/**
	 * Tests the game of life implementation, change the array values to test each
	 * condition in the game of life.
	 *
	 * @param iterations the number of times the board should be played
	 */
	private void test(int iterations) {

		// the starting playing board with life and dead cells
		int[][] board = { { DEAD, DEAD, DEAD, DEAD, DEAD }, { DEAD, DEAD, DEAD, LIVE, DEAD },
				{ DEAD, DEAD, LIVE, LIVE, DEAD }, { DEAD, DEAD, DEAD, LIVE, DEAD }, { DEAD, DEAD, DEAD, DEAD, DEAD } };

		System.out.println("Conway's GameOfLife");
		printBoard(board);

		for (int i = 0; i < iterations; i++) {
			System.out.println();
			board = getNextBoard(board);
			printBoard(board);
		}
	}

	/**
	 * Prints one board field to the output console
	 * 
	 * @param board The board to be printed to the output console
	 */
	private void printBoard(int[][] board) {

		for (int i = 0, e = board.length; i < e; i++) {

			for (int j = 0, f = board[i].length; j < f; j++) {
				System.out.print(Integer.toString(board[i][j]) + ",");
			}
			System.out.println();
		}
	}

	/**
	 * Gets the next game board, this will calculate if cells are live or dead or
	 * there are new ones that should be created by reproduction.
	 * 
	 * @param board The current board field
	 * @return TemporaryBoard newly created game buffer
	 */
	public int[][] getNextBoard(int[][] board) {

		// The board does not have any values so return the newly created
		if (board.length <= 0 || board[0].length <= 0) {
			throw new IllegalArgumentException("Board must have a positive amount of rows and columns");
		}

		int rowsLength = board.length;
		int columnsLength = board[0].length;

		// temporary board to store new values
		int[][] temporaryBoard = new int[rowsLength][columnsLength];

		for (int row = 0; row < rowsLength; row++) {

			for (int col = 0; col < columnsLength; col++) {
				temporaryBoard[row][col] = getNewCellState(board[row][col], getLiveNeighbours(row, col, board));
			}
		}
		return temporaryBoard;
	}

	/**
	 * Gets the number of the live neighbors given the cell position
	 * 
	 * @param cellRow    the column position of the cell
	 * @param cellColumn the row position of the cell
	 * @return the number of live neighbors given the position in the array
	 */
	private int getLiveNeighbours(int cellRow, int cellColumn, int[][] board) {

		int liveNeighbours = 0;
		int rowEnd = Math.min(board.length, cellRow + 2);
		int colEnd = Math.min(board[0].length, cellColumn + 2);

		for (int row = Math.max(0, cellRow - 1); row < rowEnd; row++) {

			for (int col = Math.max(0, cellColumn - 1); col < colEnd; col++) {

				if ((row != cellRow || col != cellColumn) && board[row][col] == LIVE) {
					liveNeighbours++;
				}
			}
		}
		return liveNeighbours;
	}

	/**
	 * Gets the new state of the cell given the current state and the number of live
	 * Neighbors of the cell.
	 * 
	 * @param currentState   The current state of the cell, either DEAD or ALIVE
	 * @param liveNeighbours The number of live neighbors of the given cell.
	 * 
	 * @return The new state of the cell given the number of live neighbors and the
	 *         current state
	 */
	private int getNewCellState(int currentState, int liveNeighbours) {

		int newState = currentState;

		switch (currentState) {
		case LIVE:

			if (liveNeighbours < 2) {
				newState = DEAD;
			}

			else if (liveNeighbours == 2 || liveNeighbours == 3) {
				newState = LIVE;
			}

			else if (liveNeighbours > 3) {
				newState = DEAD;
			}
			break;

		case DEAD:
			if (liveNeighbours == 3) {
				newState = LIVE;
			}
			break;

		default:
			throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
		}
		return newState;
	}
}