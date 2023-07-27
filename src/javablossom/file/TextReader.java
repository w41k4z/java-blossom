package javablossom.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to read a text file.
 */
public class TextReader extends BufferedReader {

    /**
     * This constructor is used to create a new Reader object.
     * 
     * @param file The file to read.
     * @throws FileNotFoundException If the file is not found.
     */
    public TextReader(File file) throws FileNotFoundException {
        super(new FileReader(file));
    }

    /**
     * This method is used to read the file given to this reader class.
     * 
     * @param toClose If true, the reader will be closed after reading the file.
     * @return Lines of data read from the file as a list of String.
     * @throws IOException If an I/O error occurs
     */
    public ArrayList<String> readFile(boolean toClose) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        String dt = this.readLine();
        while (dt != null) {
            data.add(dt);
            dt = this.readLine();
        }
        if (toClose) {
            this.close();
        }
        return data;
    }

    /**
     * This method is used to check if the file contains a string.
     * 
     * @param string  The string to check.
     * @param toClose If true, the reader will be closed after reading the file.
     * @return True if the file contains the string, false otherwise.
     * @throws IOException If an I/O error occurs
     */
    public boolean contains(String string, boolean toClose) throws IOException {
        ArrayList<String> data = this.readFile(toClose);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contains(string)) {
                return true;
            }
        }
        return false;
    }
}