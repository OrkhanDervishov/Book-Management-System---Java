package gui_log_reg;
/*


 * Created by Bilgeyis
 * 
 * 
 */

import app_runner.MainTableGUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import database_system.exceptions.IllegalMemberException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;

// Project classes
import login_register.Login;
import login_register.login_exceptions.WrongUserException;
import program_settings.Parametres;
import program_settings.Status;
import gui_elements.*;
import gui_library.AdminGUI;
import gui_library.DatabaseLib;
import gui_library.UserGUI;
import lang_change.Lang;

public class LoginFrame {
        // Frame objects
        private static JPanel jpanel;
        private static JFrame jframe;

        // Additional objects
        private static Label Userlabel;
        private static Label passwordlabel;
        private static TextField userText;
        private static PasswordField passwordText;
        private static Label registerLink;
        private static Label registerText;
        private static Button loginButton;
        private static Label infoForUser;

        public static void Login() {
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                login();
                                return;
                        }
                });
        }

        // Added by Orkhan
        // By calling this method we can close login page
        public static void closeFrame() {
                jframe.dispose();
        }

        protected static void login() {

                // Login frame
                jpanel = new JPanel();
                jframe = new JFrame();
                jframe.setSize(350, 230);
                jframe.setTitle(Lang.loginTitle);
                jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jframe.setLocationRelativeTo(null);
                jframe.add(jpanel);
                jpanel.setLayout(null);

                jframe.setResizable(false);
                jpanel.setLayout(null);

                /* Labels*************************************************************** */
                // Username label
                Userlabel = new Label(10, 20, 80, 25, Lang.usernameLabel, jpanel);

                // Password label
                passwordlabel = new Label(10, 70, 80, 25, Lang.passwordLabel, jpanel);

                // Label near register button
                registerText = new Label(10, 165, 200, 25, Lang.dontHaveAccount, jpanel);

                // Information for user
                infoForUser = new Label(100, 100, 300, 25, null, jpanel);
                infoForUser.getObject().setForeground(Color.RED);
                /*********************************************************************** */

                /* Text fields*********************************************************** */
                userText = new TextField(100, 20, 165, 25, 20, jpanel);

                passwordText = new PasswordField(100, 70, 165, 25, 20, jpanel);
                /*********************************************************************** */

                // Register link***********************************************************
                registerLink = new Label(190, 165, 200, 25, "<html><u>" + Lang.registerHere + "</u></html>", jpanel);
                registerLink.getObject().setForeground(Color.black);
                registerLink.getObject().setCursor(new Cursor(Cursor.HAND_CURSOR));
                registerLink.getObject().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                RegisterFrame.Register(); // Open the registration form
                                jframe.dispose();
                        }
                });
                // **************************************************************************

                /* Login button************************************************************** */
                loginButton = new Button(130, 130, 100, 25, Lang.loginTitle, jpanel);
                loginButton.getObject().setBackground(new Color(0xF1F0E8));
                loginButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 1));
        
                loginButton.getObject().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                        loginButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 1));
                 }

                @Override
                public void mouseEntered(MouseEvent e){
                        loginButton.getObject().setBorder(BorderFactory.createLineBorder(new Color(0x0C0C0C), 2));
                  }
           });                
                loginButton.getObject().addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                String user = userText.getObject().getText();
                                String password = new String(passwordText.getObject().getPassword());

                                try {
                                        if (Login.tryLogin(user, password)) {
                                                jframe.dispose();

                                                if (Parametres.getUserStatus() == Status.ADMIN) {
                                                        MainTableGUI.databaseLib = new AdminGUI();
                                                } else {
                                                        MainTableGUI.databaseLib = new UserGUI();
                                                }
                                        }
                                } catch (WrongUserException | IllegalMemberException ex) {
                                        infoForUser.getObject().setText(ex.getMessage());
                                }
                                // Here you can also add code to go back to the login form or any other action
                                // you want after registration.
                        }
                });
                /*************************************************************************** */

                // Visibility
                jframe.setVisible(true);
        }
}