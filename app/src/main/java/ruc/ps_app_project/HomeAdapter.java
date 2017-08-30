package ruc.ps_app_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends ArrayAdapter {

    Context context;
    List<String> username,dateAndTime,description,profile, allPostImage,numLikes,numFav,numCmt;

    public HomeAdapter(Context applicationContext, List<String> username,List<String> dateAndTime,
                       List<String> description,List<String> profile, List<String> allPostImage,
                       List<String> numLikes,List<String> numFav,List<String> numCmt) {
        super(applicationContext,R.layout.homelist_item);
        this.context = applicationContext;
        this.username = username;
        this.dateAndTime = dateAndTime;
        this.description = description;
        this.profile = profile;
        this.allPostImage = allPostImage;
        this.numLikes = numLikes;
        this.numFav = numFav;
        this.numCmt = numCmt;
    }




    @Override
    public int getCount() {
        return username.size();
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
            Listview = mInflater.inflate(R.layout.homelist_item, parent, false);

            holder = new ViewHolder();

            holder.createDate = (TextView)Listview.findViewById(R.id.datetime) ;
            holder.usernames = (TextView) Listview.findViewById(R.id.userItem);
            holder.desc = (TextView)Listview.findViewById(R.id.descrip) ;
            holder.posterProfile = (ImageView)Listview.findViewById(R.id.circle_image) ;
            holder.postImages = (ImageView) Listview.findViewById(R.id.displayImage);

            holder.btnLike = (Button)Listview.findViewById(R.id.btnlike) ;
            holder.btnFav = (Button)Listview.findViewById(R.id.btnfavorite) ;
            holder.bntCmt = (Button)Listview.findViewById(R.id.btncmt) ;



            Listview.setTag(holder);
        }else {

            holder = (ViewHolder) Listview.getTag();
        }


        holder.usernames.setText(username.get(position));
        holder.createDate.setText(dateAndTime.get(position));
        holder.desc.setText(description.get(position));
        holder.btnLike.setText(numLikes.get(position));
        holder.btnFav.setText(numFav.get(position));
        holder.bntCmt.setText(numCmt.get(position));
        // profile
        final String url = "http://192.168.1.14:1111/images/posters/"+profile.get(position);
        loadImage(url,holder.posterProfile );
        // post image
        final String postImageurl = "http://192.168.1.14:1111/images/posts/"+allPostImage.get(position);
        loadImagePost(postImageurl,holder.postImages);



        return Listview;


    }


    public static class ViewHolder {
        TextView usernames;
        TextView createDate;
        TextView desc;
        ImageView posterProfile;
        ImageView postImages;
        Button btnLike,bntCmt,btnFav;
    }

    // To load image of profile
    private void loadImage(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(800,800)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);

    }

    // To load image of post
    private void loadImagePost(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(1300,1200)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);

    }


}
