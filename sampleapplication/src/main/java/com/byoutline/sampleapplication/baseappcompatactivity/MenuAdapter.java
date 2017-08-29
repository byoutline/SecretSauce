package com.byoutline.sampleapplication.baseappcompatactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.byoutline.sampleapplication.R;
import com.byoutline.secretsauce.fragments.MenuOption;
import com.byoutline.secretsauce.views.CheckableCustomFontTextView;

/**
 * Created by michalp on 12.04.16.
 */
public class MenuAdapter extends ArrayAdapter<MenuOption> {

    private final LayoutInflater inflater;

    public MenuAdapter(final Context context) {
        super(context, android.R.layout.simple_list_item_1);
        inflater = LayoutInflater.from(getContext());
        add(new CustomMenuOption("First Item", FirstFragment.class));
        add(new CustomMenuOption("Second Item", SecondFragment.class));
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MenuOption item = getItem(position);
        holder.menuItemNameTv.setText(item.getTitle());

        return convertView;
    }

    public class ViewHolder {
        public CheckableCustomFontTextView menuItemNameTv;
        public View root;

        public ViewHolder(View root) {
            menuItemNameTv = (CheckableCustomFontTextView) root.findViewById(R.id.menu_item_name_tv);
            this.root = root;
        }
    }
}
