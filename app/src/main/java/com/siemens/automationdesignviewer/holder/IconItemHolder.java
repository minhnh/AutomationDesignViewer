package com.siemens.automationdesignviewer.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.siemens.automationdesignviewer.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by minh on 14.06.17.
 */

public class IconItemHolder extends TreeNode.BaseNodeViewHolder<IconItemHolder.IconItem> {
    private TextView tvValue;
    private PrintView arrowView;

    public IconItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        final PrintView iconView = view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = view.findViewById(R.id.arrow_icon);
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(
                active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconItem {
        public int icon;
        public String text;

        public IconItem(int icon, String text)
        {
            this.icon = icon;
            this.text = text;
        }
    }
}
