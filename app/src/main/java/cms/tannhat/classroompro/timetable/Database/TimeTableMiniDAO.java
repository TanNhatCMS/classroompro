package cms.tannhat.classroompro.timetable.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

public class TimeTableMiniDAO extends database {
    public TimeTableMiniDAO(Context paramContext) {
        super(paramContext);
    }

    public void add(TimeTableEntity paramTimeTableEntity) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(id, randomByteString(8) + "");
        contentValues.put(thu, paramTimeTableEntity.getThu() + "");
        contentValues.put(tiet, paramTimeTableEntity.getTiet() + "");
        contentValues.put(monhoc, paramTimeTableEntity.getMonhoc() + "");
        contentValues.put(buoi, paramTimeTableEntity.getBuoi() + "");
        contentValues.put(time, paramTimeTableEntity.getTime() + "");
        sQLiteDatabase.insert("timeTableMini", null, contentValues);
        sQLiteDatabase.close();
    }

    public boolean delete(String paramString) {
        return (getWritableDatabase().delete("timeTableMini", "id like '" + paramString + "'", null) > 0);
    }

    public ArrayList<TimeTableEntity> getAll() {
        return getBySQL("SELECT * FROM timeTableMini");
    }

    public ArrayList<TimeTableEntity> getBySQL(String paramString) {
        ArrayList<TimeTableEntity> arrayList = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().rawQuery(paramString, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                TimeTableEntity timeTableEntity = new TimeTableEntity();
                timeTableEntity.setMaso(cursor.getString(0));
                timeTableEntity.setThu(cursor.getString(1));
                timeTableEntity.setTiet(cursor.getString(2));
                timeTableEntity.setMonhoc(cursor.getString(3));
                timeTableEntity.setBuoi(cursor.getString(4));
                timeTableEntity.setTime(cursor.getString(5));
                arrayList.add(timeTableEntity);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public ArrayList<TimeTableEntity> getByThu(String paramString) {
        return getBySQL("SELECT * FROM timeTableMini where thu like '" + paramString + "'");
    }

    public ArrayList<TimeTableEntity> getByThuvaBuoi(String paramString1, String paramString2) {
        return getBySQL("SELECT * FROM timeTableMini where thu like '" + paramString1 + "' and buoi like '" + paramString2 + "'");
    }

    public int getCount() {
        @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM timeTableMini", null);
        return (cursor == null) ? 0 : cursor.getCount();
    }

    String randomByteString(int i) {
        char[] arrayOfChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder((100000 + random.nextInt(900000)) + "-");
        for (int paramInt = 0; paramInt < i; paramInt++)
            stringBuilder.append(arrayOfChar[random.nextInt(arrayOfChar.length)]);
        return stringBuilder.toString();
    }

    public void update(TimeTableEntity paramTimeTableEntity) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(id, paramTimeTableEntity.getMaso() + "");
        contentValues.put(thu, paramTimeTableEntity.getThu() + "");
        contentValues.put(tiet, paramTimeTableEntity.getTiet() + "");
        contentValues.put(monhoc, paramTimeTableEntity.getMonhoc() + "");
        contentValues.put(buoi, paramTimeTableEntity.getBuoi() + "");
        contentValues.put(time, paramTimeTableEntity.getTime() + "");
        sQLiteDatabase.update(timeTableMini, contentValues, "id = '" + paramTimeTableEntity.getMaso() + "'", null);
        sQLiteDatabase.close();
    }
}
