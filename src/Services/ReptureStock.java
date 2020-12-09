package Services;

import Beans.Book;
import api.JavaSendEmail;
import api.MailingService;

public class ReptureStock {
    static MailingService servicem =  new MailingService();
    public static void verificationStock(Book b) {
        CrudBook cb = new CrudBook();
        Book book  = cb.RecupererLivre(b);
        if (book.getQuantity() <= 0) {
            servicem.sendReptureStockEmail(book);
        } else
            System.out.println("le stoc est superieur a 0");
    }
}
