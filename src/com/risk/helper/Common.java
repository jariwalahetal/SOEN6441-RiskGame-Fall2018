package com.risk.helper;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sadgi
 *
 */
public class Common {
	
	public Color getColor(EnumColor selectedColor){
	       if(selectedColor.BLACK.equals(Color.BLACK)){
	    	   return Color.BLACK;
	       }
	       if(selectedColor.RED.equals(Color.RED)){
	    	   return Color.RED;
	       }
	       if(selectedColor.GREEN.equals(Color.GREEN)){
	    	   return Color.GREEN;
	       }
	       if(selectedColor.BLUE.equals(Color.BLUE)){
	    	   return Color.BLUE;
	       }
	       if(selectedColor.GRAY.equals(Color.GRAY)){
	    	   return Color.GRAY;
	       }
		return null;
		
	}


}
