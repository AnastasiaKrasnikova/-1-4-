package view;
import java.util.Scanner;
public class EquationView {
    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        System.out.println("Введите уравнение:");
        return scanner.nextLine();
    }

    public void displayResult(double result) {
        System.out.println("Результат: " + result);
    }

    public void showError(String message) {
        System.out.println("Ошибка: " + message);
    }
}
