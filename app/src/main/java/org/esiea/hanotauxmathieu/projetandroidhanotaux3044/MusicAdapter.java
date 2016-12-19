package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mat on 18/12/2016.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private JSONObject musics = null;
    private Context context = null;
    private ArrayList<DataMusic> listMusic = null;
    final static String TAG = "MusicAdapteur";

    public MusicAdapter(JSONObject obj, Context context, ArrayList<DataMusic> listMusic) {
        this.musics = obj;
        this.context = context;
        this.listMusic = listMusic;
        Log.d(TAG, "MusicAdapter: ()");
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_element, parent, false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: start");
        DataMusic music = new DataMusic();
        try {
            JSONObject trackO = musics.getJSONObject("tracks");
            JSONArray array = trackO.getJSONArray("items");
            JSONObject musicO = array.getJSONObject(position);
            music.setNomMusic(musicO.getString("name"));
            JSONObject albumO = musicO.getJSONObject("album");
            JSONArray artistA = musicO.getJSONArray("artists");
            JSONObject artistO = artistA.getJSONObject(0);
            JSONArray images = albumO.getJSONArray("images");
            JSONObject imageMini = images.getJSONObject(2);
            music.setLienPetitImg(imageMini.getString("url"));
            music.setNomAuteur(artistO.getString("name"));
            music.setNomAlbum(albumO.getString("name"));
            music.setID(musicO.getString("href"));
            Log.d(TAG, "onBindViewHolder: "+ music.toString());
            Log.d(TAG, "onBindViewHolder: " + music.getNomMusic());
            holder.name.setText(music.getNomMusic());
            holder.artist.setText(music.getNomAuteur());
            holder.album.setText(music.getNomAlbum());
            Picasso.with(context).load(music.getLienPetitImg()).into(holder.image);
            Log.d(TAG, "onBindViewHolder: setText");
            listMusic.set(position,music);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            Log.d(TAG, "getItemCount: "+ musics.getJSONObject("tracks").getJSONArray("items").length());
            return musics.getJSONObject("tracks").getJSONArray("items").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        public TextView name = null;
        public TextView album = null;
        public TextView artist = null;
        public ImageView image = null;

        public MusicHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_music_name);
            album = (TextView) itemView.findViewById(R.id.rv_music_album);
            artist = (TextView) itemView.findViewById(R.id.rv_music_artist);
            image = (ImageView) itemView.findViewById(R.id.rv_image);
        }
    }

    public void setNewMusic (JSONObject object) {
        Log.d(TAG, "setNewMusic: ");
        this.musics = object;
        notifyDataSetChanged();
    }
}
