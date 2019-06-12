/**
 * Created by g.s.vermeer on 25/05/2019.
 */
public enum  Cell {

    EMPTY(" - "),
    CROSS(" X "),
    ZERO(" O ");

    public final String label;

    private Cell(String label){
        this.label = label;

    }


}
