package com.example.visitacomercial;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.visitacomercial.Control.CidadeDAO;
import com.example.visitacomercial.Control.ClienteDAO;
import com.example.visitacomercial.Control.ServiceViaCep;
import com.example.visitacomercial.Database.AppDatabase;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Models.Endereco;

import java.io.IOException;

public class CadastroClienteActivity extends AppCompatActivity
{
    private EditText editTextCep, editTextCnpj, editTextRazaoSocial, editTextNomeFantasia, editTextTelefone, editTextEmail, editTextLogradouro, editTextBairro, editTextNumero, editTextCidade, editTextIbge;
    private Button buttonBuscarEndereco, buttonSalvarCliente;
    private String zipcode, end;
    private Endereco endereco;
    private Boolean cidadeExiste;

    private CidadeDAO cidadeDAO;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_cliente);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialização do DAO
        cidadeDAO = new CidadeDAO(getApplicationContext(), getLifecycle());
        clienteDAO = new ClienteDAO(getApplicationContext());

        // Inicialização dos componentes da interface
        editTextCep = findViewById(R.id.editTextCep);
        editTextCnpj = findViewById(R.id.editTextCnpj);
        editTextRazaoSocial = findViewById(R.id.editTextRazaoSocial);
        editTextNomeFantasia = findViewById(R.id.editTextNomeFantasia);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextIbge = findViewById(R.id.editTextIbge);
        buttonBuscarEndereco = findViewById(R.id.buttonBuscarEndereco);
        buttonSalvarCliente = findViewById(R.id.buttonSalvarCliente);

        // Eventos
        buttonBuscarEndereco.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buscarEndereco(view);
            }

        });

        buttonSalvarCliente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                salvarCliente(view);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void buscarEndereco(View view)
    {
        getCepFromEditText();

        Thread thread = new Thread(() -> {
            try
            {
                endereco = ServiceViaCep.buscarEnderecoViaCep(zipcode);
                Log.d("BUSCAR.END.IN.THREAD", "ENDERECO IN: " + endereco);

                if (endereco.isErro())
                {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "CEP inválido!", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
            }
            catch (IOException e)
            {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erro ao buscar endereço por CEP", Toast.LENGTH_SHORT).show();
                });
            }
        });

        thread.start();

        try
        {
            thread.join();
            Log.d("BUSCAR.END.OUT.THREAD", "ENDERECO OUT: "+endereco);

            verificarCidadeExiste();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Log.d("BUSCAR.ENDERECO", "CEP: "+zipcode);
    }

    //--------------------------------------------------------------------------------------------//
    public void salvarCliente(View view)
    {
        Cliente cliente = new Cliente();
        cliente.setCnpj(editTextCnpj.getText().toString());
        cliente.setSocialReason(editTextRazaoSocial.getText().toString());
        cliente.setFantasyName(editTextNomeFantasia.getText().toString());
        cliente.setPhone(editTextTelefone.getText().toString());
        cliente.setEmail(editTextEmail.getText().toString());
        cliente.setZipCode(zipcode);
        cliente.setStreet(editTextLogradouro.getText().toString());
        cliente.setNumber(editTextNumero.getText().toString());
        cliente.setDistrict(editTextBairro.getText().toString());
        cliente.setIbgeCity(Long.valueOf(editTextIbge.getText().toString()));

        Log.d("SALVAR.CLI", "CLIENTE: "+cliente);
        clienteDAO.inserirCliente(cliente);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void getCepFromEditText()
    {
        if (!editTextCep.getText().toString().contains("-"))
        {
            zipcode = editTextCep.getText().toString();
        }

        if ( editTextCep.getText().toString().contains("-"))
        {
            zipcode = editTextCep.getText().toString().replace("-" , "");
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void verificarCidadeExiste()
    {
        Log.d("VERIFICAR.CTY.EXISTE", "INICIO");
        Log.d("VERIFICAR.CTY.EXISTE", "IBGE: "+endereco.getIbge());

        Long l = Long.parseLong(endereco.getIbge());
        Log.d("VERIFICAR.CTY.EXISTE.LONG", "IBGE LONG: "+l);

        cidadeExiste = cidadeDAO.verificarCidadeExiste(l);

        if (cidadeExiste == false)
        {
            Cidade cidade = new Cidade();
            cidade.setIbge(Long.parseLong(endereco.getIbge()));
            cidade.setName(endereco.getLocalidade());
            cidade.setDdd(endereco.getDdd());
            cidade.setUf(endereco.getUf());

            cidadeDAO.inserirCidade(cidade);
            Toast.makeText(this, "Cidade inserida com sucesso!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Cidade já existe no banco de dados!", Toast.LENGTH_SHORT).show();
        }

        adicionarEnderecoNaTela();
    }

    public void adicionarEnderecoNaTela()
    {
        editTextLogradouro.setText(endereco.getLogradouro());
        editTextBairro.setText(endereco.getBairro());
        editTextCidade.setText(endereco.getLocalidade());
        editTextIbge.setText(endereco.getIbge());

        Log.d("ENDERECO.NA.TELA", "IBGE: "+editTextIbge.getText().toString());
    }
}