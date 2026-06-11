package fr.univ_amu.iut.exercice7;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 7 - Cercle interactif avec binding bidirectionnel.
 *
 * <p>Trois contrôles sont synchronisés en permanence :
 *
 * <ul>
 *   <li>Un {@link Circle} dont le rayon change visuellement
 *   <li>Un {@link Slider} qui contrôle le rayon
 *   <li>Un {@link TextField} qui affiche et permet de saisir le rayon
 * </ul>
 *
 * <p>Modifier l'un des trois met à jour les deux autres automatiquement grâce à {@code
 * bindBidirectional()}.
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>{@code bindBidirectional()} entre Slider et Circle
 *   <li>{@code Bindings.bindBidirectional()} avec {@link NumberStringConverter}
 *   <li>{@link TextFormatter} avec filtre de validation
 *   <li>Binding unidirectionnel pour le centrage du cercle
 * </ul>
 */
public class CercleInteractif extends Application {

  private final Circle cercle = new Circle();
  private final Slider slider = new Slider();
  private final TextField textField = new TextField();
  private final Pane panneauCercle = new Pane();
  private final BorderPane root = new BorderPane();

  @Override
  public void start(Stage primaryStage) {
    ajouterPanneau();
    ajouterSlider();
    ajouterTextField();
    creerBindings();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  void ajouterPanneau() {
    panneauCercle.getChildren().add(cercle);
    panneauCercle.setPrefSize(500, 500);
    panneauCercle.setId("panneau");
    cercle.setId("cercle");
    root.setCenter(panneauCercle);
  }

  void ajouterSlider() {
    slider.setMin(0);
    slider.setMax(250);
    slider.setId("slider");
    root.setTop(slider);
  }

  void ajouterTextField() {
    textField.setId("rayon");
    textField.setTextFormatter(
        new TextFormatter<>(
            change -> {
              String newText = change.getControlNewText();
              return newText.matches("\\d*") ? change : null;
            }));
    root.setBottom(textField);
  }

  void creerBindings() {
    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2));
    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2));

    cercle.radiusProperty().bindBidirectional(slider.valueProperty());

    Bindings.bindBidirectional(
        textField.textProperty(), slider.valueProperty(), new NumberStringConverter());

    slider.setValue(150);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
