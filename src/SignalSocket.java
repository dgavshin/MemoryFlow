import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SignalSocket implements ISignalType {

    private ServerSocket socket;
    private boolean status = false;

    @Override
    public void set() throws IOException {
        if (!status) {
            socket = new ServerSocket(DefaultNames.SOCKET_PORT);
            this.status = true;
        }
    }

    @Override
    public void reset() throws IOException {
        if (status)
        {
            socket.close();
            this.status = false;
        }
    }

    @Override
    public void invert() throws IOException {
        if (status)
            reset();
        else
            set();
    }

    @Override
    public boolean check() {
        try {
            return !socket.isClosed();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean check(int port)
    {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
