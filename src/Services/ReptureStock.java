package Services;

import Beans.Book;
import api.JavaSendEmail;

public class ReptureStock {
    public static void VerificationStock (Book b ){
        CrudBook cb = new CrudBook();
        System.out.println(cb.RecupererQuantitéLivre(b));
        if (cb.RecupererQuantitéLivre(b)<=0){

            JavaSendEmail.SendMail("samar.neji@esprit.tn",b);
        }else
            System.out.println("le stoc est superieur a 0");

    }
}
