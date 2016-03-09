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

import sugintas.com.noter.R;
import sugintas.com.noter.data.Note;
import sugintas.com.noter.data.SQLHelper;
import sugintas.com.noter.utils.TextChangedWatcher;

public class EditNoteActivity extends AppCompatActivity {

    TextWatcher textChangedWatcher;
    EditText etNoteTitle;
    EditText etNoteBody;
    Intent editIntent;
    Note noteToEdit;
    SQLHelper SQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLHelper = new SQLHelper(this);
        textChangedWatcher = new TextChangedWatcher(this);

        editIntent = getIntent();
        noteToEdit = SQLHelper.getNoteById(editIntent.getIntExtra("ID", 0));

        etNoteTitle = (EditText) findViewById(R.id.edit_note_title);
        etNoteBody = (EditText) findViewById(R.id.edit_note_body);

        etNoteTitle.setText(noteToEdit.getTitle());
        etNoteBody.setText(noteToEdit.getBody());

        etNoteTitle.addTextChangedListener(textChangedWatcher);
        etNoteBody.addTextChangedListener(textChangedWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_existing_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                updateNote();
                break;
            case R.id.action_delete:
                deleteNote();
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
     * Overriding back button press, to update note
     */
    @Override
    public void onBackPressed() {
        updateNote();
    }

    public void updateNote() {
        noteToEdit.setTitle(etNoteTitle.getText().toString());
        noteToEdit.setBody(etNoteBody.getText().toString());
        SQLHelper.updateNote(noteToEdit);
        startActivity(new Intent(this, MainActivity.class));
    }

    private void deleteNote() {
        SQLHelper.deleteNote(SQLHelper.getNoteById(editIntent.getIntExtra("ID", 0)));
        startActivity(new Intent(this, MainActivity.class));
    }

    private void sendNoteAsEmail() {
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

    private boolean titleAndBodyFieldsIsNoteEmpty() {
        return etNoteTitle.getText().length() != 0 || etNoteBody.getText().length() != 0;
    }
}
