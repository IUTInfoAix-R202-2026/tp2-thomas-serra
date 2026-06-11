package fr.univ_amu.iut.exercice3;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.StringExpression;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Exercice 3 - Palette réactive (pont avec le TP1).
 *
 * <p>Cet exercice reprend la Palette du TP1 (exercice 6) et la refactorise avec des propriétés
 * JavaFX. Le comportement est identique, mais l'implémentation est supérieure :
 *
 * <ul>
 *   <li>TP1 : {@code int[] compteurs} + {@code setText()} dans chaque handler (3x le meme code)
 *   <li>TP2 : {@code IntegerProperty nbClics} dans chaque {@link BoutonCouleur} + 1 binding
 * </ul>
 *
 * <p>Comportement attendu :
 *
 * <pre>
 * +------------------------------+
 * | [Rouge] [Vert] [Bleu]        |  HBox de 3 BoutonCouleur
 * +------------------------------+
 * |                              |
 * |     (zone de couleur)        |  Pane #zone dont le fond change
 * |                              |
 * +------------------------------+
 * | Rouge: 0  Vert: 0  Bleu: 0  |  Label #compteurs (bind)
 * +------------------------------+
 * </pre>
 *
 * @see BoutonCouleur
 */
public class PaletteReactive extends Application {

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();

    HBox top = new HBox(10);
    BoutonCouleur btnRouge = new BoutonCouleur("Rouge", "red");
    btnRouge.setId("btn-rouge");
    BoutonCouleur btnVert = new BoutonCouleur("Vert", "green");
    btnVert.setId("btn-vert");
    BoutonCouleur btnBleu = new BoutonCouleur("Bleu", "blue");
    btnBleu.setId("btn-bleu");
    top.getChildren().addAll(btnRouge, btnVert, btnBleu);

    Pane zone = new Pane();
    zone.setId("zone");
    zone.setMinSize(300, 200);

    Label labelCompteurs = new Label();
    labelCompteurs.setId("compteurs");

    root.setTop(top);
    root.setCenter(zone);
    root.setBottom(labelCompteurs);

    createBindings(btnRouge, btnVert, btnBleu, zone, labelCompteurs);

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Crée les bindings entre les boutons, la zone de couleur et le label compteurs.
   *
   * <p>Cette méthode remplace les 3 handlers {@code setOnAction} du TP1 par des bindings
   * déclaratifs. Après cette méthode, plus aucun {@code setText()} n'est nécessaire : le label se
   * met à jour automatiquement quand un compteur change.
   */
  void createBindings(
      BoutonCouleur btnRouge,
      BoutonCouleur btnVert,
      BoutonCouleur btnBleu,
      Pane zone,
      Label labelCompteurs) {
    btnRouge
        .nbClicsProperty()
        .addListener(
            (obs, oldV, newV) ->
                zone.setStyle("-fx-background-color: " + btnRouge.getCouleur() + ";"));
    btnVert
        .nbClicsProperty()
        .addListener(
            (obs, oldV, newV) ->
                zone.setStyle("-fx-background-color: " + btnVert.getCouleur() + ";"));
    btnBleu
        .nbClicsProperty()
        .addListener(
            (obs, oldV, newV) ->
                zone.setStyle("-fx-background-color: " + btnBleu.getCouleur() + ";"));

    StringExpression expr =
        Bindings.concat(
            "Rouge: ", btnRouge.nbClicsProperty().asString(),
            "  Vert: ", btnVert.nbClicsProperty().asString(),
            "  Bleu: ", btnBleu.nbClicsProperty().asString());

    BooleanExpression anyClicked =
        btnRouge
            .nbClicsProperty()
            .greaterThan(0)
            .or(btnVert.nbClicsProperty().greaterThan(0))
            .or(btnBleu.nbClicsProperty().greaterThan(0));

    labelCompteurs
        .textProperty()
        .bind(Bindings.when(anyClicked).then(expr).otherwise("Bienvenue !"));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
