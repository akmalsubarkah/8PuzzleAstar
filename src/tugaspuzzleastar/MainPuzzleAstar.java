package tugaspuzzleastar;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author AKMALSUBARKAH
 * Kelas Main
 */
public class MainPuzzleAstar {

    public static void main(String[] args) {
        //mengatur tampilan dan nuansa GUI ke tampilan dan nuansa sistem asli JAVA
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Menjalankan gui dari Kelas GUI yang telah dibuat
        GUI gui = new GUI();
        gui.setVisible(true);
    }
    
}
