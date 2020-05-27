package game.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void testInitTable() {
        Game game = new Game();
        game.initTable();

        int sum = 0;
        int[][] table = game.getTable();

        for (int i=0; i<Game.TABLE_SIZE_X; ++i)
            for (int j = 0; j < Game.TABLE_SIZE_Y; ++j)
                sum += table[i][j];

        assertEquals(3, sum); //egy darab 1-es, és egy darab 2-es, a többi nulla
    }

    @Test
    void testIsThisEndOfGame() {
        assertTrue(new Game(new int[][] {
                {1, -1, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        }).isThisEndOfGame());

        assertFalse(new Game(new int[][] {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        }).isThisEndOfGame());

        assertFalse(new Game(new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}

        }).isThisEndOfGame());
    }

    @Test
    void testGetTableCell() {
        Game game = new Game(new int[][] {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        });

        assertEquals(-1, game.getTableCell(1, 0));
        assertEquals(0, game.getTableCell(Game.TABLE_SIZE_X - 1, Game.TABLE_SIZE_Y - 1));
        assertEquals(1, game.getTableCell(0, 0));
        assertEquals(2, game.getTableCell(2, 6));
    }

    @Test
    void testGetPlayersPosition() {
        Game game = new Game();
        game.initTable();

        Pair pos = new Pair(0,0);

        pos = game.getPlayersPosition(1);
        assertEquals((int) pos.getKey(), 2);
        assertEquals((int) pos.getValue(), 0);

        pos = game.getPlayersPosition(2);
        assertEquals((int) pos.getKey(), 3);
        assertEquals((int) pos.getValue(), Game.TABLE_SIZE_Y - 1);
    }

    @Test
    void testMovePlayer() {
        Game game1 = new Game(new int[][] {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        });

        assertThrows(IllegalArgumentException.class,
                () -> game1.movePlayer(1));
        assertThrows(IllegalArgumentException.class,
                () -> game1.movePlayer(5));

        Game game2 = new Game(new int[][] {
                {0, 1, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        });

        game1.movePlayer(3);

        for (int i = 0; i < Game.TABLE_SIZE_X; ++i){
            for (int j = 0; j < Game.TABLE_SIZE_Y; ++j){
                assertEquals(game1.getTableCell(i,j), game2.getTableCell(i,j));
            }
        }
    }

    @Test
    void testDirectionToPairs() {
        Game game = new Game();

        Pair pos = new Pair(0, 0);

        pos = game.directionToPairs(1);
        assertEquals((int) pos.getKey(), -1);
        assertEquals((int) pos.getValue(), 0);

        pos = game.directionToPairs(2);
        assertEquals((int) pos.getKey(), -1);
        assertEquals((int) pos.getValue(), 1);

        pos = game.directionToPairs(5);
        assertEquals((int) pos.getKey(), 1);
        assertEquals((int) pos.getValue(), 0);

        pos = game.directionToPairs(8);
        assertEquals((int) pos.getKey(), -1);
        assertEquals((int) pos.getValue(), -1);

    }

    @Test
    void testSetCellDisabled() {
        int row = 5;
        int column = 2;

        Game game1 = new Game(new int[][] {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 0, 0}
        });
        game1.wasACellDisabled = false;
        game1.setCellDisabled(row, column);

        Game game2 = new Game(new int[][] {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {-1, -1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0},
                {0, 0, -1, 0, 0, -1, 0, 0}
        });

        assertEquals(game1.getTableCell(row,column), game2.getTableCell(row, column));
        assertTrue(game1.wasACellDisabled);

    }

    @Test
    void testGetIsFirstPlayer() {
        Game game = new Game();

        game.isFirstPlayer = true;
        game.wasACellDisabled = true;

        int num = game.getIsFirstPlayer();
        assertEquals(num, 1);

        game.isFirstPlayer = false;

        num = game.getIsFirstPlayer();
        assertEquals(num, 2);

        game.wasACellDisabled = false;

        num = game.getIsFirstPlayer();
        assertEquals(num, 0);
    }
}