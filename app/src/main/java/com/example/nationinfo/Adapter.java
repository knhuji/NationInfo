package com.example.nationinfo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<nationinfo> nationinfoArrayList;
    private ArrayList<nationinfo> listFull;

    public Adapter(Context context, ArrayList<nationinfo> nationinfoArrayList) {
        this.context = context;
        this.nationinfoArrayList = nationinfoArrayList;
        listFull = new ArrayList<>();
        listFull.addAll(nationinfoArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_nation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        nationinfo nationinfos = nationinfoArrayList.get(position);
        holder.tvCountry.setText(nationinfos.getCountryName());
    }

    @Override
    public int getItemCount() {
        return nationinfoArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCountry = itemView.findViewById(R.id.tv_country);

            tvCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra("country", nationinfoArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<nationinfo> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (nationinfo item : listFull) {
                    if (item.getCountryName().toLowerCase().contains(filterPattern) || item.getCountryCode().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            nationinfoArrayList.clear();
            nationinfoArrayList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
