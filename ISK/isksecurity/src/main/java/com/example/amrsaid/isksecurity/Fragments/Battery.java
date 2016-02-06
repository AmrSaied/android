package com.example.amrsaid.isksecurity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;


public class Battery extends Fragment {
    private UserInformationManger informationManger;
    public static Fragment newInstance(Context context) {
        Battery f = new Battery();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_battery, null);
        informationManger = new UserInformationManger(getActivity().getApplicationContext());
        return root;
    }

}
