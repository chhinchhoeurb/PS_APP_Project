package ruc.ps_app_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends ArrayAdapter {
    int logos[];
    Context context;
    String productListItem[];
    public HomeAdapter(@NonNull Context applicationContext, String[] productListItem, int[] logo) {
        super(applicationContext,R.layout.activity_gridview);
        this.context = applicationContext;
        this.logos = logo;
        this.productListItem = productListItem;

    }

    @Override
    public int getCount() {
        return logos.length;
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
    public View getView(int position, View view, ViewGroup parent) {
        View Listview = view;
        ViewHolder holder;

        if (Listview == null){

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Listview = mInflater.inflate(R.layout.activity_gridview, parent, false);

            holder = new HomeAdapter.ViewHolder();

            holder.images = (ImageView) Listview.findViewById(R.id.iconGridview);
            holder.products = (TextView) Listview.findViewById(R.id.productList);

            Listview.setTag(holder);
        }else {

            holder = (HomeAdapter.ViewHolder) Listview.getTag();
        }

        holder.images.setImageResource(logos[position]);
        holder.products.setText(productListItem[position]);

        return Listview;
    }


    public static class ViewHolder {
        ImageView images;
        TextView products;
    }



}
