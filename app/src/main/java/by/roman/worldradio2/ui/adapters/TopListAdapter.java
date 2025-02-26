package by.roman.worldradio2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.dataclasses.Database;
import by.roman.worldradio2.dataclasses.model.RadioStations;

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder>{
    private Database database;
    private Context context;
    private List<RadioStations> cards;
    private OnItemClickListener listener;

    public TopListAdapter(Context context, List<RadioStations> cards, OnItemClickListener listener) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;

    }
    @Override
    @NonNull
    public TopListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_card_home,parent,false);
        return new TopListAdapter.ViewHolder(view);//***************//
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(@NonNull TopListAdapter.ViewHolder holder, int position) {
        database = new Database(context);
        RadioStations card = cards.get(position);
        holder.nameStation.setText(card.getNameStation());
        Glide.with(context)
                .load(card.getLogoUrl())
                .into(holder.logoStation);
        if (card.getIsPlaying()) {
            Glide.with(context)
                    .asGif()
                    .load(R.drawable.sound)
                    .into(holder.sound);
            holder.sound.setVisibility(View.VISIBLE);
        } else {
            holder.sound.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) return;

            for (RadioStations item : cards) {
                item.setPlaying(false);
                database.setIsplaying(item.getId(),false);
            }
            RadioStations selectedStation = cards.get(adapterPosition);
            selectedStation.setPlaying(true);
            database.setIsplaying(selectedStation.getId(),true);
            notifyDataSetChanged();
            if(listener != null){
                listener.onItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return cards.size();
    }

    // Метод для обновления данных
    public void updateData(List<RadioStations> newList) {
        cards = newList;
        notifyDataSetChanged(); // Уведомляем адаптер, что данные изменились
    }
    public void offIsPlaying() {
        for (RadioStations station : cards) {
            if (station.getIsPlaying()) {
                database.setIsplaying(station.getId(),false);
            }
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStation;
        ImageView logoStation, sound;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView);
            logoStation = itemView.findViewById(R.id.logoStationView);
            sound = itemView.findViewById(R.id.soundView);
        }
    }
}
