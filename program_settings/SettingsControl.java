package program_settings;

import entities.rating.Rating;
import entities.review.Review;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsControl {

    public static void read() {

        try (BufferedReader bf = new BufferedReader(new FileReader(Parametres.SETTINGS_PATH))) {

            Review.setGeneralIndex(Integer.parseInt(bf.readLine().split("=")[1]));
            Rating.setGeneralIndex(Integer.parseInt(bf.readLine().split("=")[1]));

        } catch (IOException e) {
            System.out.println("error during reading settings.txt");
        }
    }

    public static void write() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Parametres.SETTINGS_PATH))) {

            bw.write("review_index=" + Review.getGeneralIndex());
            bw.newLine();

            bw.write("rating_index=" + Rating.getGeneralIndex());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("error during writing to settings.txt");
        }
    }
}
