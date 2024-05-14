package products.product;

import products.listproduct.ListProductController;
import entities.Product;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ProductController implements Initializable {
    private ListProductController listProductController;
    @FXML private Label lbName;
    @FXML private Label lbCategory;
    @FXML private Label lbDescription;
    @FXML private Label lbManufacturer;
    @FXML private Label lbPrice;
    @FXML private Label lbStock;
    @FXML private Label lbRating;
    @FXML private ImageView ivImage;
    @FXML private VBox vbProductAtributes;

    public void setProduct(Product product) {
        ivImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        lbName.setText(product.getName());
        lbCategory.setText(product.getCategory());
        lbDescription.setText(product.getDescription());
        lbManufacturer.setText(product.getManufacturer());
        lbPrice.setText(String.valueOf(product.getPrice()));
        lbStock.setText(String.valueOf(product.getStock()));
        lbRating.setText(String.valueOf(product.getRating()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    public ListProductController getListProductController() {
        return listProductController;
    }

    public void setListProductController(ListProductController listProductController) {
        this.listProductController = listProductController;
    }

    public Label getLbName() {
        return lbName;
    }

    public void setLbName(Label lbName) {
        this.lbName = lbName;
    }

    public Label getLbCategory() {
        return lbCategory;
    }

    public void setLbCategory(Label lbCategory) {
        this.lbCategory = lbCategory;
    }

    public Label getLbDescription() {
        return lbDescription;
    }

    public void setLbDescription(Label lbDescription) {
        this.lbDescription = lbDescription;
    }

    public Label getLbManufacturer() {
        return lbManufacturer;
    }

    public void setLbManufacturer(Label lbManufacturer) {
        this.lbManufacturer = lbManufacturer;
    }

    public Label getLbPrice() {
        return lbPrice;
    }

    public void setLbPrice(Label lbPrice) {
        this.lbPrice = lbPrice;
    }

    public Label getLbStock() {
        return lbStock;
    }

    public void setLbStock(Label lbStock) {
        this.lbStock = lbStock;
    }

    public Label getLbRating() {
        return lbRating;
    }

    public void setLbRating(Label lbRating) {
        this.lbRating = lbRating;
    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public void setIvImage(ImageView ivImage) {
        this.ivImage = ivImage;
    }

    public VBox getVbProductAtributes() {
        return vbProductAtributes;
    }

    public void setVbProductAtributes(VBox vbProductAtributes) {
        this.vbProductAtributes = vbProductAtributes;
    }
}
