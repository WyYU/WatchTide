package Utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    //字符串处理
    public String changeBrace(String str){
        str = str.replace("[","");
        str= str.replace("]","");
        str = str.replace(" ","");
        return str;
    }

    //时间戳转日期格式
    public String UnxiTimetoDate(long data){
        Date date = new Date((data*1000));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String nowdate = dateFormat.format(date);
        return nowdate;
    }

    public String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    public String UnxiTimetoDate(float value) {
        Date date = new Date((long) (value*10000));
        Log.e("Tool", String.valueOf(date));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String nowdate = dateFormat.format(date);
        return nowdate;
    }
}
