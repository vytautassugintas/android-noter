package sugintas.com.noter.utils;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import sugintas.com.noter.R;

/**
 * Text watcher to change home button icon if text was changed
 */
public class TextChangedWatcher implements TextWatcher {

    AppCompatActivity appCompatActivity;

    public TextChangedWatcher(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        changeActionBarHomeIcon();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeActionBarHomeIcon();
    }

    @Override
    public void afterTextChanged(Editable s) {
        changeActionBarHomeIcon();
    }

    private void changeActionBarHomeIcon() {
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check_white_24dp);
        }
    }

}
