package by.roman.worldradio2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.RadioStationRepository;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private Context context;
    private List<RadioStation> cards;
    private RadioStationDTO dto;
    private RadioStationRepository radioStationRepository;
    private OnItemClickListener listener;

    public HomeListAdapter(Context context, List<RadioStation> cards, OnItemClickListener listener, RadioStationRepository radioStationRepository) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
        this.radioStationRepository = radioStationRepository;
    }
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_card_home,parent,false);
        return new ViewHolder(view);
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RadioStation card = cards.get(position);
        holder.nameStation.setText(card.getName());
        Glide.with(context)
                .load(card.getFavicon())
                .into(holder.logoStation);
        if (card.getIsPlaying() == 1) {
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
                item.setIsPlaying(0);
                radioStationRepository.setIsPlaying(item.getStationUuid(),false);
            }
            RadioStation selectedStation = cards.get(adapterPosition);
            selectedStation.setIsPlaying(1);
            radioStationRepository.setIsPlaying(selectedStation.getStationUuid(),true);
            notifyDataSetChanged();
            if(listener != null){
                listener.onItemClick(position);
            }
        });
    }


    public int getItemCount() {
        return cards.size();
    }
    public void offIsPlaying() {
        for (RadioStation station : cards) {
            if (station.getIsPlaying() == 1) {
                radioStationRepository.setIsPlaying(station.getStationUuid(),false);
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
