package control;

import instance.Instance;
import instance.Reader;
import structures.Node;
import structures.Solution;

import static java.lang.Math.sqrt;

public class EVRP {
    public static final int CHAR_LEN = 100;
    public static final int MAX_TRIALS = 20;
    public static int DEPOT;                       //depot id (usually 0)
    public static int NUM_OF_CUSTOMERS;       //Number of customers (excluding depot)
    public static int ACTUAL_PROBLEM_SIZE;        //Tottal number of customers, charging stations and depot
    public static double OPTIMUM;
    public static int NUM_OF_STATIONS;
    public static int BATTERY_CAPACITY;       //maximum energy of vehicles
    public static int MAX_CAPACITY;           //capacity of vehicles
    public static String problem_instance;          //Name of the instance
    public static Node[] node_list;     //List of nodes with id and x and y coordinates
    public static int cust_demand;                //List with id and customer demands
    public static double[][] distances;              //Distance matrix
    public static int problem_size;                //Problem dimension read
    public static boolean charging_station;
    public static double energy_consumption;
    public static double evals;
    public static double current_best;
    public static int dimensions;
    public static int numberOfStations;


/****************************************************************/
    /*Compute and return the euclidean distance of two objects      */

    /****************************************************************/
     /* utilisation de la classe Reader
     Reader rd = new Reader();
     */
        public static void read_problem (String filename) throws Exception{
            Instance instance = Reader.readERVPFile("C:\\Users\\MAU5Y\\Desktop\\EvrpApp\\evrpapp\\Original Cpp\\E-n22-k4.evrp");
            problem_size = instance.getDimension();
            distances = generate_2D_matrix_double(ACTUAL_PROBLEM_SIZE, ACTUAL_PROBLEM_SIZE);

    }

    /****************************************************************/
    /*Compute the distance matrix of the problem instance           */

    /****************************************************************/
    public static double fitness_evaluation(int[] routes, int size) {
        int i;
        double tour_length = 0.0;

        //the format of the solution that this method evaluates is the following
        //Node id:  0 - 5 - 6 - 8 - 0 - 1 - 2 - 3 - 4 - 0 - 7 - 0
        //Route id: 1 - 1 - 1 - 1 - 2 - 2 - 2 - 2 - 2 - 3 - 3 - 3
        //this solution consists of three routes:
        //Route 1: 0 - 5 - 6 - 8 - 0
        //Route 2: 0 - 1 - 2 - 3 - 4 - 0
        //Route 3: 0 - 7 - 0
        for (i = 0; i < size - 1; i++) {
            tour_length += distances[routes[i]][routes[i + 1]];
        }

        if (tour_length < current_best) {
            current_best = tour_length;
        }

        //adds complete evaluation to the overall fitness evaluation count
        evals++;

        return tour_length;
    }


/****************************************************************/
    /*Generate and return a two-dimension array of type double      */

    /****************************************************************/

    public static void print_solution(int[] routes, int size) {
        int i;

        for (i = 0; i < size; i++) {
            System.out.print(routes[i]);
            System.out.print(" , ");
        }
    }


/****************************************************************/
    /* Read the problem instance and generate the initial object    */
    /* vector.                                                      */

    /****************************************************************/

    public static void check_solution(int[] t, int size) {
        int i;
        int from;
        int to;
        double energy_temp = BATTERY_CAPACITY;
        double capacity_temp = MAX_CAPACITY;
        double distance_temp = 0.0;

        for (i = 0; i < size - 1; i++) {
            from = t[i];
            to = t[i + 1];
            capacity_temp -= get_customer_demand(to);
            energy_temp -= get_energy_consumption(from, to);
            distance_temp += get_distance(from, to);
            if (capacity_temp < 0.0) {
                System.out.print("error: capacity below 0 at customer ");
                System.out.print(to);
                System.out.print("\n");
                print_solution(t, size);
                System.exit(1);
            }
            if (energy_temp < 0.0) {
                System.out.print("error: energy below 0 from ");
                System.out.print(from);
                System.out.print(" to ");
                System.out.print(to);
                System.out.print("\n");
                print_solution(t, size);
                System.exit(1);
            }
            if (to == DEPOT) {
                capacity_temp = MAX_CAPACITY;
            }
            if (is_charging_station(to) == true || to == DEPOT) {
                energy_temp = BATTERY_CAPACITY;
            }
        }
        if (distance_temp != fitness_evaluation(t, size)) {
            System.out.print("error: check fitness evaluation");
            System.out.print("\n");
        }
    }

    private static double get_customer_demand(int to) {
        return 0d;
    }

    public static Solution initialize_heuristic() {

        Solution best_sol = new Solution();
        best_sol.setTour(new int[NUM_OF_CUSTOMERS + 1000]);
        best_sol.setId(1);
        best_sol.setSteps(0);
        best_sol.setTour_length(Integer.MAX_VALUE);
        return best_sol;
    }

    static double get_energy_consumption(int from, int to) {
        return 0;
    }

    static boolean is_charging_station(int charging_station) {
        return false;
    }

    /****************************************************************/
    /* Returns the solution quality of the solution. Taken as input */
    /* an array of node indeces and its length                      */

    /****************************************************************/
    public static double euclidean_distance(int i, int j) {
        double xd, yd;
        double r = 0.0;
        xd = node_list[i].getX() - node_list[j].getX();
        yd = node_list[i].getY() - node_list[j].getY();
        r = sqrt(xd * xd + yd * yd);
        return r;
    }
    /****************************************************************/
    /* Outputs the routes of the solution. Taken as input           */
    /* an array of node indeces and its length                      */

    /****************************************************************/
    public static void compute_distances() {
        int i, j;
        for (i = 0; i < ACTUAL_PROBLEM_SIZE; i++) {
            for (j = 0; j < ACTUAL_PROBLEM_SIZE; j++) {
                distances[i][j] = euclidean_distance(i, j);
            }
        }
    }

/****************************************************************/
    /* Validates the routes of the solution. Taken as input         */
    /* an array of node indecs and its length                      */

    /****************************************************************/
    public static double[][] generate_2D_matrix_double(int n, int m) {
        double[][] matrix;

        matrix = new double[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new double[m];
        }
        //initialize the 2-d array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = 0.0;
            }
        }
        return matrix;
    }
/****************************************************************/
    /* Returns the distance between two points: from and to. Can be */
    /* used to evaluate a part of the solution. The fitness         */
    /* evaluation count will be proportional to the problem size    */

    /****************************************************************/
    public static double get_distance(int from, int to) {
        //adds partial evaluation to the overall fitness evaluation count
        //It can be used when local search is used and a whole evaluation is not necessary
        evals += (1.0 / ACTUAL_PROBLEM_SIZE);

        return distances[from][to];

    }
}
