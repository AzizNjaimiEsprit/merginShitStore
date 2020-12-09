package Views.Controllers;

import Services.OfferService;
import com.sun.prism.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {

    public static void main(String[] args) throws IOException{
        OfferService offerCRUD = new OfferService();
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket serverSide = serverSocket.accept();
        System.out.println("Server is listening...");
        InputStream inputStream = serverSide.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedImage bufferedReceivedImage = ImageIO.read(bufferedInputStream);
        bufferedInputStream.close();
        System.out.println("Image is received");
        ImageIcon receivedImage = new ImageIcon(bufferedReceivedImage);
        BufferedImage finalImage = (BufferedImage) receivedImage.getImage();
        String imagePath = "C:/Users/Mahdi/IdeaProjects/BookStoreGUI/src/Views/Resources/images/Offers/" + String.valueOf(offerCRUD.countOffers()) + ".jpg";
        File destination = new File(imagePath);
        ImageIO.write(finalImage, "jpg", destination);
//        OutputStream outputStream = serverSide.getOutputStream();
//        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//        dataOutputStream.writeUTF("Image is saved");
//        dataOutputStream.flush();
//        dataOutputStream.close();
        serverSide.close();
        serverSocket.close();


    }
}
