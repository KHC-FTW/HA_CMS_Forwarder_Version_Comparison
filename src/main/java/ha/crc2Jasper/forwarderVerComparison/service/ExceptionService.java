package ha.crc2Jasper.forwarderVerComparison.service;

public class ExceptionService {
    private ExceptionService(){}

    public static void printException(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
}
