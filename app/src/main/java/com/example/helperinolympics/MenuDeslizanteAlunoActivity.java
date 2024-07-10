package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.google.android.material.navigation.NavigationView;

public class MenuDeslizanteAlunoActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton btnAbreFechaMenu;
    private NavigationView navView;
    private ActivityMenuDeslizanteAlunoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuDeslizanteAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = binding.drawerLayout;
        navView = binding.navView;

        // Configuração do ImageButton para abrir e fechar o DrawerLayout
        btnAbreFechaMenu = binding.btnBarraMenuAluno;
        btnAbreFechaMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView);
            } else {
                drawerLayout.openDrawer(navView);
            }
        });

     navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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


     //Acesso a olimpiada
        findViewById(R.id.cardOlimpiada2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuDeslizanteAlunoActivity.this, InicioOlimpiadaActivity.class);
                startActivity(intent);
            }
        });
    }
}
