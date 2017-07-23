package myana;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import myshape.enty.MyMover;
import myshape.enty.myUmlFigure;
import myshape.enty.adapter.ClassDetail_StringMode;
import myshape.enty.adapter.ConnectionAdapter;
import myshape.enty.adapter.ConstructorDetail;
import myshape.enty.adapter.Context;
import myshape.enty.adapter.FieldDetail;
import myshape.enty.adapter.MethodDetail;

public class UmlView {
	private static FigureCanvas canvas;
	private ScalableFreeformLayeredPane root = null;
	private FreeformLayer primary = null;

	private String path = "C:\\Users\\cobbl\\git\\MyTrace\\MyTrace\\target";
//	private String path = "D:\\oxygenEclipse\\BTrace\\Genealogy\\bin\\com\\scr";

	public UmlView() {
	}

	private IFigure getContents() {
		if (root != null) {
			return root;
		}
//		新建层面板，设置字体
		root = new ScalableFreeformLayeredPane();
		// 总是需要给子类设置字体，以便label计算prefereed size
		root.setFont(canvas.getFont());
//		新建图形的层，设置流式布局，字体
		primary = new FreeformLayer();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHorizontal(true);
		flowLayout.setMajorSpacing(20);
		flowLayout.setMinorSpacing(20);
		primary.setLayoutManager(flowLayout);
		primary.setFont(root.getFont());
		
		// 将解析到的信息转化成图形并添加到primary
		addAllClass();
		
//		将图形层保存到面板
		root.add(primary, "Primary");
		return root;
	}
	Context context;
	private void addAllClass() {
//		新建一个上下文
		context= new Context();
//		解析路径中的文件
		context.analyse(path);
//		遍历解析到的文件
		List<ClassDetail_StringMode> detail = context.getClassDetail();
		for (ClassDetail_StringMode classDetail : detail) {
//			后去解析到的类名
			String className = classDetail.getClassName();
			if (!className.contains("$")) {
//				新建类对应的图形，设置字体，类名
				myUmlFigure figure = new myUmlFigure();
				new MyMover(figure);
				figure.setFont(primary.getFont());
				figure.setClassName(className);
//				将解析到的字段信息添加到图形中
				ArrayList<FieldDetail> fields = classDetail.getFields();
				figure.appendFieldsName("=======fields======");
				for (FieldDetail ctField : fields) {
					figure.appendFieldsName(ctField.getFieldName());
				}
//				将解析到的构造器添加到图形中
				figure.appendMethodsName("=======constructors=======");
				ArrayList<ConstructorDetail> constructors = classDetail.getConstructors();
				for (ConstructorDetail constructorDetail : constructors) {
					figure.appendMethodsName(constructorDetail.toString());
				}
//				将解析到的方法信心添加到图形中
				figure.appendMethodsName("=======method=======");
				 ArrayList<MethodDetail> methodDetails = classDetail.getMethodDetails();
				for (MethodDetail ctMethod : methodDetails) {
					figure.appendMethodsName(ctMethod.toString());
				}
//				将内部类保存到图片中
				figure.appendInnerClass("======innerClass=======");
				ArrayList<String> innerClazz = classDetail.getInnerClazz();
				for (String ctClass : innerClazz) {
					figure.appendInnerClass(ctClass);
				}
//				图形图层添加图形
				primary.add(figure, className);
			}
		}
	}

	// ====================================================================================
	public static void main(String[] args) {
		UmlView view = new UmlView();
		view.run();
	}

	private void run() {
//		新建窗体，设置布局和字体
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		Shell shell2=new Shell(display);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		shell.setFont(new Font(display, new FontData()));
//		新建画布，设置字体，可视区
		canvas = new FigureCanvas(shell, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		canvas.setFont(shell.getFont());
		FreeformViewport viewport = new FreeformViewport();
		canvas.setViewport(viewport);
//		新建图层，图形信息
		getContents();
//		新建一个配适器，用于键新的窗体来装载选择的图形和其关联的图形
		new ConnectionAdapter(shell2,context,primary);
//		设置内容，新建菜单栏
		canvas.setContents(root);
		createMenuBar(shell);
		shell.open();
		while (!shell.isDisposed()&&!shell2.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
	private ConnectionLayer connections;

	private void createMenuBar(Shell shell) {
		final Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem zoomMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		zoomMenuItem.setText("Zoom");
		Menu zoomMenu = new Menu(shell, SWT.DROP_DOWN);
		zoomMenuItem.setMenu(zoomMenu);
		createFixedZoomMenuItem(zoomMenu, "50%", 0.5);
		createFixedZoomMenuItem(zoomMenu, "100%", 1);
		createFixedZoomMenuItem(zoomMenu, "200%", 2);
		createScaleToFitMenuItem(zoomMenu);
	}

	private void createScaleToFitMenuItem(Menu menu) {
		MenuItem menuItem = new MenuItem(menu, SWT.NULL);
		menuItem.setText("Scale to fit");
		menuItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scaleToFit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

	}

	private void scaleToFit() {
		FreeformViewport viewport = (FreeformViewport) root.getParent();
		org.eclipse.draw2d.geometry.Rectangle viewArea = viewport.getClientArea();
		root.setScale(1);
		root.getFreeformExtent().union(0, 0);
		double wScale = (double) viewArea.width;
		double hScale = (double) viewArea.height;
		double newScale = Math.min(wScale, hScale);

		root.setScale(newScale);
	}

	private void createFixedZoomMenuItem(Menu menu, String text, double scale) {
		MenuItem menuItem = new MenuItem(menu, SWT.NULL);
		menuItem.setText(text);
		menuItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				root.setScale(scale);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
}
