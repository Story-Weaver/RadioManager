package by.roman.worldradio2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.dataclasses.HomeCardItem;
import by.roman.worldradio2.adapters.HomeListAdapter;
import by.roman.worldradio2.R;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<HomeCardItem> cardList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.cardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cardList = new ArrayList<>();
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        cardList.add(new HomeCardItem("https://i.pinimg.com/originals/a4/52/91/a45291850f8157ad0b53a15406eebf01.png", "Песня 1"));
        cardList.add(new HomeCardItem("https://galerey-room.ru/images/091729_1419401849.png", "Песня 2"));
        adapter = new HomeListAdapter(getContext(), cardList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
