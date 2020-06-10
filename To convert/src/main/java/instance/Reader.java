//La classe qui lit le fichier et recupere les donnees sous forme d'une instance (Parser)

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

    private static final String COORD_SECTION = "NODE_COORD_SECTION";
    private static final String DEMAND_SECTION = "DEMAND_SECTION";
    private static final String STATIONS_COORD_SECTION = "STATIONS_COORD_SECTION";
    private static final String DEPOT_SECTION = "DEPOT_SECTION";
    private static final String EOF = "EOF";
    private static final String COMMENT = "COMMENT";
    private static final String TYPE = "TYPE";
    private static final String OPTIMAL_VALUE = "OPTIMAL_VALUE";
    private static final String VEHICLES = "VEHICLES";
    private static final String DIMENSION = "DIMENSION";
    private static final String STATIONS = "STATIONS";
    private static final String CAPACITY = "CAPACITY";
    private static final String ENERGY_CAPACITY = "ENERGY_CAPACITY";
    private static final String ENERGY_CONSUMPTION = "ENERGY_CONSUMPTION";
    private static final String EDGE_WEIGHT_FORMAT = "EDGE_WEIGHT_FORMAT";
    private static final String NAME = "Name";

    public static Instance readERVPFile(String path) throws IOException {
        final Instance instance = new Instance();
        final File file = new File(path);

        final Map<String, Object> map = new LinkedHashMap<>();
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            int counOfFirsLines = 0;
            while (counOfFirsLines < 11 && scanner.hasNext()) {
                final String line = scanner.nextLine();
                if (line.contains(":")) {
                    counOfFirsLines++;
                    readFirstSections(map, line);
                } else break;
            }

            fulfillInstance(instance, map);

            whileLoop:
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                String trimmedLine = line.trim();
                switch (trimmedLine) {
                    case COORD_SECTION:
                        readNodeCoordSection(instance, scanner);
                        break;
                    case DEMAND_SECTION:
                        readDemandSection(instance, scanner);
                        break;
                    case STATIONS_COORD_SECTION:
                        readStationsCoordSection(instance, scanner);
                        break;
                    case DEPOT_SECTION:
                        readDepotSection(instance, scanner);
                        break;
                    case EOF:
                        break whileLoop;
                }
            }
        } else {
            System.err.println("File doesn't exist");
            System.exit(2);
        }

        return instance;
    }

    private static void fulfillInstance(Instance instance, Map<String, Object> map) {
        instance.setName((String) map.get(NAME));
        instance.setComment((String) map.get(COMMENT));
        instance.setType((String) map.get(TYPE));
        instance.setOptimalValue((Double) map.get(OPTIMAL_VALUE));
        instance.setVehicles(((Double) map.get(VEHICLES)).intValue());
        instance.setDimension(((Double) map.get(DIMENSION)).intValue());
        instance.setStations(((Double) map.get(STATIONS)).intValue());
        instance.setCapacity(((Double) map.get(CAPACITY)).intValue());
        instance.setEnergyCapacity(((Double) map.get(ENERGY_CAPACITY)).intValue());
        instance.setEnergyConsumption(((Double) map.get(ENERGY_CONSUMPTION)));
        instance.setEdgeWeightFormat(((String) map.get(EDGE_WEIGHT_FORMAT)));
    }

    private static void readDepotSection(Instance instance, Scanner scanner) {
        String instanceDepot = scanner.nextLine().trim();
        char ch = instanceDepot.charAt(0);
        if (Character.isDigit(ch)) {
            DepotSection depotSingleSection = getDepotSection(instanceDepot);
            instance.setDepotSection(depotSingleSection);
        }

    }

    private static void readStationsCoordSection(Instance instance, Scanner scanner) {
        List<StationsCoordSection> stationSections = instance.getStationsCoordSection();
        String instanceStations = scanner.nextLine().trim();
        Integer countOfStations = instance.getStations();
        for (int i = 0; i < countOfStations; i++) {
            StationsCoordSection insStationSection = getStationsCoordSection(instanceStations);
            stationSections.add(insStationSection);
        }
    }

    private static void readDemandSection(Instance instance, Scanner scanner) {
        List<DemandSection> demandSections = instance.getDemandSection();
        String instanceDemand = scanner.nextLine().trim();
        int countOfDemand = instance.getDimension();
        for (int i = 0; i < countOfDemand; i++) {
            DemandSection insDemandSection = getDemandSection(instanceDemand);
            demandSections.add(insDemandSection);
        }
    }

    private static void readNodeCoordSection(Instance instance, Scanner scanner) {
        List<NodeCoordSection> coordSections = instance.getNodeCoordSection();
        int count = instance.getDimension() + instance.getStations();
        for (int i = 0; i < count; i++) {
            String instanceCoord = scanner.nextLine().trim();
            NodeCoordSection insCoordSection = getNodeCoordSection(instanceCoord);
            coordSections.add(insCoordSection);
        }
    }

    private static void readFirstSections(Map<String, Object> map, String line) {
        final StringTokenizer stringTokenizer = new StringTokenizer(line, ":");
        final String label = stringTokenizer.nextToken().trim();
        Object value = stringTokenizer.nextToken().trim();
        if (isNumber(String.valueOf(value))) {
            value = valueOf(String.valueOf(value));
        }
        map.put(label, value);
    }

    private static DepotSection getDepotSection(String instanceDepot) {
        Scanner scannerInterne = new Scanner(instanceDepot);
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


    private static boolean isNumber(String input) {
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
