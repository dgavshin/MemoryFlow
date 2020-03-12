import java.io.IOException;

public interface ISignalType {

    void set() throws IOException;
    void reset() throws IOException;
    void invert() throws IOException;
    boolean check();
}
