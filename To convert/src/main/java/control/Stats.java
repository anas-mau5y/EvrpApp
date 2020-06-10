package control;

import java.io.*;

public class Stats {

    public static File logPerformance;
    //output files
    public static String perfFileName;
    public static double[] perfOfTrials;

    //Used to output offline performance and population diversity

    public Stats() {
    }

    public static void openStats() {
        //Initialize
        perfOfTrials = new double[20];

        for (int i = 0; i < 20; i++) {
            perfOfTrials[i] = 0.0;
        }


        //initialize and open output files
        File file  = new File(EVRP.problemInstance);

        String sttatsFileName = file.exists() ? file.getName(): "";
        perfFileName = String.format("stats_.%s.txt", sttatsFileName);
        //for performance
        logPerformance = new File(perfFileName);
        if (!logPerformance.exists()) {
            try {
                new FileOutputStream(logPerformance).close();
                System.out.println("File created at " + logPerformance.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("File not created ");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File already exists at " + logPerformance.getAbsolutePath());

        }
        //initialize and open output files
    }

    public static void getMean(int r, double value) {
        perfOfTrials[r] = value;

    }

    public static double mean(double[] values, int size) {
        int i;
        double m = 0.0;
        for (i = 0; i < size; i++) {
            m += values[i];
        }
        m = m / (double) size;
        return m; //mean
    }

    public static double stdev(double[] values, int size, double average) {
        int i;
        double dev = 0.0;

        if (size <= 1) {
            return 0.0;
        }
        for (i = 0; i < size; i++) {
            dev += ((double) values[i] - average) * ((double) values[i] - average);
        }
        return Math.sqrt(dev / (double) (size - 1)); //standard deviation
    }

    public static double bestOfVector(double[] values, int l) {
        double min;
        int k;
        k = 0;
        min = values[k];
        for (k = 1; k < l; k++) {
            if (values[k] < min) {
                min = values[k];
            }
        }
        return min;
    }


    public static double worstOfVector(double[] values, int l) {
        double max;
        int k;
        k = 0;
        max = values[k];
        for (k = 1; k < l; k++) {
            if (values[k] > max) {
                max = values[k];
            }
        }
        return max;
    }

    public static void closeStats() {
        int i;
        int j;
        double perfMeanValue;
        double perfStdevValue;

        //For statistics
        for (i = 0; i < 20; i++) {
            //cout << i << " " << perfOfTrials[i] << endl;
            //cout << i << " " << time_of_trials[i] << endl;
            fprintf(logPerformance, "%.2f", perfOfTrials[i]);
            fprintf(logPerformance, "\n");

        }

        perfMeanValue = mean(perfOfTrials, 20);
        perfStdevValue = stdev(perfOfTrials, 20, perfMeanValue);
        fprintf(logPerformance, "Mean %f\t ", perfMeanValue);
        fprintf(logPerformance, "\tStd Dev %f\t ", perfStdevValue);
        fprintf(logPerformance, "\n");
        fprintf(logPerformance, "Min: %f\t ", bestOfVector(perfOfTrials, 20));
        fprintf(logPerformance, "\n");
        fprintf(logPerformance, "Max: %f\t ", worstOfVector(perfOfTrials, 20));
        fprintf(logPerformance, "\n");
    }

    private static void fprintf(File logPerformance, String s, Object... args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logPerformance, true))) {
            writer.print(String.format(s, args));
        } catch (IOException e) {

        }
    }
}
