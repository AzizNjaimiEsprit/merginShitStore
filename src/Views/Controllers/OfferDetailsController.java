package Views.Controllers;

import Beans.Offer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OfferDetailsController extends MenuBarController {

    @FXML
    private ImageView coverImageView;

    @FXML
    private TextFlow descriptionView;

    @FXML
    private TextFlow TitleView;

    @FXML
    private TextFlow submitDateView;

    public void initData (Offer offer) throws IOException {
        Text title = new Text(offer.getTitle());
        TitleView.getChildren().add(title);
        Text description = new Text(offer.getDescription());
        descriptionView.getChildren().add(description);
        Text submitDate = new Text(String.valueOf(offer.getSubmitDate()));
        submitDateView.getChildren().add(submitDate);
        File file = new File(offer.getImage());
        String path = file.toURI().toString();
        Image image = new Image(path);
        coverImageView.setImage(image);
    }

}
