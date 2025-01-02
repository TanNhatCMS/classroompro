package cms.tannhat.classroompro.shub.ui;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import cms.tannhat.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import cms.tannhat.alphabetsindexfastscrollrecycler.utility.AlphabetItem;
import cms.tannhat.classroompro.R;


public class ShubActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener , WebView.OnScrollChangeListener {
    List<AlphabetItem> mAlphabetItems;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    WebView webViewnavi;
    String last_url;
    IndexFastScrollRecyclerView rv;
    public WebView webView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    String url = "https://m.shub.edu.vn/login/student";
    long back_pressed;

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (webViewnavi.canGoBack()) {
                webViewnavi.goBack();
            }else  drawer.closeDrawer(GravityCompat.START);
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

    }

    public void DoadloadingProgress() {



    }
    public void setMaxProgress(int progress) {


    }
    public void setProgressBar(int progress) {


    }
    public void WebProgress() {
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Đang tải...");


    }
    public void setWebProgressBar(int progress,String text) {
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(text);

    }
    public void PaserProgress() {



    }
    public void hideProgressBar() {


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
        if (action != null   ) {
            if (action.equals(Intent.ACTION_VIEW)) {
                Uri uri = intent.getData();
                Log.d("CustWebViewClient", " initIntent()   with  intent.getData(): " + uri);
                    loadUrl(uri.toString());
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
    @SuppressLint("SetJavaScriptEnabled")
    void initNavi(){


    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @SuppressLint("SetJavaScriptEnabled")
    void initWebView(){
        swipeRefreshLayout = findViewById(R.id.swiper);
        int colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
        int colorAccent = ContextCompat.getColor(this, R.color.colorAccent);
        int colorPrimaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        swipeRefreshLayout.setColorSchemeColors(colorPrimary, colorAccent, colorPrimaryDark);
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
                if (uri != null && uri.getHost().contains("accounts.google.com") ) {
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
        String js = "(function allowTextSelection(){" +
                "window.console&&console.log('allowTextSelection');" +
                "var style=document.createElement('style');" +
                "style.type='text/css';" +
                "style.innerHTML='*,p,div{user-select:text !important;-moz-user-select:text !important;-webkit-user-select:text !important;}';" +
                "document.head.appendChild(style);" +
                "var elArray=document.body.getElementsByTagName('*');" +
                "for(var i=0;i<elArray.length;i++){" +
                "var el=elArray[i];" +
                "el.onselectstart=el.ondragstart=el.ondrag=el.oncontextmenu=el.onmousedown=el.onmouseup=function(){return true};" +
                "if(el instanceof HTMLInputElement&&['text','password','email','number','tel','url'].indexOf(el.type.toLowerCase())>-1){el.removeAttribute('disabled');\n" +
                "el.onkeydown=el.onkeyup=function(){return true};}}}allowTextSelection();)()";
        webView.evaluateJavascript("javascript:" + js, null);

        //webView.addJavascriptInterface(new WebAppInterface(this), "JSInterface");
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
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Mobile Safari/537.36");
      //  settings.setUserAgentString(settings.getUserAgentString().replace("; wv)", ")"));
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
        setContentView(R.layout.activity_shub);




        initWebView();
        inithead();
        initNavi();
 //       setAllSearchOption();
        initIntent();



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


    public void updateData(List<AlphabetItem> mAlphabetItems) {
        this.mAlphabetItems = mAlphabetItems;
    }
}
