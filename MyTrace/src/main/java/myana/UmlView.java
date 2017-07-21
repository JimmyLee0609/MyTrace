package myana;

import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import javassist.CtField;
import javassist.CtMethod;
import myshape.enty.myUmlFigure;
import myshape.enty.adapter.ClassDetail;
import myshape.enty.adapter.Context;

public class UmlView {
	private static FigureCanvas canvas;
	private static GridLayout gridLayout;
	private GridData layoutData;
	private IFigure contents;
	private Canvas canvas2;
	LightweightSystem system;
	private String path="D:\\oxygenEclipse\\BTrace\\Genealogy\\bin\\com\\scr";
	
	public UmlView() {
	}

	public void createPartControl(Composite parent) {
		// 新建布局管理器
				gridLayout = new GridLayout(5, false);
				layoutData = new GridData(GridData.FILL_BOTH);
				// 新建 画布 组件
				canvas2 = new Canvas(parent, SWT.DOUBLE_BUFFERED);

				// 为画布和父类组件设置布局管理器
				parent.setLayout(gridLayout);
				parent.setLayoutData(layoutData);
				canvas2.setLayoutData(layoutData);
				canvas2.setLayout(gridLayout);

				IFigure contents2 = getContents();

				system = new LightweightSystem(canvas2);
				// system.setContents(contents2);
				// 新建图形容器
				canvas = new FigureCanvas(canvas2);
				//
				// 为图形容器设置布局管理
				canvas.setLayout(gridLayout);
				canvas.setLayoutData(layoutData);

				// canvas.getViewport().setContentsTracksHeight(true);
				// canvas.getViewport().setContentsTracksWidth(true);
				// 为图形管理器设置内容
				canvas.setContents(contents2);

	}

	private IFigure getContents() {
		// 新建一个图形组件容器，为容器设置边框
				contents = new Panel();
				contents.setBorder(new LineBorder());
				// 新建流式布局管理器，为容器设置管理器

				org.eclipse.draw2d.GridLayout Layout = new org.eclipse.draw2d.GridLayout(5, false);

				Layout.horizontalSpacing = 15;
				Layout.marginHeight = 15;
				Layout.marginWidth = 15;
				Layout.verticalSpacing = 15;
			
				addAllClass();
				
				contents.setLayoutManager(Layout);

				return contents;
	}

	private void addAllClass() {
		Context context = new Context();
		
		context.analyse(path);
		List<ClassDetail> detail = context.getClassDetail();
		ToolbarLayout layout = new ToolbarLayout();
		for (ClassDetail classDetail : detail) {
			String className = classDetail.getClassName();
			if(!className.contains("$")){
				myUmlFigure figure = new myUmlFigure();
				figure.setFont(contents.getFont());
				figure.setClassName(className);
				List<CtField> attributes = classDetail.getAttributes();
				for (CtField ctField : attributes) {
					figure.appendFieldsName(ctField.getName());
				}
				List<CtMethod> methods = classDetail.getMethods();
				for (CtMethod ctMethod : methods) {
					figure.appendMethodsName(ctMethod.getName());
				}
				contents.add(figure);
			}
		}
	}
//====================================================================================
	public static void main(String[] args) {
		UmlView view = new UmlView();
		view.run();
	}


	private void run() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		gridLayout = new GridLayout();
		layoutData = new GridData(GridData.FILL_BOTH);
		shell.setLayout(gridLayout);
		shell.setFont(new Font(display,new FontData()));
		
		// 新建 画布 组件
		canvas2 = new Canvas(shell,SWT.NONE);
		canvas2.setFont(shell.getFont());
		// 为画布和父类组件设置布局管理器
		canvas2.setLayoutData(layoutData);
		canvas2.setLayout(gridLayout);
		
		
		
		LightweightSystem system2 = new LightweightSystem(canvas2);
		
		
		canvas = new FigureCanvas(canvas2, SWT.V_SCROLL);
		canvas.setFont(canvas2.getFont());
		
		
		IFigure root = getContents();
//		总是需要给子类设置字体，以便label计算prefereed size
		root.setFont(canvas.getFont());
		
//		canvas.getLightweightSystem().setContents(root);
		canvas.setContents(root);
		
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.setLayout(gridLayout);

		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
}
