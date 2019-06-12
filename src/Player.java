/**
 * Created by g.s.vermeer on 25/05/2019.
 */
public class Player {


    public Player(Cell turn) {
        this.player = turn;
    }

    public void setPlayerCross(){
        this.player = Cell.CROSS;
    }

    private Cell player;

    public boolean cross(){
        return this.player.equals(Cell.CROSS);
    }

    public boolean zero(){
        return this.player.equals(Cell.ZERO);
    }

    public Cell turn(){
        return  player;
    }

    public void nextTurn (){

        if (player.equals(Cell.CROSS)){
            player = Cell.ZERO;
        } else {
            player = Cell.CROSS;

        }

    }
}
