package com.example.sipora.rizalmhs.Register;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sipora.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    RecyclerView recycler;
    TextView txtEmpty;

    Button tabAll, tabDone, tabProcess, tabFail;

    List<DownloadModel> listAll = new ArrayList<>();
    List<DownloadModel> listDone = new ArrayList<>();
    List<DownloadModel> listProcess = new ArrayList<>();
    List<DownloadModel> listFail = new ArrayList<>();

    String currentTab = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        txtEmpty = findViewById(R.id.txtEmpty);

        tabAll = findViewById(R.id.tabAll);
        tabDone = findViewById(R.id.tabDone);
        tabProcess = findViewById(R.id.tabProcess);
        tabFail = findViewById(R.id.tabFail);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        setupTabs();
        loadDownloads();
        setupBottomNav();
    }

    private void setupTabs() {
        tabAll.setOnClickListener(v -> show("all"));
        tabDone.setOnClickListener(v -> show("done"));
        tabProcess.setOnClickListener(v -> show("process"));
        tabFail.setOnClickListener(v -> show("fail"));
    }

    private void loadDownloads() {

        int userId = UserSession.getUserId(this);
        String url = "http://10.10.184.196/SIPORAWEB/backend/sipora_api/get_download.php?user_id=" + userId;

        StringRequest req = new StringRequest(Request.Method.GET, url, res -> {
            try {
                listAll.clear();
                listDone.clear();

                JSONObject obj = new JSONObject(res);
                JSONArray arr = obj.getJSONArray("downloads");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);

                    DownloadModel d = new DownloadModel(
                            o.getInt("id"),
                            o.getString("judul"),
                            o.getString("abstrak"),
                            o.getString("uploader"),
                            o.getString("tipe"),
                            o.getString("ukuran"),
                            o.getString("tanggal"),
                            "selesai",
                            o.getString("file_url")
                    );

                    listAll.add(d);
                    listDone.add(d);
                }

                show(currentTab);

            } catch (Exception ignored) { }
        }, err -> { });

        Volley.newRequestQueue(this).add(req);
    }

    private void show(String tab) {
        currentTab = tab;

        List<DownloadModel> data;

        switch (tab) {
            case "done": data = listDone; break;
            case "process": data = listProcess; break;
            case "fail": data = listFail; break;
            default: data = listAll;
        }

        txtEmpty.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);

        recycler.setAdapter(new DownloadAdapter(data, new DownloadAdapter.OnActionListener() {
            @Override
            public void onOpen(DownloadModel m) {
                showOpenDialog(m);
            }

            @Override
            public void onDelete(DownloadModel m) {
                showDeleteDialog(m);
            }
        }));
    }

    private void showOpenDialog(DownloadModel m) {
        new AlertDialog.Builder(this)
                .setTitle("Unduh Ulang?")
                .setMessage("Dokumen ini pernah diunduh. Ingin unduh ulang?")
                .setPositiveButton("Ya", (d, w) -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(m.getFileUrl()));
                    startActivity(i);
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void showDeleteDialog(DownloadModel m) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Dokumen?")
                .setMessage("Apakah yakin ingin menghapus dokumen ini dari riwayat?")
                .setPositiveButton("Hapus", (d, w) -> deleteItem(m))
                .setNegativeButton("Batal", null)
                .show();
    }

    private void deleteItem(DownloadModel m) {
        String url = "http://10.10.184.196/SIPORAWEB/backend/sipora_api/delete_download.php?id=" + m.getId();

        StringRequest req = new StringRequest(Request.Method.GET, url,
                res -> loadDownloads(),
                err -> { });

        Volley.newRequestQueue(this).add(req);
    }

    private void setupBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottomNavigation);
        nav.setSelectedItemId(R.id.nav_download);

        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home)
                startActivity(new Intent(this, DashboardActivity.class));
            else if (id == R.id.nav_upload)
                startActivity(new Intent(this, UploadActivity.class));
            else if (id == R.id.nav_browse)
                startActivity(new Intent(this, BrowseActivity.class));
            else if (id == R.id.nav_search)
                startActivity(new Intent(this, SearchActivity.class));
            else
                return true;

            finish();
            overridePendingTransition(0, 0);
            return true;
        });
    }
}
