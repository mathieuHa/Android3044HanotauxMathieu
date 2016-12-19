package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mat on 19/12/2016.
 */

public class DetailMusicActivity extends AppCompatActivity {
    public static final String TAG = "DetailMusicActivity";
    public static final String MUSIC_LOAD = "org.esiea.hanotauxmathieu.projetandroidhanotaux3044.MUSIC_LOAD";

    private DataMusic music = null;
    private JSONObject musicO = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_music);

        GetMusicService.startActionDetailMusic(getApplicationContext(),this.getIntent().getExtras().get(SearchMusicActivity.MUSIC_ID).toString());

        IntentFilter inF = new IntentFilter(MUSIC_LOAD);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(new DetailMusicActivity.MusicUpdate(), inF);

        ImageButton boutonYoutube = (ImageButton) findViewById(R.id.boutonYoutube);
        boutonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+music.getNomMusic())));
            }
        });

        ImageButton boutonSpotify = (ImageButton) findViewById(R.id.boutonSpotify);
        boutonSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.spotify.com/search/"+music.getNomMusic())));
            }
        });
        ImageButton boutonDeezer = (ImageButton) findViewById(R.id.boutonDeezer);
        boutonDeezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.deezer.com/search/"+music.getNomMusic())));
            }
        });
        ImageButton boutonSoundcloud = (ImageButton) findViewById(R.id.boutonSoundCloud);
        boutonSoundcloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://soundcloud.com/search?q="+music.getNomMusic())));
            }
        });
    }

    public class MusicUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                Log.d("INOK", intent.getAction());
            }
            music = new DataMusic();
            musicO = getMusicFromFile();
            try {
                music.setNomMusic(musicO.getString("name"));
                JSONObject albumO = musicO.getJSONObject("album");
                JSONArray artistA = musicO.getJSONArray("artists");
                JSONObject artistO = artistA.getJSONObject(0);
                JSONArray images = albumO.getJSONArray("images");
                JSONObject imageBig = images.getJSONObject(1);
                music.setLienBigImg(imageBig.getString("url"));
                music.setNomAuteur(artistO.getString("name"));
                music.setNomAlbum(albumO.getString("name"));
                music.setID(musicO.getString("href"));
                music.setDuree(musicO.getInt("duration_ms"));
                music.setPopularity(musicO.getInt("popularity"));
                TextView name = (TextView) findViewById(R.id.detail_name_music);
                name.setText(getString(R.string.nom_music) + " " + music.getNomMusic());
                TextView auteur = (TextView) findViewById(R.id.detail_auteur_music);
                auteur.setText(getString(R.string.nom_auteur) + " " + music.getNomAuteur());
                TextView album = (TextView) findViewById(R.id.detail_album_music);
                album.setText(getString(R.string.nom_album) + " " + music.getNomAlbum());
                TextView duration = (TextView) findViewById(R.id.detail_temps_music);
                duration.setText(getString(R.string.duree) + ": " + music.getDuree()/(1000*60) +":"+ (music.getDuree()/1000)%60);
                TextView popularite = (TextView) findViewById(R.id.detail_popularite_music);
                popularite.setText(getString(R.string.popularite) + " " + music.getPopularity() + "/100");
                ImageView image = (ImageView) findViewById(R.id.imageBig);
                Picasso.with(context.getApplicationContext()).load(music.getLienBigImg()).into(image);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //
        }
    }

    public JSONObject getMusicFromFile() {
        try {
            Log.d(TAG, "getMusicFromFile: ");
            InputStream is = new FileInputStream(getCacheDir() + "/musics_detail.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
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
