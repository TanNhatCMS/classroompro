package cms.tannhat.classroompro.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.adapters.BookMarkViewAdapter;
import cms.tannhat.classroompro.base.BaseActivity;
import cms.tannhat.classroompro.databinding.ActivityPdfviewBinding;
import cms.tannhat.classroompro.utils.Logs;

public class PDFViewActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {
    public static final String EXTRA_PDF = "extra.pdf";
    private static final String CHILD_DIR = "pdf";
    private static final String FILE_EXTENSION = ".pdf";
    private static final String TAG = PDFViewActivity.class.getSimpleName();
    public PDFView pdfView;
    PDFView.Configurator configurator;
    Integer pageNumber = 0;
    String pdfFileName;
    TextView header_title;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    List<BookMarkViewAdapter.BookMarkData> BookMarkList;
    BookMarkViewAdapter bookMarkViewAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPdfviewBinding binding = ActivityPdfviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        drawerLayout = binding.activityPdfviewDrawer;
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
       // NavigationView navigationView = binding.navView;
        recyclerView = binding.bookmarks;
        header_title = binding.headerTitle;
        pdfFileName = getIntent().getStringExtra(EXTRA_PDF);
        pdfView = findViewById(R.id.pdfView);
        pdfView.setBackgroundColor(Color.LTGRAY);
        BookMarkList = new ArrayList<>();
        bookMarkViewAdapter = new BookMarkViewAdapter(this, this, BookMarkList);
        try {
            Uri uri = getUriByFileName();
            if(uri !=null){
                loadPDFfromUri(uri);
            }else {
                DownloadFiles();
            }
        } catch (IOException e) {
            Log.e(TAG, "Cannot load page ", e);
        }


    }
    @Override
    public void setTitle(CharSequence title) {
        header_title.setText(title);
        super.setTitle(title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private BookMarkViewAdapter.BookMarkData createBookMark(String name, long page) {
        BookMarkViewAdapter.BookMarkData bookmode = new BookMarkViewAdapter.BookMarkData();
        bookmode.Name = name;
        bookmode.page = (int)page;
        Log.d(TAG, "createBookMark " + name);
        return bookmode;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * Get a file {@link Uri}
     * @return file Uri in the App cache or null if file wasn't found
     */
    @Nullable public Uri getUriByFileName() {
        Context context = getApplicationContext();
        File Path = new File(context.getCacheDir(), CHILD_DIR);
        File newFile = new File(Path, pdfFileName + FILE_EXTENSION);
        if(newFile.exists()) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);
        }else{
            return null;
        }
    }
    public void settingsPDF(){
        configurator.defaultPage(pageNumber).onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
    }
    public void loadPDFfromUri(Uri uri){
        configurator = pdfView.fromUri(uri);
        settingsPDF();
    }
    /*
    public void loadPDFfromBytes(byte[] bytes){
        configurator = pdfView.fromBytes(bytes);
        settingsPDF();
    }*/
    public void loadPDFfromFile(File file){
        configurator = pdfView.fromFile(file);
        settingsPDF();
    }
    public void DownloadFiles() throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // [START download_create_reference]
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
         // [END download_create_reference]
        // [START download_to_memory]
        StorageReference islandRef = storageRef.child("SGK11/"+ pdfFileName +".pdf");
        downloadToLocalFile(islandRef);
            // [END download_to_memory]
    }

    @SuppressLint("StringFormatMatches")
    private void downloadToLocalFile(StorageReference fileRef) {
        if (fileRef != null) {
            Context context = getApplicationContext();
            File Path = new File(context.getCacheDir(), CHILD_DIR);
            Path.mkdirs();
            File newFile = new File(Path, pdfFileName + FILE_EXTENSION);
            fileRef.getFile(newFile).addOnSuccessListener(taskSnapshot -> {
                loadPDFfromFile(newFile);
                Logs.d(TAG, newFile.getAbsolutePath());
            }).addOnFailureListener(exception -> {
                Logs.e(TAG , "save bug ",exception);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }).addOnProgressListener(taskSnapshot -> {
                // progress percentage
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Logs.d(TAG, "Downloaded " + ((int) progress) + "%...");
                setTitle(getString(R.string.title_dpf_downloading , getTitleBook(pdfFileName), ((int) progress)) +"%...");
            });
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(getString(R.string.title_dpf_page , getTitleBook(pdfFileName), page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        printBookmarksTree(pdfView.getTableOfContents(), "-");
        recyclerView.setAdapter(bookMarkViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("DefaultLocale")
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            BookMarkList.add(createBookMark( String.format("%s %s", sep, b.getTitle()), b.getPageIdx()));
            Logs.d(TAG, String.format("%s %s, trang %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
    @Override
    public void onPageError(int page, Throwable t) {
        Logs.e(TAG, "Cannot load page " + page , t);
    }
}
