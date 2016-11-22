package com.jdcompany.musicalinstruments;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import java.util.List;

public class MusicalInstrumentAdapter extends RecyclerView.Adapter<MusicalInstrumentAdapter.MusicalInstrumentViewHolder>
{
    List<MusicalInstrument> musicalInstruments;
    ImageLoader imageLoader;
    private RecyclerViewOnItemClickListner onItemClickListener;


    MusicalInstrumentAdapter(List<MusicalInstrument> musicalInstruments, Context context)
    {
        this.musicalInstruments = musicalInstruments;
        imageLoader = new ImageLoader(Volley.newRequestQueue(context), new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    @Override
    public MusicalInstrumentAdapter.MusicalInstrumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.musical_instrument_item, parent, false);
        MusicalInstrumentViewHolder mvh = new MusicalInstrumentViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MusicalInstrumentAdapter.MusicalInstrumentViewHolder holder, final int position)
    {
        holder.musicalInstrumentName.setText(musicalInstruments.get(position).name);
        holder.musicalInstrumentType.setText(musicalInstruments.get(position).type);
        holder.musicalInstrumentImage.setImageUrl(musicalInstruments.get(position).imageUrl,imageLoader );

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(musicalInstruments.get(position));
            }
        };
        holder.musicalInstrumentCardView.setOnClickListener(listener);

    }

    @Override
    public int getItemCount()
    {
        return musicalInstruments.size();
    }

    public static class MusicalInstrumentViewHolder extends RecyclerView.ViewHolder {
        CardView musicalInstrumentCardView;
        TextView musicalInstrumentName;
        TextView musicalInstrumentType;
        NetworkImageView musicalInstrumentImage;

        MusicalInstrumentViewHolder(View itemView) {
            super(itemView);
            musicalInstrumentCardView = (CardView)itemView.findViewById(R.id.musical_instrument_card);
            musicalInstrumentName = (TextView)itemView.findViewById(R.id.musical_instrument_name);
            musicalInstrumentType = (TextView)itemView.findViewById(R.id.musical_instrument_type);
            musicalInstrumentImage = (NetworkImageView) itemView.findViewById(R.id.musical_instrument_image);
        }
    }

    public RecyclerViewOnItemClickListner getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListner onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
