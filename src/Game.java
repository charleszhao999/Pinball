import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game extends JFrame {
    //JLabel label;
    boolean gameIsOver = false;
    Random generator = new Random();
    ExecutorService executor;
    Long elapsedTime;
    int posX, posY;

    public void gameOver() {
        if (elapsedTime > 0) {
            gameIsOver = true;
            //System.out.println(gameIsOver);
            executor.shutdown();
            //System.out.println(executor.isShutdown() + " " + executor.isTerminated());
            //System.out.println("Game Over at " + elapsedTime);
            String tmp = "You survived " + elapsedTime + " seconds. Wanna play again?";
            int value = JOptionPane.showConfirmDialog(this, tmp, "Game Over.", JOptionPane.ERROR_MESSAGE);
            if (value == JOptionPane.YES_OPTION) {
                //System.out.println("你选择了是");
                JFrame home = new Home();
                setVisible(false);
                home.setVisible(true);
                home.setLocationRelativeTo(null);
                home.setResizable(false);

            } else if (value == JOptionPane.NO_OPTION) {

                //System.out.println("你选择了否");
                System.exit(0);

            } else if (value == JOptionPane.CANCEL_OPTION) {

                //System.out.println("你选择了取消");

            } else if (value == JOptionPane.CLOSED_OPTION) {

                //System.out.println("你直接将窗体关闭了，没有选择");
                System.exit(0);
            }
        }
        //清空线程组
        //计时停止并提示
    }

    public Game(int num) {
        super("Ball Game");
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);
        Long startTime = System.currentTimeMillis();
        setSize(1280, 720);
        Ball[] balls = new Ball[num];
        //MouseThread mt = new MouseThread(this);
        executor = Executors.newFixedThreadPool(num + 1);
        //executor.execute(mt);
        //Thread mouse=new Thread(mt);
        MouseMotionListener mouse = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                posX = e.getX() - 11;
                posY = e.getY() - 39;
                //System.out.println(posX+" "+posY);
                if ((posX <= 10) || (posX >= 1250) || (posY <= 10) || (posY >= 660)) {
                    gameOver();
                }
            }
        };

        addMouseMotionListener(mouse);
        //mouse.start();
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Ball i : balls) {
                    g.setColor(i.c);
                    g.fillOval(i.posX, i.posY, i.size, i.size);
                }
                String str;
                //label = new JLabel(str);
                g.setFont(new java.awt.Font("Number", Font.BOLD, 24));
                g.setColor(Color.RED);
                Long endTime = System.currentTimeMillis();
                elapsedTime = (endTime - startTime) / 1000;
                str = elapsedTime + "s passed.";
                g.drawString(str, 605, 50);
                g.setColor(Color.BLACK);
                g.fillRect(posX, posY, 10, 10);
            }
        };
        add(panel);
        PanelThread pt = new PanelThread(panel, this);
        executor.execute(pt);
        //Thread panelTH = new Thread(pt);
        //panelTH.start();
        for (int i = 0; i < num; i++) {
            Ball ball = new Ball();
            ball.posX = generator.nextInt(1000) + 100;
            ball.posY = generator.nextInt(600) + 50;
            ball.size = generator.nextInt(80) + 10;
            ball.c = new Color(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
            balls[i] = ball;
            BallThread th = new BallThread(ball, this);
            executor.execute(th);
            //Thread thread = new Thread(th);
            //thread.start();
        }
    }
}
