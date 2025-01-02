package cms.tannhat.classroompro.view;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import cms.tannhat.classroompro.BuildConfig;
import cms.tannhat.classroompro.update.ForceUpdateChecker;
import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.databinding.ActivityMainBinding;
import cms.tannhat.classroompro.webview.CustomTabActivityHelper;
import cms.tannhat.classroompro.webview.WebviewFallback;

public class MainActivity extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener {
    private AlertDialog.Builder alertDialog;
    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = MainActivity.class.getSimpleName();
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "AI đang cập nhật", Snackbar.LENGTH_LONG).setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_contact, R.id.nav_book)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        boolean updated = task.getResult();
                        Log.d(TAG, "Config params updated: " + updated);
                    } else {
                        Log.e(TAG, "Fetch failed");
                    }

                });
    }
    @Override
    public void onUpdateNeeded(final String updateUrl,final String changeLog) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Có phiên bản mới")
                .setMessage("Vui lòng cập nhật ứng dụng lên phiên bản mới để tiếp tục.\n"+ changeLog)
                .setPositiveButton("Cập nhật",
                        (dialog12, which) -> loadurl(updateUrl)).setNegativeButton("Không, cám ơn",
                        (dialog1, which) -> dialog1.dismiss()).create();
        dialog.show();
    }
    private void loadurl(String url){
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse(url), new WebviewFallback());

    }
    private void downloadupdate(String updateUrl) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(updateUrl);
        AlertDialog build = alertDialog.create();
        build.setTitle(getString(R.string.title_update));
        build.setIcon(R.drawable.logo);
        build.setMessage(getString(R.string.msg_update));

        build.show();
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(getString(R.string.title_update));
        pd.setMessage("Đang tải cập nhật ứng dụng,xin vui lòng chờ!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        final File rootPath = new File(Environment.getExternalStorageDirectory(), "tannhatcms");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        final File localFile = new File(rootPath, "update.apk");
        storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            Log.e("firebase ", ";local tem file created  created " + localFile.toString());
            if (localFile.canRead()){
                pd.dismiss();
            }
            Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(exception -> {
            Log.e("firebase ", ";local tem file not created  created " + exception.toString());
            Toast.makeText(MainActivity.this, "Download Incompleted", Toast.LENGTH_LONG).show();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            AlertDialog build = alertDialog.create();
            build.setTitle(getString(R.string.title_info));
            build.setIcon(R.drawable.tannhat);
            build.setMessage(getString(R.string.info, BuildConfig.VERSION_NAME));
            build.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog1, which) -> build.dismiss());
            build.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}