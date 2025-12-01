package com.example.sipora.rizalmhs.Register;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipora.R;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private final List<DownloadModel> list;
    private final OnActionListener listener;

    public interface OnActionListener {
        void onOpen(DownloadModel model);
        void onDelete(DownloadModel model);
    }

    public DownloadAdapter(List<DownloadModel> list, OnActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_download, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {

        DownloadModel item = list.get(position);

        h.tvJudul.setText(item.getJudul());
        h.tvDeskripsi.setText(item.getDeskripsi());
        h.tvAuthor.setText(item.getAuthor());
        h.tvTanggal.setText(item.getTanggal());
        h.tvUkuran.setText(item.getUkuran());
        h.tvJenis.setText(item.getTipe());
        h.tvKategori.setText("Dokumen");

        // Status
        h.tvStatus.setText(item.getStatus().equals("selesai") ? "Selesai" : "Tidak diketahui");

        // Hapus progress bar (per permintaan)
        h.progressBar.setVisibility(View.GONE);
        h.tvProgressValue.setVisibility(View.GONE);

        h.btnAksi.setText("Buka File");
        h.btnAksi.setOnClickListener(v -> listener.onOpen(item));

        h.btnHapus.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvJudul, tvDeskripsi, tvAuthor, tvTanggal, tvUkuran, tvJenis, tvKategori, tvStatus, tvProgressValue;
        AppCompatButton btnAksi;
        ImageView btnHapus;
        View progressBar;

        public ViewHolder(View v) {
            super(v);

            tvJudul = v.findViewById(R.id.tvJudul);
            tvDeskripsi = v.findViewById(R.id.tvDeskripsi);
            tvAuthor = v.findViewById(R.id.tvAuthor);
            tvTanggal = v.findViewById(R.id.tvTanggal);
            tvUkuran = v.findViewById(R.id.tvUkuran);
            tvJenis = v.findViewById(R.id.tvJenis);
            tvKategori = v.findViewById(R.id.tvKategori);
            tvStatus = v.findViewById(R.id.tvStatus);

            progressBar = v.findViewById(R.id.progressBar);
            tvProgressValue = v.findViewById(R.id.tvProgressValue);

            btnAksi = v.findViewById(R.id.btnAksi);
            btnHapus = v.findViewById(R.id.btnHapus);
        }
    }
}
