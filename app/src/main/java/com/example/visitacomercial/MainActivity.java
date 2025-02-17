package com.example.visitacomercial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.visitacomercial.Adapter.ClientesAdapter;
import com.example.visitacomercial.Adapter.SpinnerCityAdapter;
import com.example.visitacomercial.Control.CidadeAPIService;
import com.example.visitacomercial.Control.ClienteAPIService;
import com.example.visitacomercial.Control.VisitaAPIService;
import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinnerCidades;
    private Button buttonCadastrarCliente;
    private ListView listViewClientes;

    private Cliente cliente;
    private Cliente clienteSelected = new Cliente();
    private List<Cliente> clientesList = new ArrayList<>();
    private ClienteAPIService clienteAPIService;
    private ClientesAdapter clientesAdapter;

    private VisitaAPIService visitaAPIService;

    private Cidade cidadeSelected = new Cidade();
    private CidadeAPIService cidadeAPIService;

    private SpinnerCityAdapter spinnerCityAdapter;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("CIDADE", "Chamada a função inserirCidadeManual()");

        //Testando se existe conexão com a internet
        if (!NetworkUtils.isConnected(this))
        {
            Toast.makeText(this, "Sem conexão com a internet. O aplicativo não pode ser usado offline.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Inicialização dos componentes da interface
        buttonCadastrarCliente = findViewById(R.id.buttonCadastrarCliente);
        listViewClientes = findViewById(R.id.ListViewClientes);

        //Adapter & List
        clientesAdapter = new ClientesAdapter(this, (ArrayList<Cliente>) clientesList);
        listViewClientes.setAdapter(clientesAdapter);
        listViewClientes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Configuaração do Spinner
        /**spinnerCityAdapter = new SpinnerCityAdapter(this, new ArrayList<>());
        spinnerCidades.setAdapter(spinnerCityAdapter);*/

        // Observe o LiveData
//        LiveData<List<Cidade>> citiesLiveData = cidadeAPIService.getCities();
//        citiesLiveData.observe(this, new Observer<List<Cidade>>() {
//            @Override
//            public void onChanged(List<Cidade> cities)
//            {
//                // Atualize o adapter do Spinner com a nova lista de cidades
//                spinnerCityAdapter.clear();
//                spinnerCityAdapter.addAll(cities);
//                spinnerCityAdapter.notifyDataSetChanged();
//            }
//        });

        // Eventos
//        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            boolean isFirstSelection = true;
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
//            {
//                if (isFirstSelection)
//                {
//                    isFirstSelection = false;
//                    return;
//                }
//
//                cidadeSelected = (Cidade) adapterView.getItemAtPosition(position);
//                Log.d("SPINNER.CTY.TESTE", "MODELO: "+ cidade.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//                Log.d("SPINNER.CTY.ERROR", "Nenhuma cidade selecionado.");
//            }
//        });

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                view.setActivated(true);

                clienteSelected = (Cliente) adapterView.getItemAtPosition(position);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void abrirCadastroCliente(View view)
    {
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onStart()
    {
        super.onStart();
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        clienteAPIService = database.clienteDAO();
        visitaAPIService = database.visitaDAO();
        cidadeAPIService = database.cidadeDAO();
    }

    public void onStop()
    {
        database.close();
        super.onStop();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}