package tn.medtech.recruitmentsystemapp.util;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

/**
 * @author Pratik Butani
 *
 * Main file for Date Picker.
 */
public class DatePickerUniversal implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private TextInputEditText mTextInputEditText;
    private Calendar mCalendar;
    private SimpleDateFormat mFormat;

    private int minDayOffset;

    /**
     * Constructor
     *
     * @param TextInputEditText your TextInputEditText
     * @param format   give your format in which you want date like dd/MM/yyyy
     * @param minDayOffset number of days to offset from current time
     */
    public DatePickerUniversal(TextInputEditText TextInputEditText, String format, int minDayOffset) {
        this.mTextInputEditText = TextInputEditText;
        mTextInputEditText.setOnFocusChangeListener(this);
        mTextInputEditText.setOnClickListener(this);
        mFormat = new SimpleDateFormat(format, Locale.getDefault());
        this.minDayOffset = minDayOffset * (1000 * 60 * 60 * 24);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            showPicker(view);
        }
    }

    @Override
    public void onClick(View view) {
        showPicker(view);
    }

    private void showPicker(View view) {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), this, year, month, day);
        // Disable past dates
        if (this.minDayOffset != 0) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + minDayOffset);
        } else {
            // setMinDate does not accept current time, so we subtract 1s
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.mTextInputEditText.setText(mFormat.format(mCalendar.getTime()));
    }
}