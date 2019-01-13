package Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CatchbyJsoup {
    private static final String TAG = "CatchbyJsoup";

    Document document = null;

    public CatchbyJsoup(){

    }

    //获取数据信息 i表示城市序号;
    public String getData(final int i){
        String jsondata = null;
                try {
                    document = document = Jsoup.connect("http://www.chaoxb.com/"+i+"/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements e = document.getElementsByTag("script").eq(5);
                String data[] =e.toString().split(";");
                String json[] =data[0].split("=");
                jsondata = json[1];
        return jsondata;
    }

}
