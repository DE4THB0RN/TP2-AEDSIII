import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aed3.ListaInvertida;

public class ArquivoLivro extends Arquivo<Livro> {

    private ListaInvertida listaInvertida;
    private List<String> stopwords;

    ArquivoLivro(String na) throws Exception {
        super(na, Livro.class.getConstructor());
        try {
            listaInvertida = new ListaInvertida(4, "SakasamanoLista", "Blocos");
            String stwrds = "de a o que e do da em um para é com não uma os no se na por mais as dos como mas foi ao ele das tem à seu sua ou ser quando muito há nos já está eu também só pelo pela até isso ela entre era depois sem mesmo aos ter seus quem nas me esse eles estão você tinha foram essa num nem suas meu às minha têm numa pelos elas havia seja qual será nós tenho lhe deles essas esses pelas este fosse dele tu te vocês vos lhes meus minhas teu tua teus tuas nosso nossa nossos nossas dela delas esta estes estas aquele aquela aqueles aquelas isto aquilo estou está estamos estão estive esteve estivemos estiveram estava estávamos estavam estivera estivéramos esteja estejamos estejam estivesse estivéssemos estivessem estiver estivermos estiverem hei há havemos hão houve houvemos houveram houvera houvéramos haja hajamos hajam houvesse houvéssemos houvessem houver houvermos houverem houverei houverá houveremos houverão houveria houveríamos houveriam sou somos são era éramos eram fui foi fomos foram fora fôramos seja sejamos sejam fosse fôssemos fossem for formos forem serei será seremos serão seria seríamos seriam tenho tem temos tém tinha tínhamos tinham tive teve tivemos tiveram tivera tivéramos tenha tenhamos tenham tivesse tivéssemos tivessem tiver tivermos tiverem terei terá teremos terão teria teríamos teriam";
            String normalizada = Normalizer.normalize(stwrds, Normalizer.Form.NFD);
            Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            normalizada = padrao.matcher(normalizada).replaceAll("");
            String[] partes = normalizada.split(" ");
            stopwords = new ArrayList<>();

            for (String s : partes) {
                stopwords.add(s);
            }

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
            String normalizada = Normalizer.normalize(novo.getTitulo(), Normalizer.Form.NFD);
            Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            normalizada = padrao.matcher(normalizada).replaceAll("");
            String[] partes = normalizada.split(" ");

            for (String s : partes) {
                if (!stopwords.contains(s))
                    listaInvertida.create(s, novo.getID());
            }

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
            String normalizada = Normalizer.normalize(tmp.getTitulo(), Normalizer.Form.NFD);
            Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            normalizada = padrao.matcher(normalizada).replaceAll("");
            String[] partes = normalizada.split(" ");
            for (String s : partes) {
                listaInvertida.delete(s, tmp.getID());
            }

            normalizada = Normalizer.normalize(novoLivro.getTitulo(), Normalizer.Form.NFD);
            padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            normalizada = padrao.matcher(normalizada).replaceAll("");
            partes = normalizada.split(" ");

            for (String s : partes) {
                if (!stopwords.contains(s)) {
                    listaInvertida.create(s, novoLivro.getID());
                }
            }
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
            String normalizada = Normalizer.normalize(tmp.getTitulo(), Normalizer.Form.NFD);
            Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            normalizada = padrao.matcher(normalizada).replaceAll("");
            String[] partes = normalizada.split(" ");
            for (String s : partes) {
                listaInvertida.delete(s, tmp.getID());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public Livro[] pesquisa(String alvo) {

        String normalizada = Normalizer.normalize(alvo, Normalizer.Form.NFD);
        Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        normalizada = padrao.matcher(normalizada).replaceAll("");
        String[] partes = normalizada.split(" ");

        int[] tmparray;

        Livro[] resp = null;
        Livro tmp;

        List<Livro> listinha = new ArrayList<>();
        try {
            int[] inserir = listaInvertida.read(partes[0]);
            // For para buscar todas as palavras da string de busca
            for (int i = 1; i < partes.length; i++) {
                if (!stopwords.contains(partes[i])) {
                    tmparray = listaInvertida.read(partes[i]);
                    inserir = intersection(tmparray, inserir);
                }

            }
            // Se o item ainda não está presente na Lista,então ele é adicionado
            for (int item : inserir) {

                tmp = this.read(item);
                if (!listinha.contains(tmp)) {
                    listinha.add(tmp);
                }

            }
            resp = new Livro[listinha.size()];
            // Então a lista é convertida para um Array e retornada
            for (int i = 0; i < listinha.size(); i++) {
                resp[i] = listinha.get(i);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    private int[] intersection(int[] a, int[] b) {
        List<Integer> listinha = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j] && !listinha.contains(a[i])) {
                    listinha.add(a[i]);
                }
            }
        }

        int[] resp = new int[listinha.size()];

        for (int i = 0; i < listinha.size(); i++) {
            resp[i] = listinha.get(i);
        }

        return resp;
    }

}
