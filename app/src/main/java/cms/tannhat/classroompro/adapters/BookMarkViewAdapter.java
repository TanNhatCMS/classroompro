package cms.tannhat.classroompro.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class BookMarkViewAdapter extends RecyclerView.Adapter<BookMarkViewAdapter.BookMarkViewHolder>
        implements View.OnClickListener {

    private final PDFViewActivity pdfViewActivity;
    private final LayoutInflater mLayoutInflater;
    private final List<BookMarkData> mInfoMode;

    public BookMarkViewAdapter(PDFViewActivity pdfViewActivity, Context context, List<BookMarkData> info) {
        this.pdfViewActivity = pdfViewActivity;
        this.mInfoMode = info;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onClick(View v) {
        int position = ((BookMarkViewHolder) v.getTag()).mPosition;
        BookMarkData book = mInfoMode.get(position);
        pdfViewActivity.pdfView.jumpTo(book.page, true);
    }

    @NonNull
    @Override
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = mLayoutInflater.inflate(R.layout.item_book_mark, viewGroup, false);
        BookMarkViewHolder viewHolder = new BookMarkViewHolder(v);
        v.setOnClickListener(this);
        v.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookMarkViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        final BookMarkData info = mInfoMode.get(position);
        viewHolder.textName.setText(info.Name);
        viewHolder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mInfoMode.size();
    }

    public static class BookMarkViewHolder extends RecyclerView.ViewHolder {
        int mPosition;
        TextView textName;
        LinearLayout linearLayout;

        public BookMarkViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            linearLayout = itemView.findViewById(R.id.linearLayoutBook);
        }
    }

    public static class BookMarkData {
        public String Name;
        public int page;
    }
}
