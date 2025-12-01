package com.example.sipora.rizalmhs.Register;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.sipora.R;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.VH> {

    private final List<TrendingItem> items;
    public TrendingAdapter(List<TrendingItem> items) { this.items = items; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TrendingItem it = items.get(position);
        holder.title.setText(it.title);
        holder.pill.setText(String.valueOf(it.value));
        holder.itemView.setOnClickListener(v -> {
            // action on click
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, pill;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_trend_title);
            pill = itemView.findViewById(R.id.tv_pill);
        }
    }
}
