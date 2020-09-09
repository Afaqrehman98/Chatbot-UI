package com.example.chatbotui;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatbotui.Data.Botmessage;
import com.example.chatbotui.Data.Usermessage;
import com.example.chatbotui.ApiService.ApiCalling;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    EditText userInput;
    RecyclerView recycleView;
    EditText voiceInput;


    ApiCalling requestAPI;
    List<ResponseMessage> responseMessageList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //HTTP
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        //Retrofit class object initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.109:5005/webhooks/rest/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        voiceInput = findViewById(R.id.userInput);
        requestAPI = retrofit.create(ApiCalling.class);
        userInput = findViewById(R.id.userInput);
        recycleView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessageList, this);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false
        ));
        recycleView.setAdapter(messageAdapter);

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {


                    ResponseMessage message = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(message);
                    messageAdapter.notifyDataSetChanged();

                    if (!isLastVisible())
                        recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                    String user_message = userInput.getText().toString();
                    Usermessage new_Message = new Usermessage("User", user_message);
                    Call<List<Botmessage>> call_response = requestAPI.sendMessage(new_Message);
                    call_response.enqueue(new Callback<List<Botmessage>>() {
                        @Override
                        public void onResponse(Call<List<Botmessage>> call, Response<List<Botmessage>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Not Successful! " + response.message()
                                        , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(response.body().isEmpty()){
                                ResponseMessage message2 = new ResponseMessage("sorry I didn't understand your message", false);
                                responseMessageList.add(message2);
                                messageAdapter.notifyDataSetChanged();
                                if (!isLastVisible())
                                    recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                                return;
                            }
                            Botmessage apiResponse = response.body().get(0);
                            String bot_reply1 = apiResponse.getText();
                            ResponseMessage message2 = new ResponseMessage(bot_reply1, false);
                            responseMessageList.add(message2);
                            messageAdapter.notifyDataSetChanged();
                            String bot_reply2;
                            if (response.body().size() > 1) {
                                for(int i=1;i<response.body().size();i++){
                                    bot_reply2 = response.body().get(i).getText();
                                    message2 = new ResponseMessage(bot_reply2, false);
                                    responseMessageList.add(message2);
                                }
                            }
                            messageAdapter.notifyDataSetChanged();
                            if (!isLastVisible())
                                recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }

                        @Override
                        public void onFailure(Call<List<Botmessage>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }

                    });

                }
                v.setText("");
                return true;
            }
        });

    }


    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton);
        {

            promptSpeechInput();
        }

    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");

        try {
            startActivityForResult(i, 100);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(MainActivity.this, "Sorry, Couldn't Connect to your device.", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent i){

        super.onActivityResult(requestCode, resultCode, i);

        switch(requestCode){

            case 100: if(resultCode == RESULT_OK && i !=null)
            {

                ArrayList<String> voiceInput = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                userInput.setText(voiceInput.get(0));
            }
                break;


        }
    }

    public boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recycleView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recycleView.getAdapter().getItemCount();
        return (pos >= numItems);
    }
}



