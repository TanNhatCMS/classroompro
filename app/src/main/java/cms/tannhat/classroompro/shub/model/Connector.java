package cms.tannhat.classroompro.shub.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connector {

    public static Object connect(String jsonURL)
    {
        try
        {
            URL url=new URL(jsonURL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //CON PROPS
            con.setRequestMethod("GET");
            con.setConnectTimeout(100000);
            con.setReadTimeout(100000);
            con.setInstanceFollowRedirects(true);
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error "+ e;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e;

        }
    }

}
