package control;


import structures.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static control.EVRP.*;

public class Heuristic {

    public Heuristic() {
    }
    public static void initializeHeuristic() {
        bestSol = new Solution();
        bestSol.setTour(new int[INSTANCE.getActuelProblemSize() - 1 + 1000]);
        bestSol.setId(1);
        bestSol.setSteps(0);
        bestSol.settourLength(Integer.MAX_VALUE);

    }
    public static void runHeuristic() {
        /*generate a random solution for the random heuristic*/
        int help, help2;
        int object;
        int TotAssigned = 0;

        double energyTemp = 0.0;
        double capacityTemp = 0.0;
        int from;
        int to;
        List<Integer> r = new ArrayList<>();
        //set indexes of objects
        for (int i = 1; i <= numOfCustomers; i++) {
            r.add(i);
        }
  //      Collections.shuffle(r);
       for(int i = 0; i < numOfCustomers; i++){
            object = (int) ((Math.random()) * (double)(numOfCustomers-TotAssigned));
            help = r.get(i);
            help2 = r.get(i + object);
            r.set(i,help2);
            r.set(i+object,help);
            TotAssigned++;
        }

        bestSol.setSteps(0);
        bestSol.settourLength(Integer.MAX_VALUE);
        bestSol.setTourAtIndex(depot, 0);
        bestSol.incrementSteps();

        int i = 0;
        while (i < numOfCustomers) {
            from = bestSol.getTourAt(bestSol.getSteps() - 1);
            to = r.get(i);

            double customer_demand = getCustomerDemand(to);
            if ((capacityTemp + customer_demand) <= maxCapacity
                    && energyTemp + getEnergyConsumption(from, to) <= batteryCapacity) {
                capacityTemp += customer_demand;
                energyTemp += getEnergyConsumption(from, to);
                bestSol.setTourAtIndex(to, bestSol.getSteps());
                bestSol.incrementSteps();

                i++;
            } else if ((capacityTemp + customer_demand) > maxCapacity) {
                capacityTemp = 0.0;
                energyTemp = 0.0;
                bestSol.setTourAtIndex(depot, bestSol.getSteps());
                bestSol.incrementSteps();
            } else if (energyTemp + getEnergyConsumption(from, to) > batteryCapacity) {
                int chargingStation = (int) (Math.random() % (INSTANCE.getActuelProblemSize() - numOfCustomers - 1) + numOfCustomers + 1);
                if (isChargingStation(chargingStation)) {
                    energyTemp = 0.0;
                    bestSol.setTourAtIndex(chargingStation, bestSol.getSteps());
                    bestSol.incrementSteps();
                }
            } else {
                capacityTemp = 0.0;
                energyTemp = 0.0;
                bestSol.setTourAtIndex(depot, bestSol.getSteps());
                bestSol.incrementSteps();
            }
        }

        //close EVRP tour to return back to the depot
        if (bestSol.getTourAt(bestSol.getSteps() - 1) != depot) {
            bestSol.setTourAtIndex(depot, bestSol.getSteps());
            bestSol.incrementSteps();
        }

    }

    public static Integer getCustomerDemand(int to) {
        return INSTANCE.getDemandSection().get(to-1).getDemande();
    }

}
