package com.example.visitacomercial;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.visitacomercial.Adapter.ClientesAdapter;
import com.example.visitacomercial.Adapter.SpinnerClienteAdapter;
import com.example.visitacomercial.Control.ClienteAPIService;
import com.example.visitacomercial.Control.ClienteDAO;
import com.example.visitacomercial.Control.VisitaDAO;
import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Models.Visita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VisitaActivity extends AppCompatActivity
{
    private EditText editTextData, editTextValorOrdem, editTextObservacao;
    private RatingBar ratingBar;
    private Spinner spinnerClientesVisita;
    private Button buttonAdicionarVisita;

    private SpinnerClienteAdapter spinnerClienteAdapter;
    private Cliente clienteSelected = new Cliente();
    private ClienteAPIService clienteApiService;
    private ClienteDAO clienteDAO;

    private VisitaDAO visitaDAO;

    private AppDatabase database;

    // Observers
    Observer<List<Cliente>> observerClientes = new Observer<List<Cliente>>() {
        @Override
        public void onChanged(List<Cliente> clientes)
        {
            spinnerClienteAdapter.clear();
            spinnerClienteAdapter.addAll(clientes);
            spinnerClienteAdapter.notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visita);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização do DAO
        visitaDAO = new VisitaDAO(getApplicationContext(), getLifecycle());
        clienteDAO = new ClienteDAO(getApplicationContext(), getLifecycle());

        // Inicialize os elementos de interface do usuário
        editTextData = findViewById(R.id.editData);
        editTextValorOrdem = findViewById(R.id.editTextValorOrdem);
        editTextObservacao = findViewById(R.id.editTextObservacao);
        ratingBar = findViewById(R.id.ratingBar);
        spinnerClientesVisita = findViewById(R.id.spinnerClientesVisita);
        buttonAdicionarVisita = findViewById(R.id.buttonAdicionarVisita);

        //Adapter & List
        if (clienteDAO.getClientes() != null)
        {
            spinnerClienteAdapter = new SpinnerClienteAdapter(this, clienteDAO.getClientes());
        }
        else
        {
            spinnerClienteAdapter = new SpinnerClienteAdapter(this, new ArrayList<>());
        }
        spinnerClientesVisita.setAdapter(spinnerClienteAdapter);

        // Eventos
        spinnerClientesVisita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isFirstSelection)
                {
                    isFirstSelection = false;
                    return;
                }

                clienteSelected = (Cliente) parent.getItemAtPosition(position);
                Log.d("SPINNER.CDD.SELECTED", "CLIENTE: " + clienteSelected.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("SPINNER.CDD.ERROR", "Nenhum cliente selecionado.");
            }
        });


        editTextData.setOnClickListener(v -> showDateTimePickerDialog());
        editTextObservacao.setOnClickListener(v -> showInputDialog());

        buttonAdicionarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                adicionarVisita(view);
            }
        });
    }

    //--------------------------------------------------------------------------------------------//
    public void onStart()
    {
        super.onStart();
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        clienteApiService = database.clienteService();
        clienteApiService.getClients().observe(this, observerClientes);
    }

    //--------------------------------------------------------------------------------------------//
    public void onStop()
    {
        super.onStop();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void adicionarVisita(View view)
    {
        Visita visita = new Visita();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int rating = (int) ratingBar.getRating();

        String valor = "";

        visita.setClientCnpj(getClienteSelected().getCnpj());
        Log.d("CDD.VISITA", "CLIENTE SELECTED: "+getClienteSelected());

        try
        {
            visita.setDate(formato.parse(editTextData.getText().toString()));
        }
        catch (ParseException e)
        {
            Toast.makeText(this, "Data inválida!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextValorOrdem.getText().toString().isEmpty() || editTextValorOrdem.getText().toString().equals(""))
        {
            valor = "0.00";
        }
        else
        {
            if (editTextValorOrdem.getText().toString().contains(","))
            {
                valor = editTextValorOrdem.getText().toString().replace(",", ".");
            }

            valor = editTextValorOrdem.getText().toString();
        }

        visita.setOrderValue(Double.parseDouble(valor));
        visita.setNotes(editTextObservacao.getText().toString());
        visita.setSatisfaction(rating);

        Log.d("CDD.VISITA", "VISITA: "+visita);

        boolean sucess = visitaDAO.inserirVisita(visita);

        if (sucess)
        {
            Toast.makeText(this, "Visita inserida com sucesso!", Toast.LENGTH_SHORT).show();
            apagarCampos();
        }
        else
        {
            Toast.makeText(this, "Erro ao inserir visita!", Toast.LENGTH_SHORT).show();
        }

        atualizarCliente(getClienteSelected(), visita);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void showDateTimePickerDialog() {
        // Obter a data e hora atuais
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Criar o DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Após selecionar a data, criar o TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            this,
                            (view1, hourOfDay, minute1) -> {
                                // Formatar a data e hora selecionadas
                                Calendar selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(year1, month1, dayOfMonth, hourOfDay, minute1);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                editTextData.setText(sdf.format(selectedDateTime.getTime()));
                            },
                            hour, minute, true);

                    // Exibir o TimePickerDialog
                    timePickerDialog.show();
                },
                year, month, day);

        // Exibir o DatePickerDialog
        datePickerDialog.show();
    }

    //--------------------------------------------------------------------------------------------//
    private void showInputDialog() {
        // Inflar o layout customizado
        final EditText input = new EditText(this);
        input.setLines(10);
        input.setHint("Escreva sua observação aqui...");

        // Criar o Dialog
        new AlertDialog.Builder(this)
                .setTitle("Escreva sua observação")
                .setView(input) // Adiciona o EditText no dialog
                .setPositiveButton("OK", (dialog, which) -> {
                    // Captura o texto digitado
                    String texto = input.getText().toString();
                    editTextObservacao.setText(texto);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void apagarCampos()
    {
        editTextData.setText("");
        editTextValorOrdem.setText("");
        editTextObservacao.setText("");
        ratingBar.setRating(0);
    }

    public Cliente getClienteSelected()
    {
        if (clienteSelected.getCnpj() == null || clienteSelected.getCnpj().isEmpty() || clienteSelected == null)
        {
            return (Cliente) spinnerClientesVisita.getItemAtPosition(0);
        }

        return clienteSelected;
    }

    public void atualizarCliente(Cliente cliente, Visita visita)
    {
        cliente.setLastVisit(visita.getDate());

        clienteDAO.alterarCliente(cliente);
    }
}