package com.shoping.mall.bean;


public class Help 
{
   private long id;
   private String title;
   public HelpDetail theHelpDetail[];
   
   /**
    */
   public Help() 
   {
    
   }

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public HelpDetail[] getTheHelpDetail() {
	return theHelpDetail;
}

public void setTheHelpDetail(HelpDetail[] theHelpDetail) {
	this.theHelpDetail = theHelpDetail;
}
   
   
}
