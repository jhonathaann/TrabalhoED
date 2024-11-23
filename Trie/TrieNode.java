package Trie;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TrieNode {
	
    Map<Character, TrieNode> children;
    List<String> arquivos;
    boolean isEndOfWord;

    // Construtor padrão
    public TrieNode() {
        this.children = new HashMap<>();
        this.arquivos = new ArrayList<>();
        this.isEndOfWord = false;
    }
    
    // Construtor para definir explicitamente o estado de `isEndOfWord`
    public TrieNode(boolean isEndOfWord) {
        this.children = new HashMap<>();
        this.isEndOfWord = isEndOfWord;
        this.arquivos = new ArrayList<>();
    }
	
}