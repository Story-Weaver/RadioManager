package by.roman.worldradio2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<HomeCardItem> cards;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ListAdapter(Context context, List<HomeCardItem> cards, OnItemClickListener listener) {
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
        HomeCardItem card = cards.get(position);

        // Устанавливаем текст
        holder.nameStation.setText(card.getNameStation());
        holder.nameSong.setText(card.getNameSong());

        // Загружаем картинку с помощью Glide
        Glide.with(context)
                .load(card.getLogoURL())
                .into(holder.logoStation);

        // Показываем или скрываем иконку в зависимости от выбранной позиции
        if (card.isPlaying()) {
            // Загружаем GIF с помощью Glide
            Glide.with(context)
                    .asGif()
                    .load(R.drawable.sound)  // Загружаем GIF (замени на свой файл)
                    .into(holder.sound);
            holder.sound.setVisibility(View.VISIBLE);
        } else {
            holder.sound.setVisibility(View.INVISIBLE);
        }


        // Обрабатываем нажатие на элемент списка
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition(); // Берём актуальную позицию
            if (adapterPosition == RecyclerView.NO_POSITION) return;
            // Сбрасываем флаг isPlaying у всех элементов
            for (HomeCardItem item : cards) {
                item.setPlaying(false);
            }
            cards.get(adapterPosition).setPlaying(true);
            notifyDataSetChanged();
            if(listener != null){
                listener.onItemClick(selectedPosition);
            }
        });
    }


    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameStation, nameSong;
        ImageView logoStation, sound;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameStation = itemView.findViewById(R.id.nameStationView);
            nameSong = itemView.findViewById(R.id.nameSongView);
            logoStation = itemView.findViewById(R.id.logoStationView);
            sound = itemView.findViewById(R.id.soundView);
        }
    }
}
