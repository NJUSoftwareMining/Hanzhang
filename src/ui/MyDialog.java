package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyDialog extends JDialog
{
	
	public MyDialog(JFrame f, String title , boolean bool, String contains)
	{
		super(f,title,bool);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JLabel jlb = new JLabel(contains);
		jlb.setFont(new   Font("字体",   1,   15)); 
		JButton ok = new JButton("确定");
		p1.add(jlb);
		p2.add(ok);
		
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.SOUTH);
		
		
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MyDialog.this.dispose();
			}
		});
		
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  
        this.setLocationRelativeTo(null);
        this.setSize(300, 150);  
        this.setVisible(true);
         
		
	}
	public MyDialog(JFrame f, String title , boolean bool, int n1, int n2)
	{
		super(f,title,bool);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JLabel jlb1 = new JLabel("traningset size: "+n1);
		JLabel jlb2 = new JLabel("positive kind: "+n2);
		jlb1.setFont(new   Font("字体",   1,   15)); 
		jlb2.setFont(new   Font("字体",   1,   15)); 
		JButton ok = new JButton("确定");
		p1.setLayout(new BorderLayout());
		p1.add(jlb1, BorderLayout.NORTH);
		p1.add(jlb2, BorderLayout.SOUTH);
		p2.add(ok);
		
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.SOUTH);
		
		
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MyDialog.this.dispose();
			}
		});
		
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  
        this.setLocationRelativeTo(null);
        this.setSize(300, 150);  
        this.setVisible(true);
         
		
	}
	

}