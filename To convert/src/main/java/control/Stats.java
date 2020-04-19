package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Stats {

    public static File log_performance;
    //output files
    public static String perf_filename;
    public static double[] perf_of_trials;

    //Used to output offline performance and population diversity

    public Stats() {
    }

    public static void open_stats() {
        //Initialize
         perf_of_trials = new double[20];

        for (int i = 0; i < 20; i++) {
            perf_of_trials[i] = 0.0;
        }


        //initialize and open output files
        File file  = new File(EVRP.problem_instance);

        String sttatsFileName = file.exists() ? file.getName(): "";
        perf_filename = String.format("stats.%s.txt", sttatsFileName);
        //for performance
        log_performance = new File(perf_filename);
        if (!log_performance.exists()) {
            try {
                new FileOutputStream(log_performance).close();
                System.out.println("File created at " + log_performance.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("File not created ");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File already exists at " + log_performance.getAbsolutePath());

        }
        //initialize and open output files
    }

    public static void get_mean(int r, double value) {
        perf_of_trials[r] = value;

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

    public static double best_of_vector(double[] values, int l) {
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


    public static double worst_of_vector(double[] values, int l) {
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

    public static void close_stats() {
        int i;
        int j;
        double perf_mean_value;
        double perf_stdev_value;

        //For statistics
        for (i = 0; i < 20; i++) {
            //cout << i << " " << perf_of_trials[i] << endl;
            //cout << i << " " << time_of_trials[i] << endl;
            fprintf(log_performance, "%.2f", perf_of_trials[i]);
            fprintf(log_performance, "\n");

        }

        perf_mean_value = mean(perf_of_trials, 20);
        perf_stdev_value = stdev(perf_of_trials, 20, perf_mean_value);
        fprintf(log_performance, "Mean %f\t ", perf_mean_value);
        fprintf(log_performance, "\tStd Dev %f\t ", perf_stdev_value);
        fprintf(log_performance, "\n");
        fprintf(log_performance, "Min: %f\t ", best_of_vector(perf_of_trials, 20));
        fprintf(log_performance, "\n");
        fprintf(log_performance, "Max: %f\t ", worst_of_vector(perf_of_trials, 20));
        fprintf(log_performance, "\n");
    }

    private static void fprintf(File log_performance, String s, Object... args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(log_performance, true))) {
            writer.print(String.format(s, args));
        } catch (IOException e) {

        }
    }
}
