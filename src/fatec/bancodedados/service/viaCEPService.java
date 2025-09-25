/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.service;

//TODO: Erro devido a falta da classe Endereco
import javax.json.Json;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.json.JsonObject;
import javax.json.JsonReader;
import fatec.bancodedados.model.Endereco;
import java.net.HttpURLConnection;;//

/**
 *
 * @author isaqu
 */
public class viaCEPService {
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";
    
    public static Endereco buscarEnderecoPorCep(String cep) throws Exception {
        try{
            String url = VIA_CEP_URL + cep + "/json";

            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            InputStream inputStream = con.getInputStream();

            JsonReader reader = Json.createReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonObject json = reader.readObject();

            if (json.containsKey("erro") && json.getBoolean("erro")) {
                return null;
            }


            Endereco endereco = new Endereco();
            endereco.setCep(json.getString("cep", ""));
            endereco.setLogradouro(json.getString("logradouro", ""));
            endereco.setComplemento(json.getString("complemento", ""));
            endereco.setBairro(json.getString("bairro", ""));
            endereco.setUf(json.getString("uf", ""));
            endereco.setCidade(json.getString("localidade", ""));

            return endereco;
        }catch(Exception e){
            System.out.println("Erro ao buscar CEP: " + e.getMessage());
            return null;
        }
    };
}
