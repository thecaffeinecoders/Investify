package android.example.com.investify;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**This class is responsible for preparing the items of companies list to
 * be displayed in the Recycler View
 * @param: Context the activity that contains the Recycler View component
 * @param: CompaniesList the list that stores
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private ArrayList<Company> companiesList= new ArrayList<Company>();
    private Context context;

    public RecyclerViewAdapter( Context context,ArrayList<Company> companiesList) {
        this.companiesList = companiesList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView tvName;
        RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgCompanyLogo);
            tvName= itemView.findViewById(R.id.tvCompanyName);
            parent=itemView.findViewById(R.id.parent_layout);

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Company company = companiesList.get(position);
        Glide.with(context).asBitmap().load(company.getLogoLInk()).into(holder.image);
        holder.tvName.setText(company.getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThirdActivity.class);
                intent.putExtra("Name",company.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }


    /*public Filter getFilter() {
        return null;
    }*/


}