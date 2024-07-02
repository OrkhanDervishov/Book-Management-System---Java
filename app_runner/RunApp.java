package app_runner;

import gui_log_reg.LoginFrame;


public class RunApp extends Thread {

    public synchronized void run() {

        ReadData.read();

        try {
            LoginFrame.Login();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
