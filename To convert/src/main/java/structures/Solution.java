package structures;

public class Solution {
    private int[] tour; //this is what the fitness_evaluation function in EVRP.hpp will evaluate
    private int id;
    private double tour_length; //quality of the solution
    private int steps; //size of the solution
    //the format of the solution is as follows:
    //*tour:  0 - 5 - 6 - 8 - 0 - 1 - 2 - 3 - 4 - 0 - 7 - 0
    //*steps: 12
    //this solution consists of three routes:
    //Route 1: 0 - 5 - 6 - 8 - 0
    //Route 2: 0 - 1 - 2 - 3 - 4 - 0
    //Route 3: 0 - 7 - 0

    public Solution() {
    }


    public int[] getTour() {
        return tour;
    }

    public void setTour(int[] tour) {
        this.tour = tour;
    }

    public void setTourAtIndex(int tourValue, int index) {
        tour[index] = tourValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTour_length() {
        return tour_length;
    }

    public void setTour_length(double tour_length) {
        this.tour_length = tour_length;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void incrementSteps() {
        steps++;
    }

    public int getTourAt(int i) {
        return tour[i];
    }
}
