import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class SignalProcess implements ISignalType {

    private ProcessBuilder process;
    private boolean status = false;
    private long PID;

    public SignalProcess() {
        ProcessBuilder process = new ProcessBuilder();
        this.process = process.command(DefaultNames.PROCESS_CMD);
    }

    @Override
    public void set() throws IOException {
        if (!status) {
            this.PID = process.start().pid();
            this.status = true;
        }
    }

    @Override
    public void reset() {
        if (status) {
            Optional<ProcessHandle> handle = ProcessHandle.of(this.PID);
            handle.ifPresent(ProcessHandle::destroy);
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
        return ProcessHandle.of(this.PID).isPresent();
    }

    public static boolean check(String processName) {
        ProcessBuilder pgrep = new ProcessBuilder("pgrep", processName);
        Process process;
        boolean answer;
        try {
            process = pgrep.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            answer = Integer.parseInt(reader.readLine()) > 0;
            reader.close();
        }
        catch (IOException | NumberFormatException e) {
            return false;
        }
        return answer;
    }
}
