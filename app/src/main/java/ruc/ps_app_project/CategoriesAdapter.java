package ruc.ps_app_project;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter{
    Context context;
    List<String> cateName,cateId;

    public CategoriesAdapter(Context applicationContext, List<String> postCategoryName, List<String> postCategoryId) {
        super(applicationContext,R.layout.activity_categories_adapter);
        this.cateId = postCategoryId;
        this.cateName = postCategoryName;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return cateName.size();
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
            Listview = mInflater.inflate(R.layout.activity_categories_adapter, parent, false);

            holder = new ViewHolder();

//            holder.id = (TextView)Listview.findViewById(R.id.cate_id) ;
            holder.usernames = (TextView) Listview.findViewById(R.id.cate_list_name);

            Listview.setTag(holder);
        }else {
            holder = (ViewHolder) Listview.getTag();
        }
//        holder.id.setText(cateId.get(position));
        holder.usernames.setText(cateName.get(position));
        return Listview;
    }

    public static class ViewHolder {
        TextView usernames;
//        TextView id;

    }
}
