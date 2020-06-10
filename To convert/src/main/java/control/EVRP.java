package control;

import instance.Instance;
import instance.NodeCoordSection;
import instance.Reader;
import structures.Node;
import structures.Solution;

import java.util.List;

import static java.lang.Math.sqrt;

public class EVRP {
    public static int depot;                       //depot id (usually 0)
    public static int numOfCustomers;       //Number of customers (excluding depot)
    //public static int INSTANCE.getActuelProblemSize();        //Tottal number of customers, charging stations and depot
    public static int NUM_OF_STATIONS;
    public static int batteryCapacity;       //maximum energy of vehicles
    public static int maxCapacity;           //capacity of vehicles
    public static List<NodeCoordSection> NodeList;     //List of nodes with id and x and y coordinates
    public static int problemSize;                //Problem dimension read
    public static boolean[] chargingStationFlags;
    public static double energy_consumption;
    public static double OPTIMUM;

    public static final int MAX_TRIALS = 20;
    public static String problemInstance;          //Name of the instance
    public static double currentBest = Double.MAX_VALUE;
    public static double[][] distances;              //Distance matrix
    public static Solution bestSol;
    public static double evals;
    public static Instance INSTANCE;
/****************************************************************/
    /*Compute and return the euclidean distance of two objects      */

    /****************************************************************/
     /* utilisation de la classe Reader
     Reader rd = new Reader();
     */
    public static void readProblem() throws Exception {
        INSTANCE = Reader.readERVPFile(problemInstance);
        distances = generate2dMatrixDouble(INSTANCE.getActuelProblemSize(), INSTANCE.getActuelProblemSize());
        OPTIMUM = INSTANCE.getOptimalValue();
        depot = INSTANCE.getDepotSection().getIdDepot();
        numOfCustomers = INSTANCE.getActuelProblemSize() - INSTANCE.getStations();
        NUM_OF_STATIONS = INSTANCE.getStations();
        batteryCapacity = INSTANCE.getEnergyCapacity();
        maxCapacity = INSTANCE.getCapacity();
        problemSize = INSTANCE.getActuelProblemSize();
        chargingStationFlags = new boolean[problemSize];
        energy_consumption = INSTANCE.getEnergyConsumption();
        NodeList = INSTANCE.getNodeCoordSection();
        computeDistances();
    }

    /****************************************************************/
    /*Compute the distance matrix of the problem instance           */

    /****************************************************************/
    public static double fitnessEvaluation(int[] routes, int size) {
        int i;
        double tourLength = 0.0;

        //the format of the solution that this method evaluates is the following
        //        //Node id:  0 - 5 - 6 - 8 - 0 - 1 - 2 - 3 - 4 - 0 - 7 - 0
        //        //Route id: 1 - 1 - 1 - 1 - 2 - 2 - 2 - 2 - 2 - 3 - 3 - 3
        //        //this solution consists of three routes:
        //        //Route 1: 0 - 5 - 6 - 8 - 0
        //        //Route 2: 0 - 1 - 2 - 3 - 4 - 0
        //        //Route 3: 0 - 7 - 0
        for (i = 0; i < size - 1; i++) {
            tourLength += distances[routes[i]][routes[i + 1]];
        }

        if (tourLength < currentBest) {
            currentBest = tourLength;
        }

        //adds complete evaluation to the overall fitness evaluation count
        evals++;

        return tourLength;
    }


/****************************************************************/
    /*Generate and return a two-dimension array of type double      */

    /****************************************************************/

    public static void printSolution(int[] routes, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(routes[i]);
            System.out.print(" , ");
        }
    }


/****************************************************************/
    /* Read the problem instance and generate the initial object    */
    /* vector.                                                      */

    /****************************************************************/

    public static void checkSolution(int[] t, int size) {
        int i;
        int from;
        int to;
        double energyTemp = batteryCapacity;
        double capacityTemp = maxCapacity;
        double distanceTemp = 0.0;

        for (i = 0; i < size - 1; i++) {
            from = t[i];
            to = t[i + 1];
            capacityTemp -= getCustomerDemand(to);
            energyTemp -= getEnergyConsumption(from, to);
            distanceTemp += getDistance(from, to);
            if (capacityTemp < 0.0) {
                System.out.print("error: capacity below 0 at customer ");
                System.out.print(to);
                System.out.print("\n");
                printSolution(t, size);
                System.exit(1);
            }
            if (energyTemp < 0.0) {
                System.out.print("error: energy below 0 from ");
                System.out.print(from);
                System.out.print(" to ");
                System.out.print(to);
                System.out.print("\n");
                printSolution(t, size);
                System.exit(1);
            }
            if (to == depot) {
                capacityTemp = maxCapacity;
            }
            if (isChargingStation(to) || to == depot) {
                energyTemp = batteryCapacity;
            }
        }
        if (distanceTemp != fitnessEvaluation(t, size)) {
            System.out.print("error: check fitness evaluation");
            System.out.print("\n");
        }
    }

    private static double getCustomerDemand(int to) {
        return 0;
    }

    public static void initializeHeuristic() {
        bestSol = new Solution();
        bestSol.setTour(new int[INSTANCE.getActuelProblemSize() - 1 + 1000]);
        bestSol.setId(1);
        bestSol.setSteps(0);
        bestSol.settourLength(Integer.MAX_VALUE);

    }

    static double getEnergyConsumption(int from, int to) {
        return energy_consumption * distances[from][to];
    }

    static boolean isChargingStation(int nde) {
        return nde >= 20;
    }

    /****************************************************************/
    /* Returns the solution quality of the solution. Taken as input */
    /* an array of node indeces and its length                      */

    /****************************************************************/
    public static double euclideanDistance(int i, int j) {
        final NodeCoordSection nodeCoordSectionAti = NodeList.get(i);
        final NodeCoordSection nodeCoordSectionAtj = NodeList.get(j);

        final Integer xd = nodeCoordSectionAti.getX() - nodeCoordSectionAtj.getX();
        final Integer yd = nodeCoordSectionAti.getY() - nodeCoordSectionAtj.getY();
        return sqrt(Math.pow(xd, 2) + Math.pow(yd, 2));
    }
    /****************************************************************/
    /* Outputs the routes of the solution. Taken as input           */
    /* an array of node indeces and its length                      */

    /****************************************************************/
    public static void computeDistances() {
        Integer actuelProblemSize = INSTANCE.getActuelProblemSize();
        for (int i = 0; i < actuelProblemSize; i++) {
            for (int j = 0; j < actuelProblemSize; j++) {
                distances[i][j] = euclideanDistance(i, j);
            }
        }
    }

/****************************************************************/
    /* Validates the routes of the solution. Taken as input         */
    /* an array of node indecs and its length                      */

    /****************************************************************/
    public static double[][] generate2dMatrixDouble(int n, int m) {
        double[][] matrix = new double[n][m];
        return matrix;
    }
/****************************************************************/
    /* Returns the distance between two points: from and to. Can be */
    /* used to evaluate a part of the solution. The fitness         */
    /* evaluation count will be proportional to the problem size    */

    /****************************************************************/
    public static double getDistance(int from, int to) {
        //adds partial evaluation to the overall fitness evaluation count
        //It can be used when local search is used and a whole evaluation is not necessary
        evals += (1.0 / INSTANCE.getActuelProblemSize());

        return distances[from][to];

    }
}
