package Main;

import View.GestionEmployesConges;  
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GestionEmployesConges().setVisible(true);
        });
    }
}
