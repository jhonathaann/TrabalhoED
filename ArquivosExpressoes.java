import java.io.*;
import java.util.*;

public class ArquivosExpressoes {
     public static List<String> carregarExpressoes(String caminhoArquivo) {
        List<String> expressoes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Adiciona cada linha (expressão) na lista
                expressoes.add(linha.trim());
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar as expressões: " + e.getMessage());
        }
        return expressoes;
    }
}
