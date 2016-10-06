package txtfile.fileExtension;

public interface FileManager {

    public StringBuffer readFromFile()throws Exception;
    public String writeToFile(StringBuffer sb)throws Exception; 
}
