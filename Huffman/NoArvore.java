package Huffman;

public class NoArvore implements Comparable<NoArvore> {
    char caracter; // caracter armazenado no no
    int frequencia;  // frequencia do caracter
    NoArvore esq, dir;   // referencia para os filhos a esquerda e a direita da arvore

    // construtor para um no interno da arvore
    public NoArvore(int frequencia, NoArvore esq, NoArvore dir){
        this.caracter = '\0';  // no interno nao armazena caracter algum
        this.frequencia = frequencia;
        this.esq = esq;
        this.dir = dir;
    }

    // construtor para um no folha
    public NoArvore(char caracter, int frequencia){
        this.caracter = caracter;
        this.frequencia = frequencia;
        this.esq = null;
        this.dir = null;
    }

    @Override
    public int compareTo(NoArvore outro){
        return this.frequencia - outro.frequencia;
    }
}
