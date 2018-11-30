package android.example.com.investify;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**This class is responsible for preparing the items of companies list to
 * be displayed in the Recycler View
 * @param: Context the activity that contains the Recycler View component
 * @param: CompaniesList the list that stores
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private ArrayList<Company> companyList = new ArrayList<Company>();
    private Context context;
    private List<Company> companyListFiltered;
    private CompanyAdapterListener listener;
    private double principal;

    public RecyclerViewAdapter( Context context,ArrayList<Company> companiesList,double principal) {
        this.context = context;
        //this.listener = listener;
        this.companyList = companiesList;
        this.companyListFiltered = companiesList;
        this.principal= principal;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tvName;
        RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgCompanyLogo);
            tvName= itemView.findViewById(R.id.tvCompanyName);
            parent=itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCompanySelected(companyListFiltered.get(getAdapterPosition()));
                }
            });
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
        //final Company company = companyList.get(position);
        final Company company = companyListFiltered.get(position);
        RequestOptions options = new RequestOptions();
        options.centerInside();
        Glide.with(context).asBitmap().load(company.getLogoLInk()).apply(fitCenterTransform()).into(holder.image);//image Loader

        holder.tvName.setText(company.getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThirdActivity.class);

                intent.putExtra("company",company);
                intent.putExtra("Name",company.getName());
                intent.putExtra("principal",principal);
                context.startActivity(intent);
            }
        });
    }

    /**
     * getItemCount() method for filtering
     * @return
     */
    @Override
    public int getItemCount() {
        //return companyList.size();
        return companyListFiltered.size();
    }

    /**
     * Filtering recyclerView items for searching
     * @return
     */
    //@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    companyListFiltered = companyList;
                } else {
                    List<Company> filteredList = new ArrayList<>();
                    for (Company row : companyList) {
                        // here we are looking for only name matching
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    companyListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = companyListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                companyListFiltered = (ArrayList<Company>) filterResults.values;
                // refresh the list (recycler view) with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface CompanyAdapterListener {
        void onCompanySelected(Company company);
    }
}