package Huffman;

public class HuffmanTrieTest {
	public static void main (String [] a) {
		String input = "esse é um arquivo teste terno rei é muito bom facom o sasori é o melhor personagem de naruto facom facom ufms ultimo arquivo .txt de teste facom";
		HuffmanTrie huffman = new HuffmanTrie(input);
		System.out.println(huffman.getHuffmanTable());
		String compressed = huffman.comprime("ultimo arquivo .txt de teste facom"
						),
			   decompressed = huffman.descomprime(compressed);
		System.out.println("Mensagem comprimida: " + compressed);
		System.out.println("Mensagem descomprimida: " + decompressed);
		
	}
}