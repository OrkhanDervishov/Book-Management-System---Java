package entities.other;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ControlOpinion {

    public static int[] getAllOpinion(String path, String name){

        if(!CheckFile.check(path, name)){
            return null;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            String line = br.readLine();
            String reviews[] = line.strip().split(" ", -1);

            int indexes[] = new int[reviews.length];

            for(int i = 0; i < reviews.length; i++){
                indexes[i] = Integer.parseInt(reviews[i]);
            }

            return indexes;
        } catch (IOException e) {
            System.out.println("file reading failed");
        }
        return null;
    }

    public static void deleteOpinionFromEntity(Integer review_index, String path){

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            String line = br.readLine();
            String reviews[] = line.strip().split(" ", -1);
            ArrayList<String> list = new ArrayList<>();
            String str_review_index = review_index.toString();

            for(String s : reviews){
                if(s.equals(str_review_index)) continue;
                
                list.add(s);
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));

            for(String s : list){
                bw.write(s + " ");
            }

            bw.close();

        }catch(IOException e){

        }
    }
}
