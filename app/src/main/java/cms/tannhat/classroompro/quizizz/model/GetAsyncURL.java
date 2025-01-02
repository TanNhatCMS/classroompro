package cms.tannhat.classroompro.quizizz.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import cms.tannhat.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import cms.tannhat.classroompro.quizizz.MyApplication;
import cms.tannhat.classroompro.quizizz.model.async.AsyncTask;
import cms.tannhat.classroompro.quizizz.ui.QuizizzActivity;


public class GetAsyncURL extends AsyncTask<Void, Void, Void> {
    private QuizizzActivity mainActivity;
    Context context;
    IndexFastScrollRecyclerView rv;
    boolean checked;
    String jsonURL;

    String code;
    ArrayList<Model> users=new ArrayList<>();
    @SuppressLint("UnsafeOptInUsageWarning")
    public GetAsyncURL(QuizizzActivity mainActivity, Context context, IndexFastScrollRecyclerView rv, String code) {
        super(mainActivity);
        this.mainActivity = mainActivity;
        this.context = context;
        this.rv = rv;
        this.code = code;
    }


    public void asyncCall(String urlServer) {
        this.jsonURL = urlServer;
        execute();
    }

    @Override
    public void onPreExecute() {
        mainActivity.DoadloadingProgress();
    }


    @Override
    protected Void doInBackground(Void params) {
        //PARSER
        users.clear();
            StringRequest request = new StringRequest(jsonURL, string -> {
                string = new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).trim();
                parseJsonData(string);
                MyApplication.getInstance().cancelPendingRequests();
            }, volleyError -> {
                volleyError.printStackTrace();
                Toast.makeText(context, "Lấy dữ liệu thất bại!! Lỗi " + volleyError.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                MyApplication.getInstance().cancelPendingRequests();

            });
        MyApplication.getInstance().addToRequestQueue(request);
        return params;
    }


    @Override
    public void onPostExecute(Void params) {
        mainActivity.hideProgressBar();
        mainActivity.hideProgressBar();
    }

    @Override
    protected void onBackgroundError(Exception e) {
        e.printStackTrace();
    }

    private void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            int status = object.getInt("status");
            if(status==200) {
                System.out.println(status);
                JSONObject data = new  JSONObject(object.getString("data"));
                JSONArray jsonArray = data.optJSONArray("answers");
                JSONObject jo;
                Model user;
                int max = Objects.requireNonNull(jsonArray).length();
                mainActivity.PaserProgress();
                mainActivity.setMaxProgress(100);
                for (int i = 0; i < max; i++) {

                    int percent =( (i +1) / max) * 100;
                    mainActivity.setProgressBar(percent);
                    mainActivity.setText("Parsing: " + percent + " %");
                    if(i == max)  {
                        mainActivity.setText("Completed!");
                    }
                    jo = jsonArray.getJSONObject(i);
                    JSONArray answers = jo.optJSONArray("answers");
                    assert answers != null;
                    String dapan = answers.getJSONObject(0).getString("text");
                    String question = jo.getJSONObject("question").getString("text");
                    user = new Model(question, dapan);
                    users.add(user);
                }
                MyAdapter myAdapter = new MyAdapter(context, mainActivity, users);
                rv.setAdapter(myAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}