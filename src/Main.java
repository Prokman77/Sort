
import java.io.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        //a - ascending, d - descending
        boolean sortDesc = false;

        // 0 - param not def, 1 - numeric, 2 - string
        int typeData = 0;

        String nameOutFile = "";
        ArrayList<String> namesInFile = new ArrayList<String> ();

        ArrayList<FileData> listFiles = new ArrayList<FileData> ();

        System.out.println("Start files sorting...");
        try {

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-d")) {
                    sortDesc = true;
                }
                else if (args[i].equals("-a")) {
                    sortDesc = false;
                }
                else if (args[i].equals("-i")) {
                    typeData = 1;
                }
                else if (args[i].equals("-s")) {
                    typeData = 2;
                }

                else if (args[i].endsWith(".txt")) {
                    //first is out file
                    if (nameOutFile.isEmpty()) {
                        nameOutFile = args[i];
                    } else {
                        namesInFile.add(args[i]);
                    }
                }
                else  throw new Exception("Argument "+ args[i] +" is not supported!");
            }

            if ( typeData == 0) {
                throw new Exception("Not set data type!");
            }

            if ( nameOutFile.isEmpty()) {
                throw new Exception("Not set name of output file!");
            }

            if ( namesInFile.isEmpty()) {
                throw new Exception("Not set names of incoming files!");
            }

            final int finalTypeData = typeData;
            File curFile = new File(nameOutFile);
            boolean isExists = !curFile.createNewFile();

            FileWriter fw = new FileWriter(curFile);
            if (isExists) fw.write("");
            BufferedWriter bw = new BufferedWriter(fw);

            namesInFile.forEach(s -> {
                try {
                    File file = new File(s);
                    FileData fd = new FileData(file, finalTypeData);
                    listFiles.add(fd);
                } catch (Exception ex) {
                    System.out.println("init file: " + s + " " + ex.getMessage());
                }
            });

            int minValIndex;
            int sizeOfOutFile=0;
            String currentValue;
            boolean resultOfProcessFile;
            boolean isFirstTime = true;
            while (!listFiles.isEmpty()) {
                currentValue = "";
                minValIndex = 0;
                for (int i = 0; i < listFiles.size(); i++) {
                    if ( currentValue.isEmpty() ) {
                        minValIndex = i;
                        currentValue = listFiles.get(i).getCurVal();
                    } else if (typeData == 2 && currentValue.compareTo(listFiles.get(i).getCurVal()) > 0) {
                        minValIndex = i;
                        currentValue = listFiles.get(i).getCurVal();
                    } else if (typeData == 1 && Integer.parseInt(currentValue) > Integer.parseInt(listFiles.get(i).getCurVal())) {
                        minValIndex = i;
                        currentValue = listFiles.get(i).getCurVal();
                    }
                }
                resultOfProcessFile = listFiles.get(minValIndex).getNextVal();
                if ( !resultOfProcessFile ) {
                    listFiles.remove(minValIndex);
                }
                if ( !isFirstTime ) bw.newLine(); else isFirstTime = false;
                bw.append(currentValue);
                sizeOfOutFile++;

            }


            //TODO add sort desc
            //if (sortDesc)

            bw.close();
            fw.close();

            if (sortDesc) {
              try {
                  FileReader fr = new FileReader(curFile);
                  BufferedReader br = new BufferedReader(fr);
                  String revers [] = new String [sizeOfOutFile];
                  isFirstTime = true;
                  for (int i=0; i<sizeOfOutFile;i++) {
                      revers[i]=br.readLine();
                    //  System.out.println("string " +i + " value "+revers[i]);

                  }
                  br.close();
                  fr.close();



                  FileWriter reversFw= new FileWriter(curFile);
                  BufferedWriter reversBw=new BufferedWriter(reversFw);
                  for (int i=sizeOfOutFile-1;i>=0;i-- ) {
                      if ( !isFirstTime ) reversBw.newLine(); else isFirstTime = false;
                      reversBw.append(revers[i]);
                    //  System.out.println("string " +i + " value "+revers[i]);
                  }
                  reversBw.close();
                  reversFw.close();
              }
                catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            }

            System.out.println("Finishing files sorting");
            //System.out.println("sizeOfOutFile "+ sizeOfOutFile);




        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
