package cms.tannhat.classroompro.quizizz.model;

import android.view.View;
import android.webkit.WebView;

import androidx.recyclerview.widget.RecyclerView;

import cms.tannhat.classroompro.R;


public class MyViewHolder extends RecyclerView.ViewHolder{

    WebView Question;

    public MyViewHolder(View itemView) {
        super(itemView);
        Question= (WebView) itemView.findViewById(R.id.question);
    }

}
