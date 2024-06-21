package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.helperinolympics.ChatsAlunoActivity;
import com.example.helperinolympics.ConfiguracoesActivity;
import com.example.helperinolympics.FavoritosAlunoActivity;
import com.example.helperinolympics.ManualActivity;
import com.example.helperinolympics.PerfilAlunoActivity;
import com.example.helperinolympics.SairActivity;
import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.ui.AppBarConfiguration;

public class MenuDeslizanteAlunoActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuDeslizanteAlunoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuDeslizanteAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenuDeslizanteAluno.toolbarHIOMenu);

        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

     navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(MenuItem item) {
             // Fecha o drawer quando um item é selecionado
             drawerLayout.closeDrawers();

             int itemID= item.getItemId();

             if(itemID == R.id.nav_perfil_aluno){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, PerfilAlunoActivity.class));
                 return true;
             }else if(itemID == R.id.nav_favoritos_aluno){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, FavoritosAlunoActivity.class));
                 return true;
             }else if(itemID == R.id.nav_chats){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, ChatsAlunoActivity.class));
                 return true;
             }else if(itemID == R.id.nav_manual){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, ManualActivity.class));
                 return true;
             }else if(itemID == R.id.nav_configuracoes){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, ConfiguracoesActivity.class));
                 return true;
             }else if(itemID == R.id.nav_sair){
                 startActivity(new Intent(MenuDeslizanteAlunoActivity.this, SairActivity.class));
                 return true;
             }else{
                 return false;
             }
         }
     });
    }
}
