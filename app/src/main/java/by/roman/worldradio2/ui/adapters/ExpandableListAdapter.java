package by.roman.worldradio2.ui.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import by.roman.worldradio2.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private List<String> listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        TextView txtListChild = convertView.findViewById(android.R.id.text1);
        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settings_item_group, null);
        }

        ImageView isExpandedImage = convertView.findViewById(R.id.expand_image_status);
        TextView lblListHeader = convertView.findViewById(R.id.text_item_group);
        lblListHeader.setText(headerTitle);

        // Запускаем анимацию поворота значка
        animateArrow(isExpandedImage, isExpanded);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void animateArrow(ImageView imageView, boolean isExpanded) {
        float fromRotation = isExpanded ? 270f : 90f;
        float toRotation = isExpanded ? 90f : 270f;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", fromRotation, toRotation);
        rotateAnimator.setDuration(1000); // Плавная анимация
        rotateAnimator.start();
    }

}
