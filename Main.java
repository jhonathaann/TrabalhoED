import Huffman.HuffmanTrie; // importando os codigos que realizam a compactação huffman
import Hash.*; // importando os codigos serão a base para a criaçao da tabela hash
import Trie.*; // importando os codigos da Trie
import java.util.List;
import java.util.Scanner;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        /* ============================ DECLARANDO VARIAVEIS ============================ */
        HuffmanTrie huffman;
        HashTable tabelaHash = new HashTable(31);
        Trie trie = new Trie();
        Scanner input = new Scanner(System.in);
        String documentos, descomprimida = "", opc, textoInserido;
        String[] nomeArquivos = new String[30], comprimida = new String[30], arquivoIndividual = new String[30];
        StringBuilder conteudoArquivos = new StringBuilder(),  aux = new StringBuilder(); 
        File pasta;
        File[] arquivosTxt;
        Runtime runtime;
        long huffmanInicio, huffmanTermino, leituraInicio, leituraTermino, hashInicio, hashTermino, trieInicio, trieTermino;
        long memoriaAntesHuffman, memoriaHuffmanDepois, memoriaAntesTrie, memoriaTrieDepois, memoriaHahsAntes, memoriaHahsDepois;
        int i = 0;

        // leitura dos documentos para serem comprimidos
        System.out.println("Inserir documentos: "); // o usuario informa o caminho onde se encontra os arquivos
        documentos = input.nextLine();
        //documentos = "C:/Users/jhona/OneDrive/Faculdade/TrabalhoED/conveterArquivos";
        pasta = new File(documentos);
        
        leituraInicio = System.currentTimeMillis(); // pegando o tempo de inicio de leitura
        runtime = Runtime.getRuntime(); // pegando memoria inicial da Trie
        runtime.gc(); // limpa memoria antes de medir para tentar obter uma execução melhor e com estimativa melhor
        memoriaAntesTrie = runtime.totalMemory() - runtime.freeMemory(); // calcula a memoria usada antes da indexacao

        /* ============================ LEITURA DO ARQUIVOS ============================ */
        if (pasta.exists() && pasta.isDirectory()) {
            System.out.println("Pasta encontrada! Lendo arquivos .txt");

            // lendo apenas os arquivos .txt
            arquivosTxt = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            // se o arquivo .txt atual NAO for nulo
            if (arquivosTxt != null) {
                // iterando sobre todos os arquivos .txt encontrados
                for (File arquivo : arquivosTxt) {
                    // limpando o conteudo antes de ler o novo arquivo
                    aux.setLength(0);

                    // lendo cada arquivo .txt palavra por palavra
                    try (Scanner scanner = new Scanner(arquivo, "UTF-8")) {
                        while (scanner.hasNext()) {
                            // le a proxima palavra
                            String palavra = scanner.next();
                            conteudoArquivos.append(palavra).append(" "); // adicionando a palavra lida no conteudoArquivos
                            aux.append(palavra).append(" ");
                            trie.insert(palavra, arquivo.getName()); // adicionando palavra por palavra do arquivo em questao na trie
                        }

                        arquivoIndividual[i] = aux.toString(); // salvando o conteudo de cada arquivo em uma posicao diferente
                        conteudoArquivos.append("\n"); // separador entre arquivos
                    
                        nomeArquivos[i] = arquivo.getName(); // salvando o nome do documento atual
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
        // pegando o tempo de termino da leitura
        leituraTermino = System.currentTimeMillis(); 
        runtime.gc();  
        memoriaTrieDepois = (runtime.totalMemory() - runtime.freeMemory());

        huffmanInicio = System.currentTimeMillis();
        runtime = Runtime.getRuntime(); // pegando memoria inicial
        runtime.gc(); // limpa memoria antes de medir para tentar obter uma execução melhor e com // estimativa melhor
        memoriaAntesHuffman = runtime.totalMemory() - runtime.freeMemory(); // calcula a memoria usada antes da indexacao

        huffman = new HuffmanTrie(conteudoArquivos.toString()); // criando a arvore de huffman

        runtime.gc();
        memoriaHuffmanDepois = (runtime.totalMemory() - runtime.freeMemory());
        huffmanTermino = System.currentTimeMillis();


        if (documentos != null) {
            System.out.println("Documentos inseridos com sucesso!");
        }
        
        /* ============================ REALIZANDO A INDEXAÇÃO NA HASH ============================ */

        System.out.println("Qual funçao de hashing (divisao/djb2): "); // usuario escolhe qual funcao de hash ele deseja usar
        opc = input.nextLine();


        /* ============================ HASH DIVISAO ============================ */
        if (opc.equalsIgnoreCase("divisao")) {
            hashInicio = System.currentTimeMillis(); // pegando o tempo de inicio da hash

            runtime = Runtime.getRuntime(); // pegando memoria inicial
            runtime.gc(); 
            memoriaHahsAntes = runtime.totalMemory() - runtime.freeMemory();

            for (int j = 0; j < i; j++) {
                System.out.println("Arquivo: " + nomeArquivos[j] + " - Indexado com sucesso!");
                comprimida[j] = huffman.comprime(arquivoIndividual[j]);
                tabelaHash.insercao(nomeArquivos[j], comprimida[j], "divisao");
            } 

            runtime = Runtime.getRuntime(); // pegando memoria inicial
            runtime.gc(); 
            memoriaHahsDepois = runtime.totalMemory() - runtime.freeMemory(); // calcula a memoria usada antes da indexacao

            System.out.println("Documentos indexados com sucesso!\n");

            hashTermino = System.currentTimeMillis();

            System.out.println("Buscar palavra: "); // usuario escolhe uma palavra para ser buscada na Trie
            textoInserido = input.nextLine();

            trieInicio = System.nanoTime();  // calculando o tempo gasto para buscar uma palavra na trie
           
            List<String> arquivosEncontrados = trie.search(textoInserido);  // buscando a palavra na trie

            trieTermino = System.nanoTime();

            if (arquivosEncontrados != null) {
                String entradas = tabelaHash.get(textoInserido, "divisao");

                // mostrando todos os arquivos que contem a palavra buscada
                System.out.println("A palavra \"" + textoInserido + "\" foi encontrada nos seguintes documentos: ");
                for (String arquivo : arquivosEncontrados) {
                    System.out.println(arquivo);
                }

                System.out.println();
                
                System.out.println("Qual arquivo deseja abrir: "); // usuario escolhe um dos arquivos em que a palavra se encontra
                textoInserido = input.nextLine();

                for (String arquivo : arquivosEncontrados) {

                    // se o arquivo for o buscado pelo usuario
                    if (arquivo.equals(textoInserido)) {
                        entradas = tabelaHash.get(textoInserido, "divisao");
                        if (entradas != null) {
                            descomprimida = huffman.descomprime(entradas);
                            System.out.println("\n ======================================================== \n");
                            System.out.println("Arquivo escolhido descomprimido:\n " + descomprimida);
                            System.out.println("\n");
                            break;

                        } else {
                            System.out.println("Erro: Entrada não encontrada para descompressão!");
                        }
                    }
                }
            } else {
                System.out.println("Palavra nao encontrada em nenhum arquivo!");
            }

                /* ============================ APRESENTANDO O TEMPO MEMEORIA GASTOS ============================ */
                System.out.println("Tempo gastos para ler todos os arquivos: "  + (leituraTermino-leituraInicio) + " ms");
                System.out.println("Tempo gasto para criar a arvore de Huffman com todos os arquivos lidos: " + (huffmanTermino-huffmanInicio) + " ms");
                System.out.println("Tempo gasto para buscar uma palavra na Trie: " + (trieTermino-trieInicio) + " ns");
                System.out.println("Tempo gasto para indexar todos os ducumentos na HASH: " + (hashTermino-hashInicio) + " ms\n");

                System.out.println("Memoria utilizada pela Huffman: " + Math.abs(memoriaHuffmanDepois-memoriaAntesHuffman) + " bytes");
                System.out.println("Memoria utilizada pela Trie: " + (memoriaTrieDepois-memoriaAntesTrie) + " bytes");
                System.out.println("Memoria utilizada pela Hash: " + (memoriaHahsDepois-memoriaHahsAntes) + " bytes");

        /* ============================ HASH DJB2 ============================ */

        } else if (opc.equalsIgnoreCase("djb2")) {
            
            // pegando o tempo de inicio
            hashInicio = System.currentTimeMillis();

            runtime = Runtime.getRuntime(); // pegando memoria inicial
            runtime.gc(); 
            memoriaHahsAntes = runtime.totalMemory() - runtime.freeMemory();

            // realizando a indexaçao dos arquivos com a hash DJB2
            for (int j = 0; j < i; j++) {
                System.out.println("Arquivo: " + nomeArquivos[j] + " - Indexado com sucesso!");
                comprimida[j] = huffman.comprime(arquivoIndividual[j]);
                tabelaHash.insercao(nomeArquivos[j], comprimida[j], "djb2");
            }

            runtime = Runtime.getRuntime(); // pegando memoria inicial
            runtime.gc(); 
            memoriaHahsDepois = runtime.totalMemory() - runtime.freeMemory(); // calcula a memoria usada antes da indexacao

            System.out.println("Documentos indexados com sucesso!\n");
            
            hashTermino = System.currentTimeMillis();


            System.out.println("Buscar palavra: ");
            textoInserido = input.nextLine();

            
            trieInicio = System.nanoTime(); // calculando o tempo gasto para buscar uma palavra na trie

            // buscando a palavra na trie
            List<String> arquivosEncontrados = trie.search(textoInserido);

            trieTermino = System.nanoTime();

            if (arquivosEncontrados != null) {
                String entradas = tabelaHash.get(textoInserido, "djb2");

                // mostrando na tela todos os arquivos em que a palavra se encontra
                System.out.println("Palavra encontrada nos seguintes arquivos: ");
                for (String arquivo : arquivosEncontrados) {
                    System.out.println(arquivo);
                }

                System.out.println();

                System.out.println("Qual arquivo deseja abrir: "); // usuario escolhe um dos arquivos em que a palavra se encontra
                textoInserido = input.nextLine();

                runtime = Runtime.getRuntime(); // pegando memoria inicial
                runtime.gc();
                memoriaHahsAntes = runtime.totalMemory() - runtime.freeMemory();

                for (String arquivo : arquivosEncontrados) {

                    // se o arquivo atual é o que o usuário escolheu
                    if (arquivo.equals(textoInserido)) {
                        entradas = tabelaHash.get(textoInserido, "djb2");
                        if (entradas != null) {
                            descomprimida = huffman.descomprime(entradas);
                            System.out.println("\n ======================================================== \n");
                            System.out.println("Arquivo escolhido descomprimido: \n"+ descomprimida);
                            System.out.println("\n");
                            break;
                        } else {
                            System.out.println("Erro: Entrada não encontrada para descompressão!");
                        }
                    }
                }

            } else {
                System.out.println("Palavra nao encontrada!");
            }

             /* ============================ APRESENTANDO O TEMPO MEMEORIA GASTOS ============================ */
             System.out.println("Tempo gastos para ler todos os arquivos: "  + (leituraTermino-leituraInicio) + " ms");
             System.out.println("Tempo gasto para criar a arvore de Huffman com todos os arquivos lidos: " + (huffmanTermino-huffmanInicio) + " ms");
             System.out.println("Tempo gasto para buscar uma palavra na Trie: " + (trieTermino-trieInicio) + " ns");
             System.out.println("Tempo gasto para indexar todos os ducumentos na HASH: " + (hashTermino-hashInicio) + " ms\n");

             System.out.println("Memoria utilizada pela Huffman: " + Math.abs(memoriaHuffmanDepois-memoriaAntesHuffman) + " bytes");
             System.out.println("Memoria utilizada pela Trie: " + (memoriaTrieDepois-memoriaAntesTrie) + " bytes");
             System.out.println("Memoria utilizada pela Hash: " + (memoriaHahsDepois-memoriaHahsAntes) + " bytes");

            input.close();
        }
        else if (opc.equals("expressoes")) {
            List<String> expressoesTeste = ArquivosExpressoes.carregarExpressoes("expressoes.txt");
            if (expressoesTeste == null) {
                System.out.println("Erro ao carregar expressões de teste.");
                return;
            }

            System.out.println("Expressões carregadas: " + expressoesTeste.size());
            for (String palavra : expressoesTeste) {
                long inicioBusca = System.nanoTime();
                List<String> arquivosEncontrados = trie.search(palavra);
                long fimBusca = System.nanoTime();
                long tempoBusca = fimBusca - inicioBusca;

                if (arquivosEncontrados != null && !arquivosEncontrados.isEmpty()) {
                    System.out.println("\nA palavra \"" + palavra + "\" foi encontrada nos seguintes arquivos:");
                    for (String arquivo : arquivosEncontrados) {
                        System.out.println(arquivo);
                    }
                    System.out.println("Tempo de busca: " + tempoBusca + " ns");
                } else {
                    System.out.println("\nA palavra \"" + palavra + "\" não foi encontrada. Tempo de busca: " + tempoBusca + " ns");
                }
            }

        }

    }
}