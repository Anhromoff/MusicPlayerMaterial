package agolubeff.musicplayermaterial.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agolubeff.musicplayermaterial.R;

/**
 * Created by Andrey on 23.10.2017.
 */

public class Statistics extends Fragment
{
    private Toolbar toolbar;

    public static Statistics NewInstance()
    {
        Statistics fragment = new Statistics();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_statistics, null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_statistics);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Statistics");

        return view;
    }
}
