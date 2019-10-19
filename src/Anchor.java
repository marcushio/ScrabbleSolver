/**
 * @author: Marcus Trujillo
 * @version: 10/10
 * Basically spots that can be played on board where we look to form words.
 */
public class Anchor {
    int row;
    int col;
    Tile anchorTile;
    int prefixCap;
    int postfixCap;
    private boolean across;

    /**
     * Create our anchor holding all our necessary fields so we can make moves on the anchor.
     * @param row
     * @param col
     * @param anchorTile
     * @param prefixCap
     * @param postfixCap
     * @param across
     */
    public Anchor(int row, int col, Tile anchorTile, int prefixCap, int postfixCap, boolean across) {
        this.row = row;
        this.col = col;
        this.anchorTile = anchorTile;
        this.prefixCap = prefixCap;
        this.postfixCap = postfixCap;
        this.across = across;
    }

    /**
     * Used to check which orientation this anchor is for if it's across return true, if its down return false.
     * @return across
     */
    public boolean isAcross(){ return across; }
}
