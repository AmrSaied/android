package com.example.amrsaid.isksecurity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dd.CircularProgressButton;
import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.ble.BluetoothHandler;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;
import com.nineoldandroids.animation.ValueAnimator;

public class Home extends Fragment {
    CircularProgressButton progressButton;
    private BluetoothHandler bluetoothHandler;
    private UserInformationManger informationManger;
    public static Fragment newInstance(Context context) {
        Home f = new Home();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
         progressButton= (CircularProgressButton) root.findViewById(R.id.btnWithText);
        informationManger = new UserInformationManger(getActivity().getApplicationContext());
        progressButton.setProgress(0);

        bluetoothHandler = new BluetoothHandler(getActivity());
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothHandler.sendData("0");

                if (progressButton.getProgress() == 0) {
                    simulateErrorProgress(progressButton);
                } else {
                    progressButton.setProgress(0);
                }
            }
        });

        return root;
    }

    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }

}
