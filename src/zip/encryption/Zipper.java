package zip.encryption;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.swing.JProgressBar;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zipper {

    private String password;
    public static final String EXTENSION = "encrypt";
    private JProgressBar progress;

    public Zipper(String password, JProgressBar porgrees) {
        this.password = password;
        this.progress = porgrees;
    }

    public String pack(File[] files) throws ZipException, IOException {
        try {
            progress.setValue(0);
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            zipParameters.setPassword(password);

            //=======Getting the file path=======
            String absolutePath = files[0].getAbsolutePath();
            String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            String destinationZipFilePath = filePath + "/" + new Date().getTime() + "." + EXTENSION;
            System.out.println("File created :: " + destinationZipFilePath);
            //====================================

            // Iterating add all the files selected
            ZipFile zipFile = new ZipFile(destinationZipFilePath);
            for (File file : files) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Progression will be displayed file by file
                        progress.setValue(zipFile.getProgressMonitor().getPercentDone());
                    }
                }).start();

                zipFile.addFile(file, zipParameters);
                file.createNewFile();
            }
            //=====================================
            System.out.println("Encryption Completed");
            progress.setValue(100);
            return "Encryption completed. File Name :: " + destinationZipFilePath;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String unpack(File sourceZipFilePath, File extractedZipFilePath) throws ZipException {
        try {
            progress.setValue(0);
            ZipFile zipFile = new ZipFile(sourceZipFilePath);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            System.out.println(extractedZipFilePath.getAbsoluteFile());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    progress.setValue(zipFile.getProgressMonitor().getPercentDone());
                }
            }).start();

            zipFile.extractAll(extractedZipFilePath.getAbsolutePath());
            System.out.println();
            progress.setValue(100);
            return "Successfully Decrypted";
        } catch (ZipException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
