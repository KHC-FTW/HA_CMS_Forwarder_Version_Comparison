package ha.crc2Jasper.forwarderVerComparison.obsoleted;

import org.springframework.stereotype.Component;

@Component
public class EventController {
    /* Obsoleted class */
    /*private EventController() {}

    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    @Getter
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = LOCK.readLock();
    @Getter
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = LOCK.writeLock();

    private static boolean runOnce = true;*/

/*    public static void kickStartFirstDataQuery(){
        APIService.getSrcForwarderData_V2();
        DataUtils.updateFuncWithDiffVerMapForAllHosp();
    }*/

//    @Scheduled(cron = "0 0 */1 * * *")
/*    public static void periodicDataQueryAndUpdate() {
        try{
            WRITE_LOCK.lock();
            Database.getInstance().resetData();
            APIService.getSrcForwarderData_V2();
            DataUtils.updateFuncWithDiffVerMapForAllHosp();
        }finally {
            WRITE_LOCK.unlock();
        }
    }*/


//    @Scheduled(cron = "0 * * * * *")
/*    public static void testCronSchedule(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current time: " + now);
        DebugUtils.print("Print every minute.");
    }*/
}
