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
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    private ArrayList<Company> companyList = new ArrayList<Company>();
    private Context context;
    private List<Company> companyListFiltered;
    private CompanyAdapterListener listener;
    private double principal;


    /**
     * the RecyclerViewAdapter Class constructor
     * @param context  The activity that contains the Recycler View component
     * @param companiesList CompaniesList the list that stores
     * @param principal The principal amount the user entered in the first activity
     *                  which will be sent through Intent to the third activity
     */
    public RecyclerViewAdapter( Context context,ArrayList<Company> companiesList,double principal) {
        this.context = context;
        this.companyList = companiesList;
        this.companyListFiltered = companiesList;
        this.principal= principal;
    }
    /**
     * Inner class that is used to hold the views in the row/item layout
     * it has the variable layout container
     * it has the Image view variable that will be loaded with
     * the company logo (stored in the database)
     * it has text view that displays the company name
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tvName;
        RelativeLayout parent;
      
        /**
         * The constructor of view holder inner class,
         * it takes the whole layout(entier row in the list) and find each sub view in the layout
         * @param itemView
         */
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
    // this method inflates the row/item form the xml file and return a holder
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    // this method populates the company name and logo from the database into the row/item throug the holder object
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // the Company object in the list of companies
        final Company company = companyListFiltered.get(position);
        RequestOptions options = new RequestOptions(); // load image options using the Glide library
        options.centerInside();
        Glide.with(context).asBitmap().load(company.getLogoLInk()).apply(fitCenterTransform()).into(holder.image);//image Loader
        holder.tvName.setText(company.getName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThirdActivity.class);

                intent.putExtra("company",company);// send the selected company object to the third activity
                intent.putExtra("Name",company.getName());
                intent.putExtra("principal",principal);// send the entered amount that is extracted from the first activity to the third one
                context.startActivity(intent);
            }
        });
    }

    /**
     * getItemCount() method for filtering
     * @return
     */
    @Override
    // returns the total count of the companies in
    // the arraylist passed in the recycler view constructor
    public int getItemCount() {
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