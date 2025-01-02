package cms.tannhat.classroompro.models.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.utils.Logs;

import com.google.gson.annotations.SerializedName;

public class ContactViewModel extends ViewModel {
    private final String TAG = "ContactViewModel";
    List<User> ContactList;
    ContactViewAdapter contactViewAdapter;
    @SuppressLint("StaticFieldLeak")
    Context context;
    public ContactViewModel(Context context) {
        this.context = context;
        ContactList = new ArrayList<>();
        contactViewAdapter = new ContactViewAdapter(context, ContactList);
        ContactList.add(createContact("Thầy Hồ Hồng Mộng", "Quản lý học sinh" , "0932776573"));
        ContactList.add(createContact("Thầy Trần Anh Tùng", "Giám Thị 1" , "0981187839"));
        ContactList.add(createContact("Thầy Trần Anh Tùng", "Giám Thị 2" , "0782546008"));
        ContactList.add(createContact("Thầy Bùi Hùng", "Toán (BM)" , "0911100031"));
        ContactList.add(createContact("Thầy Bùi Hùng", "Toán (BM)" , "0911100032"));
        ContactList.add(createContact("Thầy Nguyễn Văn Lâm", "Toán" , "0909200905"));
        ContactList.add(createContact("Thầy Trần Hửu Tính", "GDCD (BM)" , "0981187839"));
        ContactList.add(createContact("Thầy Nguyễn Thanh Long", "Vật Lý" , "0909686145"));
        ContactList.add(createContact("Thầy Đoàn Ngọc Trí", "Ngữ Văn" , "0988846686"));
        ContactList.add(createContact("Cô Đặng Thu Hiền", "Hóa Học" , "0903719821"));
        ContactList.add(createContact("Thầy Đặng Trọng Nghĩa", "Hóa Học (GVCN)" , "0903719821"));
        ContactList.add(createContact("Cô Ngô Thị Ngọc Ánh", "Sinh Học" , "0934414885"));
        ContactList.add(createContact("Cô Trương Thị Uyên", "Địa Lý" , "0399474279"));
        ContactList.add(createContact("Cô Nguyễn Hoài Thương", "Địa Lý (BM)" , "0965965380"));
        ContactList.add(createContact("Cô Nguyễn Anh Thư", "Tiếng Anh (BM)" , "0902002545"));
        ContactList.add(createContact("Thầy Lê Đình Hùng", "Lịch Sử (BM)" , "0934088446"));
        ContactList.add(createContact("Thầy Cung Hoài Phong", "Lịch Sử" , "0764099028"));
    }

    public ContactViewAdapter getContactViewAdapter() {
        return contactViewAdapter;
    }
    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            byte[] buffer;
            InputStream inputStream = null;
            try{
                inputStream = context.getAssets() .open("contact.json");
                int size = inputStream.available();
                buffer = new byte[size];
                // read values in the byte array
                inputStream.read(buffer);

            } finally {
                assert inputStream != null;
                inputStream.close();
            }
            // convert byte to string
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logs.e(TAG,"bug read contact.json",e);
            return json;
        }
        return json;
    }
    private ContactViewModel.User createContact(String name, String subname, String number) {
   ContactViewModel.User infomode = new ContactViewModel.User(name,subname,number,null, false     );
   //     infomode.Name = name;
    //    infomode.SubName = subname; String name,
        //    String sname,
        //    String phone,
        //    String email,
        //    boolean zalo
       // infomode.Phone = number;
        return infomode;
    }
    public class Contact {
        private String version;
        private List<User> list;

        public Contact(String version, List<User> user) {
            this.version = version;
            this.list = user;
        }
    }
    static class User  {
        @SerializedName(value = "Name")
        private String Name;
        @SerializedName(value = "Email")
        private String Email;
        @SerializedName(value = "SubName")
        private String SubName;
        @SerializedName(value = "Phone")
        private String Phone;
        @SerializedName(value = "isZalo")
        private boolean isZalo;

        public User(String name, String sname,String phone, String email, boolean zalo) {
            this.Name = name;
            this.SubName = sname;
            this.Phone = phone;
            this.Email = email;
            this.isZalo = zalo;

        }
        public String getName() {
            return Name;
        }
        public void setName(String name) {
            this.Name = name;
        }
        public String getSubName() {
            return SubName;
        }
        public void setSubName(String sname) {
            this.SubName = sname;
        }
        public String getPhone() {
            return Phone;
        }
        public void setPhone(String phone) {
            this.Phone = phone;
        }
        public String getEmail() {
            return Email;
        }
        public void setEmail(String email) {
            this.Email = email;
        }
        public boolean getZalo() {
            return isZalo;
        }
        public void setZalo(boolean zalo) {
            this.isZalo = zalo;
        }

    }

    private static class ContactViewHolder extends RecyclerView.ViewHolder {
        int mPosition;
        TextView textName, textSubName , textPhone;
        LinearLayout linearLayout;
        public ContactViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            textSubName = itemView.findViewById(R.id.textViewSubName);
            textPhone = itemView.findViewById(R.id.textViewPhone);
            linearLayout = itemView.findViewById(R.id.linearLayoutContactInfo);
        }
    }

    static class ContactViewAdapter extends  RecyclerView.Adapter<ContactViewHolder>
            implements View.OnClickListener{


        private final Context context;
        private final LayoutInflater mLayoutInflater;
        private final List<User> mInfoMode;

        public ContactViewAdapter(Context context, List<User> info) {
            this.mInfoMode = info;
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public void onClick(View v) {
            int position = ((ContactViewHolder)v.getTag()).mPosition;
            User info = mInfoMode.get(position);
            String phone = info.Phone;
            if(!TextUtils.isEmpty(phone)) {
                String dial = "tel:" + phone;
                Toast.makeText(context, "Gọi đến " + info.Name + ". Số điện thoại: " + phone, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

            View v = mLayoutInflater.inflate(R.layout.item_contact, viewGroup, false);
            ContactViewHolder viewHolder = new ContactViewHolder(v);
            v.setOnClickListener(this);
            v.setTag(viewHolder);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ContactViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            final User info = mInfoMode.get(position);
            viewHolder.textName.setText(info.Name);
            viewHolder.textSubName.setText(info.SubName);
            viewHolder.textPhone.setText(info.Phone);
            viewHolder.mPosition = position;
        }

        @Override
        public int getItemCount() {
            return mInfoMode.size();
        }
    }
}