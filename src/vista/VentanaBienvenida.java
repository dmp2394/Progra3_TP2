package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaBienvenida {

    private JFrame frame;
    private ventanaConfgGrafo ventanaPrincipal;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaBienvenida ventana = new VentanaBienvenida();

                    ventana.mostrar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaBienvenida() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Bienvenido - Diseño de regiones");
        frame.setBounds(100, 100, 760, 460);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelContenido = new JPanel();
        panelContenido.setBorder(new EmptyBorder(25, 30, 25, 30));
        panelContenido.setLayout(new BorderLayout(0, 20));
        frame.setContentPane(panelContenido);

        JLabel lblTitulo = new JLabel("Diseño de regiones");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelPasos = new JPanel();
        panelPasos.setLayout(new GridLayout(6, 1, 0, 8));
        panelContenido.add(panelPasos, BorderLayout.CENTER);

        JLabel lblPaso1 = new JLabel(
                "1. Cargar una imagen del mapa en formato PNG o JPG.");
        panelPasos.add(lblPaso1);

        JLabel lblPaso2 = new JLabel(
                "2. Escribir los nombres de las provincias o unidades.");
        panelPasos.add(lblPaso2);

        JLabel lblPaso3 = new JLabel(
                "3. Ubicarlas haciendo click sobre el mapa.");
        panelPasos.add(lblPaso3);

        JLabel lblPaso4 = new JLabel(
                "4. Crear conexiones e indicar sus pesos.");
        panelPasos.add(lblPaso4);

        JLabel lblPaso5 = new JLabel(
                "5. Elegir la cantidad de regiones K.");
        panelPasos.add(lblPaso5);

        JLabel lblPaso6 = new JLabel(
                "6. Generar y consultar las regiones resultantes.");
        panelPasos.add(lblPaso6);

        JPanel panelNavegacion = new JPanel();
        panelNavegacion.setLayout(new BorderLayout());
        panelContenido.add(panelNavegacion, BorderLayout.SOUTH);

        JToggleButton btnTema = new JToggleButton("Tema Oscuro");
        panelNavegacion.add(btnTema, BorderLayout.WEST);

        btnTema.addActionListener(e -> {
            Tema.setOscuro(btnTema.isSelected());

            for (Window ventana : Window.getWindows()) {
                if (ventana.isDisplayable()) {
                    Tema.aplicar(ventana);
                }
            }
        });

        JButton btnComenzar = new JButton("Comenzar →");
        panelNavegacion.add(btnComenzar, BorderLayout.EAST);

        btnComenzar.addActionListener(e -> {
            if (ventanaPrincipal == null) {
                ventanaPrincipal = new ventanaConfgGrafo();
            }

            ventanaPrincipal.mostrar();
            frame.setVisible(false);
        });
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}