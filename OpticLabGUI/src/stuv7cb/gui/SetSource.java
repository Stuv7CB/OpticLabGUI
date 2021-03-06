package stuv7cb.gui;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;

class SetSource extends SetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5221175466964736307L;

	SetSource(MainFrame f)
	{
		super(f);
	}

	@Override
	void addFields() 
	{
		setLayout(new GridLayout(4,1));
		add(new JLabel("Источник"));
		add(new JLabel("Координаты"));
		add(xcord);
		add(ycord);
	}

	@Override
	void addObject() 
	{
		Point p=new Point();
		p.setLocation(Double.valueOf(xcord.getText()), Double.valueOf(ycord.getText()));
		LabelSource label=new LabelSource(p);
		label.addMouseControl();
		frame.mainPaneladd(label);
		label.addPopup();
		label.updateUI();
	}

}
