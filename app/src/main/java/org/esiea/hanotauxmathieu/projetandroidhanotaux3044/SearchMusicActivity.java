package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mat on 18/12/2016.
 */

public class SearchMusicActivity extends AppCompatActivity{

    public static final String MUSIC_UPDATE="org.esiea.hanotauxmathieu.projetandroidhanotaux3044.MUSIC_UPDATE";
    public static final String MUSIC_ID="org.esiea.hanotauxmathieu.projetandroidhanotaux3044.MUSIC_ID";
    public android.support.v7.widget.RecyclerView rv;
    public MusicAdapter musicAdapter;
    public static final String TAG = "SearchMusicActivity";
    public static final int MAX_SIZE = 50;
    public ArrayList<DataMusic> listMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_music);

        listMusic = new ArrayList<DataMusic>();

        for (int i=0; i<MAX_SIZE; i++){
            listMusic.add(new DataMusic());
        }
        Log.d(TAG, "onCreate: size" + listMusic.size());

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText edit = (EditText) findViewById(R.id.editText);
                if (edit.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),getString(R.string.msg_empty_text), Toast.LENGTH_SHORT).show();
                else {
                    GetMusicService.startActionMusic(getApplicationContext(), edit.getText().toString());
                    Log.d("SearchMusic", "bouton appui");
                }
            }
        });

        IntentFilter inF = new IntentFilter(MUSIC_UPDATE);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(new MusicUpdate(),inF);

        rv = (android.support.v7.widget.RecyclerView)findViewById(R.id.rv_music);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d(TAG, "onItemClick: " + listMusic.get(position).getNomMusic());
                        Intent in = new Intent(getApplicationContext(),DetailMusicActivity.class);
                        in.putExtra(MUSIC_ID,listMusic.get(position).getID());
                        startActivity(in);
                    }
                })
        );

        musicAdapter = new MusicAdapter(getMusicFromFile(),getApplicationContext(),listMusic);
        rv.setAdapter(musicAdapter);

    }

    public class MusicUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent){
                Log.d("INOK",intent.getAction());
            }
            NotificationCompat.Builder not = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setContentText("Download Complete").
                    setContentTitle("Music DL");
            NotificationManager notM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notM.notify(1, not.build());
            musicAdapter.setNewMusic(getMusicFromFile());
        }
    }

    public JSONObject getMusicFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir()+"/musics.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer,"UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
        return new JSONObject();
    }
}
