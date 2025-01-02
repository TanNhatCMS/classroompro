package cms.tannhat.classroompro.base;

import androidx.appcompat.app.AppCompatActivity;

import cms.tannhat.classroompro.R;

public class BaseActivity extends AppCompatActivity {
    public String getTitleBook(String str){
        String title ="";
        if (str!=null) {
            switch (str) {
                case "english":
                    title = getString(R.string.english);
                    break;
                case "physics_formula":
                    title = getString(R.string.physics_formula);
                    break;
                case "physics":
                    title = getString(R.string.physics);
                    break;
                case "algorithm":
                    title = getString(R.string.algorithm);
                    break;
                case "geometry":
                    title = getString(R.string.geometry);
                    break;
                case "literature1":
                    title = getString(R.string.literature1);
                    break;
                case "literature2":
                    title = getString(R.string.literature2);
                    break;
                case "chemistry":
                    title = getString(R.string.chemistry);
                    break;
                case "civic":
                    title = getString(R.string.civic);
                    break;
                case "geography":
                    title = getString(R.string.geography);
                    break;
                case "history":
                    title = getString(R.string.history);
                    break;
                case "biological":
                    title = getString(R.string.biological);
                    break;
                case "mathoutline1":
                    title = getString(R.string.mathoutline1);
                    break;
                default:
                    title = "";
            }
        }
        return title;
    }
}
