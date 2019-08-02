package com.renu.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomFoodAdapter extends BaseAdapter {
    private Context context;
    private String[] breakfast;
    private int[] breakfastImages;
private LayoutInflater layoutInflater;
    public CustomFoodAdapter(Context context, String[] breakfast, int[] breakfastImages) {
        this.context = context;
        this.breakfast = breakfast;
        this.breakfastImages = breakfastImages;
    }

    @Override
    public int getCount() {
        return breakfast.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
convertView=layoutInflater.inflate(R.layout.food_view,parent,false);


        }
        ImageView imageView=convertView.findViewById(R.id.foodImageViewId);
        TextView textView=convertView.findViewById(R.id.foodNameTextViewId);
        imageView.setImageResource(breakfastImages[position]);
        textView.setText(breakfast[position]);





        return convertView;
    }
}
