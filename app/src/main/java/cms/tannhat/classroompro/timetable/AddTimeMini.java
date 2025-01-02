package cms.tannhat.classroompro.timetable;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.timetable.Utils.Util;
import cms.tannhat.classroompro.timetable.Database.TimeTableMiniDAO;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

public class AddTimeMini extends AppCompatActivity {
    Button btnSave;

    TimeTableMiniDAO dao1;

    ProgressDialog dialog;

    EditText edtBuoi;

    EditText edtMonhoc;

    EditText edtthu;

    EditText edttiet;

    EditText edttime;

    Spinner spnBuoi;

    Spinner spnmon;

    Spinner spnthu;

    Spinner spntiet;

    Spinner spntime;

    public void addBySQLite() {
        if (checkNoEmpty() && chekAddOk()) {
            this.dao1.add(getData());
            setData();
            showToast("Thành công");
            finish();
        }
    }

    public boolean checkNoEmpty() {
        boolean bool = true;
        if (getData().getThu().isEmpty() || getData().getBuoi().isEmpty() || getData().getTiet().isEmpty() || getData().getTime().isEmpty() || getData().getMonhoc().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            bool = false;
        }
        return bool;
    }

    public boolean chekAddOk() {
        try {
            TimeTableEntity timeTableEntity = getData();
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            for (byte b = 0;; b++) {
                if (b < arrayList.size()) {
                    String str = arrayList.get(b).getTiet();
                    StringBuilder stringBuilder = new StringBuilder();
                    if (str.equals(stringBuilder.append(timeTableEntity.getTiet()).append("").toString())) {
                        str = arrayList.get(b).getThu();
                        stringBuilder = new StringBuilder();
                        if (str.equals(stringBuilder.append(timeTableEntity.getThu()).append("").toString())) {
                            str = arrayList.get(b).getBuoi();
                            stringBuilder = new StringBuilder();

                            if (str.equals(stringBuilder.append(timeTableEntity.getBuoi()).append("").toString())) {
                                Toast.makeText(this, "Không thành công do trùng thời gian", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception ignored) {
            return true;
        }
    }

    public TimeTableEntity getData() {
        return new TimeTableEntity(this.edtthu.getText().toString().trim(), this.edttiet.getText().toString().trim(), this.edtMonhoc.getText().toString().trim(), this.edtBuoi.getText().toString().trim(),  this.edttime.getText().toString().trim());
    }

    public void hidedialog() {
        if (this.dialog.isShowing())
            this.dialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_add_time_mini);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.back);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        thamchieu();
        setData();
        spnClick();
        this.btnSave.setOnClickListener(param1View -> AddTimeMini.this.addBySQLite());
    }

    public void setData() {
        setSpnthu();
        setSpntiet();
        setSpnmon();
        setSpnBuoi();
        setSpntime();
    }

    public void setSpnBuoi() {
        showSpn(Util.ListBuoi(), this.spnBuoi);
    }

    public void setSpnmon() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();
            for (byte b = 0; b < arrayList.size(); b++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(b).getMonhoc()).append("").toString());
            }
            arrayList1.addAll(Util.ListMon());
            showSpn(Util.removeDuplicates(arrayList1), this.spnmon);
        } catch (Exception ignored) {}
    }

    public void setSpnthu() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Util.ListDayTab());
        showSpn(arrayList, this.spnthu);
    }

    public void setSpntiet() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();

            for (byte b = 0; b < arrayList.size(); b++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(b).getTiet()).append("").toString());
            }
            arrayList1.addAll(Util.ListTiet());
            showSpn(Util.removeDuplicates(arrayList1), this.spntiet);
        } catch (Exception ignored) {}
    }
    public void setSpntime() {
        try {
            ArrayList<TimeTableEntity> arrayList = this.dao1.getAll();
            ArrayList<String> arrayList1 = new ArrayList<>();

            for (byte b = 0; b < arrayList.size(); b++) {
                StringBuilder stringBuilder = new StringBuilder();
                arrayList1.add(stringBuilder.append(arrayList.get(b).getTime()).append("").toString());
            }
            arrayList1.addAll(Util.ListTime());
            showSpn(Util.removeDuplicates(arrayList1), this.spntime);
        } catch (Exception ignored) {}
    }
    @SuppressLint("ResourceType")
    public void showSpn(ArrayList<String> paramArrayList, Spinner paramSpinner) {
        paramSpinner.setAdapter(new ArrayAdapter<>(this, 17367049, paramArrayList));
    }

    public void showToast(final String msg) {
        runOnUiThread(() -> Toast.makeText(AddTimeMini.this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show());
    }

    public void showdialg() {
        if (!this.dialog.isShowing())
            this.dialog.show();
    }

    public void spnClick() {
        this.spntiet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = AddTimeMini.this.spntiet.getSelectedItem().toString();
                AddTimeMini.this.edttiet.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
        this.spntime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = AddTimeMini.this.spntime.getSelectedItem().toString();
                AddTimeMini.this.edttime.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
        this.spnthu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = AddTimeMini.this.spnthu.getSelectedItem().toString();
                AddTimeMini.this.edtthu.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
        this.spnBuoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = AddTimeMini.this.spnBuoi.getSelectedItem().toString();
                AddTimeMini.this.edtBuoi.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
        this.spnmon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = AddTimeMini.this.spnmon.getSelectedItem().toString();
                AddTimeMini.this.edtMonhoc.setText(str);
            }

            public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
    }

    public void thamchieu() {
        this.edtthu = findViewById(R.id.editText2);
        this.edtthu.setEnabled(false);
        this.edttiet = findViewById(R.id.editText3);
        this.edttime = findViewById(R.id.editTime);
        this.edtMonhoc = findViewById(R.id.editText4);
        this.edtBuoi = findViewById(R.id.editText5);
        this.edtBuoi.setEnabled(false);
        this.spnthu = findViewById(R.id.spthu);
        this.spntiet = findViewById(R.id.sptiet);
        this.spntime = findViewById(R.id.sptime);
        this.spnBuoi = findViewById(R.id.spnbuoi);
        this.spnmon = findViewById(R.id.spmon);
        this.btnSave = findViewById(R.id.button);
        this.dialog = new ProgressDialog(getApplicationContext());
        this.dialog.setCancelable(false);
        this.dao1 = new TimeTableMiniDAO(this);
    }
}

