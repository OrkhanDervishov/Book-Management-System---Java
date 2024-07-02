/*
 * 
 * 
 * Created by Orkhan
 * 
 * 
 */

package gui_elements;

import javax.swing.JPasswordField;
import javax.swing.JPanel;

public class PasswordField implements ElementInterface<JPasswordField> {

    private JPasswordField _textField;

    public PasswordField(int x, int y, int width, int height, int columns, JPanel panel) {
        _textField = new JPasswordField(columns);
        _textField.setBounds(x, y, width, height);
        panel.add(_textField);
    }

    public JPasswordField getObject() {
        return _textField;
    }
}
