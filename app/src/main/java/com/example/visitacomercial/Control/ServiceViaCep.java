package com.example.visitacomercial.Control;

import android.util.Log;

import com.example.visitacomercial.Models.Endereco;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceViaCep
{
    private static String baseAddress = "https://viacep.com.br/ws/%s/json";

    public static Endereco buscarEnderecoViaCep(String cep) throws IOException
    {
        Endereco enderecoObj = null;

            URL url = new URL(String.format(baseAddress, cep));
            Log.d("SERVICE.VIACEP", "URL: "+url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                    Log.d("WHILE.RESPONSE", "LINES: "+inputLine);
                }
                in.close();

                // Converter o JSON para o objeto CEP
                Gson gson = new Gson();
                enderecoObj = gson.fromJson(response.toString(), Endereco.class);
                Log.d("SERVICE.VIACEP", "ENDERECO: "+enderecoObj);
            }
            else
            {
                throw new IOException("Erro ao buscar CEP: " + conn.getResponseCode());
            }

            conn.disconnect();

        Log.d("SERVICE.VIACEP", "ENDERECO: "+enderecoObj);
        Log.d("SERVICE.VIACEP.STRING", "ENDERECO: "+enderecoObj);
        return enderecoObj;
    }
}