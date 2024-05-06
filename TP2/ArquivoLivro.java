import java.util.ArrayList;
import java.util.List;

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

    public int createLivro(Livro novo) {
        int resp = -1;
        try {
            // Código original
            resp = this.create(novo);

            // Uso da lista invertida
            listaInvertida.create(novo.getTitulo(), novo.getID());
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public boolean updateLivro(Livro novoLivro) {

        boolean resp = false;

        try {

            // Código original
            Livro tmp = this.read(novoLivro.getID());
            resp = this.update(novoLivro);

            // Uso da lista invertida
            listaInvertida.delete(tmp.getTitulo(), tmp.getID());
            listaInvertida.create(novoLivro.getTitulo(), novoLivro.getID());
        } catch (Exception e) {
            System.out.println(e);
        }

        return resp;
    }

    public boolean deleteLivro(int id) {
        boolean resp = false;
        try {
            // Código original
            Livro tmp = this.read(id);
            resp = this.delete(id);

            // Uso da lista invertida
            listaInvertida.delete(tmp.getTitulo(), tmp.getID());
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public Livro[] pesquisa(String alvo) {
        String[] partido = alvo.split(" ");
        int[] tmparray;
        Livro[] resp = null;
        Livro tmp;

        List<Livro> listinha = new ArrayList<>();
        try {
            //For para buscar todas as palavras da string de busca
            for (String buscar : partido) {
                tmparray = listaInvertida.read(buscar);
                //Se o item ainda não está presente na Lista,então ele é adicionado
                for(int item: tmparray){

                    tmp = this.read(item);
                    if(!listinha.contains(tmp)){
                        listinha.add(tmp);
                    }

                }
            }
            //Então a lista é convertida para um Array e retornada
            resp = (Livro[]) listinha.toArray();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

}
