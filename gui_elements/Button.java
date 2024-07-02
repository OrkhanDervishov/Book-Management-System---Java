/*
 * 
 * 
 * Created by Orkhan
 * 
 * 
 */

package gui_elements;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Button implements ElementInterface<JButton> {
    private JButton _button;

    public Button(int x, int y, int width, int height, String text, JPanel panel) {
        _button = new JButton(text);
        _button.setBounds(x, y, width, height);
        panel.add(_button);
    }

    public JButton getObject() {
        return _button;
    }
}
