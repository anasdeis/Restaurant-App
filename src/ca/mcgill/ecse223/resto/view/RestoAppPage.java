package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class RestoAppPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463272516157863988L;
	private JPanel contentPane;

	/** Creates new form RestoAppPage */
	public RestoAppPage() {

		initComponents();
		refreshData();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1600, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

	}

	private void refreshData() {

	}

}
