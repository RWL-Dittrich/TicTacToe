package com.saxion.robindittrich.tictactoe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.saxion.robindittrich.tictactoe.R;


public class InputView extends LinearLayout {

    private TextView mTitle;
    private EditText mEditText;

    public InputView(Context context) {
        super(context);
        init();
    }

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.input_view, this);

        mTitle = findViewById(R.id.tvInputTitle);
        mEditText = findViewById(R.id.etInputEditText);
    }

    public void setmTitle(String title) {
        mTitle.setText(title);
    }

    public void setError(String error) {
        mEditText.setError(error);
    }

    public void setmEditText(String text) {
        mEditText.setText(text);
    }

    public String getmEditTextString() {
        return mEditText.getText().toString();
    }
}