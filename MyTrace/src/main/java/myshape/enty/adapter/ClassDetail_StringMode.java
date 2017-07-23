package myshape.enty.adapter;

import java.util.ArrayList;
import java.util.Objects;

public class ClassDetail_StringMode {
	private String packageName;

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {

		return packageName;
	}

	private String superClassName;

	public String getSuperClassName() {
		// ��ȡ������
		return superClassName;
	}

	public void setSuperClassName(String superClassName) {
		// ���ó�����
		this.superClassName = superClassName;
	}

	// �����ֶ�
	ArrayList<FieldDetail> fields = new ArrayList<FieldDetail>();

	public void addField(FieldDetail fieldDetail) {
		fields.add(fieldDetail);
	}

	public ArrayList<FieldDetail> getFields() {
		return fields;
	}

	// ����ӿ�
	ArrayList<String> interfaces;

	public ArrayList<String> getInterfaces() {
		return interfaces;
	}

	public void addInterfaces(ArrayList<String> intfs) {
		interfaces = intfs;

	}

	// �����ڲ���
	ArrayList<String> innerClazz;

	public void addInnerClazz(ArrayList<String> inner) {
		innerClazz = inner;
	}

	public ArrayList<String> getInnerClazz() {
		return innerClazz;
	}

	// ���淽��
	ArrayList<MethodDetail> methodDetails;

	public void addMethods(ArrayList<MethodDetail> methods2) {
		methodDetails = methods2;
	}

	public ArrayList<MethodDetail> getMethodDetails() {
		return methodDetails;
	}

	// ��������
	private String className;

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	// �Ƿ�ӿ�
	boolean isInterface;

	public void setInterfaceFlage(boolean interface1) {
		isInterface = interface1;
	}

	public boolean isInterface() {
		return isInterface;
	}

	// �������η�
	String modifier;

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return modifier;
	}

	// ����ǩ��
	String Signature;

	public void setSignater(String genericSignature) {
		Signature = genericSignature;
	}

	public String getSignature() {
		return Signature;
	}

	// ����ע��
	ArrayList<String> classAnnotation = new ArrayList<String>();

	public void addClassAnn(String string) {
		classAnnotation.add(string);
	}

	public ArrayList<String> getClassAnnotation() {
		return classAnnotation;
	}

	// ���湹����
	ArrayList<ConstructorDetail> constructors = new ArrayList<ConstructorDetail>();
	public void addConstructor(ConstructorDetail constructorDetal) {
		constructors.add(constructorDetal);
	}
	public ArrayList<ConstructorDetail> getConstructors() {
		return constructors;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || obj.getClass() != this.getClass()) {
			return false;
		}
		ClassDetail cd = (ClassDetail) obj;
		if (className == cd.getClassName() && superClassName == cd.getSuperClassName()
				&& packageName == cd.getPackageName() && cd.getSignature() == getSignature()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(className, superClassName, packageName, Signature);
	}
}
