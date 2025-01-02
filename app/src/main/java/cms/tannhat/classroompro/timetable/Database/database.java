package cms.tannhat.classroompro.timetable.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class database extends SQLiteOpenHelper {
    public static final String buoi = "buoi";

    public static final String id = "id";

    public static final String monhoc = "monhoc";

    public static final String thu = "thu";

    public static final String tiet = "tiet";

    public static final String time = "time";

    public static final String timeTableMini = "timeTableMini";

    public database(Context paramContext) {
        super(paramContext, "TimeTable", null, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("CREATE TABLE timeTableMini (id TEXT PRIMARY KEY, thu TEXT, tiet TEXT, monhoc TEXT, buoi TEXT, time TEXT)");
    }
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS timeTableMini");
        onCreate(paramSQLiteDatabase);
    }
}
