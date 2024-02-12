package com.note.notetutorial.enumeration;

public enum Level {
	   HIGH, MEDIUM, LOW;

// Doesn't work with Hibernate 6.4.1 
//		   HIGH (3),
//	    MEDIUM (2),
//	    LOW (1);
//
//	    private final int level;
//
//	    Level(int level) {
//	        this.level = level;
//	    }
//
//	    public int getLevel() {
//	        return this.level;
//	    }
  
}
