import java.io.File;
import javafx.util.Duration;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Controller {
    @FXML
    private Label label;

    @FXML
    private ImageView imageSide;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playBtnId;

    @FXML
    private Slider slider;
   

    MediaPlayer player ;

    @FXML
    void openFileHandle(ActionEvent event) {
            System.out.println("clicked open");
            
            try{
                FileChooser chooser = new FileChooser();
                File file = chooser.showOpenDialog(null);
                
                System.out.println(file.toURI().toURL().toString());
                System.out.println(file.getName());
                Media media = new Media(file.toURI().toURL().toString());
                
                label.setText(file.getName());
                
                String s = file.getName();

                Stage mainWindow = (Stage) playBtnId.getScene().getWindow();
                if(s.endsWith(".mp4")){
                    System.out.println("video");
                    label.setVisible(false);
                    mediaView.setVisible(true);
                    mainWindow.setTitle(s);
                    
                }
                else if(s.endsWith(".mp3")){
                    label.setVisible(true);
                    System.out.println("music");
                    mediaView.setVisible(false);
                    mainWindow.setTitle("media player");
                }
                playBtnId.setText("play");
                if(player != null){
                    player.dispose();
                }
                player = new MediaPlayer(media);
                mediaView.setMediaPlayer(player);
                System.out.println(player);
                player.setOnReady(()->{
                    slider.setMin(0);
                    slider.setMax(player.getMedia().getDuration().toMinutes());
                    slider.setValue(0);
                    
                });
                
                player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    
                    @Override
                    public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
                        
                        Duration d = player.getCurrentTime();
                        
                        if(d.toMinutes() == player.getMedia().getDuration().toMinutes()){
                            player.seek(new Duration(0));
                            player.pause();
                            playBtnId.setText("play again");
                        }

                        slider.setValue(d.toMinutes());

                        

                    }
                    
                });
                

                slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if(slider.isPressed()){
                            double time = slider.getValue();
                            player.seek(new Duration(time*1000*60));
                        }
                        
                    }
                      
                });
                
            }
            catch(MediaException e){
                System.out.println(e.getMessage());
            }
            catch(Exception e){
                System.out.println(e);
            }
    }
    @FXML
    void playBTN(ActionEvent event) {
        if(player ==null){
             System.out.println("not selected file");
             return;
        }

        MediaPlayer.Status status = player.getStatus();

        if(status == MediaPlayer.Status.PLAYING){
            player.pause();
            playBtnId.setText("play");
        }
        else{
            player.play();
            playBtnId.setText("pause");

        }
    }
    @FXML
    void handleForward(ActionEvent event) {
           if(player !=null){
               double time = slider.getValue();
               player.seek(new Duration(time*1000*60 + 10*1000));
           }
    }

    @FXML
    void handleBackward(ActionEvent event){
        if(player != null){
             double time = slider.getValue();
             player.seek(new Duration(time*1000*60 - 10*1000));
        }
    }
        
    
    @FXML
    void handleReset(ActionEvent event){
        if(player!=null){
            player.seek(new Duration(0));
        }
        
    }
}

