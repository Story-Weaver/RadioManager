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
import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.RadioStationRepository;

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder>{
    private Context context;
    private List<RadioStation> cards;
    private RadioStationDTO dto;
    private RadioStationRepository radioStationRepository;
    private OnItemClickListener listener;

    public TopListAdapter(Context context, List<RadioStation> cards, OnItemClickListener listener,RadioStationRepository radioStationRepository) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
        this.radioStationRepository = radioStationRepository;
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
        RadioStation card = cards.get(position);
        holder.nameStation.setText(cards.get(position).getName());
        Glide.with(context)
                .load(card.getLogoUrl())
                .into(holder.logoStation);
        if (card.isPlaying()) {
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

            for (RadioStation item : cards) {
                item.setPlaying(false);
                radioStationRepository.setIsPlaying(item.getId(),false);
            }
            RadioStation selectedStation = cards.get(adapterPosition);
            selectedStation.setPlaying(true);
            radioStationRepository.setIsPlaying(selectedStation.getId(),true);
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
    public void updateData(List<RadioStation> newList) {
        cards = newList;
        notifyDataSetChanged(); // Уведомляем адаптер, что данные изменились
    }
    public void offIsPlaying() {
        for (RadioStation station : cards) {
            if (station.isPlaying()) {
                radioStationRepository.setIsPlaying(station.getId(),false);
            }
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStation;
        ImageView logoStation, sound;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView_Home);
            logoStation = itemView.findViewById(R.id.logoStationView_Home);
            sound = itemView.findViewById(R.id.soundView_Home);
        }
    }
}
