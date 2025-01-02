package cms.tannhat.classroompro.ui.contact;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cms.tannhat.classroompro.base.BaseFragment;
import cms.tannhat.classroompro.databinding.FragmentContactBinding;
import cms.tannhat.classroompro.models.contact.ContactViewModel;

public class ContactFragment extends BaseFragment {
    private FragmentContactBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = getContext();
        ContactViewModel contactViewModel = new ContactViewModel(context);
         RecyclerView recyclerView = binding.listcontact;
        recyclerView.setAdapter(contactViewModel.getContactViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}