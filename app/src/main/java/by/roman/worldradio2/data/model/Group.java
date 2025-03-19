package by.roman.worldradio2.data.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Group {
    private String name;

    private boolean expanded;
    private Group[] child;

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, Group[] child) {
        this.name = name;
        this.child = child;
        this.expanded = false;
    }

    public void close() {
        this.setExpanded(false);

        if (hasChild()) {
            for (Group childGroup : getChild()) {
                childGroup.close();
            }
        }
    }

    public void open() {
        this.setExpanded(true);
    }

    public int countGroups() {
        return collectAllGroups().size();
    }

    public int countExpandedGroups() {
        return collectAllExpandedGroups().size();
    }

    public boolean hasChild(){
        return getChild() != null;
    }

    public Group getElementByIndex(int index) {
        List<Group> allGroups = collectAllGroups();

        if (index >= 0 && index < allGroups.size()) {
            return allGroups.get(index);
        } else {
            return null;
        }
    }

    public List<Group> collectAllGroups() {
        List<Group> list = new ArrayList<>();
        list.add(this);

        if (hasChild()) {
            for (Group childGroup : getChild()) {
                list.addAll(childGroup.collectAllGroups());
            }
        }

        return list;
    }

    public List<Group> collectAllExpandedGroups() {
        List<Group> list = new ArrayList<>();
        list.add(this);

        if (hasChild() && isExpanded()) {
            for (Group childGroup : getChild()) {
                list.addAll(childGroup.collectAllExpandedGroups());
            }
        }

        return list;
    }

    public int getElementDepthByIndex(int index) {
        List<Group> allGroups = collectAllGroups();

        if (index >= 0 && index < allGroups.size()) {
            return getDepth(allGroups.get(index));
        } else {
            return -1; // Индекс вне диапазона
        }
    }

    public int getDepth(Group group) {
        return getDepthHelper(group, 0);
    }

    private int getDepthHelper(Group group, int currentDepth) {
        if (this == group) return currentDepth;

        if (hasChild()) {
            for (Group child : getChild()) {
                int depth = child.getDepthHelper(group, currentDepth + 1);
                if (depth != -1) return depth;
            }
        }

        return -1; // Элемент не найден
    }
}