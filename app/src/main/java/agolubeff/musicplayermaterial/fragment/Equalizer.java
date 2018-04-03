package agolubeff.musicplayermaterial.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agolubeff.musicplayermaterial.R;

/**
 * Created by Andrey on 20.11.2017.
 */

public class Equalizer extends Fragment
{
    private Toolbar toolbar;

    public static Equalizer NewInstance()
    {
        Equalizer fragment = new Equalizer();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_equalizer, null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_equaliser);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Equalizer");

        return view;
    }
}
