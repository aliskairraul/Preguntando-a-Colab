package user.client.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

public class MainWindow extends JFrame {
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    public MainWindow(String colorBackground, int w, int h) {
        setSize(w, h);
        setBackground(Color.decode(colorBackground));
        FlatArcIJTheme.setup();
        UIManager.put("Button.arc", 999);
        UIManager.put("Component.arc", 999);
        UIManager.put("TextComponent.arc", 999);
        setSize(w, h);
        setTitle("REGISTRO DE USUARIOS");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);
        setLayout(new GridLayout(1, 3));

        leftPanel.setBackground(Color.decode(colorBackground));
        leftPanel.setLayout(null);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0; // Columna 0
        gbc1.gridy = 0; // Fila 0
        gbc1.gridwidth = 1; // Ocupa una columna
        gbc1.gridheight = 1; // Ocupa una fila

        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(null);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1; // Columna 1
        gbc2.gridy = 0; // Fila 0
        gbc2.gridwidth = 2; // Ocupa dos columnas
        gbc2.gridheight = 1; // Ocupa una fila

        this.add(leftPanel, gbc1);
        this.add(rightPanel, gbc2);
        initComponents();

    }

    private void initComponents() {

    }

}
