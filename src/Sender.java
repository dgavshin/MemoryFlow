import javax.management.InvalidAttributeValueException;
import javax.swing.text.DefaultEditorKit;
import java.io.*;
import java.util.Arrays;

public class Sender {

    private static ISignalType bitSignal;
    private static ISignalType readySignal;

    private static String TYPE = "sender";
    private static String RECEIVER_FLAG = DefaultNames.RECEIVER_FLAG;
    private static String BIT_FLAG = DefaultNames.SENDER_BIT_FLAG;
    private static String READY_FLAG = DefaultNames.SENDER_READY_FLAG;

    static boolean[] getBits(byte b)
    {
        boolean[] result = new boolean[8];
        for(int i = 0, j = 8; i < 8; i++)
            result[j - i - 1] = ((b >> i) & 1) == 1;
        return result;
    }

    public static void sendData(byte[] fileData) throws IOException {
        boolean last_flag = false;

        for (byte data: fileData)
            for (boolean b: getBits(data))
            {
                if (b) bitSignal.set();
                else   bitSignal.reset();

                readySignal.invert();
                last_flag = DefaultNames.waitForAnswer(RECEIVER_FLAG, last_flag);
            }
    }

    public static void main(String[] args) throws IOException, InvalidAttributeValueException {
        File    file;
        int     len;
        byte[]  fileData;
        FileInputStream in;

        file = new File(DefaultNames.FILE_FOR_STEAL);
        fileData = new byte[(int) file.length()];
        in = new FileInputStream(file);
        len = in.read(fileData);
        in.close();

        if (!BIT_FLAG.equals(READY_FLAG)) {
            switch (BIT_FLAG) {
                case "socket":
                    bitSignal = new SignalSocket();
                    break;
                case "process":
                    bitSignal = new SignalProcess();
                    break;
                case "file":
                    bitSignal = new SignalFile();
                    break;
            }

            switch (READY_FLAG) {
                case "socket":
                    readySignal = new SignalSocket();
                    break;
                case "process":
                    readySignal = new SignalProcess();
                    break;
                case "file":
                    readySignal = new SignalFile();
                    break;
            }

            if (len > 0)
                sendData(fileData);
        }
        else
                throw new InvalidAttributeValueException();
    }

}
