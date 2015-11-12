package ui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ui extends JFrame
{
	JPanel jpanel;
	JButton jbt_open = new JButton("Open");
	JButton jbt_next = new JButton("Next");
	JButton jbt_back = new JButton("Back");
	JButton jbt_finish = new JButton("Finish");
	
	JLabel label;
	JFileChooser chooser;
	JTextField TextField;
	CheckList file_list;
	
	boolean[] positive_list;
	boolean[] trainingset_list;
	
	public ui()
	{
		 try 
		 {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	     } 
		 catch (Exception e) 
		 {
			 System.out.println("Unable to find System Look and Feel");
		 }
		
		setTitle("导入度量值文件");
		TextField=new JTextField(30);
		label = new JLabel();
		
		this.setLayout(new FlowLayout());
		jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout());
		jpanel.add(jbt_open);
		jpanel.add(TextField);
		jpanel.add(label);
		this.add(jpanel);
		chooser = new JFileChooser("./mywordcard");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
    		   "请选择文本文件", "txt");//文件名过滤器
		chooser.setFileFilter(filter);
		jbt_open.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				int result = chooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					String path = chooser.getSelectedFile().getPath();
					TextField.setText(path);
					file_list = new CheckList(path);
					jpanel.removeAll();
					
					
					JPanel p_temp = new JPanel(); 
					p_temp.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5)); 
					p_temp.add(jbt_next); 
					
					jpanel.setLayout(new BorderLayout(2,2));
					jpanel.add(file_list,BorderLayout.NORTH);
					jpanel.add(p_temp, BorderLayout.SOUTH);
					setTitle("正类标记");
					jpanel.validate();
					
					ui.this.pack();
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					int x = (int)(toolkit.getScreenSize().getWidth()-ui.this.getWidth())/2;
					int y = (int)(toolkit.getScreenSize().getHeight()-ui.this.getHeight())/2;
					ui.this.setLocation(x, y);
					
				
				}
				
			}
			
		}); 
		
		jbt_next.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				int list_len = file_list.boxList.length;
				
				//初始化均为 false
				positive_list = new boolean[list_len];
				trainingset_list = new boolean[list_len];
				
				
				for (int i=0; i<list_len; i++)
				{
					
					if (file_list.boxList[i].isChecked())
					{
						positive_list[i] = true;
						//System.out.println(i);
					}
					//System.out.println(positive_list[i]+file_list.listData.elementAt(i));
				}
				
				/*
				 * 是否需要复选框复位，
				 * 不复位能够默认将正类加入训练集
				 * 
				//复选框初始化为false
				for (int i=0; i<file_list.boxList.length; i++)
				{
					file_list.boxList[i].setChecked(false);
				}
				file_list.listCheckBox.repaint();
				file_list.listCheckBox.updateUI();
				file_list.updateUI();
				*/
				
				jpanel.removeAll();
				JPanel p_temp = new JPanel(); 
				p_temp.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5)); 
				p_temp.add(jbt_back); 
				p_temp.add(jbt_finish); 
				
				jpanel.setLayout(new BorderLayout(2,2));
				jpanel.add(file_list,BorderLayout.NORTH);
				jpanel.add(p_temp, BorderLayout.SOUTH);
				setTitle("训练集标记标记");
				jpanel.validate();
				
				ui.this.pack();
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				int x = (int)(toolkit.getScreenSize().getWidth()-ui.this.getWidth())/2;
				int y = (int)(toolkit.getScreenSize().getHeight()-ui.this.getHeight())/2;
				ui.this.setLocation(x, y);
				
			
			}
			
		}); 
		
		
		
		jbt_back.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				jpanel.removeAll();
				JPanel p_temp = new JPanel(); 
				p_temp.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5)); 
				p_temp.add(jbt_next); 
				
				jpanel.setLayout(new BorderLayout(2,2));
				jpanel.add(file_list,BorderLayout.NORTH);
				jpanel.add(p_temp, BorderLayout.SOUTH);
				setTitle("正类标记");
				jpanel.validate();
				
				ui.this.pack();
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				int x = (int)(toolkit.getScreenSize().getWidth()-ui.this.getWidth())/2;
				int y = (int)(toolkit.getScreenSize().getHeight()-ui.this.getHeight())/2;
				ui.this.setLocation(x, y);

			}
			
		}); 
		
		jbt_finish.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				int list_len = file_list.boxList.length;
				int positive_count = 0;
				int trainingset_size = 0;
				for (int i=0; i<list_len; i++)
				{
					
					if (file_list.boxList[i].isChecked())
					{
						trainingset_list[i] = true;
						
					}
					
				}
				/* export training set tag file
				 * positive: filename#?#TRUE
				 * negative: filename#?#FALSE 
				 */
				BufferedWriter bw = null;
				String dst_path = "output.txt";
				try 
				{
					bw = new BufferedWriter(new FileWriter(dst_path));
					for (int i=0; i<list_len; i++)
					{
						if (trainingset_list[i]==true)
						{
							trainingset_size++;
							if (positive_list[i]==true)
							{
								bw.write(file_list.listData.elementAt(i)+"#?#TRUE\n");
								positive_count ++;
							}
							else
							{
								bw.write(file_list.listData.elementAt(i)+"#?#FALSE\n");
							}
						}
					}
					bw.close();
					
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				MyDialog dialog = new MyDialog(ui.this, "Message" , true, trainingset_size,positive_count);

			}
			
		}); 
      
	}
	
	protected void processWindowEvent(WindowEvent e)
	{
		
		 if (e.getID() == WindowEvent.WINDOW_CLOSING) 
		 {  
			 this.setVisible(false); 
		 }
		 else 
		 {    
			 super.processWindowEvent(e);      
		 }  
		
	}
	
	 public static void main(String[] args)
	 {
		 ui frame = new ui();
		 frame.pack();
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth())/2;
		 int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight())/2;
		 frame.setLocation(x, y);		 
		 frame.setVisible(true);
	 }
  
}
