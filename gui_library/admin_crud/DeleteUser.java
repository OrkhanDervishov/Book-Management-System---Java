package gui_library.admin_crud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import lang_change.Lang;

public class DeleteUser {

    public static void deleteUser(String username) {
        // Path to the user list file
        String userListFilePath = "./data/user_list.csv";

        // we created a StringBuilder to store the modified content
        StringBuilder modifiedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(userListFilePath))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                String existingUsername = userData[0];

                if (!existingUsername.equals(username)) {
                    modifiedContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // Updating the user list file with the modified content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userListFilePath))) {
            writer.write(modifiedContent.toString());
            JOptionPane.showMessageDialog(null, "User " + username + " deleted successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Process to handle user deletion operation and ask user to select
    public void deleteUserAction() {
        String[] users = getUserList();

        if (users.length == 0) {
            JOptionPane.showMessageDialog(null, "No users to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedUser = (String) JOptionPane.showInputDialog(null, Lang.SelectUserToDelete, "Delete User",
                JOptionPane.PLAIN_MESSAGE, null, users, users[0]);

        if (selectedUser != null) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the user \"" + selectedUser + "\"?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(selectedUser);
            }
        }
    }

    // Method to get the list of users from the user list file
    private String[] getUserList() {
        String userListFilePath = "./data/user_list.csv";

        ArrayList<String> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(userListFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                String username = userData[0];
                userList.add(username);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading user list: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return userList.toArray(new String[0]);
    }
}
