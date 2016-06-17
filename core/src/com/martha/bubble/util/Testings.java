package com.martha.bubble.util;

import com.martha.bubble.actor.Cell;
import com.martha.bubble.controller.Board;

/**
 * Created by Martha on 11/19/2015.
 */
public class Testings {
    // region Color Combination check
    /** Checks in render tact if there are any case where 4 or more same color balls in a raw are generated,
     * to run this method to the following
     * 1. Uncomment Testings object in Board class
     * 2. Uncomment colorCombinationTest() method call in bubbleincolors class in render() method  after super.render() method call
     * 3. If on each render tack in console a "true" value is shown then the results are positive as there are no 4 or more same color balls in a raw
      **/
    private Board board;
    private Cell cells[][];

    public Testings(Board board, Cell[][] cells) {
        this.board = board;
        this.cells = cells;
    }
    public boolean colorCombinationTest() {
        boolean testingIndex = true;
        for (int j = 0; j < cells[0].length; j++) {
            for (int i = 0; i < cells.length; i++) {
                if (i < cells.length - 3 && cells[i][j].getBall().getType() == cells[i + 1][j].getBall().getType()
                        && cells[i][j].getBall().getType() == cells[i + 2][j].getBall().getType()
                        && cells[i][j].getBall().getType() == cells[i + 3][j].getBall().getType()) {
                    testingIndex = false;
                }
            }
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (j < cells[0].length - 3 && cells[i][j].getBall().getType() == cells[i][j + 1].getBall().getType()
                        && cells[i][j].getBall().getType() == cells[i][j + 2].getBall().getType()
                        && cells[i][j].getBall().getType() == cells[i][j + 3].getBall().getType()) {
                    testingIndex = false;
                }
            }
        }
       board.createNewGame();
        System.out.println(testingIndex);
        return testingIndex;
    }
    // endregion

}
