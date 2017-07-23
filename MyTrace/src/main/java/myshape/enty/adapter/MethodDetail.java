package myshape.enty.adapter;

import java.util.ArrayList;
import java.util.Objects;

public class MethodDetail {
	ArrayList<String> annotataion = new ArrayList<String>();

	public void addAnnotataion(String string) {
		annotataion.add(string);
	}

	public ArrayList<String> getAnnotataion() {
		return annotataion;
	}

	ArrayList<String> exception = new ArrayList<String>();

	public void addException(String name) {
		exception.add(name);
	}

	public ArrayList<String> getException() {
		return exception;
	}

	ArrayList<String> param = new ArrayList<String>();

	public void addParamaterTpes(String simpleName) {
		param.add(simpleName);
		annotationPara.add(simpleName);
	}

	public ArrayList<String> getParam() {
		return param;
	}

	String returnType;

	public void addReturnType(String name) {
		returnType = name;
		setSimpleReturnType(returnType);
	}

	public String getReturnType() {
		return returnType;
	}

	ArrayList<String> annotationPara = new ArrayList<String>();

	public void addAnnotataionParameter(String string, int i) {
		String string2 = annotationPara.get(i);
		String annoPara = string + string2;
		annotationPara.add(i, annoPara);
	}

	public ArrayList<String> getAnnotationPara() {
		return annotationPara;
	}

	String modifier;

	public void addMidifier(String modifier) {
		this.modifier = modifier;

	}

	String MethodName;

	public void addMethodName(String name) {
		MethodName = name;
	}

	public String getMethodName() {
		return MethodName;
	}

	public String getSignature() {
		return signature;
	}

	String signature;

	public void addSignture(String signature) {
		this.signature = signature;
	}
	private String simpleReturnType;
	public String getSimpleReturenTyep() {
		return simpleReturnType;
	}
 public void setSimpleReturnType(String name) {
		int lastIndexOf = returnType.lastIndexOf(".");
		if(lastIndexOf>0) {
			int length = returnType.length();
			simpleReturnType = returnType.substring(lastIndexOf+1,length);
		}else {
			simpleReturnType=name;
		}
		
 }
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !getClass().equals(obj.getClass())) {
			return false;
		}
		MethodDetail md = (MethodDetail) obj;
		if (md.getMethodName().equals(getMethodName()) && md.getSignature().equals(getSignature()))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMethodName(), getSignature(), getInClass());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(modifier).append(simpleReturnType+" ").append(MethodName+" (");
		for(String p:param) {
			int lastIndexOf = p.lastIndexOf(".");
			if(lastIndexOf>0) {
				builder.append(p.substring(lastIndexOf+1, p.length())+" ,");
			}else {
				builder.append(p);
			}
		}
//		int lastIndexOf = builder.lastIndexOf(",");
//		builder.deleteCharAt(lastIndexOf);
		builder.append(")");
		return builder.toString();
	}

	String inClass;

	public void addInClass(String name) {
		inClass = name;
	}

	public String getInClass() {
		return inClass;
	}

}
