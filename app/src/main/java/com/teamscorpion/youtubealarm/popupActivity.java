package com.teamscorpion.youtubealarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Objects;

public class popupActivity extends DialogFragment {
    SeekBar seekHour, seekMinute;
    TextView txtHour, txtMinute;
    Button btn_ok, btn_cancel;

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_popup_alarm, null);

        builder.setView(view);

        seekHour = view.findViewById(R.id.seekHour);
        seekMinute = view.findViewById(R.id.seekMinute);
        txtHour = view.findViewById(R.id.txtHourVal);
        txtMinute = view.findViewById(R.id.txtMinuteVal);
        btn_ok = view.findViewById(R.id.btn_ok);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        seekHour.setProgress(0);
        seekMinute.setProgress(0);

        final Calendar[] rightNow = {Calendar.getInstance()};
        final int[] currentHourIn24Format = {rightNow[0].get(Calendar.HOUR)};
        rightNow[0] = Calendar.getInstance();
        currentHourIn24Format[0] = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format[0] > 3 & currentHourIn24Format[0] < 12){
            view.setBackgroundColor(Color.parseColor("#f3989d"));
            btn_ok.setBackgroundResource(R.drawable.m_ok_ripple);
            btn_cancel.setBackgroundResource(R.drawable.m_cancel_ripple);
            Drawable draw_hr= getResources().getDrawable(R.drawable.m_seek_bar, null);
            Drawable draw_min= getResources().getDrawable(R.drawable.m_seek_bar, null);
            seekHour.setProgressDrawable(draw_hr);
            seekMinute.setProgressDrawable(draw_min);
        }else if(currentHourIn24Format[0] > 11 & currentHourIn24Format[0] < 17){
            view.setBackgroundColor(Color.parseColor("#d63447"));
            btn_ok.setBackgroundResource(R.drawable.a_ok_ripple);
            btn_cancel.setBackgroundResource(R.drawable.a_cancel_ripple);
            Drawable draw_hr= getResources().getDrawable(R.drawable.a_seek_bar, null);
            Drawable draw_min= getResources().getDrawable(R.drawable.a_seek_bar, null);
            seekHour.setProgressDrawable(draw_hr);
            seekMinute.setProgressDrawable(draw_min);
        }else if(currentHourIn24Format[0] > 16 & currentHourIn24Format[0] < 21){
            view.setBackgroundColor(Color.parseColor("#febc6e"));
            btn_ok.setBackgroundResource(R.drawable.e_ok_ripple);
            btn_cancel.setBackgroundResource(R.drawable.e_cancel_ripple);
            Drawable draw_hr= getResources().getDrawable(R.drawable.e_seek_bar, null);
            Drawable draw_min= getResources().getDrawable(R.drawable.e_seek_bar, null);
            seekHour.setProgressDrawable(draw_hr);
            seekMinute.setProgressDrawable(draw_min);
        }else {
            view.setBackgroundColor(Color.parseColor("#202020"));
            btn_ok.setBackgroundResource(R.drawable.n_ok_ripple);
            btn_cancel.setBackgroundResource(R.drawable.n_cancel_ripple);
            Drawable draw_hr= getResources().getDrawable(R.drawable.n_seek_bar, null);
            Drawable draw_min= getResources().getDrawable(R.drawable.n_seek_bar, null);
            seekHour.setProgressDrawable(draw_hr);
            seekMinute.setProgressDrawable(draw_min);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = txtHour.getText().toString() + " : " + txtMinute.getText().toString();
                mOnInputSelected.sendInput(input);
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
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
