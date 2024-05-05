import aed3.ListaInvertida;

public class ArquivoLivro extends Arquivo<Livro> {

    private ListaInvertida listaInvertida;

    ArquivoLivro(String na) throws Exception {
        super(na, Livro.class.getConstructor());
        try {
            listaInvertida = new ListaInvertida(4, "SakasamanoLista", "Blocos");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int create(Livro novo) {
        //Código original
        int resp = this.create(novo);

        //Uso da lista invertida
        try {
            listaInvertida.create(novo.getTitulo(), novo.getID());
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public boolean update(Livro novoLivro){
        //Código original
        boolean resp = this.update(novoLivro);

        //Uso da lista invertida
        try {
            listaInvertida.delete(novoLivro.getID);
            listaInvertida.create(novo.getTitulo(), novo.getID());
        } catch (Exception e) {
            System.out.println(e);
        }

        return resp;
    }

    public boolean delete(int id){
        //Código original
        boolean resp = this.delete(id);
        return resp;

        //Uso da lista invertida
        try {
            listaInvertida.delete(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String pesquisa(String alvo){
        String[] lista_alvo = alvo.split(" ");
        String resp = listaInvertida.read(lista_alvo[0]);

        for (String palavra : lista_alvo){
            String temp = "";
            for (String id : listaInvertida.read(palavra)) {
                if (resp.contains(id)) {
                    temp.add(id + "");
                }
            }
            res = temp;
        }
        return resp;
    }

}
