import java.util.Random;

public class BallThread implements Runnable {
    Ball b;
    Game game;

    //JPanel jp;
    BallThread(Ball ball, Game game) {
        b = ball;
        this.game = game;
        //jp=panel;
    }

    Random generator = new Random();
    int velocityX = generator.nextInt(10) - 5, velocityY = generator.nextInt(10) - 5;

    public void run() {
        while (!game.gameIsOver) {
            b.posX += velocityX;
            b.posY += velocityY;
            //jp.repaint();
            if (((b.posX + 2 * b.size) >= 1280) || ((b.posX) <= 0)) {
                velocityX *= -1;
            }
            if (((b.posY + 2 * b.size) >= 720) || ((b.posY) <= 0)) {
                velocityY *= -1;
            }
            int distance = (b.posX - game.posX) * (b.posX - game.posX) + (b.posY - game.posY) * (b.posY - game.posY);
            if (distance <= ((10 + b.size) * (10 + b.size))) {
                game.gameOver();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
