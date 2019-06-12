import java.util.Scanner;

/**
 * Created by g.s.vermeer on 08/06/2019.
 */
public class Game {


    public void startGame() {


        Board board = new Board();
        Autoplayer autoplayer = new Autoplayer();

        playInteractiveGame(board,autoplayer);

        autoplayer.printGamesToFile();

    }



    private void playInteractiveGame(Board board, Autoplayer autoplayer) {
        Player player = new Player(Cell.CROSS);
        int turns = 0;
        int nextMove;
        Boolean winnerBool = false;

        while (turns < 9 && !winnerBool){

            if (player.zero()){
                nextMove = autoplayer.playMove(board);
            } else {
               //nextMove = autoplayer.playRandomMove(board);
               nextMove = getNextMove(board, autoplayer);
            }

            board.playmove(nextMove,player.turn());
            board.printBoard();
            autoplayer.printMoveList();
            winnerBool = board.checkWinner(autoplayer);

            player.nextTurn();
            turns ++;

        }
        if (!winnerBool){
            System.out.println("It' a draw");
            autoplayer.registerWin(false);
        }
    }

    private int getNextMove(Board board, Autoplayer autoplayer) {
        Scanner consoleInput = new Scanner(System.in);
        int nextMove = 10;
        boolean validmove = false;
        while ((nextMove <0 || nextMove>8)  && !validmove) {
            System.out.println("Make your move");
            nextMove = consoleInput.nextInt();
            validmove = board.checkEmpty(nextMove) ;

        }
        autoplayer.registerMove(nextMove);
        return nextMove;
    }
}
