package server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import utility.FaceData;
import utility.FaceExpressionData;

public class ServerGui extends Thread implements ActionListener {

	private JFrame frame;
	private JTextField timeElapsedTextbox;
	private JSpinner spinnerEmoStateInterval, spinnerUpperFace, spinnerLowerFace, spinnerAffective;
	private JCheckBox chckbxAutoReset, chckbxEyeAutoReset;
	private JTextArea txtAreaEmoLogs;
	private JButton btnSend, btnClearLogs;
	private JComboBox comboUpperFace, comboLowerFace, comboEye, comboAffective;
	private JRadioButton rdbtnActive;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmAbout;
	JMenuItem mntmQuit;
	Thread inputThread;
	boolean running = false;
	double emoIntervalSelected = 1;
	Double timeElapsed = 0.0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui window = new ServerGui();
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
	public ServerGui() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Server");
		frame.setBounds(100, 100, 531, 887);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		try {
			JLabel imagePanel = new JLabel(new ImageIcon(ImageIO.read(new File("bg.jpg"))));
			imagePanel.setEnabled(true);
			frame.setContentPane(imagePanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 30, 489, 175);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
		tabbedPane.addTab("INTERACTIVE", null, panel, null);
		panel.setLayout(null);

		JLabel lblPlayer = new JLabel("Player");
		lblPlayer.setBounds(12, 26, 56, 16);
		panel.add(lblPlayer);

		JComboBox comboBoxPlayer = new JComboBox();
		comboBoxPlayer.setBounds(60, 23, 96, 22);
		comboBoxPlayer.setModel(new DefaultComboBoxModel(new String[] { "0", "1" }));
		panel.add(comboBoxPlayer);

		JLabel lblEmo = new JLabel("EmoStateInterval");
		lblEmo.setBounds(219, 20, 118, 28);
		panel.add(lblEmo);

		spinnerEmoStateInterval = new JSpinner();
		spinnerEmoStateInterval
				.setModel(new SpinnerNumberModel(new Double(0.0), new Double(0.0), null, new Double(0.25)));
		spinnerEmoStateInterval.setBounds(349, 23, 75, 22);
		panel.add(spinnerEmoStateInterval);

		JLabel lblSec = new JLabel("Sec");
		lblSec.setBounds(436, 26, 35, 16);
		panel.add(lblSec);

		chckbxAutoReset = new JCheckBox("Auto reset");
		chckbxAutoReset.setBounds(219, 58, 113, 25);
		panel.add(chckbxAutoReset);
		chckbxAutoReset.addActionListener(this);

		btnSend = new JButton("Send");
		btnSend.addActionListener(this);
		btnSend.setBounds(337, 58, 119, 25);
		panel.add(btnSend);
		panel.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(12, 243, 489, 552);
		frame.getContentPane().add(tabbedPane_1);

		JPanel panel_Detection = new JPanel();
		panel_Detection.setBorder(new MatteBorder(4, 4, 4, 4, (Color) Color.BLACK));
		tabbedPane_1.addTab("DETECTION", null, panel_Detection, null);
		SpringLayout sl_panel_Detection = new SpringLayout();
		panel_Detection.setLayout(sl_panel_Detection);

		JLabel lblEmoState = new JLabel("EMO STATE");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblEmoState, 13, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblEmoState, 12, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblEmoState, 88, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(lblEmoState);

		JLabel lblTime = new JLabel("TIME: ");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblTime, 47, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblTime, 22, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblTime, 70, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(lblTime);

		timeElapsedTextbox = new JTextField();
		timeElapsedTextbox.setEditable(false);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, timeElapsedTextbox, 44, SpringLayout.NORTH,
				panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, timeElapsedTextbox, 74, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(timeElapsedTextbox);
		timeElapsedTextbox.setColumns(10);

		JLabel lblSeconds = new JLabel("Seconds");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblSeconds, 47, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblSeconds, 202, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblSeconds, 258, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(lblSeconds);

		JLabel lblUpperFace = new JLabel("Upperface:");
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblUpperFace, 0, SpringLayout.WEST, lblEmoState);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblUpperFace, 88, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(lblUpperFace);

		comboUpperFace = new JComboBox();
		sl_panel_Detection.putConstraint(SpringLayout.SOUTH, lblUpperFace, -6, SpringLayout.NORTH, comboUpperFace);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, comboUpperFace, 144, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, comboUpperFace, 12, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, comboUpperFace, 111, SpringLayout.WEST, panel_Detection);
		comboUpperFace.setModel(new DefaultComboBoxModel(new String[] { "Raise Brow", "Furrow Brow" }));
		panel_Detection.add(comboUpperFace);

		spinnerUpperFace = new JSpinner();
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, spinnerUpperFace, 144, SpringLayout.NORTH,
				panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, spinnerUpperFace, 125, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, spinnerUpperFace, 190, SpringLayout.WEST, panel_Detection);
		spinnerUpperFace.setModel(new SpinnerListModel(
				new String[] { "0", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1" }));
		panel_Detection.add(spinnerUpperFace);

		JLabel lblLowerface = new JLabel("Lowerface:");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblLowerface, 0, SpringLayout.NORTH, lblUpperFace);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblLowerface, 276, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblLowerface, -126, SpringLayout.EAST, panel_Detection);
		panel_Detection.add(lblLowerface);

		comboLowerFace = new JComboBox();
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, comboLowerFace, 144, SpringLayout.NORTH, panel_Detection);
		comboLowerFace.setModel(
				new DefaultComboBoxModel(new String[] { "Smile", "Clench", "Smirk Left", "Smirk Right", "Laugh" }));
		panel_Detection.add(comboLowerFace);

		spinnerLowerFace = new JSpinner();
		sl_panel_Detection.putConstraint(SpringLayout.EAST, comboLowerFace, -10, SpringLayout.WEST, spinnerLowerFace);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, spinnerLowerFace, 144, SpringLayout.NORTH,
				panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, spinnerLowerFace, 402, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, spinnerLowerFace, 458, SpringLayout.WEST, panel_Detection);
		spinnerLowerFace.setModel(new SpinnerListModel(
				new String[] { "0", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1" }));
		panel_Detection.add(spinnerLowerFace);

		JLabel lblEye = new JLabel("Eye:");
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblEye, 0, SpringLayout.WEST, lblEmoState);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblEye, -2, SpringLayout.EAST, lblTime);
		panel_Detection.add(lblEye);

		comboEye = new JComboBox();
		sl_panel_Detection.putConstraint(SpringLayout.SOUTH, lblEye, -6, SpringLayout.NORTH, comboEye);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, comboEye, 238, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, comboEye, 0, SpringLayout.WEST, lblEmoState);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, comboEye, 0, SpringLayout.EAST, comboUpperFace);
		comboEye.setModel(new DefaultComboBoxModel(
				new String[] { "Blink", "Wink Left", "Wink Right", "Look Left", "Look Right" }));
		panel_Detection.add(comboEye);

		rdbtnActive = new JRadioButton("Active");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, rdbtnActive, -1, SpringLayout.NORTH, comboEye);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, rdbtnActive, 120, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, rdbtnActive, 0, SpringLayout.EAST, timeElapsedTextbox);
		panel_Detection.add(rdbtnActive);

		chckbxEyeAutoReset = new JCheckBox("Auto Reset");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, chckbxEyeAutoReset, 6, SpringLayout.SOUTH, comboEye);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, chckbxEyeAutoReset, 0, SpringLayout.WEST, lblEmoState);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, chckbxEyeAutoReset, -359, SpringLayout.EAST,
				panel_Detection);
		panel_Detection.add(chckbxEyeAutoReset);

		JLabel lblEmoengineLogs = new JLabel("EmoEngine Logs");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblEmoengineLogs, 316, SpringLayout.NORTH,
				panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblEmoengineLogs, 25, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, lblEmoengineLogs, 124, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(lblEmoengineLogs);

		txtAreaEmoLogs = new JTextArea();
		txtAreaEmoLogs.setEditable(true);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, txtAreaEmoLogs, 345, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, txtAreaEmoLogs, 22, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.SOUTH, txtAreaEmoLogs, 474, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, txtAreaEmoLogs, 458, SpringLayout.WEST, panel_Detection);
		txtAreaEmoLogs.setRows(5);
		txtAreaEmoLogs.setText("Aa");
		panel_Detection.add(txtAreaEmoLogs);

		btnClearLogs = new JButton("Clear Logs");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, btnClearLogs, 487, SpringLayout.NORTH, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, btnClearLogs, 185, SpringLayout.WEST, panel_Detection);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, btnClearLogs, 282, SpringLayout.WEST, panel_Detection);
		panel_Detection.add(btnClearLogs);
		btnClearLogs.addActionListener(this);

		JLabel lblAffective = new JLabel("Affective:");
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, lblAffective, 0, SpringLayout.NORTH, lblEye);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, lblAffective, 0, SpringLayout.WEST, lblLowerface);
		panel_Detection.add(lblAffective);

		comboAffective = new JComboBox();
		sl_panel_Detection.putConstraint(SpringLayout.WEST, comboLowerFace, 0, SpringLayout.WEST, comboAffective);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, comboAffective, 47, SpringLayout.EAST, rdbtnActive);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, comboAffective, 238, SpringLayout.NORTH, panel_Detection);
		comboAffective.setModel(new DefaultComboBoxModel(new String[] { "Meditation", "Engagement Boredom",
				"Excitement ShortTerm", "Frustration", "Excitement LongTerm" }));
		panel_Detection.add(comboAffective);

		spinnerAffective = new JSpinner();
		sl_panel_Detection.putConstraint(SpringLayout.EAST, comboAffective, -10, SpringLayout.WEST, spinnerAffective);
		sl_panel_Detection.putConstraint(SpringLayout.NORTH, spinnerAffective, 0, SpringLayout.NORTH, rdbtnActive);
		sl_panel_Detection.putConstraint(SpringLayout.WEST, spinnerAffective, 0, SpringLayout.WEST, spinnerLowerFace);
		sl_panel_Detection.putConstraint(SpringLayout.EAST, spinnerAffective, 0, SpringLayout.EAST, spinnerLowerFace);
		spinnerAffective.setModel(new SpinnerListModel(
				new String[] { "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0" }));
		panel_Detection.add(spinnerAffective);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		mnNewMenu.addActionListener(this);

		mntmAbout = new JMenuItem("About");
		mnNewMenu.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Emoticon Application. Version 1.0");
			}
		});

		mntmQuit = new JMenuItem("Quit");
		mnNewMenu.add(mntmQuit);
		mntmQuit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				System.exit(MAX_PRIORITY);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		running = false;
		String event = e.getActionCommand();
		if (event.equalsIgnoreCase("Clear Logs")) {
			running = false;
			System.out.println("Clear Log pressed");
			txtAreaEmoLogs.setText("");
			running = true;
		} else if (event.equalsIgnoreCase("Send")) {
			if (chckbxAutoReset.isSelected()) {
				System.out.println("Send pressed with check box selected");
				btnSend.setText("Stop");
				emoIntervalSelected = (Double) spinnerEmoStateInterval.getValue();
				System.out.println("EmoInterval: " + emoIntervalSelected);
				spinnerEmoStateInterval.setEnabled(false);
				chckbxAutoReset.setEnabled(false);
				running = true;
				inputThread = new Thread(this);
				inputThread.start();
			} else {
				System.out.println("Send pressed");
				getInputs();
			}
		} else if (event.equalsIgnoreCase("Stop")) {
			running = false;
			btnSend.setText("Send");
			spinnerEmoStateInterval.setEnabled(true);
			chckbxAutoReset.setEnabled(true);
			inputThread.stop();
		}
	}

	public void run() {
		while (running) {
			FaceData faceData = getInputs();
			// convert faceData to JSON and send to client
			//
			try {
				Thread.sleep((long) (emoIntervalSelected * 1000));
				timeElapsed += emoIntervalSelected;
				timeElapsedTextbox.setText(timeElapsed.toString());

			} catch (Exception ex) {
			}
			;
		}
	}

	public FaceData getInputs() {
		FaceExpressionData faceExpressionData = new FaceExpressionData();
		FaceAffectiveData faceAffectiveData = new FaceAffectiveData();
		FaceData faceData = new FaceData();

		String upperFace = (String) comboUpperFace.getSelectedItem();
		Double upperFaceValue = Double.parseDouble((String) spinnerUpperFace.getValue());

		// System.out.println("UpperFace: " + upperFace);
		// System.out.println("Upperface value: "+upperFaceValue);

		switch (upperFace) {
		case "Raise Brow":
			faceExpressionData.setRaiseBrow(upperFaceValue);
			break;
		case "Furrow Brow":
			faceExpressionData.setFurrowBrow(upperFaceValue);
			break;
		}

		String lowerFace = (String) comboLowerFace.getSelectedItem();
		Double lowerFaceValue = Double.parseDouble((String) spinnerLowerFace.getValue());

		// System.out.println("LowerFace: " + lowerFace );
		// System.out.println("LowerFace Value: "+ lowerFaceValue);

		switch (lowerFace) {
		case "Smile":
			faceExpressionData.setSmile(lowerFaceValue);
			break;
		case "Clench":
			faceExpressionData.setClench(lowerFaceValue);
			break;
		case "Smirk Left":
			faceExpressionData.setSmirkLeft(lowerFaceValue);
			break;
		case "Smirk Right":
			faceExpressionData.setSmirkRight(lowerFaceValue);
			break;
		case "Laugh":
			faceExpressionData.setLaugh(lowerFaceValue);
			break;
		}

		boolean eyeActive = rdbtnActive.isSelected();

		// System.out.println("Eye Active: "+eyeActive);

		if (eyeActive) {
			String eye = (String) comboEye.getSelectedItem();
			// System.out.println("Eye :"+ eye);

			switch (eye) {
			case "Blink":
				faceExpressionData.setBlink(1.0);
				break;
			case "Wink Left":
				faceExpressionData.setWinkLeft(1.0);
				break;
			case "Wink Right":
				faceExpressionData.setWinkRight(1.0);
				break;
			case "Look Left":
				faceExpressionData.setLookLeft(1.0);
				break;
			case "Look Right":
				faceExpressionData.setLookRight(1.0);
				break;
			}
		}

		boolean eyeAutoReset = chckbxEyeAutoReset.isSelected();
		// System.out.println("Eye Auto Reset :"+ eyeAutoReset);

		if (eyeAutoReset) {
			faceExpressionData.setEyeReset(true);
		}

		String affective = (String) comboAffective.getSelectedItem();
		Double affectiveValue = Double.parseDouble((String) spinnerAffective.getValue());

		// System.out.println("Affective: "+affective);
		// System.out.println("Affective Value: "+affectiveValue);

		switch (affective) {
		case "Meditation":
			faceAffectiveData.setMeditation(affectiveValue);
			break;
		case "Engagement Boredom":
			faceAffectiveData.setEngagementBoredom(affectiveValue);
			break;
		case "Excitement ShortTerm":
			faceAffectiveData.setExcitementShortTerm(affectiveValue);
			break;
		case "Frustration":
			faceAffectiveData.setFrustation(affectiveValue);
			break;
		case "Excitement LongTerm":
			faceAffectiveData.setExcitementLongTerm(affectiveValue);
			break;
		}

		faceData.setFaceAffectiveData(faceAffectiveData);
		faceData.setFaceExpressionData(faceExpressionData);
		System.out.println(faceData.toString());

		return faceData;
		// System.out.println(faceAffectiveData.toString());
		// System.out.println(faceExpressionData.toString());

	}
}