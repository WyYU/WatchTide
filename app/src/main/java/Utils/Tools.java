package Utils;

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
    public String UnxiTimetoDate(String data){
        Long utime = Long.parseLong(data);
        Date date = new Date(utime*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String nowdate = dateFormat.format(date);
        return nowdate;
    }
}
