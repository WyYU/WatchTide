package Conn;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnToInter {
    private static final String TAG = "ConnToInter";

    private OkHttpClient client;
    private Request request;
    public String url = "http://www.chaoxb.com";
    public String url_baidu = "https://www.baidu.com";
    public String WEIHAI = "\\39";


    //初始化
    public ConnToInter(){
        client = new OkHttpClient();
    }

    //获取call
    public Call getCall(String url){
        if(url==null){
            Log.e(TAG,"URL is null ");
            return null;
        }
        request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        return call;
    }

    //获取Response
    //返回响应
    public Response getData(Call call){
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
