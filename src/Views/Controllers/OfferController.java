package Views.Controllers;

import Beans.Offer;
import Utility.Global;
import Services.OfferService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class OfferController extends MenuBarController{

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField authorNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priceField;

    @FXML
    private Button imageUploadButton;

    @FXML
    private Button sendOfferButton;

    private Image bookCoverImage;

    private Socket clientSide;

    private OfferService offerCRUD = new OfferService();

    public void handleImageUploadButtonClicked (MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Global.getPrimaryStage());
        if (file != null) {
            System.out.println(file.getAbsolutePath());
            bookCoverImage = ImageIO.read(file);

            System.out.println(bookCoverImage.getHeight(null));
            System.out.println(bookCoverImage.getWidth(null));

        }
    }

    public void handleSendOfferButtonClicked (MouseEvent mouseEvent) {
        String imagePath = "C:/wamp64/www/BookStore/Offers/" + String.valueOf(offerCRUD.countOffers() + 1) + ".jpg";
        offerCRUD.add(new Offer(Global.getCurrentUser(), bookTitleField.getText(), descriptionField.getText(), authorNameField.getText(), Float.valueOf(priceField.getText()), imagePath));
        saveImage();
    }

    private void saveImage ()  {
        try {
            clientSide = new Socket("localhost", 1234);
            System.out.println("Connected to server");
            OutputStream outputStream = clientSide.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            Image image = bookCoverImage;
            System.out.println(image.getHeight(null));
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(bufferedImage, "jpg", bufferedOutputStream);
            bufferedOutputStream.close();
            clientSide.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


}

