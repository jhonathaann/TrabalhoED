package Hash;


public class HashEntry {
    String chave;
    String valor;

    public HashEntry(String chave, String valor){
        this.chave = chave;
        this.valor = valor;
    }

    public String toString(){
        return "(" + chave + ", " + valor + ")";
    }
}
