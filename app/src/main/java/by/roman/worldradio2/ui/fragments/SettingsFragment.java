package by.roman.worldradio2.ui.fragments;

import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.SettingsRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.activities.MainActivity;
import by.roman.worldradio2.ui.elements.view.AnimatedExpandableListView;

public class SettingsFragment extends Fragment {
    private AnimatedExpandableListView listView;
    protected UserRepository userRepository;
    protected SettingsRepository settingsRepository;
    private ExampleAdapter adapter;
    private Button exit;
    private Button del;
    private TextView nameAccount;

    @Override
    public void onResume(){
        super.onResume();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        DatabaseHelper dHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        settingsRepository = new SettingsRepository(db);

        listView = rootView.findViewById(R.id.listView);
        nameAccount = rootView.findViewById(R.id.nameAccountView);
        exit = rootView.findViewById(R.id.button_exit);
        del = rootView.findViewById(R.id.button_del);

        adapter = new ExampleAdapter(requireContext());

        List<GroupItem> items = new ArrayList<>();
        items = createItems();

        String Hi = "Hi, \n" + userRepository.getUserLogin(userRepository.getUserIdInSystem());
        nameAccount.setText(Hi);

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
        exit.setOnClickListener(v -> {
            userRepository.removeUserFromSystem();
            restartApplication();
        });

        del.setOnClickListener(v -> {
            userRepository.deleteUser(userRepository.getUserIdInSystem());
            restartApplication();
        });
        return rootView;
    }
    private void restartApplication() {
        // Получаем Intent для стартовой активности приложения
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Очищаем стек активностей

        // Завершаем текущий процесс
        requireActivity().finish();

        // Запускаем приложение заново
        startActivity(intent);

        // Завершаем процесс текущего приложения
        System.exit(0); // или android.os.Process.killProcess(android.os.Process.myPid());
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
        RadioButton btn;
        Switch swc;
        Switch chs;
        ImageView img1;
        ImageView img2;
        ConstraintLayout layout;
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
                holder.btn = convertView.findViewById(R.id.radioButton);
                holder.swc = convertView.findViewById(R.id.switch1);
                holder.chs = convertView.findViewById(R.id.switch2);
                holder.img1 = convertView.findViewById(R.id.imageView1_Settings);
                holder.img2 = convertView.findViewById(R.id.imageView2_Settings);
                holder.layout = convertView.findViewById(R.id.layout1);
                holder.title = convertView.findViewById(R.id.textTitle);
                holder.hint = convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            switch (item.hint){
                case "btn_t":
                    holder.btn.setVisibility(VISIBLE);
                    holder.btn.setChecked(settingsRepository.getThemeSetting(userRepository.getUserIdInSystem()) == childPosition);
                    break;
                case "swc_m":
                    holder.swc.setVisibility(VISIBLE);
                    holder.swc.setChecked(settingsRepository.getMapSetting(userRepository.getUserIdInSystem()) == 1);
                    break;
                case "swc_s":
                    holder.swc.setVisibility(VISIBLE);
                    holder.swc.setChecked(settingsRepository.getTimerSecSetting(userRepository.getUserIdInSystem()) == 1);
                    break;
                case "chs_s":
                    holder.chs.setVisibility(VISIBLE);
                    holder.img1.setVisibility(VISIBLE);
                    holder.img1.setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.circle));
                    holder.img2.setVisibility(VISIBLE);
                    holder.img2.setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.romb));
                    holder.chs.setChecked(settingsRepository.getTimerDotsSetting(userRepository.getUserIdInSystem()) == 1);
                    break;

            }
            holder.title.setText(item.title);
            convertView.setOnClickListener(v -> {
                boolean newCheckedState;
                switch (groupPosition){
                    case 0:
                        int selectedTheme = settingsRepository.getThemeSetting(userRepository.getUserIdInSystem());
                        if (selectedTheme == childPosition) {
                            return;
                        }
                        settingsRepository.setSetting(userRepository.getUserIdInSystem(), DatabaseHelper.COLUMN_THEME_SETTINGS, childPosition);
                        notifyDataSetChanged();
                        break;
                    case 1:
                        newCheckedState = !holder.swc.isChecked();
                        settingsRepository.setSetting(userRepository.getUserIdInSystem(), DatabaseHelper.COLUMN_MAP_SETTINGS, newCheckedState ? 1 : 0);
                        holder.swc.setChecked(newCheckedState);
                        break;
                    case 2:
                        notifyDataSetChanged();
                        break;
                    case 3:
                        if(childPosition == 0){
                            newCheckedState = !holder.swc.isChecked();
                            settingsRepository.setSetting(userRepository.getUserIdInSystem(), DatabaseHelper.COLUMN_TIMER_SECONDS_SETTINGS, newCheckedState ? 1 : 0);
                            holder.swc.setChecked(newCheckedState);
                        }
                        if(childPosition == 1){
                            newCheckedState = !holder.chs.isChecked();
                            settingsRepository.setSetting(userRepository.getUserIdInSystem(), DatabaseHelper.COLUMN_TIMER_DOTS_SETTINGS, newCheckedState ? 1 : 0);
                            holder.chs.setChecked(newCheckedState);
                        }
                        break;
                    case 4:
                        Toast.makeText(getContext(),"Оно не надо",LENGTH_SHORT).show();
                        //TODO: надо...
                }
            });
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
        float startRotation = imageView.getRotation();
        float endRotation = isExpanded ? 270f : 90f;

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", startRotation, endRotation);
        rotateAnimator.setDuration(500);
        rotateAnimator.start();
    }
    private GroupItem createGroup(String groupTitle, String[] childTitles, String[] childHints) {
        GroupItem groupItem = new GroupItem();
        groupItem.title = groupTitle;

        List<ChildItem> childItems = new ArrayList<>();
        for (int i = 0; i < childTitles.length; i++) {
            ChildItem child = new ChildItem();
            child.title = childTitles[i];
            child.hint = i < childHints.length ? childHints[i] : null;
            childItems.add(child);
        }

        groupItem.items = childItems;

        return groupItem;
    }
    private List<GroupItem> createItems() {
        List<GroupItem> items = new ArrayList<>();

        items.add(createGroup("Тема",
                new String[] {"Тёмная", "Светлая"},
                new String[] {"btn_t", "btn_t"}));
        items.add(createGroup("Карта",
                new String[] {"Скрыть карту"},
                new String[] {"swc_m"}));
        items.add(createGroup("GPS",
                new String[] {"Использовать GPS"},
                new String[] {"swc_g"}));
        items.add(createGroup("Таймер",
                new String[] {"Показывать секунды","Вид разделителя"},
                new String[] {"swc_s","chs_s"}));
        items.add(createGroup("Тех поддержка",
                new String[] {"Политика конфиденциальности","Нашли ошибку?"},
                new String[] {"tp_lnk","tp_lnk"}));

        return items;
    }
    //TODO: переделать без разворота
}
