package jktv22.fxstore;

import entities.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.PassEncrypt;


public class JKTV22FXStore extends Application {
    
    private Stage primaryStage;
    public static enum ROLES {ADMINISTRATOR, MANAGER, CUSTOMER};
    public static User user;
    private final EntityManager em;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    
    public JKTV22FXStore() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JKTV22FXStorePU");
        this.em = emf.createEntityManager();
        checkSuperUser();
        createRegularUser();
    }
    
    private void checkSuperUser() {
        if (!(getEntityManager().createQuery("SELECT u FROM User u").getResultList().size()>0)) {
            try {
                User admin = new User();
                admin.setLogin("admin");
                PassEncrypt pe = new PassEncrypt();
                admin.setPassword(pe.getEncryptPassword("12345", pe.getSalt()));
                admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
                admin.getRoles().add(ROLES.MANAGER.toString());
                admin.getRoles().add(ROLES.CUSTOMER.toString());
                getEntityManager().getTransaction().begin();
                getEntityManager().persist(admin);
                getEntityManager().getTransaction().commit();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(JKTV22FXStore.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(JKTV22FXStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void createRegularUser() {
        if ((getEntityManager().createQuery("SELECT u FROM User u").getResultList().size()<2)) {
            try {
                User user = new User();
                user.setLogin("user");
                PassEncrypt pe = new PassEncrypt();
                user.setPassword(pe.getEncryptPassword("123", pe.getSalt()));
                user.getRoles().add(ROLES.CUSTOMER.toString());
                getEntityManager().getTransaction().begin();
                getEntityManager().persist(user);
                getEntityManager().getTransaction().commit();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(JKTV22FXStore.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(JKTV22FXStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JKTV22-FX Store");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
