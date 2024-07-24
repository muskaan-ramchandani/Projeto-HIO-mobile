package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
                 return true;
             }else if(itemID == R.id.nav_favoritos_aluno){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, FavoritosAlunoActivity.class));
                 return true;
             }else if(itemID == R.id.nav_chats){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ChatsAlunoActivity.class));
                 return true;
             }else if(itemID == R.id.nav_manual){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ManualActivity.class));
                 return true;
             }else if(itemID == R.id.nav_configuracoes){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ConfiguracoesActivity.class));
                 return true;
             }else if(itemID == R.id.nav_sair){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, SairActivity.class));
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

        //DADOS PARA O BANCO
        DadosOlimpiada dado1 = new DadosOlimpiada(R.drawable.imgtelescopio, "Olimpíada Brasileira de Astronomia",
                "OBA", "Rosa");
        olimpiadas.add(dado1);

        DadosOlimpiada dado2 = new DadosOlimpiada(R.drawable.imgmacacaindo, "Olimpíada Brasileira de Física",
                "OBF", "Azul");
        olimpiadas.add(dado2);

        DadosOlimpiada dado3 = new DadosOlimpiada(R.drawable.imgcomputador, "Olimpíada Brasileira de Informática",
                "OBI", "Laranja");
        olimpiadas.add(dado3);

        //CONFIGURAR PARA AUMENTAR SE FOR OBMEP
        DadosOlimpiada dado4 = new DadosOlimpiada(R.drawable.imgoperacoesbasicas, "Olimpíada Brasileira de Matemática das Escolas Públicas",
                "OBMEP", "Ciano");
        olimpiadas.add(dado4);

        DadosOlimpiada dado5 = new DadosOlimpiada(R.drawable.imgpapiro, "Olimpíada Nacional da História Brasileira",
                "ONHB", "Rosa");
        olimpiadas.add(dado5);

        DadosOlimpiada dado6 = new DadosOlimpiada(R.drawable.imgtubodeensaio, "Olimpíada Brasileira de Química",
                "OBQ", "Azul");
        olimpiadas.add(dado6);

        DadosOlimpiada dado7 = new DadosOlimpiada(R.drawable.imgdna, "Olimpíada Brasileira de Biologia",
                "OBB", "Laranja");
        olimpiadas.add(dado7);

        DadosOlimpiada dado8 = new DadosOlimpiada(R.drawable.imgatomo, "Olimpíada Nacional de Ciências",
                "ONC", "Ciano");
        olimpiadas.add(dado8);
    }


}
