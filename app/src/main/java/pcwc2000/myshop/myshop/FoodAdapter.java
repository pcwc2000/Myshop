package pcwc2000.myshop.myshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by pichet_w on 1/3/2559.
 */
public class FoodAdapter extends BaseAdapter{
    //Explicit
    private Context context;
    private String[] foodStrings, priceStrings, iconStrings;

    public FoodAdapter(Context context, String[] foodStrings, String[] priceStrings, String[] iconStrings) {
        this.context = context;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;
        this.iconStrings = iconStrings;
    } //Constructor


    @Override
    public int getCount() {
        return foodStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.food_listview, viewGroup, false);
        // for food Name
        TextView foodTextView = (TextView) view1.findViewById(R.id.textView3);
        foodTextView.setText(foodStrings[i]);

        //for Price
        TextView priceTextView = (TextView) view1.findViewById(R.id.textView4);
        priceTextView.setText(priceStrings[i]);

        //for image
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(150,150).into(iconImageView);

        return view1;
    }
} //Main class
