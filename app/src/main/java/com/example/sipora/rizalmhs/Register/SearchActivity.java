package com.example.sipora.rizalmhs.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sipora.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rvRecent, rvTrending;
    RecentAdapter recentAdapter;
    TrendingAdapter trendingAdapter;
    EditText etSearch;
    Button btnCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        // === Atur padding sesuai gaya BrowseActivity ===
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 50);
            return insets;
        });

        // === Navbar nempel di bawah ===
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_nav), (view, insets2) -> {
            view.setPadding(0, 0, 0, 0);
            return insets2;
        });

        // === Inisialisasi View ===
        etSearch = findViewById(R.id.et_search);
        btnCari = findViewById(R.id.btn_cari);
        rvRecent = findViewById(R.id.rv_recent);
        rvTrending = findViewById(R.id.rv_trending);
        ImageView btnBack = findViewById(R.id.btnBack);

        // === Data dummy untuk uji coba ===
        List<String> recent = Arrays.asList("Machine Learning", "Analisis Keuangan", "Fisika Quantum", "React Aplikasi", "Sistem Kontrol");
        rvRecent.setLayoutManager(new LinearLayoutManager(this));
        recentAdapter = new RecentAdapter(new ArrayList<>(recent));
        rvRecent.setAdapter(recentAdapter);

        recentAdapter.setOnRecentClickListener(query -> {
            etSearch.setText(query);
        });


        List<TrendingItem> trends = new ArrayList<>();
        trends.add(new TrendingItem("Artificial Intelligence", 235));
        trends.add(new TrendingItem("Sustainability", 189));
        trends.add(new TrendingItem("Blockchain", 154));
        trends.add(new TrendingItem("IoT Sistem", 129));
        trends.add(new TrendingItem("Renewable Energy", 101));

        rvTrending.setLayoutManager(new LinearLayoutManager(this));
        trendingAdapter = new TrendingAdapter(trends);
        rvTrending.setAdapter(trendingAdapter);

        // === Tombol Cari ===
        btnCari.setOnClickListener(v -> {
            String q = etSearch.getText().toString().trim();
            if (!q.isEmpty()) {
                recentAdapter.addItem(q);
                rvRecent.smoothScrollToPosition(recentAdapter.getItemCount() - 1);
                etSearch.setText("");
            }
        });

        // === Tombol Kembali ===
        btnBack.setOnClickListener(v -> {
            String from = getIntent().getStringExtra("from");
            Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
            intent.putExtra("from", "search");
            startActivity(intent);
            finish();
        });

        // === Bottom Navigation ===
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.setSelectedItemId(R.id.nav_search);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_search) {
                return true;
            }

            Intent intent = null;

            if (id == R.id.nav_home) {
                intent = new Intent(SearchActivity.this, DashboardActivity.class);
            } else if (id == R.id.nav_upload) {
                intent = new Intent(SearchActivity.this, UploadActivity.class);
            } else if (id == R.id.nav_browse) {
                intent = new Intent(SearchActivity.this, BrowseActivity.class);
            } else if (id == R.id.nav_download) {
                intent = new Intent(SearchActivity.this, DownloadActivity.class);
            }
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                new android.os.Handler().postDelayed(this::finish, 50);
            }
            return true;
        });
    }
}
