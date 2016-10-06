package txtfile.fileExtension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class TXTFileManager implements FileManager {

    File fileName;

    public TXTFileManager(File file) {
        this.fileName = file;
    }

    @Override
    public StringBuffer readFromFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName.getPath()));
        StringBuffer sb = new StringBuffer();
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            sb.append(sCurrentLine);
            sb.append(System.getProperty("line.separator"));
        }
        return sb;
    }

    @Override
    public String writeToFile(StringBuffer sb) throws Exception {
        File file = new File(fileName.getPath());

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        String[] lines = sb.toString().split(System.getProperty("line.separator"));
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();

        System.out.println("File :: " + file.getName() + " writing completed");
        return file.getName() + " writing completed";
    }

}
