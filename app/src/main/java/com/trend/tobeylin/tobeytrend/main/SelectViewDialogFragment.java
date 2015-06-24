package com.trend.tobeylin.tobeytrend.main;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.trend.tobeylin.tobeytrend.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectViewDialogFragment extends DialogFragment {

    public static final String TAG = SelectViewDialogFragment.class.getSimpleName();
    public static final String BUNDLE_CURRENT_WIDTH = "BUNDLE_CURRENT_WIDTH";
    public static final String BUNDLE_CURRENT_HEIGHT = "BUNDLE_CURRENT_HEIGHT";

    private NumberPicker widthNumberPicker = null;
    private NumberPicker heightNumberPicker = null;
    private int oldWidth = 1;
    private int oldHeight = 1;

    public interface SelectViewDialogListener{

        void onConfirmClick(int oldWidth, int oldHeight, int newWidth, int newHeight);
        void onCancelClick();

    }

    public SelectViewDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getDataFromBundle();

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_select_view_dialog, null, false);
        widthNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_widthNumberPicker);
        widthNumberPicker.setMinValue(getResources().getInteger(R.integer.select_view_min_width));
        widthNumberPicker.setMaxValue(getResources().getInteger(R.integer.select_view_max_width));
        widthNumberPicker.setValue(oldWidth);
        heightNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_heightNumberPicker);
        heightNumberPicker.setMinValue(getResources().getInteger(R.integer.select_view_min_height));
        heightNumberPicker.setMaxValue(getResources().getInteger(R.integer.select_view_max_height));
        heightNumberPicker.setValue(oldHeight);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_view_dialog_title));
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.select_view_confirm_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmAction();
            }
        });
        builder.setNegativeButton(R.string.select_view_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelAction();
            }
        });

        return builder.create();
    }

    private void getDataFromBundle(){

        Bundle bundle = getArguments();
        oldWidth = bundle.getInt(BUNDLE_CURRENT_WIDTH);
        oldHeight = bundle.getInt(BUNDLE_CURRENT_HEIGHT);

    }

    private void confirmAction(){

        int newWidth = widthNumberPicker.getValue();
        int newHeight = heightNumberPicker.getValue();
        SelectViewDialogListener listener = (SelectViewDialogListener) getActivity();
        if(listener != null) {
            listener.onConfirmClick(oldWidth, oldHeight, newWidth, newHeight);
        }

    }

    private void cancelAction(){
        SelectViewDialogListener listener = (SelectViewDialogListener) getActivity();
        if(listener != null) {
            listener.onCancelClick();
        }
    }


}
