import java.io.*;

public class SignalFile implements ISignalType {

    private File file;
    private boolean status = false;

    SignalFile()
    {
        this.file = new File(DefaultNames.FILE_FOR_SIGNAL);
    }

    @Override
    public void set() {
        if (!status)
            try {
                FileOutputStream fis = new FileOutputStream(file);
                fis.write(2);
                fis.close();
                this.status = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void reset() {
        if (status) {
            file.delete();
            this.status = false;
        }
    }

    @Override
    public void invert() {
        if (status)
            reset();
        else
            set();
    }

    @Override
    public boolean check() {
        return file.exists();
    }

    public static boolean check(String filename)
    {
        File file = new File(filename);
        return file.exists();
    }
}
