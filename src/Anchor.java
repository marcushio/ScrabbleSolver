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
    private boolean across;

    public Anchor(int row, int col, Tile anchorTile, int prefixCap, int postfixCap, boolean across) {
        this.row = row;
        this.col = col;
        this.anchorTile = anchorTile;
        this.prefixCap = prefixCap;
        this.postfixCap = postfixCap;
        this.across = across;
    }

    public boolean isAcross(){ return across; }
}
