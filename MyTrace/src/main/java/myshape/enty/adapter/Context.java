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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
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
			CtClass ctClass = pool.makeClass(in);

			CtField[] declaredFields = ctClass.getDeclaredFields();
			CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

			String name = ctClass.getName();
			String packageName = ctClass.getPackageName();
			
			CtClass[] interfaces=null;
			CtClass[] innerClass=null;
			String superClassName =null;
			
			try{
				superClassName= ctClass.getSuperclass().getName();
			}catch(NotFoundException e){
				superClassName="";
			}
			
			try {
					interfaces = ctClass.getInterfaces();
				} catch (NotFoundException e) {
					interfaces=new CtClass []{};
				}
			try {
				innerClass=ctClass.getNestedClasses();
			} catch (NotFoundException e) {
				innerClass=new CtClass []{};
			}
			
			detail = new ClassDetail(name, superClassName, interfaces.length, declaredMethods.length,
					declaredFields.length,innerClass.length);
			detail.addAttribute(declaredFields);
			detail.addInterface(interfaces);
			detail.addMethod(declaredMethods);
			detail.addInnerClass(innerClass);
			detail.setPackageName(packageName);
			
			Packages.add(packageName);
			classDetail.add(detail);
		return detail;
	}

}
