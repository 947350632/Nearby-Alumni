package com.sannmizu.nearby_alumni.NetUtils;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.sannmizu.nearby_alumni.R;
import com.sannmizu.nearby_alumni.utils.AESUtils;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ChatResponse extends MyResponse<ChatResponse.ChatData> {
    public static interface ChatService {
        @FormUrlEncoded
        @POST("chat/user/{userId}")
        Observable<ChatResponse> chat(@Path("userId")int userId, @Field("value")String value, @Query("logToken")String logToken, @Query("connToken")String connToken);

        @FormUrlEncoded
        @POST("chat/user/{userId}")
        Observable<ChatResponse> chat(@Path("userId")int userId, @Field("value")String value, @Field("extra.pic")String base64Pic, @Query("logToken")String logToken, @Query("connToken")String connToken);
    }
    public static ChatService generateService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Net.BaseHost)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ChatService.class);
    }
    public static String getTextRequest(String content) {
        return AESUtils.encryptFromLocal( "{\"content\":\"" + content + "\"}");
    }
    public static String getPictureRequest(String picType) {
        return AESUtils.encryptFromLocal( "{\"content\":\"[图片]\",\"extra\":{\"extra.pic\":\"" + picType + "\"}}");
    }
    public static class ChatData{
        @SerializedName("state")
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
