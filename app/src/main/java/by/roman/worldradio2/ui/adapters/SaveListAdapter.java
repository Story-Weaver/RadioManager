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
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.activities.MainActivity;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.ViewHolder>{
    private Context context;
    private List<RadioStation> cards;
    private RadioStationRepository radioStationRepository;
    private FavoriteRepository favoriteRepository;
    private UserRepository userRepository;
    private OnItemClickListener listener;
    private RadioService radioService;
    private OnItemRemovedListener itemRemovedListener;

    public interface OnItemRemovedListener {
        void onItemRemoved();
    }
    public SaveListAdapter(Context context, List<RadioStation> cards, OnItemClickListener listener, RadioStationRepository radioStationRepository,
                           FavoriteRepository favoriteRepository,UserRepository userRepository,OnItemRemovedListener listener2) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
        this.radioStationRepository = radioStationRepository;
        this.favoriteRepository = favoriteRepository;
        this.radioService = RadioService.getInstance(context,(MainActivity) context);
        this.userRepository = userRepository;
        this.itemRemovedListener = listener2;
    }
    @Override
    @NonNull
    public SaveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_card_save,parent,false);
        return new SaveListAdapter.ViewHolder(view);
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
                if(item.getIsPlaying() == 1){
                    item.setIsPlaying(0);
                }
                radioStationRepository.removeIsPlaying();
            }

            RadioStation selectedStation = cards.get(adapterPosition);
            selectedStation.setIsPlaying(1);
            radioStationRepository.setIsPlaying(selectedStation.getStationUuid(),true);
            radioService.checkNow();
            notifyDataSetChanged();
            if(listener != null){
                listener.onItemClick(position);
            }
        });
        holder.deleteButton.setOnClickListener(v -> {
            favoriteRepository.removeFavorite(userRepository.getUserIdInSystem(),card.getStationUuid());
            cards.remove(position);
            notifyItemRemoved(position);
            if (itemRemovedListener != null) {
                itemRemovedListener.onItemRemoved();
            }
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
    public void updateData(List<RadioStation> newList) {
        cards = newList;
        notifyDataSetChanged();
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
