package app_runner;

import database_system.BookDataBase;
import database_system.UserDataBase;
import program_settings.SettingsControl;

public class ReadData {

    public static void read() {

        SettingsControl.read();
        UserDataBase.MainUserList.loadData();
        BookDataBase.MainBookList.loadData();
    }
}
