package ruc.ps_app_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends ArrayAdapter {

    Context context;
    String countryList[];
    int myflag[];

    public HomeAdapter(Context applicationContext, String[] countryList, int[] flags) {
        super(applicationContext,R.layout.homelist_item);
        this.context = applicationContext;
        this.countryList = countryList;
        this.myflag = flags;
    }

    @Override
    public int getCount() {
        return countryList.length;
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
            Listview = mInflater.inflate(R.layout.listview_item, parent, false);

            holder = new ViewHolder();

            holder.images = (ImageView) Listview.findViewById(R.id.displayImage);
            holder.usernames = (TextView) Listview.findViewById(R.id.userItem);


            Listview.setTag(holder);
        }else {

            holder = (ViewHolder) Listview.getTag();
        }


        holder.usernames.setText(countryList[position]);
        holder.images.setImageResource(myflag[position]);


        return Listview;
    }


    public static class ViewHolder {
        ImageView images;
        TextView usernames;
    }
}
