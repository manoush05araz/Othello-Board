package ca.yorku.eecs3311.othello.model;

/**
 * Visitor interface for traversing OthelloBoard.
 * 
 * This is part of the Visitor Pattern which separates operations
 * from the board structure.
 * 
 */
public interface OthelloBoardVisitor {
    /** 
    * called for each cell on board.
    * @param row row index in [0, DIMENSION)
    * @param col column index in [0, DIMENSION)
    * @param token token stored at (row, col): P1, P2, or EMPTY
    */

    void visitCell(int row, int col, char token);

}
