package com.example.assistant;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView mic;
    TextView userTextTv,agentTextTv;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},121);
        mic=(ImageView)findViewById(R.id.mic1);
        userTextTv=(TextView)findViewById(R.id.textView);
        agentTextTv=(TextView)findViewById(R.id.textView2);

       
        final AIConfiguration config = new AIConfiguration( "a39d7fe005894a6980d880095fe16032",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        final AIService aiService = AIService.getService(getApplicationContext(), config);
        aiService.setListener(new AIListener() {
            @Override
            public void onResult(AIResponse result) {
                agentTextTv.setText(result.getResult().getFulfillment().getSpeech());
                userTextTv.setText(result.getResult().getResolvedQuery());
            }

            @Override
            public void onError(AIError error) {
                mic.setImageResource(R.drawable.mic23);
            }

            @Override
            public void onAudioLevel(float level) {

            }

            @Override
            public void onListeningStarted() {
                mic.setImageResource(R.drawable.mic1);
            }

            @Override
            public void onListeningCanceled() {
                mic.setImageResource(R.drawable.mic23);
            }

            @Override
            public void onListeningFinished() {
                mic.setImageResource(R.drawable.mic23);
            }
        });
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiService.startListening();
            }
        });

    }
}
