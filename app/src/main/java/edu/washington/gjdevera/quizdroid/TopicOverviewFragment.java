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
    public static final String ARG_TOPIC = "topic";

    private int mTopicNumber;
    private Topic mTopic;

    private OnFragmentInteractionListener mListener;

    public TopicOverviewFragment() {
        // Required empty public constructor
    }

    public static TopicOverviewFragment newInstance(int topic) {
        TopicOverviewFragment fragment = new TopicOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOPIC, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopicNumber = getArguments().getInt(ARG_TOPIC);
            mTopic = ((QuizApp) getActivity().getApplication())
                    .getRepository().getAllTopics().get(mTopicNumber);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_overview, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvNumQuestions = (TextView) getView().findViewById(R.id.number_of_questions);
        tvNumQuestions.setText(String.format(getString(R.string.str_number_of_questions),
                mTopic.getQuestions().size()));
        TextView tvLongDesc = (TextView) getView().findViewById(R.id.topic_overview);
        tvLongDesc.setText(mTopic.getLongDesc());
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
