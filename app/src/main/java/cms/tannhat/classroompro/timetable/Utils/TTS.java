package cms.tannhat.classroompro.timetable.Utils;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTS implements
        TextToSpeech.OnInitListener {
    private final Context context;
    private final TextToSpeech toSpeech;
    public TTS(Context context) {
        this.context = context;
        toSpeech = new TextToSpeech(context,this);
    }
    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR){
            int result = toSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED||
                    result == TextToSpeech.ERROR_NOT_INSTALLED_YET){
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                context.startActivity(installIntent);
            }
        }
    }

    public void speak(String selectedWord) {
        toSpeech.speak(selectedWord, TextToSpeech.QUEUE_FLUSH,null,null);
    }
    public void destroy() {
        toSpeech.shutdown();
    }
    public void stop() {
        toSpeech.stop();
    }
}
