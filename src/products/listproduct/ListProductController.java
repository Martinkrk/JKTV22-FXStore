package products.listproduct;

import products.product.ProductController;
import entities.Product;
import entities.Purchase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jktv22.fxstore.HomeController;


public class ListProductController implements Initializable {
    private HomeController homeContoller;
    @FXML private HBox hbListProductsContent;
    private Stage modalWindow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setHomeController(HomeController homeController) {
        this.homeContoller = homeController;
    }
    
    public void loadProducts() {
        List<Product> listProduct = homeContoller.getApp().getEntityManager()
                .createQuery("SELECT b FROM Product b")
                .getResultList();
        try {
            hbListProductsContent.getChildren().clear();
            for (int i = 0; i < listProduct.size(); i++) {
                FXMLLoader productLoader = new FXMLLoader();
                productLoader.setLocation(getClass().getResource("/products/product/product.fxml"));
                VBox vbProductRoot = productLoader.load();
                Product product = listProduct.get(i);
                ProductController productController = productLoader.getController();
                productController.setListProductController(this);
                productController.setProduct(product);
                
                vbProductRoot.setOnMouseEntered(event -> {
                    vbProductRoot.setCursor(Cursor.HAND);
                });
                vbProductRoot.setOnMouseExited(event -> {
                    vbProductRoot.setCursor(Cursor.DEFAULT);
                });
                vbProductRoot.setOnMouseClicked(event -> {
                    System.out.println("You chose: " + product.getName());
                    showProduct(product);
                });
                
                hbListProductsContent.getChildren().add(vbProductRoot);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, "Page not found", ex); 
        }
    }

    public HomeController getHomeContoller() {
        return homeContoller;
    }

    private void showProduct(Product product) {
        try {
            modalWindow = new Stage();
            FXMLLoader productLoader = new FXMLLoader();
            productLoader.setLocation(getClass().getResource("/products/product/product.fxml"));
            VBox vbProductRoot = productLoader.load();
            ProductController productController = productLoader.getController();
            productController.getIvImage().setImage(new Image(new ByteArrayInputStream(product.getImage())));
            productController.getIvImage().setFitWidth(400);
            productController.getIvImage().setFitHeight(300);
            productController.setProduct(product);
            vbProductRoot.setAlignment(Pos.CENTER);
            productController.getVbProductAtributes().setVisible(true);
            Button btSell=new Button("Sell a product");
            btSell.setOnAction(event->{
                sellProduct(product);
            });
            vbProductRoot.getChildren().add(btSell);
            Scene scene = new Scene(vbProductRoot,400, 600);
            modalWindow.setTitle(product.getName());
            modalWindow.initModality(Modality.WINDOW_MODAL);
            modalWindow.initOwner(getHomeContoller().getApp().getPrimaryStage());
            modalWindow.setScene(scene);
            modalWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, "Page not found", ex);
        }
    }

    private void sellProduct(Product product) {
        if(!(product.getStock()> 0)){
            getHomeContoller().getLbInfo().setText("No products in stock!");
            modalWindow.close();
            return;
        }
        
        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        if(jktv22.fxstore.JKTV22FXStore.user==null){
            getHomeContoller().getLbInfo().setText("Login into system");
            getHomeContoller().login();
            modalWindow.close();
            return;
        }
        purchase.setUser(jktv22.fxstore.JKTV22FXStore.user);
        
        product.setStock(product.getStock()-1);
        
        getHomeContoller().getApp().getEntityManager().getTransaction().begin();
        getHomeContoller().getApp().getEntityManager().merge(product);
        getHomeContoller().getApp().getEntityManager().persist(purchase);
        getHomeContoller().getApp().getEntityManager().getTransaction().commit();
        getHomeContoller().getLbInfo()
                .setText("Product " + product.getName()
                        + " was sold: ");
        modalWindow.close();
    }   
}
