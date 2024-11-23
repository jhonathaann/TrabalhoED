package Hash;
import java.io.File;
import java.util.LinkedList;

public class TesteTabelaHash {
    public static void main(String[] args) {
        HashTable testHash = new HashTable(10);

        testHash.insercao("arquivo1.txt", new File("/caminho/para/arquivo1.txt"), "divisao");
        testHash.insercao("arquivo2.txt", new File("/caminho/para/arquivo2.txt"), "divisao");
        testHash.insercao("arquivo1.txt", new File("/outro/caminho/para/arquivo1.txt"), "divisao");
        testHash.insercao("arquivo3.txt", new File("/caminho/para/arquivo3.txt"), "divisao");

        testHash.print();

        LinkedList<File> valoresRetornados = testHash.get("arquivo1.txt", "divisao");
        System.out.println("Caminhos associados a 'arquivo1.txt':");
        for (File valor : valoresRetornados) {
            System.out.println(valor.getPath());
        }
    }
}
