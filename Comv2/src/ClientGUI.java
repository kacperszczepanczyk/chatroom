import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ClientGUI {

	private JFrame frame;
	private JTextArea JDisplayArea;
	private TextField userTextArea;
	private Button sendButton;
	public boolean isMessageReady;
	public boolean isUserPasswordReady;
	private JScrollPane scrollPane;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	private JButton loginButton;
	private JButton registerButton;
	private String separator = ";";

	ClientGUI() {
		initialize();
	}

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		addAndSetUpMainFrame();
		addAndSetUpUserTextArea();
		addAndSetUpJDisplayArea();
		addAndSetUpScrollPanel();
		addAndSetUpSendButton();
		addAndSetUpAuthenticator();
	}

	public void addJDisplayText(String text) {
		JDisplayArea.setText(JDisplayArea.getText() + text + "\n");
	}

	public void scrollJDisplayAreaDown() {
		JDisplayArea.setCaretPosition(JDisplayArea.getDocument().getLength());
	}

	public String getUserText() {
		return userTextArea.getText();
	}

	public void setUserText(String text) {
		userTextArea.setText(text);
	}

	public void setJDisplayText(String text) {
		JDisplayArea.setText(text);
	}

	public void enableChat(boolean b) {
		userTextArea.setEnabled(b);
		sendButton.setEnabled(b);
	}

	public void enableAuthenticator(boolean b) {
		userNameTextField.setEnabled(b);
		passwordTextField.setEnabled(b);
		loginButton.setEnabled(b);
		registerButton.setEnabled(b);
	}

	public String getUserNameAndPassword() {
		if (userNameTextField.getText().isEmpty()) {
			return "Wrong user name" + separator + "or password";
		}
		if (passwordTextField.getText().isEmpty()) {
			return "Wrong user name" + separator + "or password";
		}
		String userNameAndPassword = userNameTextField.getText() + separator + passwordTextField.getText();
		return userNameAndPassword;
	}

	private void addAndSetUpMainFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 748, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private void addAndSetUpUserTextArea() {
		userTextArea = new TextField();
		userTextArea.setEnabled(false);
		userTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_ENTER && getUserText() != null) {
					isMessageReady = true;
				}
			}
		});
		userTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (userTextArea.getText().equals("Write your message here.")) {
					userTextArea.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (userTextArea.getText().equals("")) {
					userTextArea.setText("Write your message here.");
				}
			}
		});
		userTextArea.setText("Write your message here.");
		userTextArea.setBounds(29, 349, 299, 22);
		frame.getContentPane().add(userTextArea);
	}

	private void addAndSetUpJDisplayArea() {
		JDisplayArea = new JTextArea();
		JDisplayArea.setToolTipText("");
		JDisplayArea.setEditable(false);
		JDisplayArea.setBounds(409, 10, 361, 311);
		JDisplayArea.setLineWrap(true);
		JDisplayArea.setWrapStyleWord(true);
		frame.getContentPane().add(JDisplayArea);
	}

	private void addAndSetUpScrollPanel() {
		scrollPane = new JScrollPane(JDisplayArea);
		scrollPane.setBounds(29, 11, 361, 311);
		scrollPane.setAutoscrolls(true);
		frame.getContentPane().add(scrollPane);
	}

	private void addAndSetUpSendButton() {
		sendButton = new Button("Send");
		sendButton.setEnabled(false);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getUserText() != null) {
					isMessageReady = true;
				}
			}
		});
		sendButton.setBounds(333, 349, 57, 22);
		frame.getContentPane().add(sendButton);
	}

	private void addAndSetUpAuthenticator() {
		userNameTextField = new JTextField();
		userNameTextField.setBounds(525, 9, 185, 20);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);

		passwordTextField = new JTextField();
		passwordTextField.setBounds(525, 40, 185, 20);
		frame.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(450, 12, 65, 14);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(450, 37, 65, 14);
		frame.getContentPane().add(lblPassword);

		loginButton = new JButton("Log in");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getUserNameAndPassword().length() > 1) {
					isUserPasswordReady = true;
				}
			}
		});
		loginButton.setBounds(525, 71, 89, 23);
		frame.getContentPane().add(loginButton);

		registerButton = new JButton("Register");
		registerButton.setBounds(624, 71, 86, 23);
		frame.getContentPane().add(registerButton);
	}

}
