package msku.ceng.madlab.week10;


import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Note> notes;
    private NoteFragment.OnNoteListInteractionsListener listener;

    public MyNoteRecyclerViewAdapter(ArrayList<Note> notes, NoteFragment.OnNoteListInteractionsListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        holder.mItem = notes.get(position);
        holder.mHeaderView.setText(notes.get(position).getHeader());
        holder.mContentView.setText(new SimpleDateFormat("yyyy-MM-dd").format(notes.get(position).getDate()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onNoteSelected(holder.mItem);
                }
            }
        });

        if(position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mHeaderView;
        public final TextView mContentView;
        public final TextView mDateView;

        public final View mView;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHeaderView = view.findViewById(R.id.note_header);
            mContentView = view.findViewById(R.id.note_date);
            mDateView = view.findViewById(R.id.note_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHeaderView.getText() + "'";
        }
    }
}