package com.trend.tobeylin.tobeytrend.main.view;


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
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectViewDialogFragment extends DialogFragment {

    public static final String TAG = SelectViewDialogFragment.class.getSimpleName();
    public static final String BUNDLE_CURRENT_COLUMN_COUNT = "BUNDLE_CURRENT_COLUMN_COUNT";
    public static final String BUNDLE_CURRENT_ROW_COUNT = "BUNDLE_CURRENT_ROW_COUNT";

    private NumberPicker columnNumberPicker = null;
    private NumberPicker rowNumberPicker = null;
    private int oldColumnCount = 1;
    private int oldRowCount = 1;
    private  SelectViewDialogListener listener = null;

    public interface SelectViewDialogListener{

        void onConfirmClick(int oldColumnCount, int oldRowCount, int newColumnCount, int newRowCount);
        void onCancelClick();

    }

    public SelectViewDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getDataFromBundle();
        setListener((SelectViewDialogListener) getActivity());

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_select_view_dialog, null, false);
        columnNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_widthNumberPicker);
        columnNumberPicker.setMinValue(getResources().getInteger(R.integer.select_view_min_column_count));
        columnNumberPicker.setMaxValue(getResources().getInteger(R.integer.select_view_max_column_count));
        columnNumberPicker.setValue(oldColumnCount);
        rowNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_heightNumberPicker);
        rowNumberPicker.setMinValue(getResources().getInteger(R.integer.select_view_min_row_count));
        rowNumberPicker.setMaxValue(getResources().getInteger(R.integer.select_view_max_row_count));
        rowNumberPicker.setValue(oldRowCount);

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
        oldColumnCount = bundle.getInt(BUNDLE_CURRENT_COLUMN_COUNT);
        oldRowCount = bundle.getInt(BUNDLE_CURRENT_ROW_COUNT);

    }

    public void setListener(SelectViewDialogListener listener) {
        this.listener = listener;
    }

    private void confirmAction(){

        int newWidth = columnNumberPicker.getValue();
        int newHeight = rowNumberPicker.getValue();
        if(listener != null) {
            listener.onConfirmClick(oldColumnCount, oldRowCount, newWidth, newHeight);
        }

    }

    private void cancelAction(){
        if(listener != null) {
            listener.onCancelClick();
        }
    }


}
