package sugintas.com.noter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import sugintas.com.noter.R;
import sugintas.com.noter.adapters.NoteListAdapter;
import sugintas.com.noter.data.SQLHelper;

public class MainActivity extends AppCompatActivity {

    SQLHelper sqlHelper;
    RecyclerView noteRecyclerView;
    NoteListAdapter noteListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqlHelper = new SQLHelper(this);
        noteListAdapter = new NoteListAdapter(sqlHelper.getAllNotes(), this);
        mLayoutManager = new LinearLayoutManager(this);

        noteRecyclerView = (RecyclerView) findViewById(R.id.list_notes);
        noteRecyclerView.setLayoutManager(mLayoutManager);
        noteRecyclerView.setAdapter(noteListAdapter);
        noteRecyclerView.setHasFixedSize(true);

        onEmptyListShowAttentionMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_layout_manager_switch:
                switchLayoutManager();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Control for layout manager, switches between grid and list
     */
    private void switchLayoutManager() {
        if (noteRecyclerView.getLayoutManager() == mLayoutManager) {
            mMenu.findItem(R.id.action_layout_manager_switch).setIcon(R.drawable.ic_view_list_white_24dp);
            noteRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mMenu.findItem(R.id.action_layout_manager_switch).setIcon(R.drawable.ic_view_grid_white_24dp);
            noteRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    /**
     * Floating action button listener to start create note activity
     */
    public void startCreateNoteActivity(View view) {
        startActivity(new Intent(this, CreateNoteActivity.class));
    }

    /**
     * Checking notes list and if its empty show attention text view
     * Useful when app is installed, or all notes was deleted
     */
    private void onEmptyListShowAttentionMessage() {
        TextView attentionMessageView = (TextView) findViewById(R.id.text_empty_list);
        attentionMessageView.setVisibility(View.GONE);
        if (noteListAdapter.getItemCount() <= 0) {
            attentionMessageView.setVisibility(View.VISIBLE);
        }
    }
}
