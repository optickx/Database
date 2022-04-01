package tools;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {
    // The method that creates the header is overwritten.
    protected void writeStreamHeader() throws IOException {
        reset();
    }

    // Constructors.
    public MyObjectOutputStream() throws IOException {
        super();
    }
    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
}
