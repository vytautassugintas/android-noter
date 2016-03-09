package sugintas.com.noter.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import sugintas.com.noter.R;
import sugintas.com.noter.data.Note;
import sugintas.com.noter.data.SQLHelper;
import sugintas.com.noter.ui.EditNoteActivity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<Note> mDataset;
    SQLHelper mSQLHelper;
    Context context;

    public NoteListAdapter(List<Note> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
        mSQLHelper = new SQLHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout container;
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtContent;

        public ViewHolder(View v) {
            super(v);
            container = (LinearLayout) v.findViewById(R.id.item_container);
            txtTitle = (TextView) v.findViewById(R.id.txt_title);
            txtDate = (TextView) v.findViewById(R.id.txt_date);
            txtContent = (TextView) v.findViewById(R.id.txt_content);
        }

    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note note = mDataset.get(position);

        holder.txtTitle.setText(note.getTitle());
        holder.txtDate.setText(note.getDate());
        holder.txtContent.setText(note.getBody());

        holder.txtTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(note);
                mSQLHelper.deleteNote(note);
            }
        });

        holder.container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity(note);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(Note item) {
        mDataset.add(getItemCount() + 1, item);
        notifyItemInserted(getItemCount() + 1);
    }

    public void remove(Note item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public void startEditActivity(Note noteToEdit) {
        Intent editNoteIntent = new Intent(context.getApplicationContext(), EditNoteActivity.class);
        editNoteIntent.putExtra("ID", noteToEdit.getId());
        context.startActivity(editNoteIntent);
    }
}

