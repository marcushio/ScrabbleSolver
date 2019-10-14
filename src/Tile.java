/**
 * @author: Marcus Trujillo
 * @version: 9/27/2019
 * Represents a scrabble letter tile that can be played on the board.
 */
public class Tile {
    public String letter;
    private boolean isSelected = false;
    public int pointValue;

    public Tile(String letter, int pointValue){
        this.letter = letter;
        this.pointValue = pointValue;
    }

    /**
     *
     * @return the letter on this tile
     */
    public String getLetter() { return letter; }

    /**
     *
     * @return the point value you get for playing this letter
     */
    public int getPointValue() { return pointValue; }

    public boolean isSelected(){ return isSelected; }

    public void setSelected(boolean selected){ isSelected = selected; }
}
