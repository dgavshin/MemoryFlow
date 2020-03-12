import javax.management.DescriptorAccess;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Receiver {

    public static String SENDER_BIT_FLAG = DefaultNames.SENDER_BIT_FLAG;
    public static String SENDER_READY_FLAG = DefaultNames.SENDER_READY_FLAG;
    public static String FLAG = DefaultNames.RECEIVER_FLAG;

    public static void main(String[] args) throws IOException {

        File file = new File("/tmp/stolen");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        ISignalType readySignal;

        switch (FLAG) {
            case "socket":
                 readySignal = new SignalSocket();
                 break;
            case "process":
                readySignal = new SignalProcess();
                break;
            case "file":
                readySignal = new SignalFile();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + FLAG);
        }

        boolean last_flag = false;
        while (true)
        {
            StringBuilder received_byte = new StringBuilder();
            for (int i = 0; i < 8; i++)
            {
                last_flag = DefaultNames.waitForAnswer(SENDER_READY_FLAG, last_flag);
                received_byte.append(DefaultNames.checkFlag(SENDER_BIT_FLAG) ? "1" : "0");
                readySignal.invert();
            }
            byte word = Byte.parseByte(received_byte.toString(), 2);
            dos.write(word);
            System.out.print((char) word);
        }
    }
}
