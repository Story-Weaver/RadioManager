package by.roman.worldradio2.ui.fragments;

import static android.widget.Toast.LENGTH_SHORT;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.ui.elements.view.AnimatedExpandableListView;

public class SettingsFragment extends Fragment {
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        listView = rootView.findViewById(R.id.listView);
        adapter = new ExampleAdapter(requireContext());

        List<GroupItem> items = new ArrayList<>();
        items = createItems();

        adapter.setData(items);
        listView.setAdapter(adapter);

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            ImageView arrow = v.findViewById(R.id.expand_image_status);
            animateArrow(arrow, listView.isGroupExpanded(groupPosition));
            if (listView.isGroupExpanded(groupPosition)) {
                listView.collapseGroupWithAnimation(groupPosition);
            } else {
                listView.expandGroupWithAnimation(groupPosition);
            }
            return true;
        });

        return rootView;
    }
    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<>();
    }
    private static class ChildItem {
        String title;
        String hint;
    }
    private static class ChildHolder {
        TextView title;
        TextView hint;
    }
    private static class GroupHolder {
        TextView title;
        ImageView status;
    }
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private final LayoutInflater inflater;
        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder.title = convertView.findViewById(R.id.textTitle);
                holder.hint = convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            holder.title.setText(item.title);
            holder.hint.setText(item.hint);
            return convertView;
        }
        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }
        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }
        @Override
        public int getGroupCount() {
            return items.size();
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.settings_item_group, parent, false);
                holder.title = convertView.findViewById(R.id.text_item_group);
                holder.status = convertView.findViewById(R.id.expand_image_status);
                convertView.setTag(holder);
                float rotationAngle = isExpanded ? 90f : 270f;
                holder.status.setRotation(rotationAngle);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }
            holder.title.setText(item.title);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    private void animateArrow(ImageView imageView, boolean isExpanded) {
        float startRotation = imageView.getRotation(); // Получаем текущий угол
        float endRotation = isExpanded ? 270f : 90f; // Определяем конечное положение

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", startRotation, endRotation);
        rotateAnimator.setDuration(500); // Оптимальная длительность анимации
        rotateAnimator.start();
    }
    private GroupItem createGroup(String groupTitle, String[] childTitles, String[] childHints) {
        GroupItem groupItem = new GroupItem();
        groupItem.title = groupTitle;

        List<ChildItem> childItems = new ArrayList<>();
        for (int i = 0; i < childTitles.length; i++) {
            ChildItem child = new ChildItem();
            child.title = childTitles[i];
            child.hint = i < childHints.length ? childHints[i] : null; // Защита от выхода за пределы массива подсказок
            childItems.add(child);
        }

        groupItem.items = childItems; // Добавление дочерних элементов в группу

        return groupItem;
    }
    private List<GroupItem> createItems() {
        List<GroupItem> items = new ArrayList<>();

        // Пример добавления групп с дочерними элементами
        items.add(createGroup("Тема",
                new String[] {"Тёмная", "Светлая"},
                new String[] {null, null}));

        items.add(createGroup("Язык",
                new String[] {"Русский","Английский"},
                new String[] {null,null}));
        items.add(createGroup("Таймер",
                new String[] {"Показывать секунды","Вид разделителя"},
                new String[] {null,null}));
        items.add(createGroup("Фильтр",
                new String[] {"Вид фильтра"},
                new String[] {null}));
        items.add(createGroup("Тех поддержка",
                new String[] {"Политика конфиденциальности","Нашли ошибку?"},
                new String[] {null,null}));

        // Вернуть заполенный список
        return items;
    }
}
