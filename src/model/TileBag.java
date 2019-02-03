package model;

import java.util.ArrayList;

/**
 * Tile Bag Generator. Generates a "bag" (array) of Pieces
 *
 * @author Bit 4 - Group 4
 */

public class TileBag {

    private final ArrayList<Piece> pieces;

    /**
     * TileBag This class generates a bag of size with 36 tiles according to the
     * rules of Spectrangle
     */
    // TODO update javadoc here
    public TileBag(int sizeInput) {
        this.pieces = new ArrayList<>(sizeInput);
    }

    /**
     * takeRandomPiece() returns a random tile from the tile bag generated
     *
     * @return
     */
    public Piece takeRandomPiece() throws EmptyBagException {
        if (pieces.size() != 0) {
            int random = (int) (Math.random() * this.pieces.size() - 1);
            Piece p = pieces.get(random);
            pieces.remove(random);
            return p;
        } else {
            throw new EmptyBagException("Bag is empty!");
        }
    }

    public Piece viewPiece(int i) {
        return pieces.get(i);
    }

    public Piece takePiece(int index) throws EmptyBagException {
        if (pieces.size() != 0) {
            Piece p = pieces.get(index);
            pieces.remove(index);
            return p;
        } else {
            throw new EmptyBagException("Bag is empty!");
        }
    }

    public int findPiece(Piece inputPiece) {
        for (Piece piece : pieces) {
            if (piece.equalsRotated(inputPiece) >= 0) {
                return pieces.indexOf(piece);
            }

        }
        return -1;
    }

    public void addPiece(Piece p) {
        pieces.add(p);
    }

    /**
     * This function will be called in the constructor and it will populate the bag
     * according to the rules of Spectrangle and includes Joker
     */
    public void populateBag() {

        // 6 Points

        for (ColorDefinition c : ColorDefinition.values()) {

            if (c != ColorDefinition.WHITE) {

                pieces.add(new Piece(c, c, c, 6));
            } else {
                // Joker
                pieces.add(new Piece(c, c, c, 1));
            }

        }

        // 5 Points

        for (ColorDefinition c : ColorDefinition.values()) {

            if (c == ColorDefinition.RED) {
                pieces.add(new Piece(c, c, ColorDefinition.YELLOW, 5));
                pieces.add(new Piece(c, c, ColorDefinition.PURPLE, 5));

            } else if (c == ColorDefinition.BLUE) {
                pieces.add(new Piece(c, c, ColorDefinition.RED, 5));
                pieces.add(new Piece(c, c, ColorDefinition.PURPLE, 5));
            } else if (c == ColorDefinition.GREEN) {
                pieces.add(new Piece(c, c, ColorDefinition.RED, 5));
                pieces.add(new Piece(c, c, ColorDefinition.BLUE, 5));
            } else if (c == ColorDefinition.YELLOW) {
                pieces.add(new Piece(c, c, ColorDefinition.BLUE, 5));
                pieces.add(new Piece(c, c, ColorDefinition.GREEN, 5));
            } else if (c == ColorDefinition.PURPLE) {
                pieces.add(new Piece(c, c, ColorDefinition.YELLOW, 5));
                pieces.add(new Piece(c, c, ColorDefinition.GREEN, 5));
            }
        }

        // 4 Points

        for (ColorDefinition c : ColorDefinition.values()) {

            if (c == ColorDefinition.RED) {
                pieces.add(new Piece(c, c, ColorDefinition.BLUE, 4));
                pieces.add(new Piece(c, c, ColorDefinition.GREEN, 4));

            } else if (c == ColorDefinition.BLUE) {
                pieces.add(new Piece(c, c, ColorDefinition.GREEN, 4));
                pieces.add(new Piece(c, c, ColorDefinition.YELLOW, 4));
            } else if (c == ColorDefinition.GREEN) {
                pieces.add(new Piece(c, c, ColorDefinition.YELLOW, 4));
                pieces.add(new Piece(c, c, ColorDefinition.PURPLE, 4));
            } else if (c == ColorDefinition.YELLOW) {
                pieces.add(new Piece(c, c, ColorDefinition.RED, 4));
                pieces.add(new Piece(c, c, ColorDefinition.PURPLE, 4));
            } else if (c == ColorDefinition.PURPLE) {
                pieces.add(new Piece(c, c, ColorDefinition.RED, 4));
                pieces.add(new Piece(c, c, ColorDefinition.BLUE, 4));
            }
        }

        // 3 Points
        pieces.add(new Piece(ColorDefinition.YELLOW, ColorDefinition.BLUE, ColorDefinition.PURPLE, 3));
        pieces.add(new Piece(ColorDefinition.RED, ColorDefinition.GREEN, ColorDefinition.YELLOW, 3));
        pieces.add(new Piece(ColorDefinition.BLUE, ColorDefinition.GREEN, ColorDefinition.PURPLE, 3));
        pieces.add(new Piece(ColorDefinition.GREEN, ColorDefinition.RED, ColorDefinition.BLUE, 3));

        // 2 Points
        pieces.add(new Piece(ColorDefinition.BLUE, ColorDefinition.RED, ColorDefinition.PURPLE, 2));
        pieces.add(new Piece(ColorDefinition.YELLOW, ColorDefinition.PURPLE, ColorDefinition.RED, 2));
        pieces.add(new Piece(ColorDefinition.YELLOW, ColorDefinition.PURPLE, ColorDefinition.GREEN, 2));

        // 1 Point
        pieces.add(new Piece(ColorDefinition.GREEN, ColorDefinition.RED, ColorDefinition.PURPLE, 1));
        pieces.add(new Piece(ColorDefinition.BLUE, ColorDefinition.YELLOW, ColorDefinition.GREEN, 1));
        pieces.add(new Piece(ColorDefinition.RED, ColorDefinition.YELLOW, ColorDefinition.BLUE, 1));

    }

    /**
     * Get an ArrayList<Piece> of Piece objects that will be used every time a Board
     * is generated
     *
     * @return
     */
    public ArrayList<Piece> getBag() {
        return this.pieces;
    }

    /**
     * Method to get the number of pieces at any point in the model
     *
     * @return
     */
    public int getNumberOfPieces() {
        return getBag().size();
    }
}
