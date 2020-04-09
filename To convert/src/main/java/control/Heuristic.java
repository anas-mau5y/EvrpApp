package control;

import structures.RandomNumbers;
import structures.Solution;

import static control.EVRP.*;

public class Heuristic {

    private static final double RAND_MAX = 0d;

    public Heuristic() {
    }

    public static void run_heuristic() {

        Solution best_sol = initialize_heuristic();

        /*generate a random solution for the random heuristic*/
        int i;
        int help;
        int object;
        int tot_assigned = 0;
        int[] r;
        double energy_temp = 0.0;
        double capacity_temp = 0.0;
        int from;
        int to;
        int temp;
        int charging_station;


        r = new int[NUM_OF_CUSTOMERS + 1];
        //set indexes of objects
        for (i = 1; i <= NUM_OF_CUSTOMERS; i++) {
            r[i - 1] = i;

        }
        //randomly change indexes of objects
        for (i = 0; i <= NUM_OF_CUSTOMERS; i++) {
            object = (int) ((RandomNumbers.nextNumber() / (RAND_MAX + 1.0)) * (double) (NUM_OF_CUSTOMERS - tot_assigned));
            help = r[i];
            r[i] = r[i + object];
            r[i + object] = help;
            tot_assigned++;
        }

        best_sol.setSteps(0);
        best_sol.setTour_length(Integer.MAX_VALUE);
        best_sol.setTourAtIndex(DEPOT, 0);
        best_sol.incrementSteps();

        i = 0;
        while (i < NUM_OF_CUSTOMERS) {
            from = best_sol.getTourAt(best_sol.getSteps() - 1);
            to = r[i];

            double customer_demand = getCustomerDemand(to);
            if ((capacity_temp + customer_demand) <= MAX_CAPACITY && energy_temp + get_energy_consumption(from, to) <= BATTERY_CAPACITY) {
                capacity_temp += customer_demand;
                energy_temp += get_energy_consumption(from, to);
                best_sol.setTourAtIndex(to, best_sol.getSteps());
                best_sol.incrementSteps();
                ;
                i++;
            } else if ((capacity_temp + customer_demand) > MAX_CAPACITY) {
                capacity_temp = 0.0;
                energy_temp = 0.0;
                best_sol.setTourAtIndex(DEPOT, best_sol.getSteps());
                best_sol.incrementSteps();
            } else if (energy_temp + get_energy_consumption(from, to) > BATTERY_CAPACITY) {
                charging_station = RandomNumbers.nextNumber() % (ACTUAL_PROBLEM_SIZE - NUM_OF_CUSTOMERS - 1) + NUM_OF_CUSTOMERS + 1;
                if (is_charging_station(charging_station)) {
                    energy_temp = 0.0;
                    best_sol.setTourAtIndex(charging_station, best_sol.getSteps());
                    best_sol.incrementSteps();
                }
            } else {
                capacity_temp = 0.0;
                energy_temp = 0.0;
                best_sol.setTourAtIndex(DEPOT, best_sol.getSteps());
                best_sol.incrementSteps();
            }
        }

        //close EVRP tour to return back to the depot
        if (best_sol.getTourAt(best_sol.getSteps() - 1) != DEPOT) {
            best_sol.setTourAtIndex(DEPOT, best_sol.getSteps());
            best_sol.incrementSteps();
        }

    }

    private static double getCustomerDemand(int to) {
        return 0d; // TODO
    }
    /*implement your heuristic in this function*/

    /*implement your heuristic in this function*/
}
