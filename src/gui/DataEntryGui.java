package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import src.Driver;
import src.Metadata;
import src.ProcessString;

public class DataEntryGui extends JFrame {

	private final JPanel contentPane;
	private final JTextField tfTestFileName;
	private final JButton btnBrowse;
	private final JTextArea txtrTrainingDataFile;
	private final JPanel panel_1;
	private final JTextField tfDecodeFileName;
	private final JButton button;
	private final JTextArea txtrDecodingDataFile;
	private final JPanel panel_2;
	private final JTextField txtSmoothing;
	private final JTextField tfSmoothing;
	private final JTextField txtStartProbability;
	private final JTextField tfStartProbability;
	private final JPanel panel_4;
	private final JButton btnStart;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final DataEntryGui frame = new DataEntryGui();
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataEntryGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 5, 5));

		final JPanel panel = new JPanel();
		contentPane.add(panel);

		tfTestFileName = new JTextField();
		tfTestFileName.setText("/home/amy/workspace3/HMM/Klingon_Train.txt");
		tfTestFileName.setColumns(10);

		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				final int returnVal = fileChooser.showOpenDialog(contentPane);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fileChooser.getSelectedFile();
					try {
						// What to do with the file, e.g. display it in a
						// TextArea
						tfTestFileName.read(new FileReader(file.getAbsolutePath()), null);
					} catch (final IOException ex) {
						System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				} else {
					System.out.println("File access cancelled by user.");
				}
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtrTrainingDataFile = new JTextArea();
		txtrTrainingDataFile.setText("Training Data File: ");
		panel.add(txtrTrainingDataFile);
		panel.add(tfTestFileName);
		panel.add(btnBrowse);

		panel_1 = new JPanel();
		contentPane.add(panel_1);

		txtrDecodingDataFile = new JTextArea();
		panel_1.add(txtrDecodingDataFile);
		txtrDecodingDataFile.setText("Decoding Data File: ");

		tfDecodeFileName = new JTextField();
		panel_1.add(tfDecodeFileName);
		tfDecodeFileName.setColumns(10);
		tfDecodeFileName.setText("/home/amy/workspace3/HMM/Klingon_Decode.txt");

		button = new JButton("Browse");
		panel_1.add(button);

		panel_2 = new JPanel();
		contentPane.add(panel_2);

		txtSmoothing = new JTextField();
		txtSmoothing.setText("Smoothing: ");
		panel_2.add(txtSmoothing);
		txtSmoothing.setColumns(10);

		tfSmoothing = new JTextField();
		tfSmoothing.setText("");
		panel_2.add(tfSmoothing);
		tfSmoothing.setColumns(10);
		tfSmoothing.setText("0.1");

		final JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);

		txtStartProbability = new JTextField();
		txtStartProbability.setText("Start Probability: ");
		txtStartProbability.setColumns(10);
		panel_3.add(txtStartProbability);

		tfStartProbability = new JTextField();
		tfStartProbability.setText("");
		tfStartProbability.setColumns(10);
		tfStartProbability.setText("1.0");
		panel_3.add(tfStartProbability);

		panel_4 = new JPanel();
		contentPane.add(panel_4);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				Metadata.INSTANCE.setFileName(tfTestFileName.getText());
				final String string = ProcessString.readFile(tfDecodeFileName.getText());
				System.out.println(string);
				Metadata.INSTANCE.setStringToDecode(string);
				Metadata.INSTANCE.setSmoothing(Double.valueOf(tfSmoothing.getText()));
				Metadata.INSTANCE.setMyStartProbability(Double.valueOf(tfStartProbability.getText()));

				if (tfSmoothing.getText().trim() == "") {
					Metadata.INSTANCE.setSmoothing(0.0);
				}
				if (tfStartProbability.getText().trim() == "") {
					Metadata.INSTANCE.setMyStartProbability(0.0);
				}

				Driver.start();

			}
		});
		panel_4.add(btnStart);
	}
}
