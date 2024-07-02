package entities.other;

import java.io.File;
import program_settings.Parametres;

public class CheckFile {
    
    public static boolean check(String path, String name){

        File folder = new File(path);
        File[] files = folder.listFiles();
        if(files == null){
            return false;
        }

        for(File file : files){
            
            if(file.getName().equals(name + Parametres.FILE_FORMAT)) return true;

            continue;
        }

        return false;
    }
}
