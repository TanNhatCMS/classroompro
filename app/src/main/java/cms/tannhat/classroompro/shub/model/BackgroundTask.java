package cms.tannhat.classroompro.shub.model;

import android.app.Activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BackgroundTask <Params,Result> {
    private final ExecutorService executors;

    private Activity activity;
    public BackgroundTask(Activity activity){
        this.activity = activity;
        this.executors = Executors.newSingleThreadExecutor();
    }

    private void startBackground() {
        onPreExecute();
        executors.execute(() -> {

            Result result = doInBackground();
            activity.runOnUiThread(() -> onPostExecute(result));
        });

    }
    public void execute() {
        startBackground();
    }
    public void shutdown() {
        executors.shutdown();
    }

    public boolean isShutdown() {
        return executors.isShutdown();
    }
    public abstract void onPreExecute();

    protected abstract Result doInBackground(Params... var1);


    protected abstract void onPostExecute(Result result);

}
