package piece;

import java.awt.*;

/**
 * Абстрактен клас съдържащ променливи и методи за елементи "Frog Leader", "Frog Guard" и "Turtle".
 *
 * @author Озан Осман
 */
public abstract class Piece
{
    public final int PIECE_SIZE = 50;

    protected int row;
    protected int col;
    protected Color color;
    protected Color outlineColor;

    /**
     * Метод, който връща ред на елемента.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Метод, който връща колона на елемента.
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Метод, който връща цвят на елемента.
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Метод, който дава нови координати на елементи.
     *
     * @param newRow    нов ред на елемента
     * @param newCol    нова колона на елемента
     */
    public void movePiece(int newRow, int newCol)
    {
        this.row = newRow;
        this.col = newCol;
    }

    /**
     * Метод съдържащ координати и цвят на елементи.
     *
     * @param g     обект на супер класа за всички графични контексти
     */
    public abstract void renderPiece(Graphics g);

    /**
     * Метод, който проверява и връща дали елемента може да се движи.
     *
     * @param moveRow   ред на елемента, който може да се движи
     * @param moveCol   колона на елемента, който може да се движи
     */
    public abstract boolean isMoveValid(int moveRow, int moveCol);
}