package org.bdlions.utility;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author nazmul hasan
 */
public class DateUtils {
    public DateUtils()
    {
    
    }
    
    /**
     * This method will return current unix time in seconds
     * @return int, current unix time
     */
    public static int getCurrentUnixTime()
    {
        return (int) (System.currentTimeMillis() / 1000L);
    }
    
    public static String getUnixToHuman(long unixTime)
    {        
        Calendar mydate = Calendar.getInstance();
        mydate.setTimeInMillis(unixTime*1000);
        return mydate.get(Calendar.YEAR)+"-"+(mydate.get(Calendar.MONTH)+1)+"-"+mydate.get(Calendar.DAY_OF_MONTH);
    }
}
