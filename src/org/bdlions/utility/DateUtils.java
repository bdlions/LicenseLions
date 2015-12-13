package org.bdlions.utility;

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
}
