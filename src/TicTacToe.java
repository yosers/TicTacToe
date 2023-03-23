import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Random;

public class TicTacToe implements ActionListener {

    Random random = new Random();

    JFrame frame = new JFrame();//creating instance of JFrame
    JFrame mainFrame = new JFrame();//creating instance of JFrame

    JPanel panel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JLabel label = new JLabel();

    JTextField textField = new JTextField(20);

    NumberFormatter formatter = new NumberFormatter();
    JFormattedTextField number = new JFormattedTextField(formatter);

    JButton[] buttons;
    JButton button = new JButton();
    JButton buttonReset;
    JButton buttonInputSize;

    boolean player1_turn;

    TicTacToe(){

        textField.setLayout(new FlowLayout());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Input size TicTacToe (Just Number)");

        button = new JButton("Submit");
        button.addActionListener(this);

        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);

        panel.add(label);
        panel.add(number);
        panel.add(button);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int inputNumber = Integer.valueOf(number.getText());

        if (e.getSource().equals(button)) {

            this.showNewJFrame(new WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    // here we listen for the second JFrame being closed so we can bring back the main JFrame
                    mainFrame.setVisible(true);
                }
            }, inputNumber, false);

            mainFrame.setVisible(false);
        }

        if (e.getSource().equals(buttonReset)) {
            for (int i = 0; i < inputNumber * inputNumber; i++) {
                buttons[i].setText("");
            }
        }
        else if (e.getSource().equals(buttonInputSize)) {
            this.showNewJFrame(new WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    // here we listen for the second JFrame being closed so we can bring back the main JFrame
                    frame.setVisible(true);
                }
            }, inputNumber, true);

            frame.setVisible(false);
            mainFrame.setVisible(true);
        }
        else {
            for(int i=0; i< inputNumber*inputNumber; i++) {
                if(e.getSource()==buttons[i]) {
                    if(player1_turn) {
                        if(buttons[i].getText().equals("")) {
                            buttons[i].setForeground(new Color(255,0,0));
                            buttons[i].setText("X");
                            player1_turn=false;
                            label.setText("0 turn");
                        }
                    }
                    else {
                        if(buttons[i].getText().equals("")) {
                            buttons[i].setForeground(new Color(0,0,255));
                            buttons[i].setText("O");
                            player1_turn=true;
                            label.setText("X turn");
                        }
                    }
                }
            }
        }
    }

    private void showNewJFrame(WindowAdapter windowAdapter, int inputNumber, boolean resetPanel) {
        int numButtons = inputNumber * inputNumber;

        if (resetPanel) {

            for (int i = 0; i<numButtons; i++) {
                buttonPanel.remove(buttons[i]);
            }
            titlePanel.remove(label);
            titlePanel.remove(buttonReset);
            titlePanel.remove(buttonInputSize);
            number.setValue(null);
        } else {
            buttons = new JButton[numButtons];

            buttonReset = new JButton("Reset");
            buttonReset.addActionListener(this);

            buttonInputSize = new JButton("Input Size TicTacToe");
            buttonInputSize.addActionListener(this);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800,800);
            frame.getContentPane().setBackground(new Color(50,50,50));
            frame.setLayout(new BorderLayout());
            frame.setVisible(true);

            label.setBackground(new Color(25,25,25));
            label.setForeground(new Color(25,255,0));
            label.setFont(new Font("Ink Free", Font.BOLD, 75));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setText("Tic-Tac-Toe");
            label.setOpaque(true);

            buttonPanel.setLayout(new GridLayout(inputNumber, inputNumber));
            buttonPanel.setBackground(new Color(150, 150, 150));

            for (int i = 0; i<numButtons; i++) {
                buttons[i] = new JButton();
                buttonPanel.add(buttons[i]);
                buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
                buttons[i].setFocusable(false);
                buttons[i].addActionListener(this);
            }

            titlePanel.add(label);
            titlePanel.add(buttonReset);
            titlePanel.add(buttonInputSize);

            frame.add(titlePanel,BorderLayout.NORTH);
            frame.setLocationRelativeTo(null);
            frame.add(buttonPanel);

            firstTurn();
        }
    }

    public void firstTurn() {

        if(random.nextInt(2)==0) {
            player1_turn=true;
            label.setText("X turn");
        }
        else {
            player1_turn=false;
            label.setText("O turn");
        }
    }
}
