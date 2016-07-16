package genopt.ricardo.simulacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public final class RecordFile {
    final Map<String, Float> records;

    private static Float round(Float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    private RecordFile(Map<String, Float> records) {
        this.records = records;
    }

    public RecordFile(String filePath) throws FileNotFoundException, IOException {
        this.records = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                addEntry(line);
            }
        }
    }

    private void addEntry(String line) {
        if (line.contains("Date/Time")) {
            return;
        }

        final String[] tokens = line.split(",");
        records.put(tokens[0], Float.parseFloat(tokens[1]));
    }

    public RecordFile subtract(RecordFile other) {
        final Map<String, Float> temp = new TreeMap<>();

        for (Map.Entry<String, Float> e : this.records.entrySet()) {
            final Float otherVal = other.records.get(e.getKey());

            if (otherVal == null) {
                System.err.println("Could not find the corresponding timestamp for " + e.getKey());
                continue;
            }

            temp.put(e.getKey(), e.getValue() - otherVal);
        }

        return new RecordFile(temp);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append("Date/Time,Subtracted Temperature \n");
        for (Map.Entry<String, Float> e : this.records.entrySet()) {
            sb.append(e.getKey() + "," + round(e.getValue(), 2) + "\n");
        }
        sb.append("\n");

        return sb.toString();
    }
}
