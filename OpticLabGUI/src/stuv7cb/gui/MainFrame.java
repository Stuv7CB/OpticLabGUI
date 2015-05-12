package stuv7cb.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7814866059557571520L;
	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Dimension screenSize=kit.getScreenSize();
	final int WIDTH=screenSize.width/2;
	final int HEIGHT=screenSize.height/2;
	private final String TITLE="Оптическая лаборатория";
	private MainPanel mainPanel =new MainPanel();
	private SpringLayout springLayout;
	private JPanel panel;
	private JButton clean=new JButton("Очистить");
	public MainFrame()
	{
		setMinimumSize(new Dimension(349, 278));
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, getContentPane());
		getContentPane().setLayout(springLayout);
		panel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel, 137, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel, -35, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, getContentPane());
		ButtonRun br=new ButtonRun("Начать");
		clean.setVisible(false);
		clean.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainPanel.clean();
				mainPanel.updateUI();
				addListeners();
				clean.setVisible(false);
			}
			
		});
		panel.add(clean);
		panel.add(br);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		getContentPane().add(panel);
		br.addClient();
		mainPanel.setLayout(null);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		getContentPane().add(mainPanel);
		/*
		LabelLense ll1=new LabelLense(new Point(50,100),100,50,0);
		LabelLense ll2=new LabelLense(new Point(125,100),100,100,0);
		LabelLense ll3=new LabelLense(new Point(200,100), 100, -25, 0);
		LabelSource ls=new LabelSource(new Point(0,100));
		LabelDisplay lw=new LabelDisplay(new Point(300,100), 100, 0);
		mainPanel.add(ls);
		mainPanel.add(ll1);
		mainPanel.add(ll2);
		mainPanel.add(ll3);
		mainPanel.add(lw);
		*/
		addMenuBar();
		addPanelOfSelection();
	}
	private void addPanelOfSelection()
	{
		JPanel panelSelection = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, panelSelection, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panelSelection, 0, SpringLayout.WEST, mainPanel);
		springLayout.putConstraint(SpringLayout.NORTH, panelSelection, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panelSelection, 0, SpringLayout.SOUTH, getContentPane());
		final SelectionLabel[] selectionLabel = new SelectionLabel[9];
		panelSelection.setLayout(null);
		SelectionLabel.parent=this;
		for(int i=0;i<9;i++)
		{
			selectionLabel[i]=new SelectionLabel(i);
			selectionLabel[i].addMouseListener(selectionLabel[i].new MyMouseListener());
			selectionLabel[i].setLocation(0, i*20);
			panelSelection.add(selectionLabel[i]);
		}
		panelSelection.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		getContentPane().add(panelSelection);
	}
	void clean()
	{
		mainPanel.clean();
	}
	void mainPaneladd(JComponent comp)
	{
		mainPanel.add(comp);
	}
	Component[] getComponentsofMainPanel()
	{
		return mainPanel.getComponents();
	}
	void removeListeners()
	{
		Component[] labelObjects=getComponentsofMainPanel();
		for(int i=0; i<labelObjects.length;i++)
		{
			((LabelObject)labelObjects[i]).removeMouseControl();
		}
	}
	void addListeners()
	{
		Component[] labelObjects=getComponentsofMainPanel();
		for(int i=0; i<labelObjects.length;i++)
		{
			((LabelObject)labelObjects[i]).addMouseControl();
		}
	}
	void mainPanelPaint(String line)
	{
		mainPanel.paintNewLine(line);
		mainPanel.updateUI();
	}
	private void addMenuBar()
	{
		MainFrame mainFrame=this;
		JMenuBar menuBar=new JMenuBar();
		JMenu file=new JMenu("Файл");
		menuBar.add(file);
		JMenuItem newOne=new JMenuItem("Новый");
		newOne.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		newOne.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Component[] component=getComponentsofMainPanel();
				for(int i=0; i<component.length; i++)
				{
					mainPanel.remove(component[i]);
				}
				mainPanel.clean();
				clean.setVisible(false);
				mainPanel.updateUI();
			}
			
		});
		file.add(newOne);
		JMenuItem open=new JMenuItem("Открыть");
		open.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Component[] component=getComponentsofMainPanel();
				for(int i=0; i<component.length; i++)
				{
					mainPanel.remove(component[i]);
				}
				mainPanel.updateUI();
				JFileChooser fileChooser=new JFileChooser();
				FileNameExtensionFilter fef=new FileNameExtensionFilter("Save file", "svo");
				fileChooser.setFileFilter(fef);
				int ret=fileChooser.showOpenDialog(mainPanel);
				if(ret==JFileChooser.CANCEL_OPTION||ret==JFileChooser.ERROR_OPTION)
				{
					System.out.println("File wasn't open");
				}
				else
				{
					File file=fileChooser.getSelectedFile();
					try
					{
						Scanner scanner=new Scanner(file);
						while(scanner.hasNextLine())
						{
							String line=scanner.nextLine();
							Scanner lineScanner=new Scanner(line);
							lineScanner.useLocale(Locale.US);
							int ID=lineScanner.nextInt();
							ArrayList<Double> params = new ArrayList<Double>();
							while(lineScanner.hasNext())
							{
								if(lineScanner.hasNextDouble())
								{
									params.add(lineScanner.nextDouble());
								}
								else
								{
									lineScanner.next();
								}
							}
							int i=0;
							try
							{
								switch(ID)
								{
								case 2:
					
								{
								
									Point p=new Point();

									p.setLocation(params.get(i++), params.get(i++));
									LabelLense label=new LabelLense(p, params.get(i++), params.get(i++), params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								
								case 0:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelSource label=new LabelSource(p);
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 1:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelDisplay label=new LabelDisplay(p,params.get(i++),params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 3:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelMirror label=new LabelMirror(p, params.get(i++),params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 4:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelPlate label=new LabelPlate(p, params.get(i++),params.get(i++),params.get(i++),params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 5:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelLaser label=new LabelLaser(p, params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 6:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelPrism label=new LabelPrism(p, params.get(i++),params.get(i++),params.get(i++),params.get(i++),params.get(i++),params.get(i++),params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
									break;
								}
								case 7:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelSphereMirror label=new LabelSphereMirror(p, params.get(i++),params.get(i++),params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
								}
								case 8:
								{
									Point p=new Point();
									p.setLocation(params.get(i++),params.get(i++));
									LabelRealLense label=new LabelRealLense(p, params.get(i++), params.get(i++), params.get(i++), params.get(i++), params.get(i++), params.get(i++));
									label.addMouseControl();
									mainPaneladd(label);
									label.addPopup();
									label.updateUI();
								}
								}
							}
							catch(NullPointerException npe)
							{
								System.err.println("Something wrong in savefile");
								JOptionPane.showMessageDialog(mainFrame, "Something wrong in savefile");
							}
							finally
							{
								lineScanner.close();
							}
						}
						scanner.close();
					}
					catch (FileNotFoundException fnfe)
					{
						System.err.println("Couldn't find file!");
						JOptionPane.showMessageDialog(mainFrame, "Couldn't find file!");
					}
					catch (InputMismatchException ime)
					{
						System.err.println("Something wrong in savefile");
						JOptionPane.showMessageDialog(mainFrame, "Something wrong in savefile");
					}
				}
			}			
		});
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		file.add(open);
		JMenuItem save=new JMenuItem("Сохранить");
		save.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setSelectedFile(new File("save.svo"));
				fileChooser.showSaveDialog(mainPanel);
				File file=fileChooser.getSelectedFile();
				if(!file.getAbsolutePath().endsWith(".svo") )
				{
	                file = new File(file.getAbsolutePath() + ".svo");
				}
				try
				{
					PrintWriter out=new PrintWriter(file.getAbsoluteFile());
					Component[] component=getComponentsofMainPanel();
					for(int i=0; i<component.length; i++)
					{
						out.println(((LabelObject)component[i]).getID()+" "+((LabelObject)component[i]).getParams());
					}
					out.close();
				}
				catch (FileNotFoundException fnfe)
				{
					System.err.println("Couldn't find file!");
					JOptionPane.showMessageDialog(mainFrame, "Couldn't find file!");
				}
			}
			
		});
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		file.add(save);
		file.addSeparator();
		JMenuItem exit=new JMenuItem("Выход");
		MainFrame mf=this;
		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mf.dispose();
			}
			
		});
		file.add(exit);
		setJMenuBar(menuBar);
	}
	void mayClean()
	{
		clean.setVisible(true);
		removeListeners();
	}
}
