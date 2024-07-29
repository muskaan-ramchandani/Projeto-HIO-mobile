package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.RankingActivity;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.example.helperinolympics.menu.ChatsAlunoActivity;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.menu.FavoritosAlunoActivity;
import com.example.helperinolympics.menu.ManualActivity;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.menu.SairActivity;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class InicialAlunoMenuDeslizanteActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ImageButton btnAbreFechaMenu, acessarRanking, acessarCalendario;
    private NavigationView navView;
    private ActivityMenuDeslizanteAlunoBinding binding;

    RecyclerView rvOlimpiadas;
    List<DadosOlimpiada> olimpiadas = new ArrayList<>();
    AdapterOlimpiadas adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuDeslizanteAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        drawerLayout = binding.drawerLayout;
        navView = binding.navView;

        //Função dos botoes inferiores
        acessarRanking=findViewById(R.id.btnAcessarRanking);
        acessarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(InicialAlunoMenuDeslizanteActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        acessarCalendario =findViewById(R.id.btnCalendario);


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
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, PerfilAlunoActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_favoritos_aluno){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, FavoritosAlunoActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_chats){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ChatsAlunoActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_manual){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ManualActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_configuracoes){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ConfiguracoesActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_sair){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, SairActivity.class));
                 finish();
                 return true;
             }else{
                 return false;
             }
         }
     });

        configurarRecyclerOlimpiadas();
    }

    public void configurarRecyclerOlimpiadas(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter= new AdapterOlimpiadas(olimpiadas);
        rvOlimpiadas=findViewById(R.id.recyclerViewTelaInicialOlimpiadas);
        rvOlimpiadas.setLayoutManager(layoutManager);
        rvOlimpiadas.setHasFixedSize(true);
        rvOlimpiadas.setAdapter(adapter);

        Intent intent = getIntent();
        ArrayList<DadosOlimpiada> listaRecebida = intent.getParcelableArrayListExtra("listaEscolhidas");
        if (listaRecebida != null) {
            olimpiadas.clear();
            olimpiadas.addAll(listaRecebida);
            adapter.notifyDataSetChanged();
        } else {
            Log.e("InicialAlunoMenuDeslizanteActivity", "Nenhuma lista de olimpiadas foi recebida.");
        }
    }

}
