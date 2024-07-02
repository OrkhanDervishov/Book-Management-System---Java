/*
 * 
 * 
 * Created by Orkhan
 * 
 * 
 */

package gui_elements;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Label implements ElementInterface<JLabel> {

    private JLabel _label;

    public Label(int x, int y, int width, int height, String text, JPanel panel) {
        _label = new JLabel(text);
        _label.setBounds(x, y, width, height);
        panel.add(_label);
    }

    public JLabel getObject() {
        return _label;
    }
}
