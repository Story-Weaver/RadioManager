package by.roman.worldradio2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.ui.activities.MainActivity;

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder>{
    private Context context;
    private List<RadioStation> cards;
    private RadioStationRepository radioStationRepository;
    private OnItemClickListener listener;
    private RadioService radioService;

    public TopListAdapter(Context context, List<RadioStation> cards, OnItemClickListener listener,RadioStationRepository radioStationRepository) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
        this.radioStationRepository = radioStationRepository;
        this.radioService = RadioService.getInstance(context,(MainActivity) context);
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
        holder.nameStation.setSelected(true);
        Glide.with(context)
                .load("https://flagsapi.com/"+ card.getCountryCode() +"/flat/64.png")
                .into(holder.flag);
        holder.country.setText(card.getCountry());
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
                if(item.getIsPlaying() == 1){
                    item.setIsPlaying(0);
                }
            }
            radioStationRepository.removeIsPlaying();
            RadioStation selectedStation = cards.get(adapterPosition);
            selectedStation.setIsPlaying(1);
            radioStationRepository.setIsPlaying(selectedStation.getStationUuid(),true);
            radioService.checkNow();
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
    public void loadMoreData(List<RadioStation> newStations) {
        int startPosition = cards.size();
        cards.addAll(newStations);
        notifyItemRangeInserted(startPosition, newStations.size());
    }

    public void offIsPlaying() {
        for (RadioStation station : cards) {
            if (station.getIsPlaying() == 1) {
                station.setIsPlaying(0);
            }
        }
        radioStationRepository.removeIsPlaying();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStation,country;
        ImageView logoStation, sound,flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView_Home);
            logoStation = itemView.findViewById(R.id.logoStationView_Home);
            sound = itemView.findViewById(R.id.soundView_Home);
            flag = itemView.findViewById(R.id.flagStation_CardHome);
            country = itemView.findViewById(R.id.countryName_CardHome);
        }
    }
}

