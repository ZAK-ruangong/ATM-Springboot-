package co.fengfeng.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public SimpleDateFormat getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter;
    }


}
