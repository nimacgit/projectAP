package ir.nimac.model.Alevel;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SynchronizeDataOutPut extends DataOutputStream {
    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter <code>written</code> is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */
    public static final Object obj = new Object();

    public SynchronizeDataOutPut(OutputStream out) {
        super(out);
    }

    public void write(String text) {
        synchronized (obj) {
            try {
                writeBytes(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
