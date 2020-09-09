package com.example.chatbotui.ApiService;

import com.example.chatbotui.Data.Botmessage;
        import com.example.chatbotui.Data.Usermessage;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.POST;


public interface ApiCalling {

    @POST("webhook")
    Call<List<Botmessage>> sendMessage(@Body Usermessage message);


}
