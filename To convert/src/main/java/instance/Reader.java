package instance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {
    public static Instance readERVPFile(String path) throws IOException {
        Instance instance = new Instance();
        final File file = new File(path);
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            Map<String, Object> map = new LinkedHashMap<>();
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.contains(":")) {
                    final StringTokenizer stringTokenizer = new StringTokenizer(line, ":");
                    final String label = stringTokenizer.nextToken().trim();
                    final String value = stringTokenizer.nextToken().trim();
                    map.put(label, value);
                } else if ("NODE_COORD_SECTION".equals(line.trim())) {
                    List<NodeCoordSection> coordSections = new ArrayList<>();
                    String instanceCoord = scanner.nextLine().trim();
                    if (Character.isDigit(instanceCoord.charAt(0))) {
                        StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                        NodeCoordSection insCoordSection = new NodeCoordSection();
                        insCoordSection.setId((Integer) stringTokenizer.nextElement());
                        insCoordSection.setX((Integer) stringTokenizer.nextElement());
                        insCoordSection.setY((Integer) stringTokenizer.nextElement());
                        coordSections.add(insCoordSection);
                    }
                }
                    else if ("DEMAND_SECTION".equals(line.trim())) {
                 List<DemandSection>  demandSections = new ArrayList<>();
                 String instanceDemand = scanner.nextLine().trim();
                 if(Character.isDigit(instanceDemand.charAt(0))){
                     StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                     DemandSection insDemandSection = new DemandSection();
                     insDemandSection.setNodeId((Integer) stringTokenizer.nextElement());
                     insDemandSection.setDemande((Integer) stringTokenizer.nextElement());
                     demandSections.add(insDemandSection);
                }
            }
                    else if ("STATIONS_COORD_SECTION".equals(line.trim())) {
                      List<StationsCoordSection>  stationSections = new ArrayList<>();
                      String instanceStations = scanner.nextLine().trim();
                    if(Character.isDigit(instanceStations.charAt(0))){
                        StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
                        StationsCoordSection insStationSection = new StationsCoordSection();
                        insStationSection.setStationId((Integer) stringTokenizer.nextElement());
                        stationSections.add(insStationSection);
                    }
                }
            }

        }

    }
}
