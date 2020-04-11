package space.springboot.community.utils;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @desc okhttp工具类，单例模式
 */
public class OkHttpUtils {

    private static int READ_TIMEOUT = 100;

    private static int CONNECT_TIMEOUT = 60;

    private static int WRITE_TIMEOUT = 60;

    private static volatile OkHttpClient okHttpClient;

    private OkHttpUtils(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    public static OkHttpClient getInstance(){
        if (null == okHttpClient){
            synchronized (OkHttpClient.class){
                if (okHttpClient == null){
                    new OkHttpUtils();
                    return okHttpClient;
                }
            }
        }
        return okHttpClient;
    }

}
