package com.example.jwim0.topdog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class customAdapter extends ArrayAdapter<String>{

    public customAdapter(@NonNull Context context, String[] foods) {
        super(context, R.layout.custom_row, foods);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater michaelInflator = LayoutInflater.from(getContext());
        View customView = michaelInflator.inflate(R.layout.custom_row, parent, false);

        String singleFoodItem = getItem(position);
        TextView michaelText = (TextView) customView.findViewById(R.id.countText);
        ImageView michaelImage = (ImageView) customView.findViewById(R.id.dogImage);

        michaelText.setText(singleFoodItem);
        //michaelImage.setImageResource( image name );
        return customView;
    }
}
