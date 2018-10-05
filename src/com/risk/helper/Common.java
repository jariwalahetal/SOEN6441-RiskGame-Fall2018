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
	       if(selectedColor.equals(EnumColor.BLACK)){
	    	   return Color.BLACK;
	       }
	       if(selectedColor.equals(EnumColor.RED)){
	    	   return Color.RED;
	       }
	       if(selectedColor.equals(EnumColor.GREEN)){
	    	   return Color.GREEN;
	       }
	       if(selectedColor.equals(EnumColor.BLUE)){
	    	   return Color.BLUE;
	       }
	       if(selectedColor.equals(EnumColor.GRAY)){
	    	   return Color.GRAY;
	       }
		return null;
		
	}


}
