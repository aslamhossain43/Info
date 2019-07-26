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
    private String[] foodNames;
    private int[]foodImages;
private LayoutInflater layoutInflater;
    public CustomFoodAdapter(Context context, String[] foodNames, int[] foodImages) {
        this.context = context;
        this.foodNames = foodNames;
        this.foodImages = foodImages;
    }

    @Override
    public int getCount() {
        return foodNames.length;
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
        imageView.setImageResource(foodImages[position]);
        textView.setText(foodNames[position]);





        return convertView;
    }
}
