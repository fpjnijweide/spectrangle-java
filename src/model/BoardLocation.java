package model;

/**
 * Class representing information on a location of a Spectrangle Board
 *
 * @author Group4
 */
public class BoardLocation {

	private final int location;
	private final int scorePoint;
	private Piece piece;

	/**
	 * Initialize Board location
	 *
	 * @param location   Number representing a location on the board
	 * @param scorePoint The amounts of points attributed to a location (multiplier)
	 */
	public BoardLocation(int location, int scorePoint) {
		this.location = location;
		this.scorePoint = scorePoint;

	}



	/**
	 * Return the amounts of point of this location
	 *
	 * @return the amounts of point of this location
	 */
	public int getScorePoint() {
		return scorePoint;
	}

	/**
	 * Return the piece of this spot
	 *
	 * @return Piece object
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * Set a piece on this spot
	 *
	 * @param moves a Piece to this Object
	 */
	public void movePiece(Piece p) {
		this.piece = p;
	}

	/**
	 * Check if this spot is a bonus location
	 *
	 * @return Boolean verifying if this location has a bonus point or not
	 */
	public boolean isBonusLocation() {
		return this.scorePoint > 1;
	}

	/**
	 * Check if this spot is an empty spot
	 *
	 * @return Boolean verifying the emptiness of the location
	 */
	public boolean isEmptySpot() {
		return piece == null;
	}
	
	/**
	 * A method that returns the location field of this object
	 * @return Location value of the object
	 */
	public int getLocation() {
		return this.location;
	}

}
