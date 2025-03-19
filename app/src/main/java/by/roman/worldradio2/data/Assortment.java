package by.roman.worldradio2.data;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.data.model.Group;

public class Assortment {

    public static final ArrayList<Group> assortment;

    static {
        ArrayList<Group> tmp = new ArrayList<>();

        // Категория: Тема
        tmp.add(new Group("Тема", new Group[]{
                new Group("Тёмная"),
                new Group("Светлая")
        }));

        // Категория: Таймер
        tmp.add(new Group("Таймер", new Group[]{
                new Group("Отображать секунды"),
                new Group("Звук таймера"),
                new Group("Вид разделителя")
        }));

        // Категория: Фильтр
        tmp.add(new Group("Фильтр", new Group[]{
                new Group("Вид фильтра")
        }));

        // Категория: Техническая поддержка
        tmp.add(new Group("Тех. поддержка", new Group[]{
                new Group("Политика конфиденциальности"),
                new Group("Нашли ошибку?")
        }));

        assortment = tmp;
    }

    public static List<Group> collectAllExpandedElements() {
        List<Group> list = new ArrayList<>();

        for (Group group : Assortment.assortment) {
            list.addAll(group.collectAllExpandedGroups());
        }

        return list;
    }

    public static int getDepth(Group group) {
        for (Group g : Assortment.assortment) {
            int elementDepth = g.getDepth(group);
            if (elementDepth != -1) {
                return elementDepth;
            }
        }

        return -1; // Неверный индекс
    }
}
