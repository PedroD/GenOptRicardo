package genopt.ricardo.simulacao;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main {
    private static String MEASURED_FILE = "medidos.csv";
    private static String SIMULATED_FILE = "simulados.csv";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        final RecordFile measured = new RecordFile(MEASURED_FILE);
        final RecordFile simulated = new RecordFile(SIMULATED_FILE);

        // measured values - simulated values
        System.out.println(measured.subtract(simulated));
    }
}
