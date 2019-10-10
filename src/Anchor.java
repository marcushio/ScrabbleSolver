/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class Anchor {
    int row;
    int col;
    Tile anchorTile;
    int prefixCap;
    int postfixCap;
    boolean across;

    public Anchor(int row, int col, Tile anchorTile, int prefixCap, int postfixCap, boolean across) {
        super();
        this.row = row;
        this.col = col;
        this.anchorTile = anchorTile;
        this.prefixCap = prefixCap;
        this.postfixCap = postfixCap;
        this.across = across;
    }

    @Override
    public String toString() {
        return "Anchor [" + row + "," + col + "] " + anchorTile.letter + ", pre: " + prefixCap
                + ", post: " + postfixCap + ", across=" + across + "]";
    }


}
