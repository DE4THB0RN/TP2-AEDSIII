
public class ArquivoLivro extends Arquivo<Livro>{

    ArquivoLivro(String na) throws Exception{
        super(na,Livro.class.getConstructor());
    }
  
}
