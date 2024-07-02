/*
 * 
 * 
 * Created by Orkhan
 * 
 * 
 */

package gui_elements;

import javax.swing.JTextField;
import javax.swing.JPanel;

public class TextField implements ElementInterface<JTextField> {

    private JTextField _textField;

    public TextField(int x, int y, int width, int height, int columns, JPanel panel) {
        _textField = new JTextField(columns);
        _textField.setBounds(x, y, width, height);
        panel.add(_textField);
    }

    public JTextField getObject() {
        return _textField;
    }

}
