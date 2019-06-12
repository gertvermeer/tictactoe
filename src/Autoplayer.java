import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by g.s.vermeer on 26/05/2019.
 */
public class Autoplayer {

    List<String> winList = new ArrayList<>();
    List<String> drawlist = new ArrayList<>();
    int Iteration = 10;

    String movesList = new String();

    public void registerMove(int move){
        movesList = movesList + move;
    }

    public void printMoveList(){
        System.out.println(movesList);
    }

    public Autoplayer() {
        FileManager fileManager = new FileManager();
        winList = fileManager.readWinFile();
        drawlist = fileManager.readDrawFile();
    }

    public ArrayList<Integer> findMoves(Board board) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int t=0;t<9;t++){
            int x = t/3;
            int y = t - x*3;
            if (board.getCell(x,y).equals(Cell.EMPTY)){
               possibleMoves.add(t);}
        }
        return possibleMoves;
    }

    public int playRandomMove(Board board){
        List<Integer> possibleMoves = findMoves(board);
        int moveInt = possibleMoves.remove(new Random().nextInt(possibleMoves.size()));
        moveInt ++;
        movesList = movesList.concat(Integer.toString(moveInt));
        return moveInt;

    };


    public int playMove(Board board){
        ArrayList<Integer> possibleMoves = findMoves(board);



        int [] score = new int[9];





        List<String> losingList = winList
                .stream()
                .filter(t -> t.substring(0,movesList.length()).equals(movesList))
                .map(u -> u.replace("0",""))
                .map(s -> s.substring(movesList.length(),movesList.length()+1))
                .filter(v -> v.length()%2==movesList.length()%2)
                .collect(Collectors.toList());

        List<String> losingUniqueList = losingList
                .stream()
                .distinct()
                .collect(Collectors.toList());

       losingUniqueList.forEach(s -> possibleMoves.remove((Integer)Integer.parseInt(s)));




       List<String> superWinList = new ArrayList<>();

       winList.forEach(
               s -> checkMate(superWinList,s,movesList)
       );

      superWinList.forEach(s -> System.out.println(s));



        List<String> winingList = winList
                .stream()
                .filter(t -> t.substring(0,movesList.length()).equals(movesList))
                .map(u -> u.replace("0",""))
                .filter(v -> v.length()%2==movesList.length()+1%2)
                .map(s -> s.substring(movesList.length(),movesList.length()+1))
                .collect(Collectors.toList());
        List<String> losingMoveList = winList
                .stream()
                .filter(t -> t.substring(0,movesList.length()).equals(movesList))
                .map(u -> u.replace("0",""))
                .filter(s -> s.length() == (movesList.length()+2))
                .map(s -> s.substring(s.length()-1,s.length()))
                .collect(Collectors.toList());

        List<String> winnerMoveList = winList
                .stream()
                .filter(t -> t.substring(0,movesList.length()).equals(movesList))
                .map(u -> u.replace("0",""))
                .filter(s -> s.length() == (movesList.length()+1))
                .map(s -> s.substring(s.length()-1,s.length()))
                .collect(Collectors.toList());


        List<String> drawPlayList = drawlist
                .stream()
                .filter(t -> t.substring(0,movesList.length()).equals(movesList))
                .map(s -> s.substring(movesList.length(),movesList.length()+1))
                .collect(Collectors.toList());

        int moveInt;

        if (losingMoveList.size()> 0){
            moveInt = Integer.parseInt(losingMoveList.get(0));
            movesList = movesList.concat(Integer.toString(moveInt));
            System.out.println("Prevent to lose");
            return  moveInt;
        }

        if (winnerMoveList.size()> 0){
            moveInt = Integer.parseInt(winnerMoveList.get(0));
            movesList = movesList.concat(Integer.toString(moveInt));
            System.out.println("This is a winner move");
            return  moveInt;
        }


        if (winingList.size()==1){
            moveInt =  Integer.parseInt(winingList.get(0));
            movesList = movesList.concat(Integer.toString(moveInt));
            System.out.println("One winning move");
            return  moveInt;

        }

        if (winingList.size()>1) {
            moveInt = findOptimalWin(winingList, losingList);
            if (moveInt != 0  && movesList.contains(Integer.toString(moveInt))) {
                movesList = movesList.concat(Integer.toString(moveInt));
                System.out.println("Balance win over lost");
                return moveInt;
            }

        }

        if (winingList.size()==0){
            moveInt =findMostOccure(drawPlayList);

            if(moveInt!=0 && possibleMoves.contains(moveInt)){
                movesList = movesList.concat(Integer.toString(moveInt));
                System.out.println("Defend");
                return  moveInt;
            }

        }

        System.out.println("New move");
        moveInt = possibleMoves.remove(0);
        moveInt ++;
        movesList = movesList.concat(Integer.toString(moveInt));
        return moveInt;
    }



    public int findMostOccure(List<String> checkList){

        if (checkList.size() == 0){
            return 0;
        }
        Map<Integer,Integer> occuMap = new HashMap<>();
        checkList.forEach(s -> occuMap.put(Integer.parseInt(s), addNumber(occuMap.get(Integer.parseInt(s)))));
        return  occuMap.entrySet()
                .stream()
                .max((s1,s2)-> s1.getValue() > s2.getValue()?1:-1)
                .get()
                .getKey();

    }

    public int findOptimalWin(List<String> winList , List<String> loseList){

        if (winList.size() == 0 || loseList.size()==0){
            return 0;
        }
        Map<Integer,Integer> occuMap = new HashMap<>();
        winList.forEach(s -> occuMap.put(Integer.parseInt(s), addNumber(occuMap.get(Integer.parseInt(s)))));
        loseList.forEach(s -> occuMap.replace(Integer.parseInt(s), addNumber(occuMap.get(Integer.parseInt(s)))));
        return  occuMap.entrySet()
                .stream()
                .max((s1,s2)-> s1.getValue() > s2.getValue()?1:-1)
                .get()
                .getKey();

    }


    private int addNumber(Integer s){
        if (s == null){
            return 1;
        }
        return s++;
    }

    public void registerWin(boolean winner){

        movesList = movesList + "0000000";

        List<String> checkList;
        if (winner){
            checkList = winList;
        } else {
            checkList = drawlist;
        }
        if (!checkList.contains(movesList.substring(0,9))){
            checkList.add(movesList.substring(0,9));
            movesList = new String();
        }

    }


    public void clearMoves(){
        movesList = new String();
    }


    public void printGamesToFile(){
        Collections.sort(winList);
        Collections.sort(drawlist);
        FileManager fileManager = new FileManager();
        fileManager.printGameListToFile(winList, drawlist);
    }


    public void checkMate(List<String> searchList,String s, String movesDone){
        int move = movesDone.length();
        List<String> found = searchList
                .stream()
                .filter(t -> t.substring(0,move).equals(movesDone))
                .map(u -> u.replace("0",""))
                .filter(u -> u.length() == move+3)
                .filter((v -> v.substring(move+1).equals(s.substring(move+2))))
                .filter((v -> v.substring(move+2).equals(s.substring(move+1))))
                .collect(Collectors.toList());
        if(found.size() > 0){
            searchList.add(found.get(0));
        }

    }

    public void printGameList(){
        System.out.println("winning games:");
        winList.forEach(s -> System.out.println(s));
        System.out.println(" draw games");
        drawlist.forEach((s-> System.out.println(s)));
    }


}
