package fr.univ_amu.iut.exercice6;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Exercice 6 - Formulaire de connexion avec bindings de validation.
 *
 * <p>Cet exercice montre comment les bindings permettent de gérer l'état des contrôles (éditable,
 * disable) de manière déclarative. C'est un exemple concret d'<b>affordance</b> (concept CM2) : les
 * contrôles désactivés communiquent visuellement les exigences à l'utilisateur.
 *
 * <p>Règles de validation :
 *
 * <ul>
 *   <li>Le champ mot de passe n'est éditable que si l'identifiant contient au moins 6 caractères
 *   <li>Le bouton OK n'est actif que si le mot de passe est valide (>= 8 chars, 1 majuscule, 1
 *       chiffre)
 *   <li>Le bouton Annuler est désactivé si les deux champs sont vides
 * </ul>
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>{@code editableProperty().bind(...)}
 *   <li>{@code disableProperty().bind(...)}
 *   <li>Low-level {@link BooleanBinding} avec {@code computeValue()} personnalisé
 *   <li>Pattern {@code createBindings()}
 * </ul>
 */
public class FormulaireConnexion extends Application {

  private TextField userId;
  private PasswordField pwd;
  private Button okBtn;
  private Button cancelBtn;
  private Label message;

  @Override
  public void start(Stage primaryStage) {
    GridPane root = new GridPane();
    root.setPadding(new Insets(20));
    root.setHgap(10);
    root.setVgap(10);

    root.add(new Label("Identifiant :"), 0, 0);
    userId = new TextField();
    userId.setId("user-id");
    root.add(userId, 1, 0);

    root.add(new Label("Mot de passe :"), 0, 1);
    pwd = new PasswordField();
    pwd.setId("pwd");
    root.add(pwd, 1, 1);

    okBtn = new Button("OK");
    okBtn.setId("btn-ok");
    okBtn.setOnAction(e -> okClicked());
    root.add(okBtn, 0, 2);

    cancelBtn = new Button("Annuler");
    cancelBtn.setId("btn-cancel");
    cancelBtn.setOnAction(e -> cancelClicked());
    root.add(cancelBtn, 1, 2);

    message = new Label();
    message.setId("message");
    root.add(message, 0, 3, 2, 1);

    createBindings();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /** Crée les bindings de validation. */
  void createBindings() {
    pwd.editableProperty().bind(Bindings.greaterThanOrEqual(userId.textProperty().length(), 6));

    cancelBtn
        .disableProperty()
        .bind(
            Bindings.and(
                Bindings.equal(0, pwd.textProperty().length()),
                Bindings.equal(0, userId.textProperty().length())));

    BooleanBinding invalidPassword =
        new BooleanBinding() {
          {
            super.bind(pwd.textProperty());
          }

          @Override
          protected boolean computeValue() {
            String text = pwd.getText();
            return text.length() < 8
                || text.chars().noneMatch(Character::isUpperCase)
                || text.chars().noneMatch(Character::isDigit);
          }
        };

    okBtn.disableProperty().bind(invalidPassword);
  }

  void okClicked() {
    String masked = "*".repeat(pwd.getText().length());
    message.setText("Identifiant : " + userId.getText() + " mot de passe : " + masked);
  }

  void cancelClicked() {
    userId.clear();
    pwd.clear();
    message.setText("");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
