/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txtfile.fileHandler;

/**
 *
 * @author SPathirana
 */
public enum FileExtension {
    TXT("txt");

    private String extension;

    private FileExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return extension;
    }

}
