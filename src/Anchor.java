/**
 * @author: Marcus Trujillo
 * @version:
 * Basically spots that can be played on board where we look to form words.
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
