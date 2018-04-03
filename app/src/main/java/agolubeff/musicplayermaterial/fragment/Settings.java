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

public class Settings extends Fragment
{
    private Toolbar toolbar;

    public static Settings NewInstance()
    {
        Settings fragment = new Settings();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar_settings);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        return view;
    }
}
