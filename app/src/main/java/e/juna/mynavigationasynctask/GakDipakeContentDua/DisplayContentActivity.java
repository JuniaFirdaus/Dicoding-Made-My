package e.juna.mynavigationasynctask.GakDipakeContentDua;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import e.juna.mynavigationasynctask.MovieItems;
import e.juna.mynavigationasynctask.R;

/**
 * Created by Juna on 2/15/2018.
 */

public class DisplayContentActivity extends AppCompatActivity {
    private static final String TAG = DisplayContentActivity.class.getSimpleName();
    public static final String AUTHORITY = "content://e.juna.mynavigationasynctask.ContentDua.StudentProvider";
    public static final String PATH = "/words";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    private RecyclerView recyclerView;
    StudentProvider m;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        recyclerView = findViewById(R.id.list_dictionary);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        m = new StudentProvider();

        String URL = "content://e.juna.mynavigationasynctask.ContentDua.StudentProvider";
        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null, null, null, null);
        if (c != null) {
            ArrayList<String> words = new ArrayList<>();
            while (c.moveToNext()) {
                words.add(c.getString(c.getColumnIndex(m._ID)));
                words.add(c.getString(c.getColumnIndex(m.JUDUL)));
                words.add(c.getString(c.getColumnIndex(m.RELEASE)));
                words.add(c.getString(c.getColumnIndex(m.OVERVIEW)));
                words.add(c.getString(c.getColumnIndex(m.IMAGE)));
                DictionaryAdapter mAdapter = new DictionaryAdapter(DisplayContentActivity.this, words);
                recyclerView.setAdapter(mAdapter);
            }
            c.close();
        }
    }
}

//        String URL = "content://e.juna.mynavigationasynctask.ContentDua.StudentProvider";
//        Uri students = Uri.parse(URL);
//        Cursor c = getContentResolver().query(students, null, null, null, null, null);
//        ArrayList<MovieItems> words = new ArrayList<>();
//        if(c.moveToFirst()){
//            do{
//                //String word = c.getString(1);
//            }while(c.moveToNext());
//            DictionaryAdapter mAdapter = new DictionaryAdapter(this, words);
//            recyclerView.setAdapter(mAdapter);
//        }else{
//            Toast.makeText(DisplayContentActivity.this, "Nothing is inside the cursor ", Toast.LENGTH_LONG).show();
//        }
//        c.close();
//    }