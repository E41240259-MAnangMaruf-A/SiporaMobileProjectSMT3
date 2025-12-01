package com.example.sipora.rizalmhs.Register;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sipora.R;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.VH> {

    private final List<String> data;
    private OnRecentClickListener listener; // listener untuk klik item

    public RecentAdapter(List<String> data) {
        this.data = data;
    }

    // 🔹 Interface listener
    public interface OnRecentClickListener {
        void onItemClick(String query);
    }

    // 🔹 Setter listener agar bisa dipanggil dari Activity
    public void setOnRecentClickListener(OnRecentClickListener listener) {
        this.listener = listener;
    }

    // 🔹 Tambah item baru ke atas daftar
    public void addItem(String s) {
        data.add(0, s);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String text = data.get(position);
        holder.tvRecent.setText(text);

        // Klik di seluruh item recent
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(text);
        });

        // Klik di ikon search 🔍 juga sama fungsinya
        holder.ivSearch.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(text);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvRecent;
        ImageView ivClock, ivSearch;

        VH(@NonNull View itemView) {
            super(itemView);
            tvRecent = itemView.findViewById(R.id.tv_recent_text);
            ivClock = itemView.findViewById(R.id.iv_clock);
            ivSearch = itemView.findViewById(R.id.iv_search_icon);
        }
    }
}
