package disasterresponse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 *
 * <p>
 * The {@code App} class initializes and manages the JavaFX application
 * life cycle.</p>
 * 
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Initializes the primary stage with the initial scene.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If an I/O error occurs while loading the FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the root of the current scene to a new FXML-based layout.
     *
     * @param fxml The name of the FXML file to load.
     * @throws IOException If an I/O error occurs while loading the FXML.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the FXML file and returns the corresponding {@code Parent} node.
     *
     * @param fxml The name of the FXML file to load.
     * @return The loaded {@code Parent} node.
     * @throws IOException If an I/O error occurs while loading the FXML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/disasterresponse/views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}
