package sugintas.com.noter.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import sugintas.com.noter.R;
import sugintas.com.noter.data.Note;
import sugintas.com.noter.data.SQLHelper;
import sugintas.com.noter.utils.TextChangedWatcher;

public class CreateNoteActivity extends AppCompatActivity {

    EditText etNoteTitle;
    EditText etNoteBody;
    SQLHelper SQLHelper;
    TextWatcher textChangedWatcher;
    DateFormat dateFormat = DateFormat.getDateTimeInstance();
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLHelper = new SQLHelper(this);
        textChangedWatcher = new TextChangedWatcher(this);
        etNoteTitle = (EditText) findViewById(R.id.create_note_title);
        etNoteBody = (EditText) findViewById(R.id.create_note_body);

        etNoteBody.addTextChangedListener(textChangedWatcher);
        etNoteTitle.addTextChangedListener(textChangedWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                saveNoteToDb();
                break;
            case R.id.action_cancel:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.action_send:
                sendNoteAsEmail();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Overriding back button press, to save note
     */
    @Override
    public void onBackPressed() {
        saveNoteToDb();
    }

    public void saveNoteToDb() {
        if (titleAndBodyFieldsIsNoteEmpty()) {
            SQLHelper.addNote(new Note(etNoteTitle.getText().toString(),
                    etNoteBody.getText().toString(),
                    dateFormat.format(cal.getTime())));
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean titleAndBodyFieldsIsNoteEmpty() {
        return etNoteTitle.getText().length() != 0 || etNoteBody.getText().length() != 0;
    }

    private void sendNoteAsEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + ""));
        if (titleAndBodyFieldsIsNoteEmpty()) {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, etNoteTitle.getText().toString());
            emailIntent.putExtra(Intent.EXTRA_TEXT, etNoteBody.getText().toString());
        }
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

}
