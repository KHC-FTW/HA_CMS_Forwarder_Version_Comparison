package ha.crc2Jasper.forwarderVerComparison;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventController {
    private EventController() {}

    private static boolean runOnce = true;

    public static void kickStartFirstDataQuery(){
        APIService.getSrcForwarderData_V2();
        DataUtils.initializeFuncWithDiffVerMapForAllHosp();
    }


    // executes every second
    @Scheduled(cron = "*/20 * * * * *")
    public static void testScheduler(){
        DebugUtils.print("Inside testScheduler()");
        System.out.println(runOnce);
        if(runOnce){
            runOnce = false;
            DebugUtils.print("Scheduler is running...");
            APIService.getSrcForwarderData_V2();
            DataUtils.initializeFuncWithDiffVerMapForAllHosp();
        }
    }
}
