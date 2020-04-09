package instance;

import java.util.ArrayList;
import java.util.List;

public class Instance {
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getOptimalValue() {
        return optimalValue;
    }

    public void setOptimalValue(Double optimalValue) {
        this.optimalValue = optimalValue;
    }

    public Integer getVehicles() {
        return vehicles;
    }

    public void setVehicles(Integer vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getDimension() {
        return dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    public Integer getStations() {
        return stations;
    }

    public void setStations(Integer stations) {
        this.stations = stations;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEnergyCapacity() {
        return energyCapacity;
    }

    public void setEnergyCapacity(Integer energyCapacity) {
        this.energyCapacity = energyCapacity;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public String getEdgeWeightFormat() {
        return edgeWeightFormat;
    }

    public void setEdgeWeightFormat(String edgeWeightFormat) {
        this.edgeWeightFormat = edgeWeightFormat;
    }

    public List<NodeCoordSection> getNodeCoordSection() {
        return nodeCoordSection;
    }

    public void setNodeCoordSection(List<NodeCoordSection> nodeCoordSection) {
        this.nodeCoordSection = nodeCoordSection;
    }

    public List<DemandSection> getDemandSection() {
        return demandSection;
    }

    public void setDemandSection(List<DemandSection> demandSection) {
        this.demandSection = demandSection;
    }

    public List<StationsCoordSection> getStationsCoordSection() {
        return stationsCoordSection;
    }

    public void setStationsCoordSection(List<StationsCoordSection> stationsCoordSection) {
        this.stationsCoordSection = stationsCoordSection;
    }

    public DepotSection getDepotSection() {
        return depotSection;
    }

    public void setDepotSection(DepotSection depotSection) {
        this.depotSection = depotSection;
    }
}
