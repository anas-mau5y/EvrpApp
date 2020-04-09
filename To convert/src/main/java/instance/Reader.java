package instance;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import static java.lang.Double.valueOf;

public class Reader {

    public Reader() {
    }

    public static Instance readERVPFile(String path) throws IOException {
        Instance instance = new Instance();
        final File file = new File(path);
        Map<String, Object> map = new LinkedHashMap<>();

        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.contains(":")) {
                    final StringTokenizer stringTokenizer = new StringTokenizer(line, ":");
                    final String label = stringTokenizer.nextToken().trim();
                    Object value = stringTokenizer.nextToken().trim();
                    if (isNumber(String.valueOf(value))) {
                        value = valueOf(String.valueOf(value));
                    }

                    map.put(label, value);
                } else if ("NODE_COORD_SECTION".equals(line.trim())) {
                    List<NodeCoordSection> coordSections = instance.getNodeCoordSection();
                    String instanceCoord = scanner.nextLine().trim();
                    if (Character.isDigit(instanceCoord.charAt(0))) {
                        NodeCoordSection insCoordSection = getNodeCoordSection(instanceCoord);
                        coordSections.add(insCoordSection);
                    }
                } else if ("DEMAND_SECTION".equals(line.trim())) {
                    List<DemandSection> demandSections = instance.getDemandSection();
                    String instanceDemand = scanner.nextLine().trim();
                    if (Character.isDigit(instanceDemand.charAt(0))) {
                        DemandSection insDemandSection = getDemandSection(instanceDemand);
                        demandSections.add(insDemandSection);
                    }
                } else if ("STATIONS_COORD_SECTION".equals(line.trim())) {
                    List<StationsCoordSection> stationSections = instance.getStationsCoordSection();
                    String instanceStations = scanner.nextLine().trim();
                    if (Character.isDigit(instanceStations.charAt(0))) {
                        StationsCoordSection insStationSection = getStationsCoordSection(instanceStations);
                        stationSections.add(insStationSection);
                    }
                } else if ("DEOPT_SECTION".equals(line.trim())) {

                    String instanceDepot = scanner.nextLine().trim();
                    if (Character.isDigit(instanceDepot.charAt(0))) {
                        DepotSection depotSingleSection = getDepotSection(instanceDepot);
                        instance.setDepotSection(depotSingleSection);
                    }
                }
            }
        }


        instance.setName((String) map.get("Name"));
        instance.setComment((String) map.get("COMMENT"));
        instance.setType((String) map.get("TYPE"));

        instance.setOptimalValue((Double) map.get("OPTIMAL_VALUE"));

        instance.setVehicles(((Double) map.get("VEHICLES")).intValue());
        instance.setDimension(((Double) map.get("DIMENSION")).intValue());
        instance.setDimension(((Double) map.get("STATIONS")).intValue());
        instance.setDimension(((Double) map.get("CAPACITY")).intValue());
        instance.setDimension(((Double) map.get("ENERGY_CAPACITY")).intValue());

        instance.setEnergyConsumption(((Double) map.get("ENERGY_CONSUMPTION")));

        instance.setEdgeWeightFormat(((String) map.get("EDGE_WEIGHT_FORMAT")));

        return instance;
    }

    private static DepotSection getDepotSection(String instanceDepot) {
        Scanner scannerInterne = new Scanner(instanceDepot);
        scannerInterne.useDelimiter("\n");
        DepotSection insDepotSection = new DepotSection();
        insDepotSection.setIdDepot(scannerInterne.nextInt());
        return insDepotSection;
    }


    private static DemandSection getDemandSection(String instanceDemand) {
        Scanner scannerInterne = new Scanner(instanceDemand);
        scannerInterne.useDelimiter(" ");
        DemandSection insDemandSection = new DemandSection();
        insDemandSection.setNodeId(scannerInterne.nextInt());
        insDemandSection.setDemande(scannerInterne.nextInt());
        return insDemandSection;
    }

    private static StationsCoordSection getStationsCoordSection(String instanceStations) {
        Scanner scannerInterne = new Scanner(instanceStations);
        scannerInterne.useDelimiter(" ");
        StationsCoordSection insStationSection = new StationsCoordSection();
        insStationSection.setStationId(scannerInterne.nextInt());
        return insStationSection;
    }

    private static NodeCoordSection getNodeCoordSection(String instanceCoord) {
        final Scanner scannerInterne = new Scanner(instanceCoord);
        scannerInterne.useDelimiter(" ");
        NodeCoordSection insCoordSection = new NodeCoordSection();
        insCoordSection.setId(scannerInterne.nextInt());
        insCoordSection.setX(scannerInterne.nextInt());
        insCoordSection.setY(scannerInterne.nextInt());
        return insCoordSection;
    }

    public static void main(String[] args) throws Exception {
        Instance instance = readERVPFile("C:\\Users\\MAU5Y\\Desktop\\EvrpApp\\evrpapp\\Original Cpp\\E-n22-k4.evrp");
        System.out.println(instance);
    }

    public static boolean isNumber(String input) {
        if (input.contains(".")) {
            input = input.replace(".", "");
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
