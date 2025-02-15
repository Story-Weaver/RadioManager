package by.roman.worldradio2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<CardItem> cards;
    private Context context;
    private OnItemClickListener listener;
    public ListAdapter(Context context, List<CardItem> cards, OnItemClickListener listener) {
        this.context = context;
        this.cards = cards;
        this.listener = listener;
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
        CardItem card = cards.get(position);

        // Устанавливаем текст в TextView
        holder.nameStation.setText(card.getNameStation());
        holder.nameSong.setText(card.getNameSong());

        // Загружаем картинку с помощью Glide
        Glide.with(context)
                .load(card.getLogoURL())
                .into(holder.logoStation);
        // Обрабатываем нажатие на элемент списка
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {  // Проверяем, что listener не null
                listener.onItemClick(position);
            }
        });
    }


    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStation, nameSong;
        ImageView logoStation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView);
            nameSong = itemView.findViewById(R.id.nameSongView);
            logoStation = itemView.findViewById(R.id.logoStationView);
        }
    }
}
