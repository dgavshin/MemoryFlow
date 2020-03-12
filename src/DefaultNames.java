import java.util.ArrayList;
import java.util.List;

public class DefaultNames {

    public static String[]  PROCESS_CMD = {"sh", "-c", "tac"};
    public static String    PROCESS_NAME = "tac";
    public static String    FILE_FOR_STEAL = "/etc/passwd";
    public static String    FILE_FOR_SIGNAL = "/tmp/31337";


    public static String    SENDER_BIT_FLAG = "process";
    public static String    SENDER_READY_FLAG = "file";

    public static String    RECEIVER_FLAG = "socket";

    public static int       SOCKET_PORT = 31337;

    public static boolean checkFlag(String flag)
    {
        if (flag.equals("socket"))
            return SignalSocket.check(DefaultNames.SOCKET_PORT);
        else if (flag.equals("process"))
            return SignalProcess.check(DefaultNames.PROCESS_NAME);
        else if (flag.equals("file"))
            return SignalFile.check(DefaultNames.FILE_FOR_SIGNAL);
        else
            throw new IllegalArgumentException("Illegal argument " + flag);
    }

    public static boolean waitForAnswer(String flag, boolean expect)
    {
        while (true)
            if (checkFlag(flag) == !expect)
                return !expect;
    }
}
