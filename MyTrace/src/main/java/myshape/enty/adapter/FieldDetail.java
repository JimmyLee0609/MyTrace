package myshape.enty.adapter;

import java.util.ArrayList;

public class FieldDetail {
	String modifier;
	String fieldName;
	String signature;
	ArrayList<String> annotion=new ArrayList<String>();
	
	public String getModifier() {
		return modifier;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getSignature() {
		return signature;
	}

	public ArrayList<String> getAnnotion() {
		return annotion;
	}

	public void setfieldModify(String modifiers) {
		this.modifier=modifiers;
	}

	public void setName(String fieldName) {
		this.fieldName=fieldName;
	}

	public void setType(String signature) {
		// TODO Auto-generated method stub
		
	}

	public void addAnnotaions(String string) {
		annotion.add(string);		
	}

}
