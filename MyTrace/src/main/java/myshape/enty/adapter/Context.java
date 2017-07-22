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
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

public class Context {
	private List<ClassDetail> classDetail = new ArrayList<ClassDetail>();
	private Set<String> Packages = new HashSet<String>();

	
	private static ClassPool pool = ClassPool.getDefault();
	
	public void analyse(InputStream in) throws IOException, RuntimeException, NotFoundException{
		analyseClass(in);
	}
	
	public List<ClassDetail> getClassDetail() {
		return classDetail;
	}

	public Set<String> getPackages() {
		return Packages;
	}

	public void analyse(String path){
		analyse(new File(path));
	}
	
	public void analyse(File file) {
		if(null!=file&&file.exists()){
			if(file.isFile()&&file.getName().endsWith(".class")){
				
				DataInputStream in;
				try {
					in = new DataInputStream(new FileInputStream(file));
					analyseClass(in);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
			else if(file.isDirectory()){
				File[] files = file.listFiles();
				for (File file2 : files) {
					analyse(file2);
				}
			}
		}
	}
	
	
	
	
	public ClassDetail analyseClass(InputStream in) throws IOException, RuntimeException {
		ClassDetail detail=null;
//		解析类文件
			CtClass ctClass = pool.makeClass(in);
//		获取解析到的字段信息   方法信息
			CtField[] declaredFields = ctClass.getDeclaredFields();
			CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
			
			CtClass[] interfaces=null;
			CtClass[] innerClass=null;
			String superClassName =null;
//			获取超类的名字
			try{
				superClassName= ctClass.getSuperclass().getName();
			}catch(NotFoundException e){
				superClassName="";
			}
//			获取接口
			try {
					interfaces = ctClass.getInterfaces();
				} catch (NotFoundException e) {
					interfaces=new CtClass []{};
				}
//			获取内部类
			try {
				innerClass=ctClass.getNestedClasses();
			} catch (NotFoundException e) {
				innerClass=new CtClass []{};
			}
			
//			获取类的名字，修饰符，包名，是否接口，泛型签名
				String name = ctClass.getName();
				boolean interface1 = ctClass.isInterface();
				int modifiers2 = ctClass.getModifiers();
				String packageName = ctClass.getPackageName();
				String genericSignature = ctClass.getGenericSignature();
//			新建 类细节对象, 保存类名，包名，父类名，修饰符，和签名
			detail = new ClassDetail();
			detail.addClassName(name);
			detail.setInterfaceFlage(interface1);
			detail.setModifier(Modifier.modifier(modifiers2));
			detail.setSignater(genericSignature);
			detail.setPackageName(packageName);
			detail.setSuperClassName(superClassName);
			
//				遍历字段。   并将信息转换为文字存入  字段对象
			FieldDetail fieldDetail=null ;
			for(CtField field:declaredFields) {
				fieldDetail= new FieldDetail();
				int modifiers = field.getModifiers();
				String fieldName = field.getName();
				String genericSignature2 = field.getGenericSignature();
				
				fieldDetail.setfieldModify(Modifier.modifier(modifiers));
				fieldDetail.setName(fieldName);
				fieldDetail.setType(genericSignature2);
				
				Object[] annotations = field.getAvailableAnnotations();
				for (Object object : annotations) {
//					遍历字段注解
					fieldDetail.addAnnotaions(object.toString());
				}
			}
			detail.addField(fieldDetail);
//			遍历获得的接口数据并保存
			ArrayList<String>intfs=null;
			for(CtClass intf:interfaces) {
				intfs=new ArrayList<String>();
				intfs.add(intf.getName());
			}
			detail.addInterfaces(intfs);
//			遍历内部类的数据并保存
			ArrayList<String>inner=null;
			for(CtClass inn:innerClass) {
				inner=new ArrayList<String>();
				inner.add(inn.getName());
			}
			detail.addInnerClazz(inner);
//			遍历得到的方法并保存
			ArrayList<MethodDetail> methods=null;
			for(CtMethod method:declaredMethods) {
				methods=new ArrayList<MethodDetail>();
				String mName = method.getName();
				System.out.println(mName);
			}
			detail.addMethods(methods);
			
			/*detail.addInterface(interfaces);
			detail.addInnerClasses(innerClass);
			detail.addMethod(declaredMethods);*/
			
			
			Packages.add(packageName);
			classDetail.add(detail);
		return detail;
	}

}
