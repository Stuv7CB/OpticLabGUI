package stuv7cb.gui;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

class SetLense extends SetPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1981505352249759178L;
	SetLense(MainFrame f) 
	{
		super(f);
	}
	protected JTextField length=new JTextField("Длина");
	protected JTextField f=new JTextField("Фокус");
	@Override
	void addFields() 
	{
		setLayout(new GridLayout(10,1));
		add(new JLabel("Линза"));
		add(new JLabel("Координаты"));
		add(xcord);
		add(ycord);
		add(new JLabel("Длина"));
		add(length);
		add(new JLabel("Угол"));
		add(angle);
		add(new JLabel("Фокус"));
		add(f);
	}
	void addObject() 
	{
		Point p=new Point();
		p.setLocation(Double.valueOf(xcord.getText()), Double.valueOf(ycord.getText()));
		LabelLense label=new LabelLense(p, Double.valueOf(length.getText()), Math.PI*Double.valueOf(angle.getText())/180, Double.valueOf(f.getText()));
		label.addMouseControl();
		frame.mainPaneladd(label);
		label.addPopup();
		label.updateUI();
	}
}
