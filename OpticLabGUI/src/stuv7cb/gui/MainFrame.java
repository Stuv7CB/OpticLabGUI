package stuv7cb.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class MainFrame extends JFrame
{
	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Dimension screenSize=kit.getScreenSize();
	final int WIDTH=screenSize.width/2;
	final int HEIGHT=screenSize.height/2;
	private final String TITLE="GUI";
	JPanel mainPanel =new JPanel();
	public MainFrame()
	{
		setSize(WIDTH, HEIGHT);
		setTitle(TITLE);
		setLocationByPlatform(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setSize(3*WIDTH/4, HEIGHT/8);
		panel.add(new ButtonRun("������ ���������"));
		setLayout(null);
		panel.setLocation(WIDTH/4, 7*HEIGHT/8);
		add(panel);
		mainPanel.setLayout(null);
		mainPanel.setSize(3*WIDTH/4, 7*HEIGHT/8);
		mainPanel.setLocation(WIDTH/4, 0);
		add(mainPanel, BorderLayout.CENTER);
		addPanelOfSelection();
	}
	void addPanelOfSelection()
	{
		JPanel panelSelection = new JPanel();
		final SelectionLabel[] selectionLabel = new SelectionLabel[3];
		panelSelection.setLayout(null);
		SelectionLabel.parent=this;
		for(int i=0;i<3;i++)
		{
			selectionLabel[i]=new SelectionLabel(i);
			selectionLabel[i].addMouseListener(selectionLabel[i].new MyMouseListener());
			selectionLabel[i].setLocation(0, i*20);
			panelSelection.add(selectionLabel[i]);
		}
		panelSelection.setSize(WIDTH/4, 7*HEIGHT/8);
		panelSelection.setLocation(0, 0);
		panelSelection.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(panelSelection);
	}
}