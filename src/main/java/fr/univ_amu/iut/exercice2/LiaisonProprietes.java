package fr.univ_amu.iut.exercice2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Exercice 2 - Liaison unidirectionnelle entre propriétés.
 *
 * <p>Une propriété peut être <b>liée</b> à une autre via {@code bind()} : elle suivra
 * automatiquement la valeur de la source. C'est le mécanisme fondamental de propagation automatique
 * des changements dans JavaFX.
 *
 * <p>Concepts découverts :
 *
 * <ul>
 *   <li>{@code bind()} : la cible suit automatiquement la source
 *   <li>{@code unbind()} : rompre la liaison
 *   <li>Une propriété liée ne peut pas être modifiée directement (RuntimeException)
 *   <li>{@code isBound()} : vérifier si une propriété est liée
 * </ul>
 *
 * @see <a href=
 *     "https://openjfx.io/javadoc/25/javafx.base/javafx/beans/property/IntegerProperty.html#bind(javafx.beans.value.ObservableValue)">IntegerProperty.bind()</a>
 */
public class LiaisonProprietes {

  private IntegerProperty anIntProperty;

  /**
   * Crée une deuxième propriété, la lie à {@code anIntProperty}, modifie la source, puis rompt la
   * liaison.
   *
   * <p>Sortie attendue (si anIntProperty vaut 1024 au départ) :
   *
   * <pre>
   * otherProperty.get() = 0
   * Binding otherProperty to anIntProperty.
   * otherProperty.get() = 1024
   * Calling anIntProperty.set(7168).
   * otherProperty.get() = 7168
   * otherProperty.get() = 7168
   * otherProperty.get() = 7168
   * Unbinding otherProperty from anIntProperty.
   * otherProperty.get() = 7168
   * Calling anIntProperty.set(8192).
   * otherProperty.get() = 7168
   * </pre>
   */
  void lierEtDelierProprietes() {
    IntegerProperty otherProperty = new SimpleIntegerProperty();

    System.out.println();
    System.out.println("otherProperty.get() = " + otherProperty.get());

    System.out.println("Binding otherProperty to anIntProperty.");
    otherProperty.bind(anIntProperty);

    System.out.println("otherProperty.get() = " + otherProperty.get());

    System.out.println("Calling anIntProperty.set(7168).");
    anIntProperty.set(7168);

    System.out.println("otherProperty.get() = " + otherProperty.get());
    System.out.println("otherProperty.get() = " + otherProperty.get());
    System.out.println("otherProperty.get() = " + otherProperty.get());

    System.out.println("Unbinding otherProperty from anIntProperty.");
    otherProperty.unbind();

    System.out.println("otherProperty.get() = " + otherProperty.get());

    System.out.println("Calling anIntProperty.set(8192).");
    anIntProperty.set(8192);

    System.out.println("otherProperty.get() = " + otherProperty.get());
  }

  public int getAnInt() {
    return anIntProperty.get();
  }

  public void setAnInt(int value) {
    if (anIntProperty == null) {
      anIntProperty = new SimpleIntegerProperty();
    }
    anIntProperty.set(value);
  }

  public IntegerProperty anIntProperty() {
    return anIntProperty;
  }

  public static void main(String[] args) {
    LiaisonProprietes exemple = new LiaisonProprietes();
    exemple.setAnInt(1024);
    exemple.lierEtDelierProprietes();
  }
}
