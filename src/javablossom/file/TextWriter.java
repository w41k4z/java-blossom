package javablossom.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * the {@code TextWriter} class is used to write in a text file.
 */
public class TextWriter extends BufferedWriter {

    /**
     * This constructor is used to create a new Writer object.
     * 
     * @param file The file to write.
     * @throws IOException If an I/O error occurs
     */
    public TextWriter(File file) throws IOException {
        super(new FileWriter(file, true));
    }

    /**
     * This method is used to write the given file to this class.
     * 
     * @param data The data to write.
     * @throws IOException If an I/O error occurs
     */
    public void writeFile(Object[] data) throws IOException {
        for (int i = 0; i < data.length; i++) {
            this.write(data[i].toString());
            this.newLine();
        }
        this.flush();
        this.close();
    }
}
