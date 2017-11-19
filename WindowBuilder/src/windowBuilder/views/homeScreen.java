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
		
		/*
		 * All of the JFormattedTextFields used throughout
		 */
		
		//Frequency
		JFormattedTextField freqDisplay = 	new JFormattedTextField();
		freqDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 60));
		freqDisplay.setEditable(false);
		freqDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		freqDisplay.setForeground(new Color(255, 250, 250));
		freqDisplay.setBackground(new Color(0, 0, 0));
		freqDisplay.setText("91100000");
		frequency = freqDisplay.getText();
		Parameters.FREQUENCY.setUiMembers(freqDisplay, freqDisplay.getClass());
		freqDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyPad freqKeyPad = new keyPad("Frequency: ", freqDisplay.getText());
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
		
		//IP Address
		JFormattedTextField ipDisplay = new JFormattedTextField();
		ipDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		ipDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		ipDisplay.setEditable(false);
		ipDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyPad ipKeyPad = new keyPad("IP Address:", ipDisplay.getText());
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
		
		//PPM Error
		JFormattedTextField ppmDisplay = new JFormattedTextField();
		ppmDisplay.setEditable(false);
		ppmDisplay.setText("default");
		ppmError = ppmDisplay.getText();
		Parameters.PPM_ERROR.setUiMembers(ppmDisplay, ppmDisplay.getClass());
		ppmDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad ppmKeyPad = new keyPad("PPM Error: ", ppmDisplay.getText());
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
		
		//Resample Rate 
		JFormattedTextField resampleDisplay = new JFormattedTextField();
		resampleDisplay.setEditable(false);
		resampleDisplay.setText("default");
		resampleRate = resampleDisplay.getText();
		Parameters.RESAMPLE_RATE.setUiMembers(resampleDisplay, resampleDisplay.getClass());
		resampleDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad resampleKeyPad = new keyPad("Resample Rate:", resampleDisplay.getText());
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
		
		//Sample Rate
		JFormattedTextField sampleDisplay = new JFormattedTextField();
		sampleDisplay.setEditable(false);
		sampleDisplay.setText("default");
		sampleRate = sampleDisplay.getText();
		Parameters.SAMPLE_RATE.setUiMembers(sampleDisplay, sampleDisplay.getClass());
		sampleDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad samplerateKeyPad = new keyPad("Sample Rate: ", sampleDisplay.getText());
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
		
		//Squelch Delay
		JFormattedTextField delayDisplay = new JFormattedTextField();
		delayDisplay.setEditable(false);
		delayDisplay.setText("default");
		squelchDelay = delayDisplay.getText();
		Parameters.SQUELCH_DELAY.setUiMembers(delayDisplay, delayDisplay.getClass());
		delayDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyPad delayKeyPad = new keyPad("Squelch Delay: ", delayDisplay.getText());
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
		
		
		/*
		 * Contains all of the JSliders used throughout
		 */
		
		//Gain
		JSlider gainSlider = new JSlider();
		gainSlider.setMaximum(50);
		Parameters.TUNER_GAIN.setUiMembers(gainSlider, gainSlider.getClass());
		gainSlider.setBackground(new Color(105, 105, 105));
		gainSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		gainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				gain = Integer.toString(gainSlider.getValue());
			}
		});
		
		//Volume
		JSlider volSlider = new JSlider();
		Parameters.VOLUME.setUiMembers(volSlider, volSlider.getClass());
		volSlider.setValue(100);
		volSlider.setBackground(new Color(105, 105, 105));
		volSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		volSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				volume = Integer.toString(volSlider.getValue());
			}
		});
		
		//Squelch
		JSlider squSlider = new JSlider();
		squSlider.setMaximum(500);
		Parameters.SQUELCH_LEVEL.setUiMembers(squSlider, squSlider.getClass());
		squSlider.setValue(0);
		squSlider.setBackground(new Color(105, 105, 105));
		squSlider.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		squSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				squelch = Integer.toString(squSlider.getValue());
			}
		});
		
		/*
		 * Contains all of the JRadioButtons used throughout
		 */
		
		//Edge
		JRadioButton rdbtnEDGE = new JRadioButton("edge");
		rdbtnEDGE.setForeground(Color.WHITE);
		rdbtnEDGE.setBackground(new Color(105, 105, 105));
		enableOptionUiMatcher.add("edge", rdbtnEDGE);
		
		//DC
		JRadioButton rdbtnDC = new JRadioButton("dc");
		rdbtnDC.setBackground(new Color(105, 105, 105));
		rdbtnDC.setForeground(Color.WHITE);
	    enableOptionUiMatcher.add("dc", rdbtnDC);
		
	    //DEEMP
		JRadioButton rdbtnDEEMP = new JRadioButton("deemp");
		rdbtnDEEMP.setForeground(Color.WHITE);
		rdbtnDEEMP.setBackground(new Color(105, 105, 105));
        enableOptionUiMatcher.add("deemp", rdbtnDEEMP);
		
        //Direct
		JRadioButton rdbtnDIRECT = new JRadioButton("direct");
		rdbtnDIRECT.setForeground(Color.WHITE);
		rdbtnDIRECT.setBackground(new Color(105, 105, 105));
        enableOptionUiMatcher.add("direct", rdbtnDIRECT);
		
        //Offset
		JRadioButton rdbtnOFFSET = new JRadioButton("offset");
		rdbtnOFFSET.setForeground(Color.WHITE);
		rdbtnOFFSET.setBackground(new Color(105, 105, 105));
        enableOptionUiMatcher.add("offset", rdbtnOFFSET);
		
        Parameters.ENABLE_OPTION.setUiMembers(enableOptionUiMatcher, enableOptionUiMatcher.getClass());
        
        /*
         * Contains the JComboBoxes used throughout
         */
        
        //Modulation Mode
        JComboBox<String> modMode = new JComboBox<>();
		modMode.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		modMode.setModel(new DefaultComboBoxModel(new String[] {"fm", "wbfm", "raw", "am", "usb", "lsb"}));
		modMode.setMaximumRowCount(6);
		modMode.setSelectedItem(modMode.getItemAt(0));
		Parameters.MODULATION_MODE.setUiMembers(modMode, modMode.getClass());
		modulationMode = modMode.getSelectedItem().toString();
		modMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modulationMode = modMode.getSelectedItem().toString();
			}
		});
		
		//FIR Size
		JComboBox<String> firDrop = new JComboBox<>();
		firDrop.setMaximumRowCount(3);
		firDrop.setModel(new DefaultComboBoxModel(new String[] {"default", "0", "9"}));
		firDrop.setSelectedIndex(0);
		Parameters.FIR_SIZE.setUiMembers(firDrop, firDrop.getClass());
		firDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				firSize = firDrop.getSelectedItem().toString();
			}
		});
		
		//Atan Math
		JComboBox<String> atanMathDrop = new JComboBox<>();
		atanMathDrop.setModel(new DefaultComboBoxModel(new String[] {"std", "fast", "lut"}));
		atanMathDrop.setMaximumRowCount(3);
		Parameters.ATAN_MATH.setUiMembers(atanMathDrop, atanMathDrop.getClass());
		atanMathDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atanMath = atanMathDrop.getSelectedItem().toString();
			}
		});
		
		JComboBox oversampleDrop = new JComboBox();
		oversampleDrop.setMaximumRowCount(5);
		oversampleDrop.setModel(new DefaultComboBoxModel(new String[] {"default", "1", "2", "3", "4"}));
		Parameters.OVERSAMPLING.setUiMembers(oversampleDrop, oversampleDrop.getClass());
		oversampleDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overSampling = oversampleDrop.getSelectedItem().toString();
			}
		});
        
        
        /*
         * Contains the two JButtons used throughout. Execute and Stop
         */
		
		//Stop
		JButton stopButton = new JButton("Stop");
		stopButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tcpClient.sendToServer("STOP");
			}
		});
		
		JButton defaultButton = new JButton("Defaults");
		defaultButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Text Boxes
				freqDisplay.setText("91100000");
				frequency = freqDisplay.getText();
				
				sampleDisplay.setText("default");
				sampleRate = sampleDisplay.getText();
				
				resampleDisplay.setText("default");
				resampleRate = resampleDisplay.getText();
				
				ppmDisplay.setText("default");
				ppmError = ppmDisplay.getText();
				
				delayDisplay.setText("default");
				squelchDelay = delayDisplay.getText();
				
				
				//Drop Downs
				modMode.setSelectedItem("fm");
				modulationMode = modMode.getSelectedItem().toString();
				
				firDrop.setSelectedItem("default");
				firSize = firDrop.getSelectedItem().toString();
				
				atanMathDrop.setSelectedItem("std");
				atanMath = atanMathDrop.getSelectedItem().toString();
				
				oversampleDrop.setSelectedItem("default");
				overSampling = oversampleDrop.getSelectedItem().toString();
				
				//Sliders
				
			}
		});
                
        //Execute
		JButton btnExecute = new JButton("Execute");
		btnExecute.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Atan Math: " + atanMath);
				Parameters.ATAN_MATH.append(atanMath);
				
				//Checks each of the radio buttons
				if(rdbtnEDGE.isSelected())
					Parameters.ENABLE_OPTION.append("edge");
				if(rdbtnDC.isSelected())
					Parameters.ENABLE_OPTION.append("dc");
				if(rdbtnDEEMP.isSelected())
					Parameters.ENABLE_OPTION.append("deemp");
				if(rdbtnDIRECT.isSelected())
					Parameters.ENABLE_OPTION.append("direct");
				if(rdbtnDIRECT.isSelected())
					Parameters.ENABLE_OPTION.append("offset");
				
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
		            p.resetValues();
		        }
				tcpClient.sendToServer("EXECUTE");	
			}
		});
		
		/*
		 * Contains all of the labels used throughout
		 */
		
		JLabel volLabel = new JLabel("Volume: ");
		volLabel.setForeground(new Color(255, 250, 250));
		volLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		
		JLabel gainLabel = new JLabel("Gain:");
		gainLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		gainLabel.setForeground(new Color(255, 250, 250));
		
		JLabel squLabel = new JLabel("Squelch:");
		squLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		squLabel.setForeground(new Color(255, 250, 250));
		
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
		lblFirsize.setLabelFor(firDrop);
		
		JLabel lblOversampling = new JLabel("Oversampling:");
		lblOversampling.setLabelFor(oversampleDrop);
		lblOversampling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblOversampling.setForeground(Color.WHITE);
		lblOversampling.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		JLabel lblPpmError = new JLabel("PPM Error: ");
		lblPpmError.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblPpmError.setForeground(Color.WHITE);
		lblPpmError.setLabelFor(ipDisplay);
		lblPpmError.setHorizontalAlignment(SwingConstants.LEFT);
		lblPpmError.setLabelFor(ppmDisplay);
		
		JLabel lblResampleRate = new JLabel("Resample Rate:");
		lblResampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblResampleRate.setForeground(Color.WHITE);
		lblResampleRate.setLabelFor(ipDisplay);
		lblResampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		lblResampleRate.setLabelFor(resampleDisplay);
		
		JLabel lblSampleRate = new JLabel("Sample Rate:");
		lblSampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSampleRate.setForeground(Color.WHITE);
		lblSampleRate.setLabelFor(ipDisplay);
		lblSampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		lblSampleRate.setLabelFor(sampleDisplay);
		
		JLabel lblSquelchDelay = new JLabel("<html>Squelch<br>Delay:</html>");
		lblSquelchDelay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSquelchDelay.setForeground(Color.WHITE);
		lblSquelchDelay.setLabelFor(ipDisplay);
		lblSquelchDelay.setHorizontalAlignment(SwingConstants.LEFT);
		lblSquelchDelay.setLabelFor(delayDisplay);
				
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
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSampleRate)
										.addComponent(lblResampleRate, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPpmError)
										.addComponent(lblIPA)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(ppmDisplay, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(rdbtnDIRECT)
														.addGap(18))
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblEnableOption)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addComponent(rdbtnEDGE))
											.addComponent(resampleDisplay, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
									.addGap(6))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(ipDisplay, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
										.addComponent(sampleDisplay, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addGap(9))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(volLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addGap(280))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
									.addGap(332))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(squLabel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
								.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(defaultButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnExecute))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(modMode, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(55)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(lblAtanMath)
													.addComponent(lblModulation)
													.addComponent(lblOversampling, Alignment.TRAILING)
													.addComponent(lblSquelchDelay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(delayDisplay, 79, 91, Short.MAX_VALUE))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblFirsize)
												.addGap(57)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(oversampleDrop, Alignment.TRAILING, 0, 76, Short.MAX_VALUE)
													.addComponent(atanMathDrop, Alignment.TRAILING, 0, 76, Short.MAX_VALUE)
													.addComponent(firDrop, 0, 76, Short.MAX_VALUE)))))))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(32)
								.addComponent(rdbtnDC)
								.addGap(18)
								.addComponent(rdbtnDEEMP)
								.addGap(18)
								.addComponent(rdbtnOFFSET))))
					.addGap(51))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(freqDisplay, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(2)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(atanMathDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblAtanMath))
											.addGap(17)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblFirsize)
												.addComponent(firDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(11)
											.addComponent(lblModulation)
											.addGap(4))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(27)
											.addComponent(ipDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(lblSampleRate)
											.addGap(12)))
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(modMode, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
											.addGap(22)
											.addComponent(oversampleDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblOversampling)
											.addGap(22))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblResampleRate)
											.addGap(28))))
								.addComponent(sampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(6)
										.addComponent(volLabel))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(15)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblPpmError)
											.addComponent(lblSquelchDelay))))
								.addComponent(delayDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblIPA)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(resampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(45)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblEnableOption)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnDIRECT))
								.addComponent(rdbtnEDGE)
								.addComponent(rdbtnDC)
								.addComponent(rdbtnDEEMP)
								.addComponent(rdbtnOFFSET)))
						.addComponent(volSlider, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(gainSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExecute, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(defaultButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ppmDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(173))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
