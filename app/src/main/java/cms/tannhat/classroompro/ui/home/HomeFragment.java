package cms.tannhat.classroompro.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;


import cms.tannhat.classroompro.base.BaseFragment;
import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.databinding.FragmentHomeBinding;
//import cms.tannhat.classroom.databinding.ImageTkbBinding;
import cms.tannhat.classroompro.webview.CustomTabActivityHelper;
import cms.tannhat.classroompro.webview.WebviewFallback;
import cms.tannhat.classroompro.quizizz.ui.QuizizzActivity;
import cms.tannhat.classroompro.shub.ui.ShubActivity;
import cms.tannhat.classroompro.timetable.StartActivity;

public class HomeFragment extends BaseFragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private FragmentHomeBinding binding;
    AlertDialog.Builder alertDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
      //  AppBarMainBinding appBarMainBinding = new AppBarMainBinding;`
        alertDialog = new AlertDialog.Builder(getContext());
        final Button buttonTkb = binding.buttonTkb;
        final Button buttontWeb = binding.buttonWeb;
        final Button buttonQuizizz = binding.buttonQuizizz;
        final Button buttonShub = binding.buttonShub;
       buttonTkb.setOnClickListener(this);
        buttonShub.setOnClickListener(this);
        buttonQuizizz.setOnClickListener(this);
        buttontWeb.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if(v != null) {
            Button b = (Button) v;
            AlertDialog build = alertDialog.create();
            switch(b.getId()) {
/*
                case R.id.button_tkb:
                    build.setTitle(getString(R.string.tkb));
                    build.setIcon(R.drawable.logotkb);
                    ImageTkbBinding ImageTkb = ImageTkbBinding.inflate(getLayoutInflater());
                    build.setView(ImageTkb.getRoot());
                    build.setButton(AlertDialog.BUTTON_NEUTRAL,  "OK", this);
                    build.show();
                    break;

 */
                case R.id.button_tkb:
                    requireActivity().startActivity( new Intent(requireActivity(), StartActivity.class));
                    break;
                case R.id.button_shub:
                    requireActivity().startActivity( new Intent(requireActivity(), ShubActivity.class));
                    break;
                case R.id.button_quizizz:
                    requireActivity().startActivity( new Intent(requireActivity(), QuizizzActivity.class));
                    break;
                case R.id.button_web:
                    String host = "http://gdnngdtxq10.edu.vn/";
                    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                    CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent, Uri.parse(host), new WebviewFallback());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + b.getId());
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}