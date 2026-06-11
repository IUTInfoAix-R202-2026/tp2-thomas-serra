package fr.univ_amu.iut.exercice8;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 8 (capstone) - Convertisseur de températures.
 *
 * <p>Cet exercice synthétise tous les types de bindings vus dans le TP :
 *
 * <ul>
 *   <li>Binding unidirectionnel (Labels de lecture)
 *   <li>Binding bidirectionnel (TextField ↔ Slider via {@link NumberStringConverter})
 *   <li>{@code ChangeListener} pour la conversion avec formule (C = (F-32)*5/9)
 *   <li>Sliders verticaux ({@code Orientation.VERTICAL})
 * </ul>
 *
 * <p>L'application affiche deux panneaux côte à côte : un pour Celsius, un pour Fahrenheit.
 * Modifier l'un met à jour l'autre automatiquement.
 */
public class ConvertisseurTemperatures extends Application {

  private boolean updating = false;

  @Override
  public void start(Stage primaryStage) {
    Label labelCelsius = new Label("°C");
    labelCelsius.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    Slider sliderCelsius = new Slider(0, 100, 0);
    sliderCelsius.setOrientation(javafx.geometry.Orientation.VERTICAL);
    sliderCelsius.setId("slider-celsius");
    TextField tfCelsius = new TextField("0");
    tfCelsius.setId("tf-celsius");
    tfCelsius.setMaxWidth(50);

    VBox vboxCelsius = new VBox(10, labelCelsius, sliderCelsius, tfCelsius);
    vboxCelsius.setAlignment(Pos.CENTER);

    Label labelFahrenheit = new Label("°F");
    labelFahrenheit.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    Slider sliderFahrenheit = new Slider(0, 212, 32);
    sliderFahrenheit.setOrientation(javafx.geometry.Orientation.VERTICAL);
    sliderFahrenheit.setId("slider-fahrenheit");
    TextField tfFahrenheit = new TextField("32");
    tfFahrenheit.setId("tf-fahrenheit");
    tfFahrenheit.setMaxWidth(50);

    VBox vboxFahrenheit = new VBox(10, labelFahrenheit, sliderFahrenheit, tfFahrenheit);
    vboxFahrenheit.setAlignment(Pos.CENTER);

    sliderCelsius
        .valueProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              if (updating) {
                return;
              }
              updating = true;
              double celsius = newValue.doubleValue();
              double fahrenheit = celsius * 9 / 5 + 32;
              sliderFahrenheit.setValue(fahrenheit);
              updating = false;
            });

    sliderFahrenheit
        .valueProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              if (updating) {
                return;
              }
              updating = true;
              double fahrenheit = newValue.doubleValue();
              double celsius = (fahrenheit - 32) * 5 / 9;
              sliderCelsius.setValue(celsius);
              updating = false;
            });

    Bindings.bindBidirectional(
        tfCelsius.textProperty(), sliderCelsius.valueProperty(), new NumberStringConverter());
    Bindings.bindBidirectional(
        tfFahrenheit.textProperty(), sliderFahrenheit.valueProperty(), new NumberStringConverter());

    HBox root = new HBox(20, vboxCelsius, vboxFahrenheit);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
