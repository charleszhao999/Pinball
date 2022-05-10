import javax.swing.*;

public class PanelThread implements Runnable {
    JPanel jp;
    Game game;

    PanelThread(JPanel panel, Game game) {
        jp = panel;
        this.game = game;
    }

    @Override
    public void run() {
        while (!game.gameIsOver) {
            jp.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jp.repaint();
    }
}
