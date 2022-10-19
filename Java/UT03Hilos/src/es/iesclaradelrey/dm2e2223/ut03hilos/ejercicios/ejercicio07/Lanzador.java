package es.iesclaradelrey.dm2e2223.ut03hilos.ejercicios.ejercicio07;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Lanzador {

	private static final int TIEMPO_SUENIO_MS = 5;

	private JFrame frame;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;

	// Hilos
	private Hilo hilo1;
	private Hilo hilo2;
	private Hilo hilo3;
	private JProgressBar progressBar1;
	private JProgressBar progressBar2;
	private JProgressBar progressBar3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lanzador window = new Lanzador();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lanzador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JButton btnComenzarCarrera = new JButton("Comenzar carrera");
		btnComenzarCarrera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estanCorriendoHilos()) {
					JOptionPane.showMessageDialog(btnComenzarCarrera, "Ya están corriendo hilos", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					iniciarCarrera();
				}
			}
		});
		frame.getContentPane().add(btnComenzarCarrera, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 3, 0, 0));

		JSlider slider1 = new JSlider();
		slider1.setMaximum(10);
		slider1.setMinimum(1);
		slider1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cambiarPrioridad(hilo1, slider1.getValue());
			}
		});
		slider1.setMajorTickSpacing(1);
		slider1.setToolTipText("");
		slider1.setValue(5);
		panel.add(slider1);

		JSlider slider2 = new JSlider();
		slider2.setMaximum(10);
		slider2.setMinimum(1);
		slider2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cambiarPrioridad(hilo2, slider2.getValue());
			}
		});
		slider2.setMajorTickSpacing(1);
		slider2.setValue(5);
		panel.add(slider2);

		JSlider slider3 = new JSlider();
		slider3.setMaximum(10);
		slider3.setMinimum(1);
		slider3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cambiarPrioridad(hilo3, slider3.getValue());
			}
		});
		slider3.setMajorTickSpacing(1);
		slider3.setValue(5);
		panel.add(slider3);

		progressBar1 = new JProgressBar();
		progressBar1.setMaximum(10000);
		panel.add(progressBar1);

		progressBar2 = new JProgressBar();
		progressBar2.setMaximum(10000);
		panel.add(progressBar2);

		progressBar3 = new JProgressBar();
		progressBar3.setMaximum(10000);
		panel.add(progressBar3);

		textField1 = new JTextField();
		panel.add(textField1);
		textField1.setColumns(10);

		textField2 = new JTextField();
		panel.add(textField2);
		textField2.setColumns(10);

		textField3 = new JTextField();
		panel.add(textField3);
		textField3.setColumns(10);
	}

	private boolean estanCorriendoHilos() {
		if ((hilo1 == null) && (hilo2 == null) && (hilo3 == null)) {
			return false;
		}
		if ((hilo1.getState() == Thread.State.TERMINATED) && (hilo2.getState() == Thread.State.TERMINATED)
				&& (hilo3.getState() == Thread.State.TERMINATED)) {
			return false;
		}
		return true;
	}

	private void iniciarCarrera() {
		hilo1 = new Hilo(1, TIEMPO_SUENIO_MS, progressBar1, textField1);
		hilo2 = new Hilo(2, TIEMPO_SUENIO_MS, progressBar2, textField2);
		hilo3 = new Hilo(3, TIEMPO_SUENIO_MS, progressBar3, textField3);

		hilo1.start();
		hilo2.start();
		hilo3.start();
	}

	private void cambiarPrioridad(Hilo hilo, int prioridad) {
		System.out.println("Cambiando prioridad de un hilo a " + prioridad);
		if (hilo != null)
			hilo.setPriority(prioridad);
	}

}
