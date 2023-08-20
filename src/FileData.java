import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class FileData {

    FileReader fr;
    BufferedReader br;
    String curVal;
    String fileName;
    String prevVal;
    int typeDate;


    public FileData(File file, int typeDate)  throws Exception {
        try {
            this.typeDate = typeDate;
            this.fr = new FileReader(file);
            this.br = new BufferedReader(fr);
            this.fileName = file.getName();
            this.curVal = this.br.readLine();
            this.prevVal = "";
            checkVal(curVal);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String getCurVal() {
        return this.curVal;
    }

    public boolean getNextVal() {
        try {
            this.prevVal = this.curVal;
            this.curVal = this.br.readLine();
            checkVal(curVal);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    void checkVal(String valString ) throws Exception {
            if (valString == null) {
                throw new Exception("End of file " + this.fileName + ". File processing completed.");
            }

            if (valString.isEmpty()) {
                throw new Exception("In file " + this.fileName + " find null line. File processing stopped");
            }

            if ( this.typeDate == 1 && !valString.matches("-?\\d+") ) {
                throw new Exception("In file " + this.fileName + " find not numeric line. File processing stopped.");
            }

            if ( valString.contains(" ") ) {
                throw new Exception("In file " + this.fileName + " find space char. File processing stopped.");
            }

            if ( this.typeDate == 2 && !this.prevVal.isEmpty() && this.prevVal.compareTo(this.curVal) > 0 ) {
                throw new Exception("File " + this.fileName + " is not sorting. File processing stopped.");
            }

            if ( this.typeDate == 1 && !this.prevVal.isEmpty() && Integer.parseInt(this.prevVal) > Integer.parseInt(this.curVal) ) {
                throw new Exception("File " + this.fileName + " is not sorting. File processing stopped.");
            }
    }
}
