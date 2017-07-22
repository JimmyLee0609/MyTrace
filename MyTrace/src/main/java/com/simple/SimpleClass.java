package com.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.HandlerChain;
import javax.xml.bind.annotation.XmlValue;

public class SimpleClass <String>{
	@XmlValue
	
	HashMap<List<String>,String> map;
	ArrayList<String> list;
	Enum e;
	
	
	public int a=1;
	public double b=2;
	public float f=3f;
	public long l=44444444l;
	public char c='c';
	public short sh=3;
	public byte by=45;
	public boolean bo=false;
	
	
	Thread t;
}
