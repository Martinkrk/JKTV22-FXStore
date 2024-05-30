package products.newproduct;

import entities.Product;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import jktv22.fxstore.HomeController;
import jktv22.fxstore.JKTV22FXStore;
import org.imgscalr.Scalr;


public class NewProductController implements Initializable {
    private EntityManager em;
    private JKTV22FXStore app;
    private HomeController homeController;
    private File selectedFile;
    @FXML
    private TextField tf_Name;
    @FXML
    private TextField tf_Category;
    @FXML
    private TextField tf_Description;
    @FXML
    private TextField tf_Price;
    @FXML
    private TextField tf_Stock;
    @FXML
    private TextField tf_Rating;
    @FXML
    private TextField tf_Manufacturer;
    @FXML
    private Button b_SelectImage;
    @FXML
    private Button b_AddProduct;
    @FXML
    private Label l_info;
    
    @FXML
    public void uploadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        b_SelectImage.setText("File selected: " + selectedFile.getName());
        b_SelectImage.setDisable(true);
    }
    
    @FXML
    public void addProduct(){
        l_info.setText("");
        if(tf_Name.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Name field is empty!", tf_Name.getText()));}
        if(tf_Category.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Category field is empty!", tf_Category.getText()));}
        if(tf_Description.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Description field is empty!", tf_Description.getText()));}
        if(tf_Price.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Price field is empty!", tf_Price.getText()));}
        if(tf_Stock.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Stock field is empty!", tf_Stock.getText()));}
        if(tf_Rating.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Rating field is empty!", tf_Rating.getText()));}
        if(tf_Manufacturer.getText().isEmpty()){l_info.setText(String.format("%s\n" + "Manufacturer field is empty!", tf_Manufacturer.getText()));}
        if(!"".equals(l_info.getText())) return;
        Product product = new Product();
        product.setName(tf_Name.getText());
        product.setCategory(tf_Category.getText());
        product.setDescription(tf_Description.getText());
        product.setPrice(Integer.parseInt(tf_Price.getText()));
        product.setStock(Integer.parseInt(tf_Stock.getText()));
        product.setRating(Integer.parseInt(tf_Rating.getText()));
        product.setManufacturer(tf_Manufacturer.getText());
        try{
            BufferedImage biProductImage = ImageIO.read(selectedFile);
            BufferedImage biScaledBookCover = Scalr.resize(biProductImage, Scalr.Mode.FIT_TO_WIDTH,400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ImageIO.write (biScaledBookCover, "jpg", baos);
            product.setImage(baos.toByteArray());
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            l_info.setText("The product was successfully added");
        } catch (IOException ex) {
            l_info.setText("Couldn't add the product");
            Logger.getLogger(NewProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        b_SelectImage.disableProperty().set(false);
        selectedFile = null;
        tf_Name.setText("");
        tf_Category.setText("");
        tf_Description.setText("");
        tf_Price.setText("");
        tf_Stock.setText("");
        tf_Rating.setText("");
        tf_Manufacturer.setText("");
        b_SelectImage.setText("Choose product image");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public JKTV22FXStore getApp() {
        return app;
    }

    public void setApp(JKTV22FXStore app) {
        this.app = app;
    }
    public static BufferedImage convertToBufferedImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    } 
}
