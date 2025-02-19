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
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.visitacomercial.Adapter.ClientesAdapter;
import com.example.visitacomercial.Adapter.SpinnerCityAdapter;
import com.example.visitacomercial.Control.CidadeAPIService;
import com.example.visitacomercial.Control.CidadeDAO;
import com.example.visitacomercial.Control.ClienteAPIService;
import com.example.visitacomercial.Control.ClienteDAO;
import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinnerCidades;
    private Button buttonCadastrarCliente;
    private ListView listViewClientes;

    private Cliente clienteSelected = new Cliente();
    private ClienteDAO clienteDAO;
    private List<Cliente> clientesList = new ArrayList<>();
    private ClientesAdapter clientesAdapter;
    private ClienteAPIService clienteApiService;

    private Cidade cidadeSelected = new Cidade();
    private CidadeDAO cidadeDAO;
    private CidadeAPIService cidadeApiService;
    private SpinnerCityAdapter spinnerCityAdapter;

    private AppDatabase database;

    // Observers
    Observer<List<Cidade>> observerCidades = new Observer<List<Cidade>>() {
        @Override
        public void onChanged(List<Cidade> cidades)
        {
            spinnerCityAdapter.clear();
            spinnerCityAdapter.addAll(cidades);
            spinnerCityAdapter.notifyDataSetChanged();
        }
    };

    Observer<List<Cliente>> observerClientes = new Observer<List<Cliente>>() {
        @Override
        public void onChanged(List<Cliente> clientes)
        {
            clientesAdapter.clear();
            clientesAdapter.addAll(clientes);
            clientesAdapter.notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização do DAO
        cidadeDAO = new CidadeDAO(getApplicationContext(), getLifecycle());
        clienteDAO = new ClienteDAO(getApplicationContext(), getLifecycle());

        //Testando se existe conexão com a internet
        if (!NetworkUtils.isConnected(this)) {
            Toast.makeText(this, "Sem conexão com a internet. O aplicativo não pode ser usado offline.", Toast.LENGTH_LONG).show();
            finish();
        }

        // Inicialização dos componentes da interface
        buttonCadastrarCliente = findViewById(R.id.buttonCadastrarCliente);
        listViewClientes = findViewById(R.id.ListViewClientes);
        spinnerCidades = findViewById(R.id.spinnerCities);

        //Adapter & List
        if (clienteDAO.getClientes() != null)
        {
            clientesAdapter = new ClientesAdapter(this, clienteDAO.getClientes());
        }
        else
        {
            clientesAdapter = new ClientesAdapter(this, new ArrayList<>());
        }
        listViewClientes.setAdapter(clientesAdapter);
        listViewClientes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Configuaração do Spinner
        if (cidadeDAO.getCidades() != null)
        {
            spinnerCityAdapter = new SpinnerCityAdapter(this, cidadeDAO.getCidades());
        }
        else
        {
            spinnerCityAdapter = new SpinnerCityAdapter(this, new ArrayList<>());
        }
        spinnerCidades.setAdapter(spinnerCityAdapter);

        // Eventos
        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                if (isFirstSelection)
                {
                    isFirstSelection = false;
                    return;
                }

                cidadeSelected = (Cidade) adapterView.getItemAtPosition(position);
                Log.d("SPINNER.CIDADE.SELECTED", "CIDADE SELECT: " + cidadeSelected);

                clienteApiService.getClientPerCity(cidadeSelected.getIbge()).observe(MainActivity.this, new Observer<List<Cliente>>() {
                    @Override
                    public void onChanged(List<Cliente> clientes)
                    {
                        clientesAdapter.clear();
                        clientesAdapter.addAll(clientes);
                        clientesAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("SPINNER.CTY.ERROR", "Nenhuma cidade selecionado.");
            }
        });

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                view.setActivated(true);

                clienteSelected = (Cliente) adapterView.getItemAtPosition(position);
            }
        });

        buttonCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                abrirCadastroCliente(view);
            }
        });
    }

    //--------------------------------------------------------------------------------------------//
    public void onStart()
    {
        super.onStart();
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database")
                .setQueryCallback((sqlQuery, bindArgs) -> { Log.d("ROOM.QUERY.||||||||||", "SQL: " + sqlQuery); }, Executors.newSingleThreadExecutor())
                .build();

        cidadeApiService = database.cidadeService();
        cidadeApiService.getCities().observe(this, observerCidades);

        clienteApiService = database.clienteService();
        clienteApiService.getClients().observe(this, observerClientes);
    }

    //--------------------------------------------------------------------------------------------//
    public void onStop()
    {
        super.onStop();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void abrirCadastroCliente(View view)
    {
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroVisita(View view)
    {
        Intent intent = new Intent(this, VisitaActivity.class);
        intent.putExtra("cliente", clienteSelected);
        startActivity(intent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
}