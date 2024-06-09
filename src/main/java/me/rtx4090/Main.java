package me.rtx4090;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class Main {
    private static EdgeDriver driver;
    private static String token;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel("Login with your token:");
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 30));
        JButton button = new JButton("Login");
        frame.add(label);
        frame.add(textField);
        frame.add(button);

        button.addActionListener(e -> {
            token = textField.getText();
            login();
            frame.setVisible(false);
        });
        frame.pack();
        frame.setVisible(true);

        while (true) {
            if (driver.getWindowHandles().size() == 0) {
                System.out.println("Browser window is closed. Stopping execution.");
                System.exit(0);
            }
        }
    }

    private static void login() {
        String command =
                "function login(token) {\n" +
                        "setInterval(() => {\n" +
                        "document.body.appendChild(document.createElement `iframe`).contentWindow.localStorage.token = `\"${token}\"`\n" +
                        "}, 50);\n" +
                        "setTimeout(() => {\n" +
                        "location.reload();\n" +
                        "}, 2500);\n" +
                        "}\n" +
                        "login ('" + token + "')";
        driver = new EdgeDriver();
        driver.get("https://discord.com/login");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        ((JavascriptExecutor) driver).executeScript(command);
    }
}