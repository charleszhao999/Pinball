import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    JLabel label;
    JButton b_start;
    JTextField text;
    BorderLayout layout;

    Home() {
        super("Ball Game");
        layout = new BorderLayout(10, 10);
        setLayout(layout);
        setSize(400, 160);
        label = new JLabel("Please input the number of balls.");
        label.setFont(new java.awt.Font("Title", Font.PLAIN, 24));
        add(label, BorderLayout.NORTH);
        text = new JTextField(5);
        text.setFont(new java.awt.Font("Text", Font.PLAIN, 18));
        add(text, BorderLayout.CENTER);
        b_start = new JButton("Start");
        b_start.setFont(new java.awt.Font("Button", Font.PLAIN, 18));
        b_start.addActionListener(this);
        add(b_start, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event) {
        int num = Integer.parseInt(text.getText());
        JFrame game = new Game(num);
        game.setVisible(true);
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        this.setVisible(false);
    }
}
