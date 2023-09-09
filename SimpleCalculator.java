import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator {
    public JFrame frame;
    public JPanel panel;
    private JTextField textField;
    private JButton[] buttons;
    private String[] buttonLabels = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", ".",
        "0", "C", "=", "+"
    };

    private String input = "";
    private double result = 0;
    private char operator = ' ';

    public SimpleCalculator() {
        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        textField = new JTextField();
        textField.setEditable(false);

        buttons = new JButton[buttonLabels.length];
        for(int i=0; i<buttonLabels.length;i++){
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(new ButtonClickListener());
            panel.add(buttons[i]);
        }

        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                input += command;
                textField.setText(input);
            }else if(command.charAt(0) == 'C'){
                input = "";
                result = 0;
                operator = ' ';
                textField.setText("");
            }else if(command.charAt(0) == '=') {
                try {
                    double num = Double.parseDouble(input);
                    switch (operator) {
                        case '+':
                            result += num;
                            break;
                        case '-':
                            result -= num;
                            break;
                        case '*':
                            result *= num;
                            break;

                        case '/':
                            if(num == 0){
                                throw new ArithmeticException("Division by zero");
                            }
                            result /= num;
                            break;

                        default:
                            result = num;
                            break;
                    }

                    textField.setText(String.valueOf(result));
                    input = "";
                    operator = ' ';
                } catch (NumberFormatException ex){
                    textField.setText("invalid input");
                } catch (ArithmeticException ex) {
                    textField.setText("Error : Division by zero");
                }
            } else {
                if (input.isEmpty()) {
                    operator = command.charAt(0);
                } else {
                    try {
                        double num = Double.parseDouble(input);
                        switch (operator) {
                            case '+':
                                result += num;
                                break;

                            case '-':
                                result -= num;
                                break;

                            case '*':
                                result *= num;
                                break;

                            case '/':
                                if(num == 0) {
                                    throw new ArithmeticException("Division by zero");
                                }
                                result /= num;
                                break;

                            default:
                                result = num;
                                break;
                        }

                        input = "";
                        operator = command.charAt(0);
                    } catch (NumberFormatException ex) {
                        textField.setText("Invalid input");
                    } catch (ArithmeticException ex) {
                        textField.setText("Division by zero");
                    }
                }
            }
        }
    }
    public static void main(String[] args){
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new SimpleCalculator();
        }
        });
    }
}