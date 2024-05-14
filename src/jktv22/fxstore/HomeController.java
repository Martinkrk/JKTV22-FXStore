package jktv22.fxstore;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import products.listproduct.ListProductController;
import products.newproduct.NewProductController;
import users.login.LoginController;
import users.newuser.NewUserController;


public class HomeController implements Initializable {
    private JKTV22FXStore app;
    private EntityManager em;
    
    private final String windowTitle = "JKTV22-FX Store";
    
    @FXML private MenuItem menuItem;
    @FXML private MenuItem mi_getProducts;
    @FXML private MenuItem mi_newProduct;

    @FXML private VBox vb_content;
    @FXML private Label l_info;
    
    @FXML
    private void getUsers() {
        
    }
    
    @FXML public void login() {
        this.app.getPrimaryStage().setTitle(String.format("%s Login", windowTitle));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/login/login.fxml"));
        
        try {
            VBox vbLoginRoot = loader.load();
            vbLoginRoot.setPrefHeight(JKTV22FXStore.HEIGHT);
            vbLoginRoot.setPrefWidth(JKTV22FXStore.WIDTH);
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getApp().getEntityManager());
            loginController.setInfo("");
            vb_content.getChildren().clear();
            vb_content.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить vbLoginRoot", ex);
        }
    }
    
    @FXML
    private void addNewUser(){
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox hb_NewUser = loader.load();
            NewUserController newUserController = loader.getController();
            newUserController.setHomeController(this);
            app.getPrimaryStage().setTitle(String.format("%s Adding a new user", windowTitle));
            vb_content.getChildren().clear();
            vb_content.getChildren().add(hb_NewUser);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML void getProducts() {
        setMenuItem(mi_getProducts);
        l_info.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/listproduct/listproduct.fxml"));
            VBox child = loader.load();
            ListProductController listProductController = loader.getController();
            listProductController.setHomeController(this);
            listProductController.loadProducts();
            app.getPrimaryStage().setTitle(String.format("%s Product list", windowTitle));
            vb_content.getChildren().clear();
            vb_content.getChildren().add(child);
        } catch (IOException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, "Couldn't load the file", ex);
        }
    }
    
    @FXML void newProduct() {
        setMenuItem(mi_newProduct);
        l_info.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/newproduct/newproduct.fxml"));
            VBox child = loader.load();
            app.getPrimaryStage().setTitle(String.format("%s Add Product", windowTitle));
            NewProductController newProductController = loader.getController();
            newProductController.setEntityManager(getApp().getEntityManager());
            vb_content.getChildren().clear();
            vb_content.getChildren().add(child);
        } catch (IOException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, "Couldn't load the file", ex);
        }
    }
    
    @FXML void newPurchase() {
        
    }
    
    void setApp(JKTV22FXStore app) {
        this.app = app;
    }

    public JKTV22FXStore getApp() {
        return app;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Label getLbInfo() {
        return l_info;
    }
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vb_content.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        if(jktv22.fxstore.JKTV22FXStore.currentUser == null){
//            lbInfoUser.setText("Авторизуйтесь!");
//        }else{
//            lbInfoUser.setText("Управление программой от имени пользователя: "+jptv22fxlibrary.JPTV22FXLibrary.currentUser.getLogin());
//        }
    }    
}
