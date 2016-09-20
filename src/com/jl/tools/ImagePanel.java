package com.jl.tools;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;

public class ImagePanel extends JPanel{
    Image im;
    //构造函数指定panel的大小
    public ImagePanel(Image im)
    {
        this.im=im;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w,h);
    }
    public void paintComponent(Graphics g)
    {
    	//清屏
    	super.paintComponents(g);
    	g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(),this);
    	
    }
}
