package game;

import piece.FrogGuard;
import piece.FrogLeader;
import piece.Piece;
import piece.Turtle;
import tile.*;
import ui.Modal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Клас наследяващ JFrame и прилагащ MouseListener, съдържащ конструктор и методи за визуализиране на приложението.
 *
 * @author Озан Осман
 */
public class GameBoard extends JFrame implements MouseListener
{
    public final int TILE_SIDE_COUNT = 5;

    private Piece[][] pieceCollection;
    private Piece selectedPiece;

    private int randomCol1;
    private int randomCol2;

    /**
     * Конструктор съдържащ характеристиките за създаване на прозореца, в която се визуализира игралната дъска и неговите елементи.
     */
    public GameBoard()
    {
        this.pieceCollection = new Piece[TILE_SIDE_COUNT][TILE_SIDE_COUNT];

        this.pieceCollection[0][4] = (new FrogLeader(0, 4, new Color(255, 242,0)));

        this.pieceCollection[4][0] = (new FrogLeader(4, 0, new Color(34, 177,76)));

        this.pieceCollection[0][0] = (new FrogGuard(0, 0, new Color(255, 242,0), new Color(34, 177,76)));
        this.pieceCollection[0][1] = (new FrogGuard(0, 1, new Color(255, 242,0), new Color(34, 177,76)));
        this.pieceCollection[0][2] = (new FrogGuard(0, 2, new Color(255, 242,0), new Color(34, 177,76)));
        this.pieceCollection[0][3] = (new FrogGuard(0, 3, new Color(255, 242,0), new Color(34, 177,76)));

        this.pieceCollection[4][1] = (new FrogGuard(4, 1, new Color(34, 177,76), new Color(255, 242,0)));
        this.pieceCollection[4][2] = (new FrogGuard(4, 2, new Color(34, 177,76), new Color(255, 242,0)));
        this.pieceCollection[4][3] = (new FrogGuard(4, 3, new Color(34, 177,76), new Color(255, 242,0)));
        this.pieceCollection[4][4] = (new FrogGuard(4, 4, new Color(34, 177,76), new Color(255, 242,0)));

        // Поради някаква странна причина горните плочки не се виждат изцяло.
        this.setTitle("Frog Battle Simulator");
        this.setSize(500,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);

        randomColGenerator();
    }

    /**
     * Метод, който контролира елементите върху игралната дъска с мишката и проверява дали някой е спечелил.
     *
     * @param e     обект на супер класа за всички графични контексти
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int row = this.getBoardCoordinates(e.getY());
        int col = this.getBoardCoordinates(e.getX());

        // Само блокирането на фигури липсва.
        if (this.selectedPiece != null)
        {
            Piece piece = this.selectedPiece;

            if (piece.isMoveValid(row, col))
            {
                movePiece(row, col, piece);

                this.repaint();
            }
            if (this.pieceCollection[2][2] instanceof FrogLeader)
            {
                Modal.render(this, "Край на играта!", getWinnerColor(piece.getColor()));
                System.exit(0);
            }
        }

        if (this.hasBoardPiece(row, col))
        {
            this.selectedPiece = this.getBoardPiece(row, col);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    /**
     * Метод съдържащ цикъл за визуализиране на игралната дъска и неговите елементи.
     *
     * @param g     обект на супер класа за всички графични контексти
     */
    @Override
    public void paint(Graphics g)
    {
        // Не успях да удебеля само жабите гардове, затова удебелих всички по-малко.
        ((Graphics2D) g).setStroke(new BasicStroke(2.5f));

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                this.renderGameTile(g, row , col);
                this.renderPiece(g, row , col);
                this.renderTurtle(g);
            }
        }
    }

    /**
     * Метод, който контролира елементите върху игралната дъска.
     *
     * @param row   ред на елемента
     * @param col   колона на елемента
     * @param piece     инстанция на клас
     */
    private void movePiece(int row, int col, Piece piece)
    {
        int initialRow = piece.getRow();
        int initialCol = piece.getCol();

        piece.movePiece(row, col);

        this.pieceCollection[piece.getRow()][piece.getCol()] = this.selectedPiece;
        this.pieceCollection[initialRow][initialCol] = null;

        this.selectedPiece = null;
    }

    /**
     * Метод, който връща низ според цвета на победителя.
     *
     * @param winnerColor   цвета на победителя
     */
    private String getWinnerColor(Color winnerColor)
    {
        return String.format("%s жаби са победители!", winnerColor.equals(new Color(255, 242,0)) ? "Жълтите" : "Зелените");
    }

    /**
     * Метод съдържащ инстанции на класове за визуализиране на игралната дъска.
     *
     * @param g     обект на супер класа за всички графични контексти
     * @param row   ред на елемента
     * @param col   колона на елемента
     */
    private void renderGameTile(Graphics g, int row, int col)
    {
        OrangeTile tile1 = new OrangeTile(row, col, new Color(255, 99, 71), Color.BLACK);
        tile1.renderTile(g);

        GreyTile tile2 = new GreyTile(row, col, new Color(68, 68, 68), Color.BLACK);
        tile2.renderTile(g);

        SilverTile tile3 = new SilverTile(row, col, new Color(170, 170, 170), Color.BLACK);
        tile3.renderTile(g);

        WhiteTile tile4 = new WhiteTile(row, col, Color.WHITE, Color.BLACK);
        tile4.renderTile(g);

        GreyOvalTile tile5 = new GreyOvalTile(row, col, new Color(119, 119, 119), Color.BLACK);
        tile5.renderTile(g);
    }

    /**
     * Метод съдържащ инстанция на клас за визуализиране на елементи "Frog Leader" и "Frog Guard".
     *
     * @param g     обект на супер класа за всички графични контексти
     * @param row   ред на елемента
     * @param col   колона на елемента
     */
    private void renderPiece(Graphics g, int row, int col)
    {
        if (this.hasBoardPiece(row, col))
        {
            Piece piece = this.getBoardPiece(row, col);
            piece.renderPiece(g);
        }
    }

    /**
     * Метод съдържащ инстанция на клас за визуализиране на елементи "Turtle".
     *
     * @param g     обект на супер класа за всички графични контексти
     */
    private void renderTurtle(Graphics g)
    {
        Turtle turtle1 = new Turtle(2, randomCol1, Color.RED);
        turtle1.renderPiece(g);

        Turtle turtle2 = new Turtle(2, randomCol2, Color.RED);
        turtle2.renderPiece(g);
    }

    /**
     * Метод, който създава 2 случайни числа, докато те не са равни на 2 или на себе си.
     */
    private void randomColGenerator()
    {
        do
        {
            randomCol1 = ThreadLocalRandom.current().nextInt(0, 5);
            randomCol2 = ThreadLocalRandom.current().nextInt(0, 5);
        }
        while (randomCol1 == 2 || randomCol2 == 2 || randomCol1 == randomCol2);
    }

    /**
     * Метод, който връща елемент от обекта за елементи "Frog Leader" и "Frog Guard".
     *
     * @param row   ред на елемента
     * @param col   колона на елемента
     */
    private Piece getBoardPiece(int row, int col)
    {
        return this.pieceCollection[row][col];
    }

    /**
     * Метод, който проверява и връща елемент от обекта за елементи "Frog Leader" и "Frog Guard", ако те съществуват.
     *
     * @param row   ред на елемента
     * @param col   колона на елемента
     */
    private boolean hasBoardPiece(int row, int col)
    {
        return this.getBoardPiece(row, col) != null;
    }

    /**
     * Метод, който връща координати на игралната дъска в единични числа.
     *
     * @param coordinates   координати
     */
    private int getBoardCoordinates(int coordinates)
    {
        return coordinates / GameTile.TILE_SIZE;
    }
}