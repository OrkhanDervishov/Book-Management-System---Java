package app_runner;

import database_system.BookDataBase;
import database_system.UserDataBase;
import program_settings.SettingsControl;

public class SaveData {

    public static void save() {

        SettingsControl.write();
        UserDataBase.MainUserList.writeData();
        BookDataBase.MainBookList.writeData();
    }
}
