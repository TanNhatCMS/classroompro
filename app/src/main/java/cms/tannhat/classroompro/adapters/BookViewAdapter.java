package cms.tannhat.classroompro.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cms.tannhat.classroompro.view.PDFViewActivity;
import cms.tannhat.classroompro.R;

public class BookViewAdapter extends RecyclerView.Adapter<BookViewAdapter.BookViewHolder>
        implements View.OnClickListener {


    private final Context context;
    private final LayoutInflater mLayoutInflater;
    private final List<BookData> mInfoMode;

    public BookViewAdapter(Context context, List<BookData> info) {
        this.mInfoMode = info;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onClick(View v) {
        int position = ((BookViewHolder) v.getTag()).mPosition;
        BookData book = mInfoMode.get(position);
        String bookID = book.ID;
        Intent intent = new Intent(context, PDFViewActivity.class);
        intent.putExtra(PDFViewActivity.EXTRA_PDF, bookID);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View v = mLayoutInflater.inflate(R.layout.item_book, viewGroup, false);
        BookViewHolder viewHolder = new BookViewHolder(v);
        v.setOnClickListener(this);
        v.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        final BookData info = mInfoMode.get(position);
        viewHolder.textName.setText(info.Name);
        viewHolder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mInfoMode.size();
    }

    public static class BookData {
        public String Name, ID;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        int mPosition;
        TextView textName;
        LinearLayout linearLayout;
        public BookViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            linearLayout = itemView.findViewById(R.id.linearLayoutBook);
        }
    }
}
