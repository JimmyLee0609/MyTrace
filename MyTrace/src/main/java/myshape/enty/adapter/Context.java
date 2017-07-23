package myshape.enty.adapter;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class Context {
	private List<ClassDetail_StringMode> classDetail = new ArrayList<ClassDetail_StringMode>();
	private Set<String> Packages = new HashSet<String>();

	private static ClassPool pool = ClassPool.getDefault();

	public void analyse(InputStream in) throws IOException, RuntimeException, NotFoundException {
		analyseClass(in);
	}

	public List<ClassDetail_StringMode> getClassDetail() {
		return classDetail;
	}

	public Set<String> getPackages() {
		return Packages;
	}

	public void analyse(String path) {
		analyse(new File(path));
	}

	public void analyse(File file) {
		if (null != file && file.exists()) {
			if (file.isFile() && file.getName().endsWith(".class")) {

				DataInputStream in;
				try {
					in = new DataInputStream(new FileInputStream(file));
					classDetail.add(analyseClass(in));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					analyse(file2);
				}
			}
		}
	}

	public ClassDetail_StringMode analyseClass(InputStream in) throws IOException, RuntimeException {
		
		// 解析类文件
		CtClass ctClass = pool.makeClass(in);
		// 获取解析到的字段信息 方法信息
		CtField[] declaredFields = ctClass.getDeclaredFields();
		CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

		CtClass[] interfaces = null;
		CtClass[] innerClass = null;
		String superClassName = null;
		// 获取超类的名字
		try {
			superClassName = ctClass.getSuperclass().getName();
		} catch (NotFoundException e) {
			superClassName = "";
		}
		// 获取接口
		try {
			interfaces = ctClass.getInterfaces();
		} catch (NotFoundException e) {
			interfaces = new CtClass[] {};
		}
		// 获取内部类
		try {
			innerClass = ctClass.getNestedClasses();
		} catch (NotFoundException e) {
			innerClass = new CtClass[] {};
		}
		// 新建 类细节对象,
		ClassDetail_StringMode detail = new ClassDetail_StringMode();
		// 保存父类名，包名
		detail.setSuperClassName(superClassName);
		String packageName = ctClass.getPackageName();
		detail.setPackageName(packageName);

//		保存构造器信息
		addConstructor(detail,ctClass);
		
		
		
		
		// 获取类的名字，修饰符，是否接口，泛型签名,注解
		addClassIssue(detail, ctClass);

		// 遍历字段。 并将信息转换为文字存入 字段对象
		addFieldIssue(detail, declaredFields);

		// 遍历获得的接口数据并保存
		addInterfaceIssue(detail, interfaces);

		// 遍历内部类的数据并保存
		addInnerIssue(detail, innerClass);

		// 遍历得到的方法并保存
		addMethodIssue(detail, declaredMethods);

		/*
		 * detail.addInterface(interfaces); detail.addInnerClasses(innerClass);
		 * detail.addMethod(declaredMethods);
		 */

		/*
		 * Packages.add(packageName); classDetail.add(detail);
		 */

		return detail;
	}

	private void addConstructor(ClassDetail_StringMode detail, CtClass ctClass) {
		//保存构造器信息  名字 ，修饰符， 参数 参数注解，异常 ，注解 ， 签名，所在类
		CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
		
		for (CtConstructor ctConstructor : declaredConstructors) {
			ConstructorDetail constructorDetal = new ConstructorDetail();
			int modifiers = ctConstructor.getModifiers();
			String name = ctConstructor.getName();
			String signature = ctConstructor.getSignature();
			String genericSignature = ctConstructor.getGenericSignature();
			CtClass declaringClass = ctConstructor.getDeclaringClass();
			
			constructorDetal.setContructorName(name);
			constructorDetal.setSignarure(signature);
			constructorDetal.setGenericSignature(genericSignature);
			constructorDetal.setInClass(declaringClass.getName());
			constructorDetal.setModifier(Modifier.modifier(modifiers));
			
			Object[] annotations = ctConstructor.getAvailableAnnotations();				for (Object object : annotations) {
				constructorDetal.addAnnotatoins(object.toString());
			}
			try {
				CtClass[] exceptionTypes = ctConstructor.getExceptionTypes();
				for (CtClass ctClass2 : exceptionTypes) {
					constructorDetal.addExceptions(ctClass2.getName());
				}
			} catch (NotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				CtClass[] parameterTypes = ctConstructor.getParameterTypes();
				for (CtClass ctClass2 : parameterTypes) {
					constructorDetal.addParameters(ctClass2.getName());
				}
				Object[][] parameterAnnotations = ctConstructor.getAvailableParameterAnnotations();
				int i=0;
				for (Object[] objects : parameterAnnotations) {
					for (Object object : objects) {
						constructorDetal.addParamAnnotation(object.toString(),i);
					}
					i++;
				}
				i=0;
			} catch (NotFoundException e2) {
			}finally{
				detail.addConstructor( constructorDetal);
			}
		}
	}

	private void addMethodIssue(ClassDetail_StringMode detail, CtMethod[] declaredMethods) {
//		遍历方法，并保存方法细节
		ArrayList<MethodDetail> methods = new ArrayList<MethodDetail>();
		for (CtMethod method : declaredMethods) {
			MethodDetail methodDetail = new MethodDetail();
//			保存方法的名字，修饰符，方法签名
			String mName = method.getName();
			int modifiers = method.getModifiers();
			String signature = method.getSignature();
			CtClass declaringClass = method.getDeclaringClass();
			methodDetail.addInClass(declaringClass.getName());
			methodDetail.addMethodName(mName);
			methodDetail.addMidifier(Modifier.modifier(modifiers));
			methodDetail.addSignture(signature);
//			保存方法的参数和参数注解
			try {
				CtClass[] parameterTypes = method.getParameterTypes();
				for (CtClass ctClass : parameterTypes) {
					methodDetail.addParamaterTpes(ctClass.getName());
				}
				Object[][] availableParameterAnnotations = method.getAvailableParameterAnnotations();
				int i=0;
				for (Object[] objects : availableParameterAnnotations) {
					for (Object object : objects) {
						methodDetail.addAnnotataionParameter(object.toString(),i);
					}
					i++;
				}
				i=0;
			} catch (NotFoundException e2) {
			}
//			保存方法的返回值
			try {
				CtClass returnType = method.getReturnType();
				methodDetail.addReturnType(returnType.getName());
			} catch (NotFoundException e1) {}
//			保存声明的异常
			try {
				CtClass[] exceptionTypes = method.getExceptionTypes();
				for (CtClass ctClass : exceptionTypes) {
					methodDetail.addException(ctClass.getName());
				}
			} catch (NotFoundException e) {}
//			保存方法的注解
			Object[] availableAnnotations = method.getAvailableAnnotations();
			for (Object object : availableAnnotations) {
				methodDetail.addAnnotataion(object.toString());
			}
			methods.add(methodDetail);
		}
		detail.addMethods(methods);

	}

	private void addInnerIssue(ClassDetail_StringMode detail, CtClass[] innerClass) {
//		遍历内部类信息并保存内部类名字
		ArrayList<String> inner = new ArrayList<String>();
		for (CtClass inn : innerClass) {
			inner.add(inn.getName());
		}
		detail.addInnerClazz(inner);
	}

	private void addInterfaceIssue(ClassDetail_StringMode detail, CtClass[] interfaces) {
//		遍历接口，并保存接口名字
		ArrayList<String> intfs = new ArrayList<String>();
		for (CtClass intf : interfaces) {
			intfs.add(intf.getName());
		}
		detail.addInterfaces(intfs);
	}

	private void addFieldIssue(ClassDetail_StringMode detail, CtField[] declaredFields) {
		// 遍历字段信息，新建字段细节对象，保存，修饰符，字段名，字段签名（含泛型） ，注解
		for (CtField field : declaredFields) {
			FieldDetail fieldDetail = new FieldDetail();
			
			int modifiers = field.getModifiers();
			String fieldName = field.getName();
			String genericSignature2 = field.getGenericSignature();
			CtClass declaringClass = field.getDeclaringClass();
			fieldDetail.setfieldModify(Modifier.modifier(modifiers));
			fieldDetail.setName(fieldName);
			fieldDetail.setDeclarClass(declaringClass.getName());
			fieldDetail.setSignature(genericSignature2);
			Object[] annotations = field.getAvailableAnnotations();
			for (Object object : annotations) {
				// 遍历字段注解
				fieldDetail.addAnnotaions(object.toString());
			}
			try {
				CtClass type = field.getType();
				fieldDetail.setType(type.getName());
			} catch (NotFoundException e) {
			}finally{
				
				detail.addField(fieldDetail);
			}
//			detail.addField(fieldDetail);
		}
	}

	private void addClassIssue(ClassDetail_StringMode detail, CtClass ctClass) {
		// 保存类名，，是否接口，修饰符，和签名
		String name = ctClass.getName();
		boolean interface1 = ctClass.isInterface();
		int modifiers2 = ctClass.getModifiers();

		String genericSignature = ctClass.getGenericSignature();
		Object[] availableAnnotations = ctClass.getAvailableAnnotations();
		for (Object object : availableAnnotations) {
			detail.addClassAnn(object.toString());
		}
		detail.setClassName(name);
		detail.setInterfaceFlage(interface1);
		detail.setModifier(Modifier.modifier(modifiers2));
		detail.setSignater(genericSignature);
	}

}
