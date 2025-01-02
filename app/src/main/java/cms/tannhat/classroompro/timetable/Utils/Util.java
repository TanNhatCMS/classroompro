package cms.tannhat.classroompro.timetable.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

import cms.tannhat.classroompro.timetable.Database.TimeTableMiniDAO;
import cms.tannhat.classroompro.timetable.Entities.TimeTableEntity;

public class Util {

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
    /**
     * Lọc từ khoá
     * @param key chuỗi đầu vào
     * @return [String] đã lọc
     */
    @NonNull
    public static String removeKey(String key) {
        key = key.toLowerCase()
                .replace("18:00-18:40", "")
                .replace("18:40-19:20", "")
                .replace("19:40-20:20","")
                .replace("20:20-21:00","")
                .replace("21:00-21:40","")
                .replace("19:20-19:40","")
                .replace("-"," đến ");
        return key;
    }

    /**
     * Danh sách ngày trong tuần
     * @return [ArrayList<String>] Chủ Nhật, Thứ 2, Thứ 3, Thứ 4, Thứ 5, Thứ 6, Thứ 7
     */
    public static ArrayList<String> ListDay() {
        ArrayList<String> List = new ArrayList<>();
        List.add("Chủ Nhật");//0
        List.add("Thứ 2");//1
        List.add("Thứ 3");//2
        List.add("Thứ 4");//3
        List.add("Thứ 5");//4
        List.add("Thứ 6");//5
        List.add("Thứ 7");//6
        return List;
    }
    /**
     * Danh sách ngày trong tuần của tabs
     * @return [ArrayList<String>] Thứ 2, Thứ 3, Thứ 4, Thứ 5, Thứ 6, Thứ 7, Chủ Nhật
     */
    public static ArrayList<String> ListDayTab() {
        ArrayList<String> List = new ArrayList<>();
        List.add("Thứ 2");//0
        List.add("Thứ 3");//1
        List.add("Thứ 4");//2
        List.add("Thứ 5");//3
        List.add("Thứ 6");//4
        List.add("Thứ 7");//5
        List.add("Chủ Nhật");//6
        return List;
    }
    /**
     * Danh sách môn học
     * @return [ArrayList<String>] Sinh Hoạt CN, Ngữ Văn, Lịch sử, Hóa Học, Địa Lý, Vật Lý, Sinh Học, Toán, Ra Chơi
     */
    public static ArrayList<String> ListMon() {
        ArrayList<String> List = new ArrayList<>();
        List.add("Sinh Hoạt Chủ Nhiệm");//0
        List.add("Ngữ Văn");//1
        List.add("Lịch Sử");//2
        List.add("Hóa Học");//3
        List.add("Địa Lý");//4
        List.add("Vật Lý");//5
        List.add("Sinh Học");//6
        List.add("Toán");//7
        List.add("Ra Chơi");//8
        return List;
    }
    public static String getSpeech(Context context, String title) {
        TimeTableMiniDAO dao = new TimeTableMiniDAO(context);
        StringBuilder str = new StringBuilder();
        try {
            ArrayList<TimeTableEntity> Day = dao.getByThu(title);
            str.append(title);
            if (Day.size() > 0) {
                ArrayList<TimeTableEntity> Bsang = dao.getByThuvaBuoi(title, "Buổi sáng");
                if (Bsang.size() > 0) {
                    str.append("Buổi sáng ");
                    str.append(Util.TietMonHoc(Bsang));
                    str.append(" ");
                }
                ArrayList<TimeTableEntity> Bchieu = dao.getByThuvaBuoi(title, "Buổi chiều");
                if (Bchieu.size() > 0) {
                    str.append("Buổi chiều ");
                    str.append(Util.TietMonHoc(Bchieu));
                    str.append(" ");
                }
                ArrayList<TimeTableEntity> Btoi = dao.getByThuvaBuoi(title, "Buổi tối");
                if (Btoi.size() > 0) {
                    str.append("Buổi tối ");
                    str.append(Util.TietMonHoc(Btoi));
                    str.append(" ");
                }
            }else{
                str.append("Thời Khoá biểu trống");
            }
            return str.toString();
        } catch (Exception ex) {
            Log.d("Speech", ex.toString() );
        }
        return "";
    }

    /**
     * Danh sách Buổi
     * @return [ArrayList<String>] Buổi Sáng, Buổi Chiều, Buổi Tối
     */
    public static ArrayList<String> ListBuoi() {
        ArrayList<String> List = new ArrayList<>();
        List.add("Buổi Sáng");//0
        List.add("Buổi Chiều");//1
        List.add("Buổi Tối");//2
        return List;
    }

    /**
     * Danh sách Tiết
     * @return [ArrayList<String>] 19:20-19:40, Tiết 1 18:00-18:40, Tiết 2 18:40-19:20, Tiết 3 19:40-20:20, Tiết 4 20:20-21:00, Tiết 5 21:00-21:40
     */
    public static ArrayList<String> ListTiet() {
        ArrayList<String> List = new ArrayList<>();
        List.add(" ");//0
        List.add("Tiết 1");//1
        List.add("Tiết 2");//2
        List.add("Tiết 3");//3
        List.add("Tiết 4");//4
        List.add("Tiết 5");//5
        return List;
    }
    public static ArrayList<String> ListTime() {
        ArrayList<String> List = new ArrayList<>();
        List.add("19:20-19:40");//0
        List.add("18:00-18:40");//1
        List.add("18:40-19:20");//2
        List.add("19:40-20:20");//3
        List.add("20:20-21:00");//4
        List.add("21:00-21:40");//5
        return List;
    }
    public static int getDay2Page(){
        int page;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
            default:
                page = 0;
                break;
            case Calendar.TUESDAY:
                page = 1;
                break;
            case Calendar.WEDNESDAY:
                page = 2;
                break;
            case Calendar.THURSDAY:
                page = 3;
                break;
            case Calendar.FRIDAY:
                page = 4;
                break;
            case Calendar.SATURDAY:
                page = 5;
                break;
            case Calendar.SUNDAY:
                page = 6;
                break;
        }
        return page;
    }
    public static String getPage2Day(int page) {
        int num;
        ArrayList<String> Lthu = Util.ListDay();
        switch(page){
            case 0://Thứ 2
            default:
                num=1;
                break;
            case 1://Thứ 3
                num=2;
                break;
            case 2://Thứ 4
                num=3;
                break;
            case 3://Thứ 5
                num=4;
                break;
            case 4://Thứ 6
                num=5;
                break;
            case 5://Thứ 7
                num=6;
                break;
            case 6://Chủ Nhật
                num=0;
                break;
        }
        return Lthu.get(num);
    }
    public static void setupDB(Context context){
        TimeTableMiniDAO dao = new TimeTableMiniDAO(context);
        if(dao.getCount()==0){
            // Thu 2
            dao.add(getTimeTableEntity(2, 1, 2));   // lịch sử
            dao.add(getTimeTableEntity(2, 2, 2));    // lịch sử
            dao.add(getTimeTableEntity(2, 0, 8)); // Ra Chơi
            dao.add(getTimeTableEntity(2, 3, 1)); //Ngữ Văn
            dao.add(getTimeTableEntity(2, 4, 1)); // Ngữ Văn
            dao.add(getTimeTableEntity(2, 5, 0)); // SHCN
// Thu 3
            dao.add(getTimeTableEntity(3, 1, 4)); // Địa
            dao.add(getTimeTableEntity(3, 2, 5)); // Lý
            dao.add(getTimeTableEntity(3, 0, 8)); //  Ra Chơi
            dao.add(getTimeTableEntity(3, 3, 5)); // Lý
            dao.add(getTimeTableEntity(3, 4, 7)); // Toán
            dao.add(getTimeTableEntity(3, 5, 7)); // Toán
// Thu 4
            dao.add(getTimeTableEntity(4, 1, 3)); //Hoá
            dao.add(getTimeTableEntity(4, 2, 3)); // Hoá
            dao.add(getTimeTableEntity(4, 0, 8)); // Hoá
            dao.add(getTimeTableEntity(4, 3, 1)); //Ngữ Văn
            dao.add(getTimeTableEntity(4, 4, 1));   //Ngữ Văn
            dao.add(getTimeTableEntity(4, 5, 1));   //Ngữ Văn
//Thu 5
            dao.add(getTimeTableEntity(5, 1, 2)); // lịch sử
            dao.add(getTimeTableEntity(5, 2, 7)); // Toán
            dao.add(getTimeTableEntity(5, 0, 8)); // ra choi
            dao.add(getTimeTableEntity(5, 3, 7)); // Toán
            dao.add(getTimeTableEntity(5, 4, 6)); //Sinh Học
            dao.add(getTimeTableEntity(5, 5, 6));//Sinh Học

//Thu 6
            dao.add(getTimeTableEntity(6, 1, 4));  //Địa
            dao.add(getTimeTableEntity(6, 2, 4)); //Địa
            dao.add(getTimeTableEntity(6, 0, 8)); // Ra Chơi
            dao.add(getTimeTableEntity(6, 3, 7)); //Toán
            dao.add(getTimeTableEntity(6, 4, 7)); //Toán
            dao.add(getTimeTableEntity(6, 5, 7));   //Toán
        }
    }
    private static TimeTableEntity getTimeTableEntity(int thu, int tiet, int mon){
        ArrayList<String> Lthu = Util.ListDay();
        ArrayList<String> Lmon = Util.ListMon();
        ArrayList<String> Lbuoi = Util.ListBuoi();
        ArrayList<String> Ltiet = Util.ListTiet();
        ArrayList<String> Ltime = Util.ListTime();
        return new TimeTableEntity(Lthu.get((thu-1)), Ltiet.get(tiet), Lmon.get(mon), Lbuoi.get(2), Ltime.get(tiet));
    }
    public static String TietMonHoc(ArrayList<TimeTableEntity> arrayList) {
        StringBuilder Str = new StringBuilder();
        for (int paramInt = 0; paramInt < arrayList.size(); paramInt++) {
            if (!arrayList.get(paramInt).getMonhoc().contains("Ra Chơi")) {
                Str.append(Util.removeKey(arrayList.get(paramInt).getTiet()));
                Str.append(" môn ");
                Str.append(arrayList.get(paramInt).getMonhoc());
                Str.append(" ");
            }
        }
        return Str.toString();
    }
}
