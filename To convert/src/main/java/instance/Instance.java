package instance;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Instance {

    private String problemInstanceFileName;

    private String name;

    private String comment;

    private String type;

    private Double optimalValue;

    private Integer vehicles;

    private Integer dimension;

    private Integer stations;

    private Integer capacity;

    private Integer energyCapacity;

    private Double energyConsumption;

    private String edgeWeightFormat;

    private List<NodeCoordSection> nodeCoordSection = new ArrayList<>();

    private List<DemandSection> demandSection = new ArrayList<>();

    private List<StationsCoordSection> stationsCoordSection = new ArrayList<>();

    private DepotSection depotSection;

    public Instance() {
    }

    public Integer getActuelProblemSize(){
        return getDimension() + getStations();
    }
}
