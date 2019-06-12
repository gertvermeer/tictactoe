import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by g.s.vermeer on 25/05/2019.
 */
public class Board {

    Cell [][] board = new Cell[3][3];

    public Board() {
        Arrays.stream(board).forEach(s -> Arrays.fill(s,Cell.EMPTY));
    }


    public boolean playmove(int move, Cell symbol) {
        move --;
        int x = move/3;
        int y = move - x*3;
        if (board[x][y].equals(Cell.EMPTY)){
            board[x][y] = symbol;
            return true;
        } else {
            return false;
        }

    }

    public boolean checkEmpty(int cell){
        cell --;
        int x = cell/3;
        int y = cell - x*3;
        return board[x][y].equals(Cell.EMPTY);
    }

    public Cell getCell (int x, int y){
        return board[x][y];
    }

    public boolean checkWinner(Autoplayer autoplayer){
      Cell winner = checkBoardWinner();
        if (!winner.equals(Cell.EMPTY)){
            System.out.println("Winner is " + winner);
            autoplayer.registerWin(true);
            return true;

        }
        return false;
    };

    private Cell checkBoardWinner(){

        for (int t=0; t<3;t++){
            if (!board[t][0].equals(Cell.EMPTY)&& board[t][0].equals(board[t][1]) && (board[t][1].equals(board[t][2]))){
                return board[t][0];
            }
            if (!board[0][t].equals(Cell.EMPTY)&& board[0][t].equals(board[1][t]) && board[1][t].equals(board[2][t])){
                return board[0][t];
            }
        }
        if (!board[0][0].equals(Cell.EMPTY)&& (board[0][0].equals(board[1][1])&& (board[1][1].equals(board[2][2])))){
            return board[0][0];
        }
        if (!board[2][0].equals(Cell.EMPTY)&& (board[2][0].equals(board[1][1])&& (board[1][1].equals(board[0][2])))){
            return board[2][0];
        }



       return Cell.EMPTY;
    }


    public void printBoard(){
        Arrays.stream(board)
                .forEach(s -> printRow(s));
        System.out.println(1);
    }

    public void printRow(Cell[] row){
        Arrays.stream(row).forEach(
                cell -> System.out.print(cell.label)
        );
        System.out.println();
    }



}
