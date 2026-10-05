package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class VentanaResultado {

        private JFrame frame;
        private VentanaConfgRegiones ventanaAnterior;
        private List<List<String>> regiones;

        public VentanaResultado(
                        VentanaConfgRegiones ventanaAnterior,
                        List<List<String>> regiones) {
                this.ventanaAnterior = ventanaAnterior;
                this.regiones = regiones;

                initialize();
        }

        private void initialize() {

                frame = new JFrame();

                frame.setTitle("Regiones generadas");

                frame.setBounds(
                                100,
                                100,
                                850,
                                550);

                frame.setDefaultCloseOperation(
                                JFrame.EXIT_ON_CLOSE);

                frame.getContentPane().setLayout(
                                new BorderLayout());

                // TITULO

                JLabel lblTitulo = new JLabel("Regiones generadas");

                lblTitulo.setHorizontalAlignment(
                                SwingConstants.CENTER);

                frame.getContentPane().add(
                                lblTitulo,
                                BorderLayout.NORTH);

                // CENTRO

                JPanel panelCentral = new JPanel(new GridLayout(0, 1, 0, 8));

                for (int i = 0; i < regiones.size(); i++) {
                        JLabel etiqueta = new JLabel(
                                        "Región " + (i + 1) + ": "
                                                        + String.join(", ", regiones.get(i)));
                        panelCentral.add(etiqueta);
                }

                frame.getContentPane().add(
                                new JScrollPane(panelCentral),
                                BorderLayout.CENTER);

                // Después acá va el mapa/regiones

                // NAVEGACION

                JPanel panelNavegacion = new JPanel();

                frame.getContentPane().add(
                                panelNavegacion,
                                BorderLayout.SOUTH);

                // VOLVER

                JButton btnVolver = new JButton("← Volver");

                btnVolver.addActionListener(e -> {

                        frame.setVisible(false);

                        ventanaAnterior.mostrar();
                });

                panelNavegacion.add(btnVolver);

                // SALIR

                JButton btnSalir = new JButton("Salir");

                btnSalir.addActionListener(e -> {

                        System.exit(0);
                });

                panelNavegacion.add(btnSalir);
        }

        public void mostrar() {

                frame.setVisible(true);
        }
}