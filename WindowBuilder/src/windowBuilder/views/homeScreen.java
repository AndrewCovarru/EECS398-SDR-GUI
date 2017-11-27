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
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

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
	
	//Creates CWRU Blue & Gray
	Color cwruBlue = new Color(10, 48, 78);
	Color cwruGray = new Color (106, 106, 106);
	
	private static homeScreen instance;
	
	public static homeScreen getInstance()
	{
	    return instance;
	}	

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		/*
		 * All of the JFormattedTextFields used throughout
		 */
		
		//Frequency
		JFormattedTextField freqDisplay = 	new JFormattedTextField();
		freqDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		freqDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 60));
		freqDisplay.setEditable(false);
		freqDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		freqDisplay.setForeground(Color.GREEN);
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
		ipDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		ipDisplay.setBackground(new Color(0, 0, 0));
		ipDisplay.setForeground(Color.GREEN);
		ipDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		ipDisplay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
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
		ppmDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		ppmDisplay.setBackground(new Color(0, 0, 0));
		ppmDisplay.setForeground(Color.GREEN);
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
		resampleDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		resampleDisplay.setBackground(new Color(0, 0, 0));
		resampleDisplay.setForeground(Color.GREEN);
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
		sampleDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		sampleDisplay.setBackground(new Color(0, 0, 0));
		sampleDisplay.setForeground(Color.GREEN);
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
		delayDisplay.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		delayDisplay.setBackground(new Color(0, 0, 0));
		delayDisplay.setForeground(Color.GREEN);
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
		gain = Integer.toString(gainSlider.getValue());
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
		volSlider.setMaximum(100);
		volume = Integer.toString(volSlider.getValue());
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
		squelch = Integer.toString(squSlider.getValue());
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
		rdbtnEDGE.setForeground(Color.GREEN);
		rdbtnEDGE.setBackground(new Color(0, 0, 0));
		enableOptionUiMatcher.add("edge", rdbtnEDGE);
		
		//DC
		JRadioButton rdbtnDC = new JRadioButton("dc");
		rdbtnDC.setBackground(new Color(0, 0, 0));
		rdbtnDC.setForeground(Color.GREEN);
	    enableOptionUiMatcher.add("dc", rdbtnDC);
		
	    //DEEMP
		JRadioButton rdbtnDEEMP = new JRadioButton("deemp");
		rdbtnDEEMP.setForeground(Color.GREEN);
		rdbtnDEEMP.setBackground(new Color(0, 0, 0));
        enableOptionUiMatcher.add("deemp", rdbtnDEEMP);
		
        //Direct
		JRadioButton rdbtnDIRECT = new JRadioButton("direct");
		rdbtnDIRECT.setForeground(Color.GREEN);
		rdbtnDIRECT.setBackground(new Color(0, 0, 0));
        enableOptionUiMatcher.add("direct", rdbtnDIRECT);
		
        //Offset
		JRadioButton rdbtnOFFSET = new JRadioButton("offset");
		rdbtnOFFSET.setForeground(Color.GREEN);
		rdbtnOFFSET.setBackground(new Color(0, 0, 0));
        enableOptionUiMatcher.add("offset", rdbtnOFFSET);
		
        Parameters.ENABLE_OPTION.setUiMembers(enableOptionUiMatcher, enableOptionUiMatcher.getClass());
        
        /*
         * Contains the JComboBoxes used throughout
         */
        
        //Modulation Mode
        JComboBox<String> modMode = new JComboBox<>();
        modMode.setFocusable(false);
        modMode.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
        modMode.setBackground(new Color(0, 0, 0));
        modMode.setForeground(Color.GREEN);
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
		firDrop.setFocusable(false);
		firDrop.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		firDrop.setBackground(new Color(0, 0, 0));
		firDrop.setForeground(Color.GREEN);
		firDrop.setMaximumRowCount(3);
		firDrop.setModel(new DefaultComboBoxModel(new String[] {"default", "0", "9"}));
		firDrop.setSelectedIndex(0);
		firSize = firDrop.getSelectedItem().toString();
		Parameters.FIR_SIZE.setUiMembers(firDrop, firDrop.getClass());
		firDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				firSize = firDrop.getSelectedItem().toString();
			}
		});
		
		//Atan Math
		JComboBox<String> atanMathDrop = new JComboBox<>();
		atanMathDrop.setFocusable(false);
		atanMathDrop.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		atanMathDrop.setBackground(new Color(0, 0, 0));
		atanMathDrop.setForeground(Color.GREEN);
		atanMathDrop.setModel(new DefaultComboBoxModel(new String[] {"std", "fast", "lut"}));
		atanMathDrop.setSelectedIndex(0);
		atanMathDrop.setMaximumRowCount(3);
		atanMath = atanMathDrop.getSelectedItem().toString();
		Parameters.ATAN_MATH.setUiMembers(atanMathDrop, atanMathDrop.getClass());
		atanMathDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atanMath = atanMathDrop.getSelectedItem().toString();
			}
		});
		
		JComboBox<String> oversampleDrop = new JComboBox<>();
		oversampleDrop.setFocusable(false);
		oversampleDrop.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		oversampleDrop.setBackground(new Color(0, 0, 0));
		oversampleDrop.setForeground(Color.GREEN);
		oversampleDrop.setMaximumRowCount(5);
		oversampleDrop.setModel(new DefaultComboBoxModel(new String[] {"default", "1", "2", "3", "4"}));
		overSampling = oversampleDrop.getSelectedItem().toString();
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
		stopButton.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		stopButton.setForeground(Color.GREEN);
		stopButton.setBackground(Color.BLACK);
		stopButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tcpClient.sendToServer("STOP");
			}
		});
		
		JButton defaultButton = new JButton("Defaults");
		defaultButton.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		defaultButton.setBackground(Color.BLACK);
		defaultButton.setForeground(Color.GREEN);
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
				volSlider.setValue(0);
				squSlider.setValue(0);
				gainSlider.setValue(0);
				
				//Radio Buttons
				rdbtnEDGE.setSelected(false);
				rdbtnDC.setSelected(false);
				rdbtnDEEMP.setSelected(false);
				rdbtnDIRECT.setSelected(false);
				rdbtnOFFSET.setSelected(false);
			}
		});
                
        //Execute
		JButton btnExecute = new JButton("Execute");
		btnExecute.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		btnExecute.setBackground(Color.BLACK);
		btnExecute.setForeground(Color.GREEN);
		btnExecute.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
				
				System.out.println("Atan Math: " + atanMath);
				Parameters.ATAN_MATH.append(atanMath);
				
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
				
				//System.out.println("Scannable Frequency: " + scannableFrequency);
				//Parameters.SCANNABLE_FREQUENCY.append(scannableFrequency);
				
				System.out.println("Squelch Delay: " + squelchDelay);
				Parameters.SQUELCH_DELAY.append(squelchDelay);
				
				System.out.println("Squelch: " + squelch);
				//Parameters.SQUELCH_LEVEL.append(squelch);
				
				System.out.println("Gain: "+ gain);
				//Parameters.TUNER_GAIN.append(gain);
				
				System.out.println("Volume:" + volume);
				//Parameters.VOLUME.append(volume);
				
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
		volLabel.setForeground(Color.GREEN);
		volLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		
		JLabel gainLabel = new JLabel("Gain:");
		gainLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		gainLabel.setForeground(Color.GREEN);
		
		JLabel squLabel = new JLabel("Squelch:");
		squLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 30));
		squLabel.setForeground(Color.GREEN);
		
		JLabel lblIPA = new JLabel("IP Address:");
		lblIPA.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblIPA.setForeground(Color.GREEN);
		lblIPA.setLabelFor(ipDisplay);
		lblIPA.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblModulation = new JLabel("ModMode:");
		lblModulation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblModulation.setForeground(Color.GREEN);
		lblModulation.setLabelFor(ipDisplay);
		lblModulation.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblAtanMath = new JLabel("Atan Math:");
		lblAtanMath.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblAtanMath.setForeground(Color.GREEN);
		lblAtanMath.setLabelFor(ipDisplay);
		lblAtanMath.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtanMath.setLabelFor(atanMathDrop);
		
		JLabel lblEnableOption = new JLabel("Enable Option:");
		lblEnableOption.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblEnableOption.setForeground(Color.GREEN);
		lblEnableOption.setLabelFor(ipDisplay);
		lblEnableOption.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblFirsize = new JLabel("FirSize:");
		lblFirsize.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblFirsize.setForeground(Color.GREEN);
		lblFirsize.setLabelFor(ipDisplay);
		lblFirsize.setHorizontalAlignment(SwingConstants.LEFT);
		lblFirsize.setLabelFor(firDrop);
		
		JLabel lblOversampling = new JLabel("Oversampling:");
		lblOversampling.setLabelFor(oversampleDrop);
		lblOversampling.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblOversampling.setForeground(Color.GREEN);
		lblOversampling.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		JLabel lblPpmError = new JLabel("PPM Error: ");
		lblPpmError.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblPpmError.setForeground(Color.GREEN);
		lblPpmError.setLabelFor(ipDisplay);
		lblPpmError.setHorizontalAlignment(SwingConstants.LEFT);
		lblPpmError.setLabelFor(ppmDisplay);
		
		JLabel lblResampleRate = new JLabel("Resample Rate:");
		lblResampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblResampleRate.setForeground(Color.GREEN);
		lblResampleRate.setLabelFor(ipDisplay);
		lblResampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		lblResampleRate.setLabelFor(resampleDisplay);
		
		JLabel lblSampleRate = new JLabel("Sample Rate:");
		lblSampleRate.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSampleRate.setForeground(Color.GREEN);
		lblSampleRate.setLabelFor(ipDisplay);
		lblSampleRate.setHorizontalAlignment(SwingConstants.LEFT);
		lblSampleRate.setLabelFor(sampleDisplay);
		
		JLabel lblSquelchDelay = new JLabel("<html>Squelch<br>Delay:</html>");
		lblSquelchDelay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		lblSquelchDelay.setForeground(Color.GREEN);
		lblSquelchDelay.setLabelFor(ipDisplay);
		lblSquelchDelay.setHorizontalAlignment(SwingConstants.LEFT);
		lblSquelchDelay.setLabelFor(delayDisplay);
				
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(volSlider, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
								.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(freqDisplay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
									.addComponent(gainSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblSampleRate)
										.addComponent(lblResampleRate, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPpmError)
										.addComponent(lblIPA)
										.addComponent(lblEnableOption)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(rdbtnDIRECT)
												.addGap(18)
												.addComponent(rdbtnEDGE))
											.addComponent(resampleDisplay, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
											.addComponent(ppmDisplay, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
									.addGap(6))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(ipDisplay, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
										.addComponent(sampleDisplay, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addGap(9))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(volLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(280))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(squSlider, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(squLabel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(55)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFirsize)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblAtanMath)
									.addComponent(lblModulation)
									.addComponent(lblOversampling, Alignment.TRAILING)
									.addComponent(lblSquelchDelay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
							.addGap(19)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(delayDisplay, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(oversampleDrop, Alignment.TRAILING, 0, 90, Short.MAX_VALUE)
								.addComponent(atanMathDrop, Alignment.TRAILING, 0, 90, Short.MAX_VALUE)
								.addComponent(firDrop, 0, 90, Short.MAX_VALUE)
								.addComponent(modMode, Alignment.TRAILING, 0, 90, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnDC)
									.addGap(33)
									.addComponent(rdbtnDEEMP)
									.addGap(18)
									.addComponent(rdbtnOFFSET))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(defaultButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
									.addComponent(btnExecute, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))))
					.addGap(51))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
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
									.addGap(29)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(oversampleDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblOversampling)
											.addGap(22))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblResampleRate)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(resampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(2))))
								.addComponent(sampleDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(15)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblPpmError)
										.addComponent(lblSquelchDelay)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(delayDisplay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(volLabel)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblIPA)
							.addGap(84)
							.addComponent(modMode, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(9)
							.addComponent(lblEnableOption)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnDIRECT)
								.addComponent(rdbtnDEEMP)
								.addComponent(rdbtnOFFSET)
								.addComponent(rdbtnDC)
								.addComponent(rdbtnEDGE))
							.addGap(30))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(volSlider, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(gainLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
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
					.addGap(187))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
