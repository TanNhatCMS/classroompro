package cms.tannhat.classroompro.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.timetable.Utils.Util;
import cms.tannhat.classroompro.timetable.Fragments.DayFragment;
import cms.tannhat.classroompro.timetable.Utils.TTS;


public class TimeTableActivity extends AppCompatActivity {

    TTS tts;
    //fab
    FloatingActionButton fab;
    private Toolbar toolbar;
    private ViewPager viewPager;

    private void setupViewPager(ViewPager paramViewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ArrayList<String> arrayList = Util.ListDayTab();
        for (int i= 0; i < arrayList.size(); i++){
            viewPagerAdapter.addFragment(DayFragment.newInstance(arrayList.get(i)), arrayList.get(i));//0
        }
        paramViewPager.setAdapter(viewPagerAdapter);
        paramViewPager.setCurrentItem(Util.getDay2Page());
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_timetable);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.viewPager = findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        Util.setupDB(this);
        tts = new TTS(this);
        fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(view -> Speech());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.destroy();
    }
    @Override
    public void onPause() {
        super.onPause();

    }
    private void Speech(){
        tts.speak(Util.getSpeech(this, Util.getPage2Day(viewPager.getCurrentItem())));
    }


    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.timetable, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        Bundle bundle;
        if (paramMenuItem.getItemId() == R.id.add) {
            bundle = new Bundle();
            Intent intent = new Intent(this, AddTimeMini.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();

        private final List<String> mFragmentTitleList = new ArrayList<String>();


        public ViewPagerAdapter(FragmentManager param1FragmentManager) {
            super(param1FragmentManager);
        }

        public void addFragment(Fragment param1Fragment, String param1String) {
            this.mFragmentList.add(param1Fragment);
            this.mFragmentTitleList.add(param1String);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        @NonNull
        public Fragment getItem(int param1Int) {
            return this.mFragmentList.get(param1Int);
        }

        public CharSequence getPageTitle(int param1Int) {
            return this.mFragmentTitleList.get(param1Int);
        }
    }
}
