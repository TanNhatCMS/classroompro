package cms.tannhat.classroompro.quizizz.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cms.tannhat.alphabetsindexfastscrollrecycler.Helpers;
import cms.tannhat.alphabetsindexfastscrollrecycler.utility.AlphabetItem;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.quizizz.ui.QuizizzActivity;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>   implements SectionIndexer {

    Context c;
    ArrayList<Model> users;

    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private HashMap<Integer, Integer> sectionsTranslator = new HashMap<>();
    private ArrayList<Integer> mSectionPositions;

    public MyAdapter(Context c, QuizizzActivity mainActivity, ArrayList<Model> data) {
        this.c = c;
        this.users = data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort( this.users, Comparator.comparing(Model::getQuestion));
        }
        List<AlphabetItem> mAlphabetItems = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            String name = users.get(i).getKeywords();
            if (name == null || name.trim().isEmpty()){
                continue;
            } else {
                mAlphabetItems.add(new AlphabetItem(i,  name, false));
            }
        }
        mainActivity.updateData(mAlphabetItems);
    }

    // method for filtering our recyclerview items.
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Model> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        users = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {

        List<String> sections = new ArrayList<>();
        ArrayList<String> alphabetFull = new ArrayList<>();

        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = users.size(); i < size; i++) {
            String section = users.get(i).getNid();
            if (!sections.contains(section)) {
                alphabetFull.add(section);
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        for (int i = 0; i < mSections.length(); i++) {
            //alphabetFull.add(String.valueOf(mSections.charAt(i)));
        }

        sectionsTranslator = Helpers.Companion.sectionsHelper(sections, alphabetFull);

        return alphabetFull.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionsTranslator.get(sectionIndex));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Model user= users.get(position);

        final String answers=user.getAnswers();
        final String question=user.getQuestion();
        String js = "<!DOCTYPE html>" +
                "<html lang=\"en\" data-head-attrs=\"lang\" class=\"translated-ltr\">" +
                "<head>" +
                "" +
                "<meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +



                "</head>\n" +
                "<body aria-hidden=\"false\" style=\"padding: 0px;\">" +
                "<div id=\"__nuxt\" style=\"height: auto !important;\">" +
                "<div class=\"h-full\" style=\"height: auto !important;\">" +
                "<main class=\"h-full\">" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/katex@0.15.1/dist/katex.min.css\">" +
                "<div class=\"rounded-xl shadow mx-auto max-w-5xl w-full cursor-pointer bg-white hover:shadow border border-gray-200 p-5 mb-10\">";
String end =
        "</main>" +
                "</div></div></body>" +
        "</html>";
        //BIND
        holder.Question.loadData(js+"  <div class=\"flex flex-col gap-4\"><div class=\"w-full\">" +
                " <h5 class=\"mb-2 text-2xl font-bold text-gray-900\">"+ question +"</h5> <div class=\"flex w-full items-center justify-center\"></div></div>"+ answers +"  </div></div>\n" +
                "    \n" +
                "    <div class=\"flex w-full items-center justify-center\"></div>" +
                "" +
                "</div>"+ end, "text/html", "UTF-8");

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
