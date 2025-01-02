package cms.tannhat.classroompro.timetable;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.timetable.Utils.Util;
import cms.tannhat.classroompro.timetable.Database.TimeTableMiniDAO;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

public class UpdateActivity extends AppCompatActivity {
    Button btnSave;

    TimeTableMiniDAO dao1;

    ProgressDialog dialog;

    EditText edtMonhoc;

    EditText edttiet;

    EditText edttime;


    Spinner spnmon;

    Spinner spntiet;

    Spinner spntime;

    TextView tvbuoi;

    TextView tvthu;

    public void buttonOnClick() {
        this.btnSave.setOnClickListener(param1View -> UpdateActivity.this.updateBySQLite());
    }

    public boolean checkNoEmpty() {
        boolean bool = true;
        if (getData().getThu().isEmpty() || getData().getBuoi().isEmpty() || getData().getTiet().isEmpty()  || getData().getTime().isEmpty() || getData().getMonhoc().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            bool = false;
        }
        return bool;
    }

    public boolean chekUpdateOk() {
        boolean bool2;
        boolean bool1 = true;
        try {
            TimeTableEntity timeTableEntity1 = (TimeTableEntity)getIntent().getSerializableExtra("timeMini");
            TimeTableEntity timeTableEntity2 = getData();
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            byte b;
            for (b = 0; b < arrayList.size(); b++) {
                if (arrayList.get(b).getMaso().equals(timeTableEntity1.getMaso()))
                    arrayList.remove(b);
            }
            for (b = 0;; b++) {
                bool2 = bool1;
                if (b < arrayList.size()) {
                    String str = arrayList.get(b).getTiet();
                    StringBuilder stringBuilder = new StringBuilder();
                    if (str.equals(stringBuilder.append(timeTableEntity2.getTiet()).append("").toString())) {
                        String str1 = arrayList.get(b).getThu();
                        StringBuilder stringBuilder1 = new StringBuilder();
                        if (str1.equals(stringBuilder1.append(timeTableEntity2.getThu()).append("").toString())) {
                            str1 = arrayList.get(b).getBuoi();
                            stringBuilder1 = new StringBuilder();
                            if (str1.equals(stringBuilder1.append(timeTableEntity2.getBuoi()).append("").toString())) {
                                bool2 = false;
                                Toast.makeText(this, "Không thành công do trùng thời gian", Toast.LENGTH_SHORT).show();
                                return bool2;
                            }
                        }
                    }
                } else {
                    return bool2;
                }
            }
        } catch (Exception exception) {
            bool2 = true;
        }
        return bool2;
    }

    public TimeTableEntity getData() {
        new TimeTableEntity();
        return new TimeTableEntity(this.tvthu.getText().toString().trim(), this.edttiet.getText().toString().trim(), this.edtMonhoc.getText().toString().trim(), this.tvbuoi.getText().toString().trim(), this.edttime.getText().toString().trim());
    }

    public void hidedialog() {
        if (this.dialog.isShowing())
            this.dialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_update);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>"));
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        thamchieu();
        try {
            if (getIntent().hasExtra("timeMini")) {
                TimeTableEntity timeTableEntity = (TimeTableEntity)getIntent().getSerializableExtra("timeMini");
                this.tvthu.setText(timeTableEntity.getThu());
                this.tvbuoi.setText(timeTableEntity.getBuoi());
                this.edttiet.setText(timeTableEntity.getTiet());
                this.edttime.setText(timeTableEntity.getTime());
                this.edtMonhoc.setText(timeTableEntity.getMonhoc());
            }
            setData();
            spnClick();
            buttonOnClick();
        } catch (Exception ignored) {
        }
    }

    public void setData() {
        setSpntiet();
        setSpnmon();
        setSpntime();
    }

    public void setSpnmon() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();
            arrayList1.add("---");
            for (int i = 0; i < arrayList.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(i).getMonhoc()).append("").toString());
            }
            arrayList1.addAll(Util.ListMon());
            showSpn(Util.removeDuplicates(arrayList1), this.spnmon);
        }catch (Exception e) {
            Log.e("setSpnmon", e.getMessage());
        }
    }

    public void setSpntiet() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();
            arrayList1.add("---");
            for (int i = 0; i < arrayList.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(i).getTiet()).append("").toString());
            }
            arrayList1.addAll(Util.ListTiet());
            showSpn(Util.removeDuplicates(arrayList1), this.spntiet);
        } catch (Exception e) {
            Log.e("setSpntiet", e.getMessage());
        }
    }
    public void setSpntime() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();
            arrayList1.add("---");
            for (int i = 0; i < arrayList.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(i).getTime()).append("").toString());
            }
            arrayList1.addAll(Util.ListTime());
            showSpn(Util.removeDuplicates(arrayList1), this.spntime);
        } catch (Exception e) {
            Log.e("setSpntime", e.getMessage());
        }
    }

    @SuppressLint("ResourceType")
    public void showSpn(ArrayList<String> paramArrayList, Spinner paramSpinner) {
        paramSpinner.setAdapter(new ArrayAdapter<>(this, 17367049, paramArrayList));
    }

    public void showdialg() {
        if (!this.dialog.isShowing())
            this.dialog.show();
    }

    public void spnClick() {
        this.spntiet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = UpdateActivity.this.spntiet.getSelectedItem().toString().trim();
                if (!str.equals("---"))
                    UpdateActivity.this.edttiet.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
        this.spntime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = UpdateActivity.this.spntime.getSelectedItem().toString().trim();
                if (!str.equals("---"))
                    UpdateActivity.this.edttime.setText(str);
            }
            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });

        this.spnmon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = UpdateActivity.this.spnmon.getSelectedItem().toString().trim();
                if (!str.equals("---"))
                    UpdateActivity.this.edtMonhoc.setText(str);
            }
            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
    }

    public void thamchieu() {
        this.tvthu = findViewById(R.id.tv);
        this.edttiet = findViewById(R.id.editText3);
        this.edttime = findViewById(R.id.editTime);
        this.edtMonhoc = findViewById(R.id.editText4);
        this.tvbuoi = findViewById(R.id.tv2);
        this.spntiet = findViewById(R.id.sptiet);
        this.spntime = findViewById(R.id.sptime);
        this.spnmon = findViewById(R.id.spmon);
        this.btnSave = findViewById(R.id.button);
        this.dialog = new ProgressDialog(getApplicationContext());
        this.dialog.setCancelable(false);
        this.dao1 = new TimeTableMiniDAO(this);
    }

    public void updateBySQLite() {
        if (checkNoEmpty() && chekUpdateOk()) {
            TimeTableEntity timeTableEntity1 = (TimeTableEntity)getIntent().getSerializableExtra("timeMini");
            TimeTableEntity timeTableEntity2 = getData();
            timeTableEntity2.setMaso(timeTableEntity1.getMaso());
            this.dao1.update(timeTableEntity2);
            Toast.makeText(getApplicationContext(),  "Thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}