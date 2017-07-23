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
		// 获取超类名
		return superClassName;
	}

	public void setSuperClassName(String superClassName) {
		// 设置超类名
		this.superClassName = superClassName;
	}

	// 保存字段
	ArrayList<FieldDetail> fields = new ArrayList<FieldDetail>();

	public void addField(FieldDetail fieldDetail) {
		fields.add(fieldDetail);
	}

	public ArrayList<FieldDetail> getFields() {
		return fields;
	}

	// 保存接口
	ArrayList<String> interfaces;

	public ArrayList<String> getInterfaces() {
		return interfaces;
	}

	public void addInterfaces(ArrayList<String> intfs) {
		interfaces = intfs;

	}

	// 保存内部类
	ArrayList<String> innerClazz;

	public void addInnerClazz(ArrayList<String> inner) {
		innerClazz = inner;
	}

	public ArrayList<String> getInnerClazz() {
		return innerClazz;
	}

	// 保存方法
	ArrayList<MethodDetail> methodDetails;

	public void addMethods(ArrayList<MethodDetail> methods2) {
		methodDetails = methods2;
	}

	public ArrayList<MethodDetail> getMethodDetails() {
		return methodDetails;
	}

	// 保存类名
	private String className;

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	// 是否接口
	boolean isInterface;

	public void setInterfaceFlage(boolean interface1) {
		isInterface = interface1;
	}

	public boolean isInterface() {
		return isInterface;
	}

	// 保存修饰符
	String modifier;

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return modifier;
	}

	// 保存签名
	String Signature;

	public void setSignater(String genericSignature) {
		Signature = genericSignature;
	}

	public String getSignature() {
		return Signature;
	}

	// 保存注解
	ArrayList<String> classAnnotation = new ArrayList<String>();

	public void addClassAnn(String string) {
		classAnnotation.add(string);
	}

	public ArrayList<String> getClassAnnotation() {
		return classAnnotation;
	}

	// 保存构造器
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
