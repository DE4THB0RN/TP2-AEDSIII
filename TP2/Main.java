import java.util.Scanner;
import aed3.ListaInvertida;

public class Main {

  // Método principal apenas para testes
  public static void main(String[] args) {

    String stopwords = "a, ainda, alem, ambas, ambos, antes, ao, aonde, aos, apos, aquele, aqueles, as, assim, com, como, contra, contudo, cuja, cujas, cujo, cujos, da, das, de, dela, dele, deles, demais, depois, desde, desta, deste, dispoe, dispoem, diversa, diversas, diversos, do, dos, durante, e, ela, elas, ele, eles, em, entao, entre, essa, essas, esse, esses, esta, estas, este, estes, ha, isso, isto, logo, mais, mas, mediante, menos, mesma, mesmas, mesmo, mesmos, na, nas, nao, nas, nem, nesse, neste, nos, o, os, ou, outra, outras, outro, outros, pelas, pelas, pelo, pelos, perante, pois, por, porque, portanto, proprio, proprios, quais, qual, qualquer, quando, quanto, que, quem, quer, se, seja, sem, sendo, seu, seus, sob, sobre, sua, suas, tal, tambem, teu, teus, toda, todas, todo, todos, tua, tuas, tudo, um, uma, umas, uns";
    ListaInvertida inverted_list;
    try{
      inverted_list = new ListaInvertida(8, "SakasamanoLista", "Blocos");
    
    Scanner console = new Scanner(System.in);
    String[] book_title = console.nextLine().split(" "); //take the full title of the book and make a list of the words
    int book_ID = Integer.parseInt(console.nextLine());

    for (String word : book_title) {                       //for each word of the title, checks if it's a stopword
      if (stopwords.contains(word)) {
        System.out.println("Stopword " + word + " is present in the list."); //it's a stopword = ignore
        
      } else {
        System.out.println("Stopword " + word + " is not present in the list."); //it's not a stopword
        
        inverted_list.create(word, book_ID);    //word not in it, create a new element
        
        //inverted_list.create(CHAVE, DADO) // how do I define chave and dado ?
      }
    }
    
    console.close();
  }
  catch(Exception e){
    System.out.println(e);
  }
  }
}