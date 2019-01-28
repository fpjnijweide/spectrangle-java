package Game;

import java.util.ArrayList;

// Board Class to represent the Spectrangle Board

public class Board {

	public static final int DIM = 36; // loop t/m 35
	private BoardLocation[] boardLocations;
	private TileBagGenerator bag;
	private ArrayList<Piece> pieces;

	/*
	 * Generate a Board with the following attributes: a bag with pieces an
	 * ArrayList of pieces List of board locations with bonusQuotients
	 */

	public Board() {
		this.boardLocations = new BoardLocation[DIM];

		// populate the board

		for (int i = 0; i < Board.DIM; i++) {
			int bonusQuotient = 1;

			if (i == 10 || i == 14 || i == 30) {
				bonusQuotient = 2;

			} else if (i == 2 || i == 26 || i == 34) {
				bonusQuotient = 3;

			} else if (i == 11 || i == 13 || i == 20) {
				bonusQuotient = 4;

			}

			boardLocations[i] = new BoardLocation(i, bonusQuotient);
		}

		bag = new TileBagGenerator();
		pieces = bag.getBag();

	}

	/**
	 * Get a BoardLocation object that can be used to get information on the
	 * location
	 * 
	 * @param location
	 * @return
	 */
	public BoardLocation getBoardLocation(int location) {
		if (this.isValidLocation(location)) {
			return this.boardLocations[location];
		} else {
			return null;
		}
	}

	/**
	 * Get a Piece object that can be used to get information on the Piece
	 * 
	 * @param location
	 * @return
	 */
	public Piece getPiece(int location) {
		return this.boardLocations[location].getPiece();
	}

	/**
	 * Make a move on the location and return the success state This gives the
	 * BoardLocation a Piece object
	 * 
	 * @param location
	 * @param piece
	 * @return
	 */
	public boolean setMove(int location, Piece piece) {

		if (isValidLocation(location) && isEmptyLocation(location)) {

			this.boardLocations[location].movePiece(piece);

			return true;
		}
		return false;
	}

	// Queries

	// TODO: make sure that a piece can only be put next to another piece
	/**
	 * Check validity of the move Move is valid if location is valid AND: color
	 * matches at least on one side (except for Joker) board is empty then
	 * everywhere except for bonus spots board is not empty then move is valid if
	 * 
	 * @param location
	 * @param piece
	 * @return
	 */
	public boolean isValidMove(int location, Piece piece) {

		boolean result = false;

		if (isValidLocation(location)) {

			if (boardIsEmpty()) {

				result = !boardLocations[location].isBonusLocation();

			} else if (this.isValidColor(location, piece)

					&& isEmptyLocation(location)) {

				result = true;
			}
		}
		return result;
	}

	/*
	 * convert Coordinates to index according to the formula: r^2 + r + c
	 */
	public int getIndex(int r, int c) {
		return ((int) (Math.pow(r, 2) + r + c));
	}

	/*
	 * TODO: Implement a converter from index to coordinates
	 */

	public TileBagGenerator getTileBag() {
		return this.bag;
	}

	/*
	 * Move a piece on the boardLocation set a Piece on a boardLocation[location]
	 */
	public int movePiece(int location, Piece piece) {
		int point = 0;
		if (isValidLocation(location) && isValidMove(location, piece)) {

			boardLocations[location].movePiece(piece);
			point = boardLocations[location].getScorePoint() * piece.getPoint();

		}
		return point;
	}

	/*
	 * TODO: Add color validity checker
	 */

	public boolean isValidColor(int location, Piece piece) {

		// TODO: implement
		boolean result = false;

		// Joker fits at all locations

		if (isValidLocation(location) && piece.isJoker()) {

			result = true;
		}

		// TODO: check for matching colors

		return result;
	}

	// TODO: Check for left, right and bottom neighbors

	/*
	 * Get Left Neighbor, to be used when checking if colors match
	 */

	public Piece getLeftPiece(Piece piece) {
		return null;
	}

	/**
	 * Check if the given location is valid
	 * 
	 * @param location
	 * @return
	 */
	public boolean isValidLocation(int location) {
		return (location >= 0 && location < Board.DIM);
	}

	/*
	 * check if location is Empty use isEmptySpot() from BoardLocation
	 */
	public boolean isEmptyLocation(int location) {
		return (this.boardLocations[location].isEmptySpot());
	}

	/*
	 * Check if the entire board is totally empty
	 */
	public boolean boardIsEmpty() {
		boolean result = true;
		for (int i = 0; i < this.boardLocations.length; i++) {
			if (!boardLocations[i].isEmptySpot()) {
				result = false;
			}
		}
		return result;
	}

	// TODO: Add a function to check if colors are valid

}
