package Hash;
import java.util.LinkedList;
import java.lang.Math;

public class HashTable {
    private LinkedList<HashEntry>[] tabelaHash; 
    private int tamanho;    // tamanho da tabela hash

    public HashTable(int tamanho){
        tabelaHash = new LinkedList[tamanho];
        this.tamanho = tamanho;
    }

    // calcula a posicao com base no nome do arquivo
    public int getPosition(String texto, String funcao) {

        // escolhendo qual funcao de hash irei usar
        if(funcao.equals("divisao")){
            // funcao hash de DIVISAO
            
            int soma = 0;
            for (char c : texto.toCharArray()) {
                soma += (int) c;
            }
            return soma % tamanho;

        }else{
            
            // funcao hash DJB2

            long hash = 5381;
            for (char c : texto.toCharArray()) {
                 hash = ((hash << 5) + hash) + c; // hash * 33 + c
            }

            int aux = (int) (hash % tamanho);

            return Math.abs(aux);
        }


    }

    // inserindo o nome e o caminho do arquivo na tabela
    public boolean insercao(String chave, String valor, String funcao) {
        if (chave == null || valor == null) {
            return false;
        }

        // pegando a posicao na tabela com base na chave
        int posicao = getPosition(chave, funcao);
        LinkedList<HashEntry> lista = tabelaHash[posicao];

        if (lista == null) {
            lista = new LinkedList<>();
        } else {
            for (HashEntry entry : lista) {
                if (entry.chave.equals(chave) && entry.valor.equals(valor)) {
                    return false;
                }
            }
        }

        lista.add(new HashEntry(chave, valor));
        tabelaHash[posicao] = lista;
        return true;
    }

    // retorna os textos compactados associados ao nome do arquivo
    public String get(String chave, String funcao) {
        if (chave == null) {
            return null;
        }

        // LinkedList<String> valoresPosicao = new LinkedList<>();
        int posicao = getPosition(chave, funcao);

        LinkedList<HashEntry> lista = tabelaHash[posicao];
        if (lista != null) {
            for (HashEntry entry : lista) {
                if (entry.chave.equals(chave)) {
                    // valoresPosicao.add(entry.valor);
                    return entry.valor;
                }
            }
        }
        return null;
    }

    // imprime a tabela hash
    public void print() {
        for (int i = 0; i < tabelaHash.length; i++) {
            System.out.println("--------------------");
            System.out.println("Posição " + i + ":");
            if (tabelaHash[i] == null) {
                System.out.println("Posição vazia na tabela");
            } else {
                for (HashEntry entry : tabelaHash[i]) {
                    System.out.println(entry.toString() + " - ");
                }
            }
        }
    }
}
