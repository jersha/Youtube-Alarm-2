package com.teamscorpion.youtubealarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class popupActivity extends DialogFragment {
    SeekBar seekHour, seekMinute;
    TextView txtHour, txtMinute;
    Button btn_ok, btb_cancel;

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_popup, null);

        builder.setView(view);

        seekHour = view.findViewById(R.id.seekHour);
        seekMinute = view.findViewById(R.id.seekMinute);
        txtHour = view.findViewById(R.id.txtHourVal);
        txtMinute = view.findViewById(R.id.txtMinuteVal);
        btn_ok = view.findViewById(R.id.btn_ok);
        btb_cancel = view.findViewById(R.id.btn_cancel);

        seekHour.setProgress(0);
        seekMinute.setProgress(0);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = txtHour.getText().toString() + " : " + txtMinute.getText().toString();
                mOnInputSelected.sendInput(input);
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        btb_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        seekHour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String hour = String.valueOf(i);
                if(hour.length() < 2){
                    txtHour.setText("0" + hour);
                }else{
                    txtHour.setText(hour);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekMinute.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String minute = String.valueOf(i);
                if(minute.length() < 2){
                    txtMinute.setText("0" + minute);
                }else{
                    txtMinute.setText(minute);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Listener");
        }
    }
}