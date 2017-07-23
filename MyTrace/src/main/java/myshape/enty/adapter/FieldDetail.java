package myshape.enty.adapter;

import java.util.ArrayList;
import java.util.Objects;

public class FieldDetail {
	
	
	
	String modifier;
	public void setfieldModify(String modifiers) {
		this.modifier = modifiers;
	}
	public String getModifier() {
		return modifier;
	}

	String fieldName;
	public String getFieldName() {
		return fieldName;
	}
	public void setName(String fieldName) {
		this.fieldName = fieldName;
	}
	String type;
	public void setType(String signature) {
		type=signature;
	}

	public String getType() {
		return type;
	}

	ArrayList<String> annotion = new ArrayList<String>();

	public void addAnnotaions(String string) {
		annotion.add(string);
	}

	public ArrayList<String> getAnnotion() {
		return annotion;
	}

	String Inclass;

	public void setDeclarClass(String name) {
		Inclass = name;
	}

	public String getInclass() {
		return Inclass;
	}

	String signature;

	public void setSignature(String genericSignature2) {
		signature = genericSignature2;
	}

	public String getSignature() {
		return signature;
	}
	@Override
	public boolean equals(Object obj) {
		if(null==obj||obj.getClass()!=getClass())
			return false;
		FieldDetail fd=(FieldDetail)obj;
		if(fd.getSignature()==getSignature()&&fd.getFieldName()==getFieldName())
			return true;
		return false;
	}
	@Override
	public int hashCode() {
		return Objects.hash(getInclass(),getFieldName(),getSignature());
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int lastIndexOf  ;
		if(null!=type&&( lastIndexOf=type.lastIndexOf("."))>0) {
			String substring = type.substring(lastIndexOf+1, type.length());
			builder.append(modifier+"-").append(substring+" --").append(fieldName);
		}
		else builder.append(modifier+"-").append(type+" --").append(fieldName);
		return builder.toString();
	}
	
}
