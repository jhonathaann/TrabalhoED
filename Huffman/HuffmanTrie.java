package Huffman;
import java.util.*;


public class HuffmanTrie {
    NoArvore raiz;
    Map<Character, String> huffmanCodeMap;

    public HuffmanTrie(String texto){
        Map<Character, Integer> frequencias = calculaFrequencia(texto);
        huffmanCodeMap = new HashMap<>();
        buildTrie(frequencias);
    }

    // calcula a frequencia de cada simbolo no texto de entrada
    private Map<Character, Integer> calculaFrequencia(String texto){
        Map<Character, Integer> frequencias = new HashMap<>();
        char caracterAtual;
        int frequenciaAtual;

        // percorrendo todos os simbolos do texto
        for(int i = 0; i < texto.length(); i++){
            // pegando o caracter que esta na posicao atual
            caracterAtual = texto.charAt(i);

            // pegando a frequencia desse caracter atual na tabela de frequencias (0 eh padrao para caracteres ainda nao inseridos)
            frequenciaAtual = frequencias.getOrDefault(caracterAtual, 0);

            // adiciona +1 na frequencia e salva esse novo valor na tabela
            frequencias.put(caracterAtual, frequenciaAtual+1);
        }

        return frequencias;
    }

    private void buildTrie(Map<Character, Integer> frequencias){
        // fila de prioridade que ira armazenar os nós que precisam ser adicionados a arvore
        // a prioridade é inversamente proporcional a frequencia do simbolo
        // ou seja: simbolos menos frequentes devem ser adicionados primeiro
        PriorityQueue<NoArvore> prioridade = new PriorityQueue<NoArvore>();

        // percorrendo a tabela de frequencias e criando um nó folha para cada símbolo
        for(Map.Entry<Character, Integer> entry: frequencias.entrySet()){
            NoArvore novoNo = new NoArvore(entry.getKey(), entry.getValue());
            prioridade.add(novoNo);  // adiciona esse novo nó que foi criado na fila de prioridade
            
        }

        // enquanto estiver no para ser inserido na arvore, aguardando na fila
        while(prioridade.size() > 1){
            // cria um novo nó com as referencias para os filhos
            NoArvore esq = prioridade.poll();
            NoArvore dir = prioridade.poll();

            // criando agora um nó interno da arvore
            NoArvore novoNo = new NoArvore(esq.frequencia + dir.frequencia, esq, dir);

            // adicionando o novo no na fila
            prioridade.add(novoNo);

        }

        raiz = prioridade.poll();  // arvore esta criada
        generateHuffmanCodes(raiz, "");
    }

    private void generateHuffmanCodes(NoArvore raiz, String codigo){

        if(raiz == null){
            return;
        }

        // se o nó atual for uma folha, adicionamos ele na tabela de simbolos do Huffman
        // com a codificacao correspondente
        if(raiz.dir == null && raiz.esq == null){
            huffmanCodeMap.put(raiz.caracter, codigo);
        }

        // senao, descemos para os filhos a direita e para os filhos a esquerda
        generateHuffmanCodes(raiz.esq, codigo + "0");
        generateHuffmanCodes(raiz.dir, codigo + "1");
    }

    // comprime uma string
    public String comprime(String texto){
        String textoComprimido = "";

        // percorre todos os simbolos da string a ser comprimida
        for(int i = 0; i < texto.length(); i++){
            // adiciona na string comprimida a representacao Huffman do caracter
            textoComprimido += huffmanCodeMap.get(texto.charAt(i));
        }

        return textoComprimido;
    }

    // recebe uma string binaria, com a representacao comprimida
    // retorna a string original
    public String descomprime(String comprimida){
        String descomprimida = "";  // palavra descomprimida
        NoArvore noAtual = raiz;
        char bitAtual;

        // começa a percorrer pela raiz, navegando pelas posicoes indicadas
        // pelo simbolo binario atual. se for 0, vai para a esquerda
        // se for 1, vai para a direita
        for(int i = 0; i < comprimida.length(); i++){
            bitAtual = comprimida.charAt(i);
            if(bitAtual == '0'){
                noAtual = noAtual.esq;
            }else{
                noAtual = noAtual.dir;
            }

            // se ele alcancou uma folha, adiciona o simbolo correspondente
            // na mensagem descomprimida e continuar a navegar - volta a 
            // raiz e desce pelo simbolos restantes
            if(noAtual.dir == null && noAtual.esq == null){
                descomprimida += noAtual.caracter;
                noAtual = raiz;
            }
        }
        return descomprimida;
    }

    public String getHuffmanTable() {
		String table = "";
		for(Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
			table += "Caractere " + entry.getKey() + " -- " + entry.getValue() + "\n";
		}
		return table;
	}
}
