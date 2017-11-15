package windowBuilder.views;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox; 
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.UnknownHostException;
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

import com.bensherman.rtlsdrdjava.tcpcli.TcpClient;
import javax.swing.JEditorPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import com.bensherman.rtlsdrdjava.tcpcli.TcpClient;

public class homeScreen extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	private TcpClient tcpClient;
	private Thread tcpClientThread; 
	private ResponseListener listener;
	private Thread listenerThread;
	private final EnableOptionUiMatcher enableOptionUiMatcher = new EnableOptionUiMatcher();

	
	//Creates String for execute method
	private String atanMath;
	private String firSize;
	private String frequency;
	private String modulationMode;
	private String overSampling;
	private String ppmError;
	private String resampleRate;
	private String sampleRate;	
	private String scannableFrequency;
	private String squelchDelay;
	private String squelch;
	private String gain;
	private String volume;
	
	private static homeScreen instance;
	
	public static homeScreen getInstance()
	{
	    return instance;
	}
	
	//Associate Parameters with UI Members
	//Parameters.FREQUENCY.setUiMembers(freqDisplay, freqDisplay.getClass());
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homeScreen frame = new homeScreen();
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
	public homeScreen() {
	        instance = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JFormattedTextField freqDisplay = 	new JFormattedTextField();
		freqDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 60));
		freqDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyPad freqKeyPad = new keyPad("Frequency: ");
				freqKeyPad.setVisible(true);
				
				 freqKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                freqDisplay.setText(freqKeyPad.returnField());
			               frequency = freqDisplay.getText();
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
		freqDisplay.setEditable(false);
		freqDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		freqDisplay.setForeground(new Color(255, 250, 250));
		freqDisplay.setBackground(new Color(0, 0, 0));
		freqDisplay.setText("Frequency");
		Parameters.FREQUENCY.setUiMembers(freqDisplay, freqDisplay.getClass());
		
		
		JFormattedTextField gainField = new JFormattedTextField();
		gainField.setHorizontalAlignment(SwingConstants.CENTER);
		gainField.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 35));
		gainField.setEditable(false);
		Parameters.TUNER_GAIN.setUiMembers(gainField, gainField.getClass());
		
		JFormattedTextField volField = new JFormattedTextField();
		volField.setHorizontalAlignment(SwingConstants.CENTER);
		volField.setFont(volField.getFont().deriveFont(volField.getFont().getSize() + 24f));
		volField.setEditable(false);
		Parameters.VOLUME.setUiMembers(volField, volField.getClass());
		
		JFormattedTextField squField = new JFormattedTextField();
		squField.setHorizontalAlignment(SwingConstants.CENTER);
		squField.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 35));
		squField.setEditable(false);
		Parameters.SQUELCH_LEVEL.setUiMembers(squField, squField.getClass());
		
		JSlider gainSlider = new JSlider();
		gainField.setText("50");
		gain = gainField.getText();
		Parameters.TUNER_GAIN.setUiMembers(gainSlider, gainSlider.getClass());
		gainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double percentage = (double)gainSlider.getValue()/100;
				double gainPercent = Math.round((49.6 * percentage) * 100.0) / 100.0; 
				gainField.setText("" + gainPercent);
				gain = gainField.getText();
			}
		});
		gainSlider.setBackground(new Color(105, 105, 105));
		gainSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		
		JSlider volSlider = new JSlider();
		volSlider.setValue(100);
		volField.setText("100");
		volume = volField.getText();
		Parameters.VOLUME.setUiMembers(volField, volField.getClass());
		volSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				volField.setText("" + volSlider.getValue());
				volume = volField.getText();
			}
		});
		volSlider.setBackground(new Color(105, 105, 105));
		volSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		JSlider squSlider = new JSlider();
		squSlider.setValue(0);
		squField.setText("0");
		squelch = squField.getText();
		squSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				squField.setText("" + squSlider.getValue());
				squelch = squField.getText();
			}
		});
		squSlider.setBackground(new Color(105, 105, 105));
		squSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		
		JLabel volLabel = new JLabel("Volume: ");
		volLabel.setLabelFor(volField);
		volLabel.setForeground(new Color(255, 250, 250));
		volLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		
		JLabel gainLabel = new JLabel("Gain:");
		gainLabel.setLabelFor(gainField);
		gainLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		gainLabel.setForeground(new Color(255, 250, 250));
		
		JLabel squLabel = new JLabel("Squelch:");
		squLabel.setLabelFor(squField);
		squLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		squLabel.setForeground(new Color(255, 250, 250));
		freqDisplay.setText("91100000");
		frequency = freqDisplay.getText();
		
		JFormattedTextField ipDisplay = new JFormattedTextField();
		ipDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		ipDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyPad ipKeyPad = new keyPad("IP Address:");
				ipKeyPad.setVisible(true);
				
				 ipKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                ipDisplay.setText(ipKeyPad.returnField());
			            	 try {
			            	                tcpClient = new TcpClient(ipKeyPad.returnField(), TcpClient.RTLSDRD_DEFAULT_TCP_PORT_NUMBER);
					                tcpClientThread = new Thread(tcpClient);
					                tcpClientThread.start();
					                listener = new ResponseListener(tcpClient, instance);
					                listenerThread = new Thread(listener);
					                listenerThread.start();
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
		ipDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		ipDisplay.setEditable(false);
		
		JRadioButton rdbtnEDGE = new JRadioButton("edge");
		enableOptionUiMatcher.add("edge", rdbtnEDGE);
		
		JRadioButton rdbtnDC = new JRadioButton("dc");
	        enableOptionUiMatcher.add("dc", rdbtnDC);
		
		JRadioButton rdbtnDEEMP = new JRadioButton("deemp");
                enableOptionUiMatcher.add("deemp", rdbtnDEEMP);
		
		JRadioButton rdbtnDIRECT = new JRadioButton("direct");
                enableOptionUiMatcher.add("direct", rdbtnDIRECT);
		
		JRadioButton rdbtnOFFSET = new JRadioButton("offset");
                enableOptionUiMatcher.add("offset", rdbtnOFFSET);
		
                Parameters.ENABLE_OPTION.setUiMembers(enableOptionUiMatcher, enableOptionUiMatcher.getClass());
                
		JButton btnExecute = new JButton("Execute");
		btnExecute.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Atan Math: " + atanMath);
				Parameters.ATAN_MATH.append(atanMath);
				
//				//Checks each of the radio buttons
//				if(rdbtnEDGE.isSelected())
//					Parameters.ENABLE_OPTION.append("edge");
//				if(rdbtnDC.isSelected())
//					Parameters.ENABLE_OPTION.append("dc");
//				if(rdbtnDEEMP.isSelected())
//					Parameters.ENABLE_OPTION.append("deemp");
//				if(rdbtnDIRECT.isSelected())
//					Parameters.ENABLE_OPTION.append("direct");
//				if(rdbtnDIRECT.isSelected())
//					Parameters.ENABLE_OPTION.append("offset");
				
				System.out.println("FIR Size: " + firSize);
				Parameters.FIR_SIZE.append(firSize);
				
				System.out.println("Frequency:" + frequency);
				Parameters.FREQUENCY.append(frequency);
				
				System.out.println("Modulation Mode: " + modulationMode);
				Parameters.MODULATION_MODE.append(modulationMode);
				
				System.out.println("Oversampling: " + overSampling);
				Parameters.OVERSAMPLING.append(overSampling);
				
				System.out.println("PPM Error: " + ppmError);
				Parameters.PPM_ERROR.append(ppmError);
				
				System.out.println("Resample Rate: " + resampleRate);
				Parameters.RESAMPLE_RATE.append(resampleRate);
				
				System.out.println("Sample Rate: " + sampleRate);
				Parameters.SAMPLE_RATE.append(sampleRate);
				
				System.out.println("Scannable Frequency: " + scannableFrequency);
				Parameters.SCANNABLE_FREQUENCY.append(scannableFrequency);
				
				System.out.println("Squelch Delay: " + squelchDelay);
				Parameters.SQUELCH_DELAY.append(squelchDelay);
				
				System.out.println("Squelch: " + squelch);
				Parameters.SQUELCH_LEVEL.append(squelch);
				
				System.out.println("Gain: "+ gain);
				Parameters.TUNER_GAIN.append(gain);
				
				System.out.println("Volume:" + volume);
				Parameters.VOLUME.append(volume);
				
				for (Parameters p : Parameters.values())
		        {
		            for(String s : p.getDameonCallableStrings()){

		                tcpClient.sendToServer(s);
		            }
		           // MainActivity.getTcpClient().sendToServer("EXECUTE");
		            p.resetValues();
		        }
				tcpClient.sendToServer("EXECUTE");	
			}
		});
		
		JComboBox modMode = new JComboBox();
		modMode.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		modMode.setModel(new DefaultComboBoxModel(new String[] {"fm", "wbfm", "raw", "am", "usb", "lsb"}));
		modMode.setMaximumRowCount(6);
		modMode.setSelectedItem(modMode.getItemAt(0));
		modulationMode = modMode.getSelectedItem().toString();
		modMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modulationMode = modMode.getSelectedItem().toString();
			}
		});
		
		JLabel lblIPA = new JLabel("IP Address:");
		lblIPA.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblIPA.setForeground(Color.WHITE);
		lblIPA.setLabelFor(ipDisplay);
		lblIPA.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblModulation = new JLabel("ModMode:");
		lblModulation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblModulation.setForeground(Color.WHITE);
		lblModulation.setLabelFor(ipDisplay);
		lblModulation.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblAtanMath = new JLabel("Atan Math:");
		lblAtanMath.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblAtanMath.setForeground(Color.WHITE);
		lblAtanMath.setLabelFor(ipDisplay);
		lblAtanMath.setHorizontalAlignment(SwingConstants.LEFT);
		
		JComboBox atanMathDrop = new JComboBox();
		atanMathDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atanMath = atanMathDrop.getSelectedItem().toString();
			}
		});
		atanMathDrop.setModel(new DefaultComboBoxModel(new String[] {"std", "fast", "lut"}));
		atanMathDrop.setMaximumRowCount(3);
		lblAtanMath.setLabelFor(atanMathDrop);
		
		JLabel lblEnableOption = new JLabel("Enable Option:");
		lblEnableOption.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblEnableOption.setForeground(Color.WHITE);
		lblEnableOption.setLabelFor(ipDisplay);
		lblEnableOption.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblFirsize = new JLabel("FirSize:");
		lblFirsize.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblFirsize.setForeground(Color.WHITE);
		lblFirsize.setLabelFor(ipDisplay);
		lblFirsize.setHorizontalAlignment(SwingConstants.LEFT);
		
		JComboBox firDrop = new JComboBox();
		firDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				firSize = firDrop.getSelectedItem().toString();
			}
		});
		firDrop.setMaximumRowCount(3);
		firDrop.setModel(new DefaultComboBoxModel(new String[] {"-1", "0", "9"}));
		firDrop.setSelectedIndex(0);
		lblFirsize.setLabelFor(firDrop);
		
		JLabel lblOversampling = new JLabel("Oversampling:");
		lblOversampling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblOversampling.setForeground(Color.WHITE);
		lblOversampling.setLabelFor(ipDisplay);
		lblOversampling.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		JLabel lblPpmError = new JLabel("PPM Error: ");
		lblPpmError.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblPpmError.setForeground(Color.WHITE);
		lblPpmError.setLabelFor(ipDisplay);
		lblPpmError.setHorizontalAlignment(SwingConstants.LEFT);
		
		JFormattedTextField oversamplingDisplay = new JFormattedTextField();
		oversamplingDisplay.setEditable(false);
		oversamplingDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyPad oversamplingKeyPad = new keyPad("Oversampling:");
				oversamplingKeyPad.setVisible(true);
				
				 oversamplingKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                oversamplingDisplay.setText(oversamplingKeyPad.returnField());
			               overSampling = oversamplingDisplay.getText();
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
		lblOversampling.setLabelFor(oversamplingDisplay);
		
		JFormattedTextField ppmDisplay = new JFormattedTextField();
		ppmDisplay.setEditable(false);
		ppmDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad ppmKeyPad = new keyPad("PPM Error: ");
				ppmKeyPad.setVisible(true);
				
				 ppmKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                ppmDisplay.setText(ppmKeyPad.returnField());
			               ppmError = ppmDisplay.getText();
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
		lblPpmError.setLabelFor(ppmDisplay);
		
		JLabel lblResampleRate = new JLabel("Resample Rate:");
		lblResampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblResampleRate.setForeground(Color.WHITE);
		lblResampleRate.setLabelFor(ipDisplay);
		lblResampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		
		JFormattedTextField resampleDisplay = new JFormattedTextField();
		resampleDisplay.setEditable(false);
		resampleDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad resampleKeyPad = new keyPad("Resample Rate:");
				resampleKeyPad.setVisible(true);
				
				 resampleKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                resampleDisplay.setText(resampleKeyPad.returnField());
			               resampleRate = resampleDisplay.getText();
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
		lblResampleRate.setLabelFor(resampleDisplay);
		
		JLabel lblSampleRate = new JLabel("Sample Rate:");
		lblSampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSampleRate.setForeground(Color.WHITE);
		lblSampleRate.setLabelFor(ipDisplay);
		lblSampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		JFormattedTextField sampleDisplay = new JFormattedTextField();
		sampleDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad samplerateKeyPad = new keyPad("Sample Rate: ");
				samplerateKeyPad.setVisible(true);
				
				 samplerateKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                sampleDisplay.setText(samplerateKeyPad.returnField());
			               sampleRate = sampleDisplay.getText();
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
		sampleDisplay.setEditable(false);
		lblSampleRate.setLabelFor(sampleDisplay);
		
		JLabel lblScannableFrequency = new JLabel("<html>Scannable<br>Frequency:</html>");
		lblScannableFrequency.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblScannableFrequency.setForeground(Color.WHITE);
		lblScannableFrequency.setLabelFor(ipDisplay);
		lblScannableFrequency.setHorizontalAlignment(SwingConstants.LEFT);
		
		JFormattedTextField scannableDisplay = new JFormattedTextField();
		scannableDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad scannableKeyPad = new keyPad("Scannable: ");
				scannableKeyPad.setVisible(true);
				
				 scannableKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			                scannableDisplay.setText(scannableKeyPad.returnField());
			               scannableFrequency = scannableDisplay.getText();
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
		scannableDisplay.setEditable(false);
		lblScannableFrequency.setLabelFor(scannableDisplay);
		
		JLabel lblSquelchDelay = new JLabel("<html>Squelch<br>Delay:</html>");
		lblSquelchDelay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSquelchDelay.setForeground(Color.WHITE);
		lblSquelchDelay.setLabelFor(ipDisplay);
		lblSquelchDelay.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		JFormattedTextField delayDisplay = new JFormattedTextField();
		delayDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad delayKeyPad = new keyPad("Squelch Delay: ");
				delayKeyPad.setVisible(true);
				
				 delayKeyPad.addWindowListener(new WindowListener() {
			            public void windowClosed(WindowEvent arg0) {
			            		delayDisplay.setText(delayKeyPad.returnField());
			               squelchDelay = delayDisplay.getText();
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
		delayDisplay.setEditable(false);
		lblSquelchDelay.setLabelFor(delayDisplay);
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tcpClient.sendToServer("STOP");
				
				
			}
		});
		
		stopButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		
//		
//		//JComboBox
//		Parameters.ATAN_MATH.setUiMembers(atanMathDrop, atanMathDrop.getClass());
//		Parameters.FIR_SIZE.setUiMembers(firDrop, firDrop.getClass());
//		Parameters.MODULATION_MODE.setUiMembers(modMode, modMode.getClass());
//		
//		//JSliders
//		Parameters.VOLUME.setUiMembers(volSlider, volSlider.getClass());
//		Parameters.TUNER_GAIN.setUiMembers(gainSlider, gainSlider.getClass());
//		Parameters.SQUELCH_LEVEL.setUiMembers(squSlider, squSlider.getClass());
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(volSlider, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(freqDisplay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
									.addComponent(gainSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblResampleRate, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOversampling)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblIPA)
										.addComponent(lblScannableFrequency))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(scannableDisplay, 101, 101, 101)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(ipDisplay, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
											.addComponent(resampleDisplay, Alignment.TRAILING)
											.addComponent(oversamplingDisplay, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblEnableOption)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(rdbtnEDGE)
												.addComponent(rdbtnDC)
												.addComponent(rdbtnDEEMP))
											.addGap(4)))
									.addGap(7)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(rdbtnDIRECT)
										.addComponent(rdbtnOFFSET)))
								.addComponent(stopButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(squLabel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(volLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(volField, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
									.addGap(180))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(gainField, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addGap(250))))
						.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnExecute)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSquelchDelay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSampleRate)
										.addComponent(lblAtanMath)
										.addComponent(lblModulation))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(ppmDisplay, Alignment.TRAILING)
										.addComponent(sampleDisplay, Alignment.TRAILING)
										.addComponent(modMode, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
										.addComponent(delayDisplay, Alignment.TRAILING, 79, 79, Short.MAX_VALUE)))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblFirsize)
									.addGap(57)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(atanMathDrop, Alignment.TRAILING, 0, 57, Short.MAX_VALUE)
										.addComponent(firDrop, 0, 57, Short.MAX_VALUE))))
							.addGap(51))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPpmError)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(freqDisplay, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(atanMathDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAtanMath))
									.addGap(17)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblFirsize)
										.addComponent(firDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(11)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(oversamplingDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblModulation))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(4)
											.addComponent(lblResampleRate)
											.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(resampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(ppmDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(5))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(21)
											.addComponent(lblPpmError)
											.addPreferredGap(ComponentPlacement.RELATED))))
								.addComponent(modMode, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(volField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addComponent(volLabel)
										.addComponent(sampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(scannableDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblScannableFrequency, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSampleRate)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIPA)
								.addComponent(ipDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(lblOversampling)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(gainField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblEnableOption)
										.addComponent(lblSquelchDelay)
										.addComponent(volSlider, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
									.addGap(10))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(rdbtnEDGE)
									.addComponent(rdbtnDIRECT)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnDC)
								.addComponent(rdbtnOFFSET)))
						.addComponent(delayDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(15)
							.addComponent(gainSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(squLabel)
								.addComponent(squField, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnDEEMP)
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnExecute, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
