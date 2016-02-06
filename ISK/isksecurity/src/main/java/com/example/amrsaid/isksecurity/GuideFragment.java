package com.example.amrsaid.isksecurity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class GuideFragment extends Fragment {
    private int bgRes;
    private ImageView imageView;
    Button button2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgRes = getArguments().getInt("data");
        Toast.makeText(getActivity(), "" + getArguments().get("data"), Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_guide, container, false);
        if (getArguments().getInt("data") == 1) {
            return inflater.inflate(R.layout.fragment_guide, container, false);
        } else if (getArguments().getInt("data") == 2) {
            return inflater.inflate(R.layout.fragment_guide, container, false);

        } else if (getArguments().getInt("data") == 3) {
            return inflater.inflate(R.layout.fragment_guide, container, false);

        } else if (getArguments().getInt("data") == 4) {
            return inflater.inflate(R.layout.fragment_guide, container, false);
        } else {
//            return inflater.inflate(R.layout.fragment_guide, container, false);
        }
        return null;
    }

    @Override


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) getView().findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.bg1);
    }
}
