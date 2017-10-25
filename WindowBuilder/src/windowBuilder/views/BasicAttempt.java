package windowBuilder.views;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener; 
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class BasicAttempt extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasicAttempt frame = new BasicAttempt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BasicAttempt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 269);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JFormattedTextField freqDisplay = new JFormattedTextField();
		freqDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		freqDisplay.setForeground(new Color(255, 250, 250));
		freqDisplay.setBackground(new Color(0, 0, 0));
		freqDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 42));
		freqDisplay.setEditable(false);
		freqDisplay.setText("Frequency");
		
		JFormattedTextField gainField = new JFormattedTextField();
		gainField.setEditable(false);
		
		JFormattedTextField volField = new JFormattedTextField();
		volField.setEditable(false);
		
		JFormattedTextField squField = new JFormattedTextField();
		squField.setEditable(false);
		
		JSlider gainSlider = new JSlider();
		gainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				gainField.setText("" + gainSlider.getValue());
			}
		});
		gainSlider.setBackground(new Color(105, 105, 105));
		gainSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		
		JSlider volSlider = new JSlider();
		volSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				volField.setText("" + volSlider.getValue());
			}
		});
		volSlider.setBackground(new Color(105, 105, 105));
		volSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		JSlider squSlider = new JSlider();
		squSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				squField.setText("" + squSlider.getValue());
			}
		});
		squSlider.setBackground(new Color(105, 105, 105));
		squSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		JLabel volLabel = new JLabel("Volume: ");
		volLabel.setForeground(new Color(255, 250, 250));
		volLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		JLabel gainLabel = new JLabel("Gain:");
		gainLabel.setForeground(new Color(255, 250, 250));
		
		JLabel squLabel = new JLabel("Squelch:");
		squLabel.setForeground(new Color(255, 250, 250));
		
		JButton btnFrequency = new JButton("Frequency");
		btnFrequency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KeyPad keypad = new KeyPad();
				keypad.setVisible(true);
				
				 keypad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                freqDisplay.setText(keypad.returnFreq());
			            }
			            public void windowActivated(WindowEvent arg0) {
			            }
			            public void windowClosing(WindowEvent arg0) {
			            }
			            public void windowDeactivated(WindowEvent arg0) {
			            }
			            public void windowDeiconified(WindowEvent arg0) {
			            }
			            public void windowIconified(WindowEvent arg0) {
			            }
			            public void windowOpened(WindowEvent arg0) {
			            }
			        });
				
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(volSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(squSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(volLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(volField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addComponent(freqDisplay, Alignment.LEADING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(gainLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gainField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(squLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addComponent(gainSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnFrequency))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnFrequency)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(freqDisplay, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(volLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(volField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(volSlider, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(gainLabel)
								.addComponent(gainField, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gainSlider, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(squLabel)
								.addComponent(squField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6))))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
