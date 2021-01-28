package ui;

import javax.swing.*;

/**
 * Клас наследяващ JDialog, съдържащ конструктор и метод за визуализиране на прозореца "Modal".
 *
 * @author Озан Осман
 */
public class Modal extends JDialog
{
    /**
     * Конструктор съдържащ характеристиките за създаване на прозореца "Modal".
     *
     * @param parent    родителят, който JFrame ще го използва
     * @param title     заглавие на прозореца
     * @param message   съобщение в прозореца
     */
    public Modal(JFrame parent, String title, String message)
    {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);

        panel.add(label);
        getContentPane().add(panel);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Метод за визуализиране на прозореца "Modal".
     *
     * @param parent    родителят, който JFrame ще го използва
     * @param title     заглавие на прозореца
     * @param message   съобщение в прозореца
     */
    public static void render(JFrame parent, String title, String message)
    {
        new Modal(parent, title, message);
    }
}
