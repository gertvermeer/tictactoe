import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by g.s.vermeer on 26/05/2019.
 */
public class FileManager {

    Path winPath = Paths.get("winList.csv" );
    Path drawPath = Paths.get("drawList.csv" );


    public void printGameListToFile(List<String> winList, List<String> drawList){

        try (BufferedWriter winWriter = Files.newBufferedWriter(winPath);
             BufferedWriter drawWriter = Files.newBufferedWriter(drawPath)){

            winList.forEach(s -> printGameToFile(winWriter,s));
            drawList.forEach((s-> printGameToFile(drawWriter,s)));

        } catch  (IOException e) {
            e.printStackTrace();
        } {

        }

    }

    public void printGameToFile(BufferedWriter writer ,String game){
        try {
            if (!game.equals("")){
                writer.write(game);
                writer.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readWinFile(){
        return readFile(winPath);
    }

    public List<String> readDrawFile(){
        return readFile(drawPath);
    }

    public List<String> readFile(Path path){
        List<String> resultList  = new ArrayList<>();
        try {
            Files.lines(path).forEach(s -> addStringToList(s, resultList));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private List<String> addStringToList(String value, List<String> stringList){
        if (!value.equals("")){
            stringList.add(value);
            return stringList;
        }
        return stringList;
    }

}
