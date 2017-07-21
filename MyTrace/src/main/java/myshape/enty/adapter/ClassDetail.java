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
	private  Set<String> attributeDependents=null;
	private List<CtField> attributes=null;
	private Set<String> classDependents=null;
	
	
	private String className;
	private List<CtClass> interfaceNames=null;
	private  Set<String> methodDependents=null;
	private List<CtMethod> methods=null;
	private String packageName;
	private List<CtClass> innerClass=null;
	private String superClassName;
	{	
		classDependents=new HashSet<>();
		methodDependents=new HashSet<>();
		attributeDependents=new HashSet<>();
		
	}
	public ClassDetail() {
		this("", "", 16, 16, 16,16);
//		�ղγ�ʼ����ϸ��
	}
	

	public ClassDetail(String className, String superClassName, int interfaceNum, int methodNum, int attributeNum,int innerNum) {
		super();
//		ʹ��ȫ���������������ӿ������������������ֶ�������ʼ����ϸ��
		this.className = className;
		this.superClassName = superClassName;
		interfaceNames = new ArrayList<>(interfaceNum);
		methods = new ArrayList<>(methodNum);
		attributes = new ArrayList<>(attributeNum);
		innerClass=new ArrayList<>(innerNum);
	}
	public boolean addInnerClass(CtClass innerClass){
		return this.innerClass.add(innerClass);
	}
	public void addInnerClass(CtClass[] innerClasses){
		for (CtClass ctClass : innerClasses) {
			innerClass.add(ctClass);
		}
	}
	public List<CtClass> getInnerClass() {
		return innerClass;
	}

	public boolean addAttribute(CtField attributeName) {
//		����������ֶ�
		return attributes.add(attributeName);
	}
	public void addAttribute(CtField[] declaredFields) {
//		����������ֶ�
		for (CtField ctField : declaredFields) {
			attributes.add(ctField);
		}
	}
	public boolean addInterface(CtClass interfaceName) {
//		��������ӽӿ�
		return interfaceNames.add(interfaceName);
	}
	public void addInterface(CtClass[] interfaces) {
//		��������ӽӿ�
		for (CtClass inf : interfaces) {
			interfaceNames.add(inf);
		}
	}
	 public boolean addMethod(CtMethod methodName) {
//		��������ӷ���
		return methods.add(methodName);
	}
	public void addMethod(CtMethod[] declaredMethods) {
//	��������ӷ���
		for (CtMethod ctMethod : declaredMethods) {
			methods.add(ctMethod);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null==obj||obj.getClass()!=this.getClass()) {
			return false;
		}
		ClassDetail cd=(ClassDetail)obj;
		if(className==cd.getClassName()) {
			if(superClassName==cd.getSuperClassName()) {
				if(packageName==cd.getPackageName()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void getAssiation() throws ClassNotFoundException {
		for(CtField attribute: attributes) {
			String name=null;
			try {
				name = attribute.getType().getName();
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			attributeDependents.add(name);
			String signature = attribute.getSignature();
//			genericSignature �ǰ����ֶ����ͼ��������͵�ȫ�� �� Ljava/util/List<Ljava/lang/Thread;>
			if(null!=signature) {
				String[] split = signature.substring(signature.indexOf("<")+1,signature.indexOf(">")).split(";");
				for (String string : split) {
					String replace = string.substring(1,string.length()).replace("/", ".");
					attributeDependents.add(replace);
				}
			}
			Object[] annotations = attribute.getAnnotations();
			for (Object object : annotations) {
				
				attributeDependents.add(object.getClass().getName());
			}
		}
	}

	public List<CtField> getAttributes() {
//		��ȡ���Լ���
		return attributes;
	}

	public String getClassName() {
//		��ȡȫ����
		return className;
	}

	public Set<String> getDependent(){
		classDependents.add(superClassName);
		for(CtClass interfaceName : interfaceNames) {
			classDependents.add(interfaceName.getName());
		}
		return classDependents;
	}

	public CtField getField(String field) throws NotFoundException {
		CtField att=null;
		for(CtField attr :attributes) {
			if(attr.getSignature().equals(field) ){
				att=attr;
			}
		}
		if(null==att) {
			throw new NotFoundException("��ע����ֶ���û���ҵ����ֶ�"+field);
		}
		return att;
	}

	public List<CtClass> getInterfaceNames() {
//		��ȡ�ӿڼ���
		return interfaceNames;
	}
	public CtMethod getMethod(String method) throws NotFoundException {
		CtMethod ctMethod=null;
		for(CtMethod ctMd:methods) {
			if(ctMd.getName().equals(method)) {
				ctMethod=ctMd;
			}
		}
		if(null==ctMethod) {
			throw new NotFoundException("��ע��ķ�����û���ҵ��÷���"+method);
		}
		return ctMethod;
	}

	public Set<String> getMethodDependent(String method) throws NotFoundException, ClassNotFoundException{
		CtMethod ctMethod = getMethod(method);
		if(null!=ctMethod) {
			CtClass[] types = ctMethod.getParameterTypes();
			for (CtClass ctClass : types) {
				methodDependents.add(ctClass.getName());
			}
			Object[] annotations = ctMethod.getAnnotations();
			for (Object object : annotations) {
				methodDependents.add(object.getClass().getName());
			}
			String Signature = ctMethod.getSignature();
			if(null!=Signature) {
				analysesGenericSignature(Signature,methodDependents);
			}
		}
		return methodDependents;
	}
	private void analysesGenericSignature(String genericSignature, Set<String> methodDependents) {
		
//		<T:Ljava/lang/Object;>(Ljava/util/ArrayList<Ljava/lang/Integer;>;
//		Ljava/lang/String;I)Ljava/util/List<Ljava/lang/Integer;>;
		String substring = genericSignature.substring(genericSignature.indexOf("("),genericSignature.indexOf(")"));
		String[] split = substring.split(">");
		for (String string : split) {
			String[] split2 = string.split("<");
				if(split2.length>=2) {
					methodDependents.add(split2[1]);
				}
			}		
	}
	

	public List<CtMethod> getMethods() {
//		��ȡ��������
		return methods;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSuperClassName() {
//		��ȡ������
		return superClassName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(className,superClassName,packageName);
	}

	public void setAttributes(List<CtField> attributes) {
//		�������Լ���
		this.attributes = attributes;
	}

	public void setClassName(String className) {
//		��������
		this.className = className;
	}

	public void setInterfaceNames(List<CtClass> interfaceNames) {
//		���ýӿڼ���
		this.interfaceNames = interfaceNames;
	}

	public void setMethods(List<CtMethod> methods) {
//		���÷�������
		this.methods = methods;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setSuperClassName(String superClassName) {
//		���ó�����
		this.superClassName = superClassName;
	}

}
