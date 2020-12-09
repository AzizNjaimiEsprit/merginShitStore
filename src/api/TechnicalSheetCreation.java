package api;

import Beans.Book;
import Services.CrudBook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class TechnicalSheetCreation {
    public static PdfPCell createImageCell(String path) throws  Exception {
        Image img = Image.getInstance(path);
        img.setWidthPercentage(1);
        PdfPCell cell = new PdfPCell(img, true);
        return cell;
    }
    CrudBook cb = new CrudBook();
    public void CreatTS (Book b)
    {
        String file_name = "C:/wamp64/www/BookStore/BookTechnicalSheet/"+b.getId()+".pdf";


        try {
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(file_name));
            document.open();
            //******Colors setting ************
            Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
            Font blue = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);

            //****************************
            Paragraph para = new Paragraph("Technical Sheet of " +b.getTitle()+"\n ", blue);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(new Paragraph("  "));

            PdfPTable table = new PdfPTable(2);
            PdfPCell c1 = new PdfPCell(new Phrase("Titre",blue));
            table.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase(b.getTitle()));
            table.addCell(c2);

            table.setHeaderRows(1);

            /*table.addCell(new Phrase("Couverture",blue));
            table.addCell(createImageCell("AddAuthorController:\\Users\\Samar Neji\\Desktop\\BookStore_Application\\Image des Livres\\l.jpg"));*/

            table.addCell(new Phrase("Author(s)",blue));
            table.addCell(b.getAuthors().toString());

            table.addCell(new Phrase("Published House",blue));
            table.addCell(b.getPubHouse());

            table.addCell(new Phrase("Release Date",blue));
            table.addCell(b.getReleaseDate().toString());


            table.addCell(new Phrase("Category",blue));
            table.addCell(b.getCategory().getName());


            table.addCell(new Phrase("Number of pages",blue));
            table.addCell(String.valueOf(b.getNbPage()));

            table.addCell(new Phrase("Summary",blue));
            table.addCell(b.getSummary());

            document.add(table);


            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));

            Image image = Image.getInstance("C:/wamp64/www/BookStore/BooksImage/"+b.getId()+".jpg");
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
            document.close();
            System.out.println("finished");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
