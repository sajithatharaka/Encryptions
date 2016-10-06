package txtfile.fileHandler;

import txtfile.fileExtension.FileManager;
import txtfile.fileExtension.TXTFileManager;
import java.io.File;

public class FileHandler {

    private static FileExtension getFileExtension(File file) throws Exception {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            // Getting the file extension
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
            try {
                return FileExtension.valueOf(extension);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Returning Default File Type :: TEXT");
                // returning default type as TEXT
                return FileExtension.TXT;
            }
        } else {
            System.out.println("UnDefinedd File Extension");
            throw new Exception("UnDefinedd File Extension");
        }
    }

    public static FileManager extenstionSelector(File file) throws Exception {
        FileExtension extension = getFileExtension(file);
        FileManager fm = null;
        //====selecting the File Type to read and write data====
        switch (extension) {
            case TXT:
                fm = new TXTFileManager(file);
                break;
            default:
                fm = new TXTFileManager(file); // Even for default text is selected
                break;
        }
        //=========================================================
        return fm;
    }

    public static StringBuffer readFile(File file) throws Exception {
        FileManager fm = extenstionSelector(file);
        return fm.readFromFile();
    }

    public static String writeFile(File file, StringBuffer sb) throws Exception {
        FileManager fm = extenstionSelector(file);
        return fm.writeToFile(sb);
    }

    public static StringBuffer readAndWriteFile(File file, FileManager fm) throws Exception {
        StringBuffer sb = fm.readFromFile();
        FileManager fm_write = extenstionSelector(file);
        fm_write.writeToFile(sb);
        return fm.readFromFile();
    }
}
