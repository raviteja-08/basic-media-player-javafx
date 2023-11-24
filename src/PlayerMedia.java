import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerMedia extends Application {

    @Override
    public void start(Stage primaryStage)  {
          Parent root;
       try{
           
           root = FXMLLoader.load(getClass().getResource("index.fxml"));
           
           Scene scene = new Scene(root);
           primaryStage.setTitle("music player");
           primaryStage.setScene(scene);
           primaryStage.show();


       }
       catch(Exception e){

       }
    }
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
