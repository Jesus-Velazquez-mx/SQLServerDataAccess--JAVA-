package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class JTextTelefono extends JTextField {

    private static final long serialVersionUID = 1L;

    public JTextTelefono() {
        setPreferredSize(new Dimension(150, 30)); 

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                
                if (!Character.isDigit(c) || getText(false).length() >= 13) {
                    e.consume();
                    return;
                }
                
                int posicion = getText(false).length();
                if (posicion == 3 || posicion == 7 || posicion == 10) {
                    setText(getText(false) + '-');
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (getText(false).length() != 13) {
                    setBackground(Color.RED);
                } else {
                    setBackground(Color.WHITE);
                }
            }
        });
    }
 // Hay otro metodo getText para que no haya problemas cuando se usa en el KeyListener
    public String getText(boolean bandera) {
        if (bandera) {
            return super.getText().replace("-", ""); 
        }
        return super.getText(); 
    }

    @Override
    public String getText() {
        return getText(true);
    }
}
