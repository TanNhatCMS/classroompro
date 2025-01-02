package cms.tannhat.classroompro.timetable.Entities;

import java.io.Serializable;

public class TimeTableEntity implements Serializable {
    private String buoi;

    private String maso;

    private String monhoc;

    private String thu;

    private String tiet;

    private String time;
    public TimeTableEntity() {}

    public TimeTableEntity(String paramString1, String paramString2, String paramString3, String paramString4) {
        this.thu = paramString1;
        this.tiet = paramString2;
        this.monhoc = paramString3;
        this.buoi = paramString4;
    }
    public TimeTableEntity(String paramString1, String paramString2, String paramString3, String paramString4,  String paramString5) {
        this.thu = paramString1;
        this.tiet = paramString2;
        this.monhoc = paramString3;
        this.buoi = paramString4;
        this.time = paramString5;
    }
    public String getBuoi() {
        return this.buoi;
    }

    public String getMaso() {
        return this.maso;
    }

    public String getMonhoc() {
        return this.monhoc;
    }

    public String getThu() {
        return this.thu;
    }

    public String getTiet() {
        return this.tiet;
    }
    public String getTime() {
        return this.time;
    }
    public void setBuoi(String paramString) {
        this.buoi = paramString;
    }

    public void setMaso(String paramString) {
        this.maso = paramString;
    }

    public void setMonhoc(String paramString) {
        this.monhoc = paramString;
    }

    public void setThu(String paramString) {
        this.thu = paramString;
    }

    public void setTiet(String paramString) {
        this.tiet = paramString;
    }

    public void setTime(String paramString) {
        this.time = paramString;
    }
}
