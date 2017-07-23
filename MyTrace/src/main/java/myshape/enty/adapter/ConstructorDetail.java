package myshape.enty.adapter;

import java.util.ArrayList;
import java.util.Objects;

public class ConstructorDetail {
	String constructorName;

	public void setContructorName(String name) {
		constructorName = name;
	}

	public String getConstructorName() {
		return constructorName;
	}

	String signature;

	public void setSignarure(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return signature;
	}

	String genericSignature;

	public void setGenericSignature(String genericSignature) {
		this.genericSignature = genericSignature;
	}

	public String getGenericSignature() {
		return genericSignature;
	}

	String InClassName;

	public void setInClass(String name) {
		InClassName = name;
	}

	public String getInClassName() {
		return InClassName;
	}

	String modifier;

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return modifier;
	}

	ArrayList<String> annotations = new ArrayList<String>();

	public void addAnnotatoins(String string) {
		annotations.add(string);
	}

	public ArrayList<String> getAnnotations() {
		return annotations;
	}

	ArrayList<String> exceptions = new ArrayList<String>();

	public void addExceptions(String name) {
		exceptions.add(name);
	}

	public ArrayList<String> getExceptions() {
		return exceptions;
	}

	ArrayList<String> parameters = new ArrayList<String>();

	public void addParameters(String name) {
		parameters.add(name);
		parameters_with_Annotation.add(name);
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	ArrayList<String> parameters_with_Annotation = new ArrayList<String>();

	public void addParamAnnotation(String annotation, int index) {
		String string = parameters_with_Annotation.get(index);
		parameters_with_Annotation.add(index, annotation + string);
	}

	public ArrayList<String> getParameters_with_Annotation() {
		return parameters_with_Annotation;
	}

	@Override
	public boolean equals(Object obj) {
		if(null==obj||obj.getClass()!=getClass()) return false;
		ConstructorDetail cd=(ConstructorDetail)obj;
		if(cd.getSignature()==getSignature()&&cd.getConstructorName()==getConstructorName())
			return true;
		return false;
	}

	@Override
	public int hashCode() {
	
		return Objects.hash(getConstructorName(),getSignature(),getInClassName());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(modifier+" ").append(constructorName+" (");
		for(String p :parameters) {
			int index=p.lastIndexOf(".");
			if(index>0) {
				builder.append(p.substring(index+1,p.length())+", ");
			}else
			builder.append(p+", ");
		}
//		int index = builder.lastIndexOf(",");
//		builder.deleteCharAt(index-1);
		builder.append(")");
		return builder.toString();
	}
	
}
