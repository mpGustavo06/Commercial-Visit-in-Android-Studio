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

import com.example.visitacomercial.Control.CidadeDAO;
import com.example.visitacomercial.Control.ClienteDAO;
import com.example.visitacomercial.Control.ServiceViaCep;
import com.example.visitacomercial.Models.Cidade;
import com.example.visitacomercial.Models.Cliente;
import com.example.visitacomercial.Models.Endereco;

import java.io.IOException;

public class CadastroClienteActivity extends AppCompatActivity
{
    private EditText editTextCep, editTextCnpj, editTextRazaoSocial, editTextNomeFantasia, editTextTelefone, editTextEmail, editTextLogradouro, editTextBairro, editTextNumero, editTextCidade, editTextIbge, editTextContatoPessoa;
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
        clienteDAO = new ClienteDAO(getApplicationContext(), getLifecycle());

        // Inicialização dos componentes da interface
        editTextCep = findViewById(R.id.editTextCep);
        editTextCnpj = findViewById(R.id.editTextCnpj);
        editTextRazaoSocial = findViewById(R.id.editTextRazaoSocial);
        editTextNomeFantasia = findViewById(R.id.editTextNomeFantasia);
        editTextContatoPessoa = findViewById(R.id.editTextContatoPessoa);
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
    public void salvarCliente(View view)
    {
        Cliente cliente = new Cliente();
        String cnpj;

        if ( isNotNull(editTextCnpj.getText().toString()) )
        {
            cnpj = editTextCnpj.getText().toString();;
        }
        else
        {
            Toast.makeText(this, "CNPJ está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextRazaoSocial.getText().toString()) )
        {
            cliente.setSocialReason(editTextRazaoSocial.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Razão Social está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextNomeFantasia.getText().toString()) )
        {
            cliente.setFantasyName(editTextNomeFantasia.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Nome Fantasia está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextTelefone.getText().toString()) )
        {
            cliente.setPhone(editTextTelefone.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Telefone está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextContatoPessoa.getText().toString()) )
        {
            cliente.setPhone(editTextContatoPessoa.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Responsável está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextEmail.getText().toString()) )
        {
            cliente.setEmail(editTextEmail.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Email está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextLogradouro.getText().toString()) )
        {
            cliente.setStreet(editTextLogradouro.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Logradouro está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextBairro.getText().toString()) )
        {
            cliente.setDistrict(editTextBairro.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Bairro está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextNumero.getText().toString()) )
        {
            cliente.setNumber(editTextNumero.getText().toString());
        }
        else
        {
            Toast.makeText(this, "Número está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextCidade.getText().toString()) )
        {
            cliente.setIbgeCity(Long.parseLong(editTextIbge.getText().toString()));
        }
        else
        {
            Toast.makeText(this, "IBGE está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( isNotNull(editTextCep.getText().toString()) )
        {
            cliente.setZipCode(editTextCep.getText().toString());
        }
        else
        {
            Toast.makeText(this, "CEP está Nulo!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (cnpj.length() >= 14 && cnpj.length() <= 18)
        {
            if (cnpj.contains("."))
                cnpj = cnpj.replace(".", "");

            if (cnpj.contains("-"))
                cnpj = cnpj.replace("-", "");

            if (cnpj.contains("/"))
                cnpj = cnpj.replace("/", "");

            try
            {
                Double cnpjNumber = Double.parseDouble(cnpj);
                cliente.setCnpj(cnpj);
            }
            catch (NumberFormatException e)
            {
                Toast.makeText(this, "CNPJ inválido!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            Toast.makeText(this, "CNPJ é maior ou menor que o válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("SALVAR.CLI", "CLIENTE: "+cliente);
        boolean sucess = clienteDAO.inserirCliente(cliente);

        if (sucess)
        {
            Toast.makeText(this, "Cliente inserido com sucesso!", Toast.LENGTH_SHORT).show();
            apagarCampos();
        }
        else
        {
            Toast.makeText(this, "Erro ao inserir cliente!", Toast.LENGTH_SHORT).show();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void buscarEndereco(View view)
    {
        boolean sucess = getCepFromEditText();

        if (sucess == true)
        {
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
                Log.d("BUSCAR.END.OUT.THREAD", "ENDERECO OUT: " + endereco);

                verificarCidadeExiste();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            Log.d("BUSCAR.ENDERECO", "CEP: " + zipcode);
        }
        else
        {
            Toast.makeText(this, "CEP inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean getCepFromEditText()
    {
        if (editTextCep.getText().toString().length() >= 8 && editTextCep.getText().toString().length() <= 9)
        {
            if (!editTextCep.getText().toString().contains("-"))
            {
                zipcode = editTextCep.getText().toString();
            }

            if (editTextCep.getText().toString().contains("-"))
            {
                zipcode = editTextCep.getText().toString().replace("-", "");
            }

            return true;
        }

        zipcode = "";
        Toast.makeText(this, "CEP inválido!", Toast.LENGTH_SHORT).show();
        return false;
    }

    //--------------------------------------------------------------------------------------------//
    public void verificarCidadeExiste()
    {
        Log.d("VERIFICAR.CTY.EXISTE", "INICIO");


        Long l = 0L;

        try
        {
            if (endereco.getIbge() != null)
            {
                l = Long.parseLong(endereco.getIbge());
                Log.d("VERIFICAR.CTY.EXISTE", "IBGE: "+endereco.getIbge());
            }
            else
            {
                Log.d("VERIFICAR.CTY.EXISTE", "endereco.getIbge(): null");
                Toast.makeText(this, "Cidade inválida!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (NumberFormatException e)
        {
            Log.d("VERIFICAR.CTY.EXISTE", "NumberFormatException: "+e.getMessage());
            return;
        }

        Log.d("VERIFICAR.CTY.EXISTE.LONG", "IBGE LONG: "+l);

        cidadeExiste = cidadeDAO.verificarCidadeExiste(l);

        if (cidadeExiste)
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

    //--------------------------------------------------------------------------------------------//
    public void adicionarEnderecoNaTela()
    {
        editTextLogradouro.setText(endereco.getLogradouro());
        editTextBairro.setText(endereco.getBairro());
        editTextCidade.setText(endereco.getLocalidade());
        editTextIbge.setText(endereco.getIbge());

        Log.d("ENDERECO.NA.TELA", "IBGE: "+editTextIbge.getText().toString());
    }

    //--------------------------------------------------------------------------------------------//
    public void apagarCampos()
    {
        editTextCep.setText("");
        editTextCnpj.setText("");
        editTextRazaoSocial.setText("");
        editTextNomeFantasia.setText("");
        editTextTelefone.setText("");
        editTextEmail.setText("");
        editTextLogradouro.setText("");
        editTextBairro.setText("");
        editTextNumero.setText("");
        editTextCidade.setText("");
        editTextIbge.setText("");
        editTextContatoPessoa.setText("");
    }

    //--------------------------------------------------------------------------------------------//
    public static boolean isNotNull(String texto)
    {
        return texto != null && !texto.trim().isEmpty();
    }
}