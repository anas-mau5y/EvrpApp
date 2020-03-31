package control;
import structures.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.Math.sqrt;

public class EVRP {
    public static final int CHAR_LEN = 100;
    public static final int MAX_TRIALS = 20;



   private String problem_instance;          //Name of the instance
    private Node[] node_list;     //List of nodes with id and x and y coordinates
    private int cust_demand;                //List with id and customer demands
    private boolean charging_station;
    private double[][] distances;              //Distance matrix
    private int problem_size;                //Problem dimension read
    private double energy_consumption;

    private int DEPOT;                       //depot id (usually 0)
    private int NUM_OF_CUSTOMERS;       //Number of customers (excluding depot)
    private int ACTUAL_PROBLEM_SIZE;        //Tottal number of customers, charging stations and depot
    private double OPTIMUM;
    private int NUM_OF_STATIONS;
    private int BATTERY_CAPACITY;       //maximum energy of vehicles
    private  int MAX_CAPACITY;           //capacity of vehicles



    private double evals;
    private  double current_best;


/****************************************************************/
    /*Compute and return the euclidean distance of two objects      */
    /****************************************************************/
   double euclidean_distance(int i, int j) {
        double xd,yd;
        double r = 0.0;
        xd = node_list[i].getX() - node_list[j].getX();
        yd = node_list[i].getY() - node_list[j].getY();
        r  = sqrt(xd*xd + yd*yd);
        return r;
    }

/****************************************************************/
    /*Compute the distance matrix of the problem instance           */
    /****************************************************************/
    void compute_distances(void) {
        int i, j;
        for(i = 0; i < ACTUAL_PROBLEM_SIZE; i++){
            for(j = 0; j < ACTUAL_PROBLEM_SIZE; j++){
                distances[i][j] = euclidean_distance(i,j);
            }
        }
    }


/****************************************************************/
    /*Generate and return a two-dimension array of type double      */
/****************************************************************/
    public double[][] generate_2D_matrix_double(int n, int m){
        double[][] matrix;

        matrix = new double[n][];
        for ( int i = 0 ; i < n ; i++ ) {
            matrix[i] = new double[m];
        }
        //initialize the 2-d array
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++) {
                matrix[i][j] = 0.0;
            }
        }
        return matrix;
    }


/****************************************************************/
    /* Read the problem instance and generate the initial object    */
    /* vector.                                                      */
    /****************************************************************/
    public static void read_problem(structures.RefObject<String> filename)
    {
        int i;
        String line = new String(new String[CHAR_LEN]);
        String keywords;
        final String delimiters = new String(" :=\n\t\r\f\v");
        ifstream fin = new ifstream(filename.argValue);
        //InputStream fin = new FileInputStream(filename);
        //Scanner scanFile = new Scanner(fin);
        //while(scanFile.hasNext()) {
        //var = scanFile.next();
       // var2 = scanFile.nextInt();
     //   }
    }
        while ((fin.getline(line, CHAR_LEN - 1)))
        {

            if(!(keywords = structures.StringFunctions.strTok(line, delimiters)))
            {
                continue;
            }
            if (!strcmp(keywords, "DIMENSION"))
            {
                if (!sscanf(tangible.StringFunctions.strTok(null, Delimiters), "%d", problem_size))
                {
                    System.out.print("DIMENSION error");
                    System.out.print("\n");
                    System.exit(0);
                }
            }
            else if (!strcmp(keywords, "EDGE_WEIGHT_TYPE"))
            {
                String tempChar;
                if (!(tempChar = tangible.StringFunctions.strTok(null, Delimiters)))
                {
                    System.out.print("EDGE_WEIGHT_TYPE error");
                    System.out.print("\n");
                    System.exit(0);
                }
                if (strcmp(tempChar, "EUC_2D"))
                {
                    System.out.print("not EUC_2D");
                    System.out.print("\n");
                    System.exit(0);
                }
            }
            else if (!strcmp(keywords, "CAPACITY"))
            {
                if (!sscanf(tangible.StringFunctions.strTok(null,Delimiters), "%d", MAX_CAPACITY))
                {
                    System.out.print("CAPACITY error");
                    System.out.print("\n");
                    System.exit(0);
                }
            }
            else if (!strcmp(keywords, "ENERGY_CAPACITY"))
            {
                if (!sscanf(tangible.StringFunctions.strTok(null,Delimiters), "%d", BATTERY_CAPACITY))
                {
                    System.out.print("ENERGY_CAPACITY error");
                    System.out.print("\n");
                    System.exit(0);
                }
            }
            else if (!strcmp(keywords, "ENERGY_CONSUMPTION"))
            {
                if (!sscanf(structures.StringFunctions.strTok(null,Delimiters), "%lf", energy_consumption))
                {
                    System.out.print("ENERGY_CONSUMPTION error");
                    System.out.print("\n");
                    System.exit(0);
                }
            }
            else if (!strcmp(keywords, "STATIONS"))
            {
                if (!sscanf(tangible.StringFunctions.strTok(null,Delimiters), "%d", NUM_OF_STATIONS)) {
                    System.out.print("STATIONS error");
                }


            }
     }