package org.fuzzy.syncmach;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<SynchronousMachine> readMachinesFromCSV(String fileName, String flc) {
        List<SynchronousMachine> machines = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                SynchronousMachine machine = createMachine(attributes, flc);

                // adding book into ArrayList
                machines.add(machine);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return machines;
    }

    private static SynchronousMachine createMachine(String[] metadata, String flc) {
        double I_y = Double.parseDouble(metadata[0]);
        double PF = Double.parseDouble(metadata[1]);
        double e_PF = Double.parseDouble(metadata[2]);
        double d_if = Double.parseDouble(metadata[3]);
        double actualI_f = Double.parseDouble(metadata[4]);

        // create and return book of this metadata
        return new SynchronousMachine(flc, I_y, PF, e_PF, d_if, actualI_f);
    }

}

