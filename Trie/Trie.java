package Trie;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Método para inserir uma palavra na Trie
    public void insert(String word, String nomeArquivo) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;

        if(!node.arquivos.contains(nomeArquivo)){
            node.arquivos.add(nomeArquivo);
        }
    }

    // Método para buscar uma palavra completa na Trie
    public List<String> search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return null;
            }
        }
        if(node.isEndOfWord){
            return node.arquivos;
        }else{
            return null;
        }
    }

    // Método para verificar se existe alguma palavra que começa com o prefixo
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return false;
            }
        }
        return true;
    }


    // Método para imprimir toda a Trie
    public void printTrie() {
        printTrieRecursive(root, "");
    }

    // Método auxiliar recursivo para imprimir os elementos da Trie
    private void printTrieRecursive(TrieNode node, String prefix) {
        // Se o nó é o fim de uma palavra, imprimimos o prefixo
        if (node.isEndOfWord) {
            System.out.println(prefix);
        }

        // Recorre todos os filhos do nó e imprime suas palavras
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            char ch = entry.getKey();
            TrieNode childNode = entry.getValue();
            printTrieRecursive(childNode, prefix + ch);  // Adiciona o caractere ao prefixo
        }
    }

}