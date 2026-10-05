package vista;

import java.awt.BorderLayout;
//import java.awt.GridLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;

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

                JPanel panelCentral = new JPanel();
                panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

                for (int i = 0; i < regiones.size(); i++) {
                        JList<String> listaProvincias = new JList<>(
                                        regiones.get(i).toArray(new String[0]));
                        listaProvincias.setVisibleRowCount(
                                        Math.min(regiones.get(i).size(), 5));

                        JPanel panelRegion = new JPanel(new BorderLayout());
                        panelRegion.setBorder(
                                        BorderFactory.createTitledBorder("Región " + (i + 1)));
                        panelRegion.add(
                                        new JScrollPane(listaProvincias),
                                        BorderLayout.CENTER);

                        panelRegion.setAlignmentX(Component.LEFT_ALIGNMENT);
                        panelCentral.add(panelRegion);
                        panelCentral.add(Box.createVerticalStrut(8));
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