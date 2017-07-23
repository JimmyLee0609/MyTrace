package myshape.enty.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class ClassDetail {

	/*
	 * private List<CtClass> interfaceNames = null;
	 * 
	 * private Set<String> classDependents = null; private Set<String>
	 * attributeDependents = null; private Set<String> methodDependents = null;
	 * 
	 * private List<CtField> attributes = null; private List<CtMethod> methods =
	 * null; private List<CtClass> innerClass = null;
	 */
	/*
	 * { classDependents = new HashSet<>(); methodDependents = new HashSet<>();
	 * attributeDependents = new HashSet<>();
	 * 
	 * }
	 */

	/*
	 * public ClassDetail() { this("", "", 16, 16, 16, 16); // 空参初始化类细节 }
	 * 
	 * public ClassDetail(String className, String superClassName, int interfaceNum,
	 * int methodNum, int attributeNum, int innerNum) { super(); //
	 * 使用全类名，超类名，接口数量，方法数量，字段数量初始化类细节 this.className = className;
	 * this.superClassName = superClassName; interfaceNames = new
	 * ArrayList<>(interfaceNum); methods = new ArrayList<>(methodNum); attributes =
	 * new ArrayList<>(attributeNum); innerClass = new ArrayList<>(innerNum); }
	 * 
	 * public boolean addInnerClass(CtClass innerClass) { return
	 * this.innerClass.add(innerClass); }
	 * 
	 * public void addInnerClasses(CtClass[] innerClasses) { for (CtClass ctClass :
	 * innerClasses) { innerClass.add(ctClass); } }
	 * 
	 * public List<CtClass> getInnerClass() { return innerClass; }
	 * 
	 * public boolean addAttribute(CtField attributeName) { // 在类中添加字段 return
	 * attributes.add(attributeName); }
	 * 
	 * public void addAttribute(CtField[] declaredFields) { // 在类中添加字段 for (CtField
	 * ctField : declaredFields) { attributes.add(ctField); } }
	 * 
	 * public boolean addInterface(CtClass interfaceName) { // 在类中添加接口 return
	 * interfaceNames.add(interfaceName); }
	 * 
	 * public void addInterface(CtClass[] interfaces) { // 在类中添加接口 for (CtClass inf
	 * : interfaces) { interfaceNames.add(inf); } }
	 */

	/*
	 * public boolean addMethod(CtMethod methodName) { // 在类中添加方法 return
	 * methods.add(methodName); }
	 */

	/*
	 * public void addMethod(CtMethod[] declaredMethods) { // 在类中添加方法 for (CtMethod
	 * ctMethod : declaredMethods) { methods.add(ctMethod); } }
	 */

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

	/*
	 * public Set<String> getAssiation() throws ClassNotFoundException { for
	 * (CtField attribute : attributes) { String name = null; try { name =
	 * attribute.getType().getName(); } catch (NotFoundException e) {
	 * e.printStackTrace(); } attributeDependents.add(name); String signature =
	 * attribute.getSignature();
	 * 
	 * // genericSignature 是包含字段类型及泛型类型的全部 如 Ljava/util/List<Ljava/lang/Thread;> if
	 * (signature.contains("<")) { String[] split =
	 * signature.substring(signature.indexOf("<") + 1,
	 * signature.indexOf(">")).split(";"); for (String string : split) { String
	 * replace = string.substring(1, string.length()).replace("/", ".");
	 * attributeDependents.add(replace); } } Object[] annotations =
	 * attribute.getAnnotations(); for (Object object : annotations) {
	 * 
	 * attributeDependents.add(object.getClass().getName()); } } return
	 * attributeDependents; }
	 */

	/*
	 * public List<CtField> getAttributes() { // 获取属性集合 return attributes; }
	 */

	/*
	 * public Set<String> getDependent() { classDependents.add(superClassName); for
	 * (CtClass interfaceName : interfaceNames) {
	 * classDependents.add(interfaceName.getName()); } return classDependents; }
	 */

	/*
	 * public CtField getField(String field) throws NotFoundException { CtField att
	 * = null; for (CtField attr : attributes) { if
	 * (attr.getSignature().equals(field)) { att = attr; } } if (null == att) {
	 * throw new NotFoundException("在注册的字段中没有找到该字段" + field); } return att; }
	 */

	/*
	 * public List<CtClass> getInterfaceNames() { // 获取接口集合 return interfaceNames; }
	 */

	/*
	 * public CtMethod getMethod(String method) throws NotFoundException { CtMethod
	 * ctMethod = null; for (CtMethod ctMd : methods) { if
	 * (ctMd.getName().equals(method)) { ctMethod = ctMd; } } if (null == ctMethod)
	 * { throw new NotFoundException("在注册的方法中没有找到该方法" + method); } return ctMethod;
	 * }
	 */

	/*
	 * public Set<String> getMethodDependent(String method) throws
	 * NotFoundException, ClassNotFoundException { CtMethod ctMethod =
	 * getMethod(method); if (null != ctMethod) { CtClass[] types =
	 * ctMethod.getParameterTypes(); for (CtClass ctClass : types) {
	 * methodDependents.add(ctClass.getName()); } Object[] annotations =
	 * ctMethod.getAnnotations(); for (Object object : annotations) {
	 * methodDependents.add(object.getClass().getName()); } String Signature =
	 * ctMethod.getSignature(); if (null != Signature) {
	 * analysesGenericSignature(Signature, methodDependents); } } return
	 * methodDependents; }
	 */

	/*private void analysesGenericSignature(String genericSignature, Set<String> methodDependents) {

		// <T:Ljava/lang/Object;>(Ljava/util/ArrayList<Ljava/lang/Integer;>;
		// Ljava/lang/String;I)Ljava/util/List<Ljava/lang/Integer;>;
		String substring = genericSignature.substring(genericSignature.indexOf("("), genericSignature.indexOf(")"));
		String[] split = substring.split(">");
		for (String string : split) {
			String[] split2 = string.split("<");
			if (split2.length >= 2) {
				methodDependents.add(split2[1]);
			}
		}
	}*/

	/*
	 * public List<CtMethod> getMethods() { // 获取方法集合 return methods; }
	 */
	// =======================================================================

	@Override
	public int hashCode() {
		return Objects.hash(className, superClassName, packageName, Signature);
	}

	/*
	 * public void setAttributes(List<CtField> attributes) { // 设置属性集合
	 * this.attributes = attributes; }
	 */

	/*
	 * public void setInterfaceNames(List<CtClass> interfaceNames) { // 设置接口集合
	 * this.interfaceNames = interfaceNames; }
	 */

	/*
	 * public void setMethods(List<CtMethod> methods) { // 设置方法集合 this.methods =
	 * methods; }
	 */
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

	// =======================用String来保存数据=================================================
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

}
