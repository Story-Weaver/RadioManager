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
import by.roman.worldradio2.data.dto.FavoriteDTO;
import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.Favorite;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.ViewHolder>{
    private Context context;
    private List<RadioStation> cards;
    private RadioStationDTO dto;
    private RadioStationRepository radioStationRepository;
    private FavoriteDTO favoriteDTO;
    private FavoriteRepository favoriteRepository;
    private OnItemClickListener listener;

    public SaveListAdapter(Context context, List<RadioStation> cards, OnItemClickListener listener, RadioStationRepository radioStationRepository, FavoriteRepository favoriteRepository) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
        this.radioStationRepository = radioStationRepository;
        this.favoriteRepository = favoriteRepository;
    }
    @Override
    @NonNull
    public SaveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_card_save,parent,false);
        return new SaveListAdapter.ViewHolder(view);//***************//
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(@NonNull SaveListAdapter.ViewHolder holder, int position) {
        RadioStation card = cards.get(position);
        holder.nameStation.setText(cards.get(position).getName());
        Glide.with(context)
                .load(card.getFavicon())
                .into(holder.logoStation);
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
        holder.deleteButton.setOnClickListener(v -> {
            favoriteDTO = null;

            notifyDataSetChanged();
        });
    }
    @Override
    public int getItemCount() {
        return cards.size();
    }
    public void updateList(List<RadioStation> newList) {
        this.cards.clear();
        this.cards.addAll(newList);
        notifyDataSetChanged();
    }
    // Метод для обновления данных
    public void updateData(List<RadioStation> newList) {
        cards = newList;
        notifyDataSetChanged(); // Уведомляем адаптер, что данные изменились
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
        ImageView logoStation,deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView_Save);
            logoStation = itemView.findViewById(R.id.logoStationView_Save);
            deleteButton = itemView.findViewById(R.id.deleteButton_Save);
        }
    }
}
