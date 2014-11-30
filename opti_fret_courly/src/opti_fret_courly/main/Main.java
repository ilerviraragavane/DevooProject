package opti_fret_courly.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import opti_fret_courly.vue.Fenetre;

public class Main {

    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = fenetre.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        fenetre.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        fenetre.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);

    }

}
