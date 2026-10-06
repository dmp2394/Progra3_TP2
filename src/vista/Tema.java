package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public final class Tema {

    private static boolean oscuro;

    private static final Color FONDO_CLARO = new Color(245, 246, 248);
    private static final Color TEXTO_CLARO = new Color(30, 35, 40);
    private static final Color CONTROL_CLARO = Color.WHITE;

    private static final Color FONDO_OSCURO = new Color(38, 40, 44);
    private static final Color TEXTO_OSCURO = new Color(235, 237, 240);
    private static final Color CONTROL_OSCURO = new Color(55, 58, 64);

    private Tema() {
    }

    public static void setOscuro(boolean activar) {
        oscuro = activar;
    }

    public static boolean esOscuro() {
        return oscuro;
    }

    public static Color fondo() {
        return oscuro ? FONDO_OSCURO : FONDO_CLARO;
    }

    public static Color texto() {
        return oscuro ? TEXTO_OSCURO : TEXTO_CLARO;
    }

    public static Color fondoControl() {
        return oscuro ? CONTROL_OSCURO : CONTROL_CLARO;
    }

    public static void aplicar(Component componente) {
        if (componente instanceof JLabel) {
            componente.setForeground(texto());
        }

        if (componente instanceof JPanel
                || componente instanceof JScrollPane
                || componente instanceof JViewport
                || componente instanceof JTextField
                || componente instanceof JComboBox<?>
                || componente instanceof JSpinner
                || componente instanceof JTable
                || componente instanceof JList<?>
                || componente instanceof AbstractButton) {

            componente.setBackground(fondoControl());
            componente.setForeground(texto());
        }

        if (componente instanceof JPanel panel
                && panel.getBorder() instanceof TitledBorder borde) {
            borde.setTitleColor(texto());
        }

        if (componente instanceof JTable tabla) {
            tabla.setGridColor(texto());
        }

        if (componente instanceof JList<?> lista) {
            lista.setSelectionBackground(new Color(75, 105, 145));
            lista.setSelectionForeground(Color.WHITE);
        }

        if (componente instanceof Container contenedor) {
            for (Component hijo : contenedor.getComponents()) {
                aplicar(hijo);
            }
        }

        componente.repaint();
    }
}