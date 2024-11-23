import Huffman.HuffmanTrie; // importando os codigos que realizam a compactação huffman
import Hash.*; // importando os codigos serão a base para a criaçao da tabela hash
import Trie.*; // importando os codigos da Trie
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        HuffmanTrie huffman;

        HashTable tabelaHash = new HashTable(7);

        Trie trie = new Trie();

        Scanner input = new Scanner(System.in);
        String documentos, descomprimida = "", opc, textoInserido;
        String[] nomeArquivos = new String[7];
        String[] comprimida = new String[7];
        String[] arquivoIndividual = new String[30];

        StringBuilder conteudoArquivos = new StringBuilder();  // variavel que ira conter o conteudo de todos os arquivos lidos
        StringBuilder aux = new StringBuilder();   // variavel auxiliar para ajudar na individualizacao do conteudo do arquivos 

        File pasta;
        File[] arquivosTxt; // lista que ira conter todos os caminhos de todos os arquivos .txt da pasta

        // leitura dos documentos para serem comprimidos
        // o usuario informa o caminho onde se encontra os arquivos
        System.out.println("Inserir documentos: ");
        // documentos = input.nextLine();
        documentos = "C:/Users/jhona/OneDrive/Faculdade/TrabalhoED/conveterArquivos";
        pasta = new File(documentos);

        int i = 0;

        if (pasta.exists() && pasta.isDirectory()) {

            System.out.println("Pasta encontrada! Lendo arquivos .txt");

            // lendo apenas os arquivos .txt
            arquivosTxt = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            // se o arquivo .txt atual NAO for nulo
            if (arquivosTxt != null) {

                // iterando sobre todos os arquivos .txt encontrados
                for (File arquivo : arquivosTxt) {

                    // limpando o conteudo antes de ler o novo arquivo
                    // conteudoArquivos.setLength(0);
                    aux.setLength(0);

                    // lendo cada arquivo .txt palavra por palavra
                    try (Scanner scanner = new Scanner(arquivo, "UTF-8")) {
                        while (scanner.hasNext()) {
                            // le a proxima palavra
                            String palavra = scanner.next();
                            conteudoArquivos.append(palavra).append(" "); // adicionando a palavra lida no conteudoArquivos
                            aux.append(palavra).append(" ");
                            
                            // adicionando palavra por palavra do arquivo em questao na trie
                            trie.insert(palavra, arquivo.getName());
                        }

                        // salvando o conteudo de cada arquivo em uma posicao diferente
                        arquivoIndividual[i] = aux.toString();
                        conteudoArquivos.append("\n"); // separador entre arquivos
                    
                        // salvando o nome do documento atual
                        nomeArquivos[i] = arquivo.getName();
                        i++;

                        System.out.println("Arquivo " + arquivo.getName() + " lido com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                        e.printStackTrace();
                    }
                }

            } else {
                System.out.println("Nao foram encontrados arquivos .txt na pasta.");
            }
        } else {
            System.out.println("Erro ao inserir o caminho para leitura dos arquivos.");
        }

        // criando a arvore de huffman
        huffman = new HuffmanTrie(conteudoArquivos.toString());
        //System.out.println("ALFABETO EM QUESTAO:\n");
        //System.out.println(huffman.getHuffmanTable());

        if (documentos != null) {
            System.out.println("Documentos inseridos com sucesso!");
        }

        // usuario escolhe qual funcao de hash ele deseja usar
        System.out.println("Qual funçao de hashing (divisao/djb2): ");
        opc = input.nextLine();

        if (opc.equals("divisao")) {

            // realizando a indexacao dos aquivos com a hash divisao
            for (int j = 0; j < i; j++) {
                System.out.println("\nVARIAVEL DO ARQUIVO INDIVIDUAL: " + arquivoIndividual[j]);
                comprimida[j] = huffman.comprime(arquivoIndividual[j]);
                tabelaHash.insercao(nomeArquivos[j], comprimida[j], "divisao");
            } 

            System.out.println("Documentos indexados com sucesso!\n");

            System.out.println("TABELA HASH GERADA A PARTIR DA FUNCAO DE DIVISAO:\n");
            tabelaHash.print();

            System.out.println("Buscar palavra: ");
            textoInserido = input.nextLine();

            // buscando a palavra na trie
            List<String> arquivosEncontrados = trie.search(textoInserido);

            if (arquivosEncontrados != null) {
                String entradas = tabelaHash.get(textoInserido, "divisao");

                // mostrando todos os arquivos que contem a palavra buscada
                System.out.println("A palavra \"" + textoInserido + "\" foi encontrada nos seguintes documentos: ");
                for (String arquivo : arquivosEncontrados) {
                    System.out.println(arquivo);
                }

                System.out.println();
                
                // usuario escolhe um dos arquivos em que a palavra se encontra
                System.out.println("Qual arquivo deseja abrir: ");
                textoInserido = input.nextLine();

                for (String arquivo : arquivosEncontrados) {

                    // se o arquivo for o buscado pelo usuario
                    if (arquivo.equals(textoInserido)) {
                        entradas = tabelaHash.get(textoInserido, "divisao");
                        //System.out.println("COMPRIMIDO " + "   " + entradas);

                        if (entradas != null) {
                            descomprimida = huffman.descomprime(entradas);
                            System.out.println("Descomprido: " + descomprimida);
                            break;

                        } else {
                            System.out.println("Erro: Entrada não encontrada para descompressão!");
                        }
                    }
                    
                }

            } else {
                System.out.println("Palavra nao encontrada em nenhum arquivo!");
            }



        } else if (opc.equals("djb2")) {

            // realizando a indexaçao dos arquivos com a hash DJB2
            for (int j = 0; j < i; j++) {

                comprimida[j] = huffman.comprime(arquivoIndividual[j]);
                tabelaHash.insercao(nomeArquivos[j], comprimida[j], "djb2");
            }

            System.out.println("Documentos indexados com sucesso!\n");
            
            System.out.println("TABELA HASH GERADA A PARTIR DA FUNCAO DE DJB2:\n");
            tabelaHash.print();

            System.out.println("Buscar palavra: ");
            textoInserido = input.nextLine();

            // buscando a palavra na trie
            List<String> arquivosEncontrados = trie.search(textoInserido);

            if (arquivosEncontrados != null) {
                String entradas = tabelaHash.get(textoInserido, "djb2");

                // mostrando na tela todos os arquivos em que a palavra se encontra
                System.out.println("Palavra encontrada nos seguintes arquivos: ");
                for (String arquivo : arquivosEncontrados) {
                    System.out.println(arquivo);
                }

                System.out.println();

                // usuario escolhe um dos arquivos em que a palavra se encontra
                System.out.println("Qual arquivo deseja abrir: ");
                textoInserido = input.nextLine();

                for (String arquivo : arquivosEncontrados) {

                    // se o arquivo atual é o que o usuário escolheu
                    if (arquivo.equals(textoInserido)) {
                        entradas = tabelaHash.get(textoInserido, "djb2");
                        // System.out.println("COMPRIMIDO " + "  " + entradas);

                        if (entradas != null) {
                            descomprimida = huffman.descomprime(entradas);
                            System.out.println("Descomprido: " + descomprimida);
                            break;
                        } else {
                            System.out.println("Erro: Entrada não encontrada para descompressão!");
                        }
                    }
                    
                }

            } else {
                System.out.println("Palavra nao encontrada!");
            }

            input.close();
        }

    }
}