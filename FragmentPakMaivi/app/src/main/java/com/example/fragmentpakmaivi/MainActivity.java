package com.example.fragmentpakmaivi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnvMusikKita;
    private ActionBar judulBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        judulBar = getSupportActionBar();
        bukaFragment(new MusicFragment());
        judulBar.setTitle("Musik");
        bnvMusikKita = findViewById(R.id.bnv_musik_kita);
        bnvMusikKita.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment FR;
                int itemId = item.getItemId();
                if (itemId == R.id.menu_musik) {
                    bukaFragment(new MusicFragment());
                    judulBar.setTitle("Musik");
                    return true;
                } else if (itemId == R.id.menu_album) {
                    bukaFragment(new AlbumFragment());
                    judulBar.setTitle("Album");
                    return true;
                } else if (itemId == R.id.menu_artis) {
                    bukaFragment(new ArtisFragment());
                    judulBar.setTitle("Artis");
                    return true;
                }
                return false;
            }
        });

    }
    private void bukaFragment(Fragment FRG) {
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.fl_container, FRG);
        FT.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atas, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem
                                                 item) {
        if(item.getItemId() == R.id.menu_about) {
            startActivity(new Intent(MainActivity.this,
                    AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}