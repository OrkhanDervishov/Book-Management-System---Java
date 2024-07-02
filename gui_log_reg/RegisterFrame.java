/*


 * Created by Bilgeyis
 * 
 * 
 */

package gui_log_reg;

import database_system.exceptions.IllegalMemberException;
import entities.user_and_admin.exceptions.IllegalPasswordException;
import entities.user_and_admin.exceptions.IllegalUsernameException;
import gui_elements.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lang_change.Lang;
import login_register.Register;

public class RegisterFrame extends LoginFrame {

    // Frame objects
    private static JFrame registrationFrame;
    private static JPanel registrationPanel;

    // Addidtional objects
    private static TextField newUsernameField;
    private static PasswordField newPasswordField;
    private static PasswordField newPasswordField2;
    private static Button registerButton;
    private static Label loginText;
    private static Label loginLink;
    private static Label usernameLabel;
    private static Label passwordLabel;
    private static Label repeatPasswordLabel;
    private static Label infoForUser;

    public static synchronized void Register() {
        openRegistrationForm();
    }

    // I made it protected because, I could call it from main
    // and it gave NullPointerException as Login page was not
    // created. So, I made it for security purposes.
    public static void openRegistrationForm() {

        // Frame and Panel settings********************************************
        registrationPanel = new JPanel();
        registrationFrame = new JFrame(Lang.registerTitle);
        registrationFrame.setSize(350, 280);
        registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrationFrame.setLocationRelativeTo(null);
        registrationFrame.add(registrationPanel);

        // Added by Orkhan. For some reason this page is can not be non-resizable
        registrationFrame.setResizable(false);
        registrationPanel.setLayout(null);
        // *********************************************************************

        /* Labels */
        // Username label
        usernameLabel = new Label(10, 20, 80, 25, Lang.usernameLabel, registrationPanel);

        // Password label
        passwordLabel = new Label(10, 60, 80, 25, Lang.passwordLabel, registrationPanel);

        // Added by Orkhan
        repeatPasswordLabel = new Label(10, 100, 120, 25, Lang.repeatPasswordLabel, registrationPanel);

        // Login text
        loginText = new Label(10, 210, 200, 25, Lang.haveAccount, registrationPanel);

        // Information label
        infoForUser = new Label(100, 130, 165, 25, null, registrationPanel);
        infoForUser.getObject().setForeground(Color.RED);

        // Text Fields**********************************************************
        // Userename field
        newUsernameField = new TextField(120, 20, 165, 25, 20, registrationPanel);

        // Password field 1
        newPasswordField = new PasswordField(120, 60, 165, 25, 20, registrationPanel);

        // Password field 2
        newPasswordField2 = new PasswordField(120, 100, 165, 25, 20, registrationPanel);
        // *********************************************************************

        // Register button******************************************************
        registerButton = new Button(100, 170, 140, 25, Lang.registerTitle, registrationPanel);
        registerButton.getObject().setBackground(new Color(0xF1F0E8));
        registerButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 1));

        registerButton.getObject().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                  registerButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 1));
             }

            @Override
             public void mouseEntered(MouseEvent e){
                registerButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 2));

             }
    });

        registerButton.getObject().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String newUsername = newUsernameField.getObject().getText();
                String newPassword = new String(newPasswordField.getObject().getPassword());
                String newPassword2 = new String(newPasswordField2.getObject().getPassword());

                // Perform registration logic with newUsername and newPassword

                // Added by Orkhan
                try {
                    if (Register.tryRegister(newUsername, newPassword, newPassword2)) {
                        LoginFrame.login();
                        registrationFrame.dispose();
                    }
                } catch (IllegalPasswordException | IllegalUsernameException | IllegalMemberException ex) {
                    infoForUser.getObject().setText(ex.getMessage());
                }
                // Here you can also add code to close the registration form or any other action
                // Here you can also add code to close the registration form or any other action
                // you want after registration.
            }
        });
        // ***********************************************************************

        // Login link
        loginLink = new Label(170, 210, 100, 25, "<html><u>" + Lang.loginHere + "</u></html>", registrationPanel);
        loginLink.getObject().setForeground(Color.BLACK);
        loginLink.getObject().setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginLink.getObject().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                LoginFrame.login();
                registrationFrame.dispose();
            }
        });

        // Frame visibility
        registrationFrame.setVisible(true);
    }
}