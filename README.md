# 📚 Sistema de Indexação e Busca de Textos  

Este repositório contém o trabalho desenvolvido na disciplina de Estruturas de Dados. O objetivo do projeto é construir um sistema eficiente de indexação e busca de palavras em textos. 
O programa foi desenvolvido em **Java** e suporta todas as etapas do processo, desde a inserção de documentos até a busca por palavras indexadas.

### Principais características:
- **Corpus:** 30 artigos do arXiv, convertidos para texto plano (.txt) e processados pelo sistema.
- **Estruturas de Dados:**  
  - **Tabela Hash** com duas funções de hash (Divisão e DJB2) para armazenamento de textos compactados.
  - **Trie (Árvore Digital)** para indexação e busca eficiente das palavras.
- **Compressão:** Algoritmo de **Huffman** utilizado para compactação dos textos.
  
O sistema permite buscar palavras e recuperar documentos nos quais elas foram encontradas de forma otimizada.
