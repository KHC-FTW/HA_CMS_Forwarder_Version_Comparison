package ha.crc2Jasper.forwarderVerComparison;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SystemIni {
    private SystemIni(){}
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper(){return SystemIni.objectMapper;}
}
