package com.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlValue;

public class SimpleClass <String>{
	public static final transient double c=10d;
	Enum e;
	@XmlValue
	
	HashMap<List<String>,String> map;
	final  ArrayList<String> list=null;
	
	public int a=1;
	public double b=2;
	public float f=3f;
	public long l=44444444l;
	public char s1='c';
	public short sh=3;
	public byte by=45;
	public boolean bo=false;
	
	
	Thread t;
	@XmlAttachmentRef
	public int add(HashMap<LinkedList<String>,String> p, @XmlAttachmentRef String s) {
		return 3;
	}
}
