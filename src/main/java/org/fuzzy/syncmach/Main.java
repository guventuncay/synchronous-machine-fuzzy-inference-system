package org.fuzzy.syncmach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String csvFile = "src/main/resources/SynchronousMachineDataset.csv";
        String flcFile = "src/main/resources/flcs/synchronous-machine.fcl";

        if (!new File(csvFile).isFile()) {
            throw new FileNotFoundException("File not found");
        }

        List<SynchronousMachine> machines = CSVReader.readMachinesFromCSV(csvFile, flcFile);

        machines.forEach(SynchronousMachine::evaluate);

        System.out.println(machines.stream().sorted(Comparator.comparing(SynchronousMachine::getSuccess).reversed()).toList());

        System.out.println("average success: " + machines.stream().mapToDouble(SynchronousMachine::getSuccess).average().getAsDouble());

        List<Double> actual = machines.stream().map(SynchronousMachine::getActualI_f).toList();
        List<Double> predicated = machines.stream().map(SynchronousMachine::getI_f).toList();

        double mae = calculateMae(actual, predicated);
        System.out.println("mae: " + mae);
        double rmse = calculateRmse(actual, predicated);
        System.out.println("rmse: " + rmse);
    }

    private static double calculateMae(List<Double> actual, List<Double> predicted) {
        double sum = 0.0;
        int n = actual.size();
        for (int i = 0; i < n; i++) {
            sum = sum + Math.abs(actual.get(i) - predicted.get(i));
        }
        double mae = sum / n;
        return mae;
    }

    private static double calculateMse(List<Double> actual, List<Double> predicted) {
        int n = actual.size();
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double diff = actual.get(i) - predicted.get(i);
            sum = sum + diff * diff;
        }
        double mse = sum / n;
        return mse;
    }

    private static double calculateRmse(List<Double> actual, List<Double> predicted) {
        double mse = calculateMse(actual, predicted);
        double rmse = Math.sqrt(mse);
        return rmse;
    }
}
