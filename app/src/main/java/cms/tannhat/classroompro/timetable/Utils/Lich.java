package cms.tannhat.classroompro.timetable.Utils;


import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import cms.tannhat.classroompro.timetable.Adapters.timeMiniAdapter;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

public class Lich implements Serializable {
    Context context;

    View view;

    public Lich(Context paramContext, View paramView) {
        this.view = paramView;
        this.context = paramContext;
    }

    public void showRc(RecyclerView paramRecyclerView, ArrayList<TimeTableEntity> paramArrayList) {
        timeMiniAdapter timeMiniAdapter = new timeMiniAdapter(this.context, paramArrayList);
        paramRecyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        paramRecyclerView.setAdapter(timeMiniAdapter);
    }
}
