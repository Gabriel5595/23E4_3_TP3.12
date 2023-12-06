package org.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.User;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void PostTest() {
        try {
            // URL da API
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");

            // Abrindo a conexão
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurando a conexão para o método POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Criando um objeto User com os dados
            User user = new User(1, 1, "delectus aut autem", false);

            // Usando ObjectMapper para converter o objeto User em JSON
            String jsonInputString = new ObjectMapper().writeValueAsString(user);

            // Obtendo o stream de saída da conexão para enviar o JSON
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtendo o código de resposta da requisição
            int responseCode = connection.getResponseCode();

            // Verificando se o código de resposta é 200 (OK)
            assertEquals(201, responseCode);

            // Fechando a conexão
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
