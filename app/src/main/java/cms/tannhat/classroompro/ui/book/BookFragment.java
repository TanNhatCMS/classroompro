package cms.tannhat.classroompro.ui.book;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cms.tannhat.classroompro.base.BaseFragment;
import cms.tannhat.classroompro.databinding.FragmentBookBinding;

public class BookFragment extends BaseFragment {
    private @NonNull FragmentBookBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = getContext();
        BookViewModel bookViewModel = new BookViewModel(context);
         RecyclerView recyclerView = binding.listbook;
        recyclerView.setAdapter(bookViewModel.getBookViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}