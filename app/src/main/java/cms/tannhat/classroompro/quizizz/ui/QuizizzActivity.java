package cms.tannhat.classroompro.quizizz.ui;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import cms.tannhat.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import cms.tannhat.alphabetsindexfastscrollrecycler.utility.AlphabetItem;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.quizizz.model.GetAsyncURL;


public class QuizizzActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener , WebView.OnScrollChangeListener {
    List<AlphabetItem> mAlphabetItems;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    String code;
    EditText etComments;
    String jsonHost ="https://api.quizit.online/quizizz/answers?pin=";
    public ProgressBar progressBar;
    public TextView textprogressBar;
    String last_url;
    IndexFastScrollRecyclerView rv;
    public WebView webView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    String url = "https://quizizz.com/join";
    long back_pressed;

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                if ((System.currentTimeMillis() ) <(back_pressed + 3000) ) {
                    super.onBackPressed();
                } else {
                    Snackbar.make(webView, "Nhấn trở lại lần nữa để thoát!", Snackbar.LENGTH_LONG)
                            .setAction("Thoát", v -> finish())
                            .show();
                }
            }
            back_pressed = System.currentTimeMillis();
        }
    }
    public void setText(String text) {
        textprogressBar.setText(text);
        if(textprogressBar.getVisibility()==View.GONE) {
            textprogressBar.setVisibility(View.VISIBLE);
        }
    }
    public void hideText() {
        textprogressBar.setVisibility(View.GONE);
    }
    public void DoadloadingProgress() {
        textprogressBar.setText("Đang lấy dữ liệu");
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void setMaxProgress(int progress) {
        progressBar.setMax(progress);
        setProgressBar(1);
    }
    public void setProgressBar(int progress) {

        progressBar.setProgress(progress);
    }
    public void WebProgress() {
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Đang tải...");

        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.VISIBLE);
        setMaxProgress(100);
    }
    public void setWebProgressBar(int progress,String text) {
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(text);

        progressBar.setProgress(progress);
    }
    public void PaserProgress() {
        setText("Parse\nParsing...Please wait");
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar() {

        hideText();
        progressBar.setVisibility(View.GONE);
    }
    void inithead(){
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initIntent(){
        Intent intent = getIntent();
        String action = intent.getAction();
        if (action!= null   ) {
            if (action.equals(Intent.ACTION_VIEW)) {
                Uri uri = intent.getData();
                Log.d("CustWebViewClient", " initIntent()   with  intent.getData(): " + uri);
                if (uri != null && uri.getHost().equals("quizizz.com") && uri.getPath().equals("/join")) {
                    code = uri.getQueryParameter("gc");
                    if (code != null) {
                        url = "https://quizizz.com/join?gc=" + code;
                        String from = uri.getQueryParameter("from");
                        if (from != null) {
                            url = "https://quizizz.com/join?gc=" + code + "&from=" + from;
                        }
                        etComments.setText(code);
                        loadData(code);
                        if (!code.startsWith("http")) {
                            loadUrl("https://quizizz.com/join?gc=" + code);
                        }
                    }
                } else if (uri != null && uri.getHost().equals("quizizz.com") && uri.getPath().contains("/auth")) {
                    loadUrl(uri.toString());
                } else if (uri != null && uri.getHost().equals("quizizz.com")) {
                    loadUrl(uri.toString());
                }
            } else loadUrl(url);
        } else loadUrl(url);
    }
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
    {
        if(oldScrollY < scrollY)
        {
         //   Objects.requireNonNull(getSupportActionBar()).hide();
        }
        else if(oldScrollY > scrollY)
        {
       //     Objects.requireNonNull(getSupportActionBar()).show();
        }
    }
    void initNavi(){
        etComments = findViewById(R.id.editTextCode);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        initialiseUI();
        MaterialButton btn = findViewById(R.id.contained_button);
        btn.setOnClickListener(v ->{
            code = etComments.getText().toString().trim();
            loadData(code);
        } );
        MaterialButton btn2 = findViewById(R.id.contained_button2);
        btn2.setOnClickListener(v ->{
            code = etComments.getText().toString().trim();
            if(!code.startsWith("http")) {
                loadUrl("https://quizizz.com/join?gc=" + code);
            }
        } );
    }
    void loadData(String code){
        new GetAsyncURL(this,this, rv, code).asyncCall(jsonHost + code);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    void initWebView(){
        swipeRefreshLayout = findViewById(R.id.swiper);
        int colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
        int colorAccent = ContextCompat.getColor(this, R.color.colorAccent);
        int colorPrimaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
       swipeRefreshLayout.setColorSchemeColors(colorPrimary, colorAccent, colorPrimaryDark);

        assert swipeRefreshLayout != null;
        swipeRefreshLayout.setOnRefreshListener(this);
        webView = findViewById(R.id.webview);
        Context context= this;
        webView.setWebViewClient(new WebViewClient() {

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String message = "SSL Certificate error.";
                swipeRefreshLayout.setRefreshing(false);
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", (dialog, which) -> handler.proceed());
                builder.setNegativeButton("cancel", (dialog, which) -> handler.cancel());
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d("CustWebViewClient", "error:" + error.toString());
                swipeRefreshLayout.setRefreshing(false);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Uri uri = Uri.parse(url);
                boolean google = false;
                if (uri != null && uri.getHost().contains("accounts.google.com") && google== true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                    Log.d("CustWebViewClient", "onPageStarted: " + url);
                    toolbar.setLogo(new BitmapDrawable(getResources(), favicon));
                    WebProgress();
                    super.onPageStarted(view, url, favicon);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d("CustWebViewClient", "shouldOverrideUrlLoading: " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                last_url = url;
                swipeRefreshLayout.setRefreshing(false);
                toolbar.setLogo(new BitmapDrawable(getResources(),view.getFavicon()));
                Objects.requireNonNull(getSupportActionBar()).setSubtitle(view.getTitle());
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    Objects.requireNonNull(getSupportActionBar()).setSubtitle(title);
                }
            }
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                setWebProgressBar(progress ,"Đang tải: " + progress  + "%"); //Make the bar disappear after URL is loaded

                // disappear after URL
                // is loaded
                if(progress ==100) {
                    Objects.requireNonNull(getSupportActionBar()).setSubtitle(view.getTitle());
                    hideProgressBar();
                }
                super.onProgressChanged(view, progress);
            }
        });
        //webView.addJavascriptInterface(new WebAppInterface(requireContext()), "JSInterface");
        webView.setOnScrollChangeListener(this);
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        //settings.setSaveFormData(true);
        settings.setAllowContentAccess(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setDefaultTextEncodingName("utf-8");
     //   settings.setUserAgentString(USER_AGENT_FAKE);
        settings.setUserAgentString(settings.getUserAgentString().replace("; wv)", ")"));
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        settings.setDatabaseEnabled(true);
    }
    @Override
    public void onRefresh() {
        webView.reload();
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_quizizz);
        // Makes Progress bar Visible
      //  getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                //Window.PROGRESS_VISIBILITY_ON);
        progressBar = findViewById(R.id.progressBar);
        textprogressBar = findViewById(R.id.text_info);
        initWebView();
        inithead();
        initNavi();
        setAllSearchOption();
        initIntent();
     //   https://quizizz.com/join?gc=2640476&from=challengeFriends



    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    private void setAllSearchOption(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.searchViewN);


        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        // Không biểu tượng hóa tiện ích con; mở rộng nó theo mặc định
        searchView.setIconifiedByDefault(false);
        //searchView.setQueryRefinementEnabled(true);
        // searchView.requestFocus(1);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    updateFilter(query);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    updateFilter(newText);
                    return true;
                } else {
                    // user don't input anything
                    Objects.requireNonNull(rv.getLayoutManager()).scrollToPosition(0);
                    return false;
                }
            }
        });

    }

    private void updateFilter(String query) {
        if (mAlphabetItems != null && mAlphabetItems.size() > 0 && query != null) {
            for (int i = 0; i < mAlphabetItems.size(); i++) {
                if(mAlphabetItems.get(i).word.toLowerCase().contains(query.trim().toLowerCase())) {
                    Objects.requireNonNull(rv.getLayoutManager()).scrollToPosition(mAlphabetItems.get(i).position);
                    return;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String filter = "";
        updateFilter(filter);
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) {
            webView.saveState(outState);
        }
    }


    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //Khôi phục vị trí thả xuống hiện tại được tuần tự hóa trước đó.
        super.onRestoreInstanceState(savedInstanceState);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    protected void initialiseUI() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setIndexTextSize(12);
        rv.setIndexBarColor("#33334c");
        rv.setIndexBarCornerRadius(8);
        rv.setIndexBarTransparentValue((float) 0.5);
        rv.setIndexbarTopMargin(60);
        rv.setIndexbarBottomMargin(100);
        rv.setIndexbarHorizontalMargin(20);
        rv.setPreviewPadding(0);
        rv.setIndexBarTextColor("#FFFFFF");
        rv.setPreviewTextSize(60);
        rv.setPreviewColor("#33334c");
        rv.setPreviewVisibility(true);
        rv.setPreviewTextColor("#FFFFFF");
        rv.setPreviewTransparentValue(0.6f);
        rv.setIndexBarVisibility(true);
        rv.setIndexBarStrokeVisibility(true);
        rv.setIndexBarStrokeWidth(1);
        rv.setIndexBarStrokeColor("#000000");
        rv.setIndexbarHighLightTextColor("#33334c");
        rv.setIndexBarHighLightTextVisibility(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider, getTheme()));
        rv.addItemDecoration(itemDecoration);
        Objects.requireNonNull(rv.getLayoutManager()).scrollToPosition(0);
    }

    public void updateData(List<AlphabetItem> mAlphabetItems) {
        this.mAlphabetItems = mAlphabetItems;
    }
}
