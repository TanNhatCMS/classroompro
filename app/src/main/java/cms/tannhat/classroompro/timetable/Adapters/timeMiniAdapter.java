package cms.tannhat.classroompro.timetable.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.timetable.Database.TimeTableMiniDAO;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;
import cms.tannhat.classroompro.timetable.UpdateActivity;

public class timeMiniAdapter extends RecyclerView.Adapter<timeMiniAdapter.Item> {
    boolean b = false;

    Context c;

    LayoutInflater inflater;

    ArrayList<TimeTableEntity> list;

    int pos;

    TimeTableEntity t;

    public timeMiniAdapter(Context paramContext, ArrayList<TimeTableEntity> paramArrayList) {
        this.c = paramContext;
        this.list = paramArrayList;
        this.inflater = LayoutInflater.from(paramContext);
    }

    public int getItemCount() {
        return this.list.size();
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(Item paramItem, @SuppressLint("RecyclerView") int paramInt) {
        this.pos = paramInt;
        this.t = this.list.get(paramInt);
        paramItem.tiet.setText(this.t.getTiet() + "");
        paramItem.time.setText(this.t.getTime() + "");
        paramItem.mon.setText(this.t.getMonhoc() + "");
    }

    @NonNull
    public Item onCreateViewHolder(@NonNull ViewGroup paramViewGroup, int paramInt) {
        return new Item(this.inflater.inflate(R.layout.lichhoc_item, paramViewGroup, false));
    }

    class Item extends RecyclerView.ViewHolder {
        Button btnsua;

        Button btnxoa;

        LinearLayout itemview;

        LinearLayout linearLayout;

        TextView mon;

        TextView time;

        TextView tiet;

        @SuppressLint("NotifyDataSetChanged")
        public Item(View param1View) {
            super(param1View);
            this.tiet = param1View.findViewById(R.id.tv_tiethoc);
            this.time = param1View.findViewById(R.id.tv_time);
            this.mon = param1View.findViewById(R.id.tv_monhoc);
            this.linearLayout = param1View.findViewById(R.id.linearLayout);
            this.itemview = param1View.findViewById(R.id.itemview);
            this.btnxoa = param1View.findViewById(R.id.btnxoa);
            this.btnsua = param1View.findViewById(R.id.btnsua);
            this.linearLayout.setVisibility(View.GONE);
            param1View.setOnClickListener(param2View -> {
                if (Item.this.linearLayout.isShown()) {
                    Item.this.linearLayout.setVisibility(View.GONE);
                    return;
                }
                Item.this.linearLayout.setVisibility(View.VISIBLE);
            });
            this.btnxoa.setOnClickListener(param2View -> {
                TimeTableEntity timeTableEntity = timeMiniAdapter.this.list.get(Item.this.getAdapterPosition());
                (new TimeTableMiniDAO(timeMiniAdapter.this.c)).delete(timeTableEntity.getMaso());
                timeMiniAdapter.this.list.remove(Item.this.getAdapterPosition());
                timeMiniAdapter.this.notifyDataSetChanged();
            });
            this.btnsua.setOnClickListener(param2View -> {
                TimeTableEntity timeTableEntity = timeMiniAdapter.this.list.get(Item.this.getAdapterPosition());
                Intent intent = new Intent(timeMiniAdapter.this.c, UpdateActivity.class);
                intent.putExtra("timeMini", timeTableEntity);
                param2View.getContext().startActivity(intent);
            });
        }
    }

}
