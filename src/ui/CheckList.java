package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class CheckList extends JPanel {

	Vector<String> listData;
	CheckBoxItem[] boxList;
	
	JList listCheckBox;
	JList listDescription;
	
    public CheckList(String path) 
    {
        
        listData = new Vector<String>();
       
        File file = new File(path);
		if (!file.exists()) {
			System.out.println("File does not exists");
			System.exit(0);
		}
		
		BufferedReader br = null;
		String line = null;
		try 
		{
			br = new BufferedReader(new FileReader(file));
			boolean firstline_flag = true;
			while((line = br.readLine())!=null)
			{
				//过滤第一行
				if (firstline_flag == true)
				{
					firstline_flag = false;
					continue;
				}
				int index  = line.indexOf(",");
				if (index<0)
				{
					System.out.println("输入文件格式错误");
					System.exit(ERROR);
				}
				line = line.substring(0,index);
				listData.add(line);
			}
			br.close();
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		try 
		{
			Scanner input = new Scanner(file);
			while (input.hasNext())
			{
				listData.add(input.nextLine());
			}
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		*/
		
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            System.out.println("Unable to find System Look and Feel");
        }
        
        
        boxList = buildCheckBoxItems(listData.size());
        listCheckBox = new JList(boxList);
        listDescription = new JList(listData);
        
        listDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDescription.addMouseListener(new MouseAdapter() 
        {

            public void mouseClicked(MouseEvent me) 
            {
                if (me.getClickCount() != 2) 
                {
                    return;
                }
                int selectedIndex = listDescription.locationToIndex(me.getPoint());
                if (selectedIndex < 0) 
                {
                    return;
                }
                CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel().getElementAt(selectedIndex);
                item.setChecked(!item.isChecked());
                listCheckBox.repaint();

            }
        });
        listCheckBox.setCellRenderer(new CheckBoxRenderer());
        listCheckBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCheckBox.addMouseListener(new MouseAdapter() 
        {

            public void mouseClicked(MouseEvent me) 
            {
                int selectedIndex = listCheckBox.locationToIndex(me.getPoint());
                if (selectedIndex < 0) 
                {
                    return;
                }
                CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel().getElementAt(selectedIndex);
                item.setChecked(!item.isChecked());
                listDescription.setSelectedIndex(selectedIndex);
                listCheckBox.repaint();
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setRowHeaderView(listCheckBox);
        scrollPane.setViewportView(listDescription);
        listDescription.setFixedCellHeight(20);
       
        listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
        listCheckBox.setFixedCellWidth(20);
     
        TitledBorder bd = new TitledBorder("文件列表");
        scrollPane.setBorder(bd);
        scrollPane.setPreferredSize(new Dimension(400,600));
        this.add(scrollPane);
    }

    private CheckBoxItem[] buildCheckBoxItems(int totalItems) 
    {
        CheckBoxItem[] checkboxItems = new CheckBoxItem[totalItems];
        for (int counter = 0; counter < totalItems; counter++) 
        {
            checkboxItems[counter] = new CheckBoxItem();
        }
        return checkboxItems;
    }
  
   
  
    class CheckBoxItem {

        private boolean isChecked;

        public CheckBoxItem() 
        {
            isChecked = false;
        }

        public boolean isChecked() 
        {
            return isChecked;
        }

        public void setChecked(boolean value) 
        {
            isChecked = value;
        }
    }

    /* Inner class that renders JCheckBox to JList*/
    class CheckBoxRenderer extends JCheckBox implements ListCellRenderer 
    {

        public CheckBoxRenderer() 
        {
            setBackground(UIManager.getColor("List.textBackground"));
            setForeground(UIManager.getColor("List.textForeground"));
        }

        public Component getListCellRendererComponent(JList listBox, Object obj, int currentindex,
                boolean isChecked, boolean hasFocus) 
        {
            setSelected(((CheckBoxItem) obj).isChecked());
            return this;
        }
    }
    
 
}