package edu.washington.gjdevera.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_TOPIC = "edu.washington.gjdevera.quizdroid.TOPIC";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Topic> topics = ((QuizApp) getApplication()).getRepository().getAllTopics();

        // instantiate RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<TopicViewHolder>() {

            @Override
            public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.topic_row,
                        parent,
                        false);
                return new TopicViewHolder(v);
            }

            @Override
            public void onBindViewHolder(TopicViewHolder vh, int position) {
                // get TextViews and replace with the title and short description
                TextView tv = (TextView) vh.itemView.findViewById(R.id.text1);
                tv.setText(topics.get(position).getTitle());
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_library_books, 0, 0, 0);
                tv.setCompoundDrawablePadding(12);
                tv = (TextView) vh.itemView.findViewById(R.id.text2);
                tv.setText(topics.get(position).getShortDesc());
            }

            @Override
            public int getItemCount() {
                return topics.size();
            }
        });
    }

    private class TopicViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TopicViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            intent.putExtra(EXTRA_TOPIC, getAdapterPosition());
            startActivity(intent);
        }
    }
}
