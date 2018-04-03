package agolubeff.musicplayermaterial.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import agolubeff.musicplayermaterial.Globals;

/**
 * Created by andre on 07.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper
{
    private static String create_table_tracks =
            "create table tracks ("
                    + "id integer primary key,"
                    + "title text,"
                    + "artist text,"
                    + "data char(50),"
                    + "duration_seconds integer,"
                    + "image integer"
                    + ");";

    private static String create_index_tracks =
            "create index data_index on tracks(data)";


    public DBHelper(Context context)
    {
        super(context, "myDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_table_tracks);
        db.execSQL(create_index_tracks);
        db.execSQL("create table playlists (id integer primary key, name text unique);");
        db.execSQL("create table contains (id_playlist integer, id_track integer);");
        Log.d(Globals.log_tag,"database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
