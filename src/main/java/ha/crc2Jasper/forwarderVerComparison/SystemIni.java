package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.ObjectMapper;
import ha.crc2Jasper.forwarderVerComparison.component.Cluster;
import ha.crc2Jasper.forwarderVerComparison.component.SetupConfig;

import java.io.File;
import java.util.Scanner;

public class SystemIni {
    private SystemIni(){}
    private static final String JSON_CONFIG_NAME = "SetupConfig.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static SetupConfig SETUP_CONFIG = SetupConfig.getInstance();

    public static ObjectMapper getObjectMapper(){return SystemIni.OBJECT_MAPPER;}

    public static void readJsonConfigFile(String[] args){
        File jsonFile;
        String jsonPath;
        if (args.length < 1 || !(jsonFile = new File(args[0] + "\\" + JSON_CONFIG_NAME)).isFile()){
            Scanner getInput = new Scanner(System.in);
            do{
                System.out.print("\nNo valid json file identified for set up. Please enter the json file path again.\n(Make sure the json file is named \"" + JSON_CONFIG_NAME + "\". Enter \"q\" or \"Q\" to exit the system.)\n> ");
                jsonPath = getInput.nextLine();
                if(quitCheck(jsonPath)) System.exit(0);
                jsonFile = new File(jsonPath + "\\" + JSON_CONFIG_NAME);
            }while(!jsonFile.isFile());
        }else jsonPath = args[0];

        SETUP_CONFIG.setConfigInputPath(jsonPath);


        try {
            // Read JSON file and convert to JsonNode
            SetupConfig setupConfig = OBJECT_MAPPER.readValue(jsonFile, SetupConfig.class);
            SetupConfig.setInstance(setupConfig);
            SetupConfig.getInstance().setValidClusters(createValidClustersList(setupConfig));
            System.out.println("\nJson data saved with the following:\n");
            System.out.println(setupConfig);

        } catch (Exception e) {
            ExceptionService.printException(e);
        }
    }

    private static boolean quitCheck(String input){
        return input.equalsIgnoreCase("q");
    }

    private static String createValidClustersList(SetupConfig setupConfig){
        return String.join(", ", setupConfig.getAllClusters()
                .stream().map(Cluster::getCluster).toList());
    }
}
