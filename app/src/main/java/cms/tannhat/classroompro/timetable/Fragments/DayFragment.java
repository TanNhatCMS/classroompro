package cms.tannhat.classroompro.timetable.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.timetable.Database.TimeTableMiniDAO;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

import cms.tannhat.classroompro.timetable.Utils.Lich;

public class DayFragment extends Fragment {
    private static final String TAG = "DayFragment";
    TimeTableMiniDAO dao1;
    Lich l;
    ArrayList<TimeTableEntity> list;
    View view;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;

    public static DayFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        DayFragment f = new DayFragment();
        f.setArguments(args);
        return f;
    }
    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.lichhoc, paramViewGroup, false);
        recyclerView1 = this.view.findViewById(R.id.list_item);
        recyclerView2 = this.view.findViewById(R.id.list_item2);
        recyclerView3 = this.view.findViewById(R.id.list_item3);
        this.dao1 = new TimeTableMiniDAO(getContext());
        this.l = new Lich(getContext(), this.view);
        this.list = new ArrayList<TimeTableEntity>();
        showRc2();


        return this.view;
    }
    public String getTitle() {
        assert getArguments() != null;
        return getArguments().getString("title");
    }
    public void onResume() {
        super.onResume();
        showRc2();
    }

    public void showRc2() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getByThuvaBuoi(getTitle(), "Buổi sáng");
            this.l.showRc(recyclerView1, arrayList);
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getByThuvaBuoi(getTitle(), "Buổi chiều");
            this.l.showRc(recyclerView2, arrayList);
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getByThuvaBuoi(getTitle(), "Buổi tối");
            this.l.showRc(recyclerView3, arrayList);
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }
    }
}
