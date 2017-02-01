package edu.washington.gjdevera.quizdroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopicOverviewFragment extends Fragment {
    private static final String ARG_TOPIC = "topic";

    private String mTopic;

    private OnFragmentInteractionListener mListener;

    public TopicOverviewFragment() {
        // Required empty public constructor
    }

    public static TopicOverviewFragment newInstance(String topic) {
        TopicOverviewFragment fragment = new TopicOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOPIC, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopic = getArguments().getString(ARG_TOPIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_overview, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tv = (TextView) getView().findViewById(R.id.number_of_questions);
        tv.setText(String.format(getString(R.string.str_number_of_questions),
                getResources().getStringArray(R.array.questions).length));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
