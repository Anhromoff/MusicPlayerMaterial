package agolubeff.musicplayermaterial.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import agolubeff.musicplayermaterial.R;

/**
 * Created by andre on 14.10.2017.
 */

public class AlbumArtFragment extends Fragment
{
    private Bitmap album_art;

    public static AlbumArtFragment NewInstance(Bitmap image)
    {
        AlbumArtFragment fragment = new AlbumArtFragment();
        Bundle args = new Bundle();
        //args.putInt("image", image_res);
        args.putParcelable("image", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        album_art = getArguments().getParcelable("image");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.album_art_fullscreen, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.album_art);
        image.setImageBitmap(album_art);
        return view;
    }

}
