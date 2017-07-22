package myshape.enty.adapter;

import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import javassist.CtClass;
import myshape.enty.MyMover;
import myshape.enty.myUmlConnection;
import myshape.enty.myUmlFigure;

public class ConnectionAdapter {
	private static ConnectionLayer conn;
	private static FreeformLayer primary;
	private static Context context;
	private static ScalableFreeformLayeredPane root;
	private static Figure showPrimary;
	public ConnectionAdapter(Context context, FreeformLayer primary) {
//		保存原来记录图像的图层,和传入的上下文地址
		ConnectionAdapter.context = context;
		ConnectionAdapter.primary = primary;
		
//		新建窗体设置布局
		Display display = Display.getDefault();
		shell2 = new Shell(display);
		shell2.setLayout(new FillLayout());
//		新建画布设置字体，布局
		FigureCanvas figureCanvas = new FigureCanvas(shell2, SWT.DOUBLE_BUFFERED);
		figureCanvas.setFont(shell2.getFont());
		figureCanvas.setViewport(new FreeformViewport());
		
		root = new ScalableFreeformLayeredPane();
		root.setFont(figureCanvas.getFont());
//		新建连接图层,设置线的连接路径
		conn=new ConnectionLayer();
		conn.setConnectionRouter( new FanRouter());
		
//		新建保存图形的层设置字体，设置布局
		showPrimary = new FreeformLayer();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHorizontal(true);
		flowLayout.setMajorSpacing(20);
		flowLayout.setMinorSpacing(20);
		showPrimary.setLayoutManager(flowLayout);
		showPrimary.setFont(root.getFont());
//		目前没起作用
		new ShortestPathConnectionRouter(showPrimary);
//		保存两个层		
		root.add(showPrimary, "Primary");
		root.add(conn, "conn");
//		设置显示画布的根
		figureCanvas.setContents(root);
		createMenuBar(shell2);
		createFind(shell2);
	}

	private void createFind(Shell shell) {
		final Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem findMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		findMenuItem.setText("查找");
		findMenuItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				new MessageBox(shell,SWT.OK|SWT.ICON_INFORMATION);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	static Shell shell2;

	public static boolean showConnection(Figure figure) {
//		将传入的图形进行类型转化，并获取头的文字
		myUmlFigure temp=(myUmlFigure) figure;
		String text = temp.getHeader().getText();
//		获取图形相关的类的信息
		ClassDetail find = null;
		List<ClassDetail> list = context.getClassDetail();
		for (ClassDetail detail : list) {
			if (detail.getClassName().equals(text)) {
				find = detail;
			}
		}
//		获取扎到的类信息的  父类，  接口， 字段信息
		String superClassName = find.getSuperClassName();
		List<CtClass> interfaceNames = find.getInterfaceNames();
		Set<String> assiation = null;
		try {
			assiation = find.getAssiation();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		克隆传入的图形并保存新的图形图层中
		myUmlFigure clone = temp.clone();
		new MyMover(clone);
		showPrimary.add(clone, "origin");
		
//		获取原来图层的图形信息，遍历，并如找到的类信息进行比对，符合的就存入新图层中，并将它们的关系新建连接图形保存到连接图层
		List<myUmlFigure> children = primary.getChildren();
//		找父类
		if(null!=superClassName) {
			for (myUmlFigure f : children) {
				if (f.getHeader().getText().equals(superClassName)) {
					showConnnect(f, clone,"extends");
				}
			}
		}
//		找接口
		for(CtClass in:interfaceNames) {
			String c = in.getName();
			for (myUmlFigure f : children) {
				if (f.getHeader().getText().equals(c)) {
					showConnnect(f, clone,"implements");
				}
			}
		}
//		找字段
		for (String c : assiation) {
			for (myUmlFigure f : children) {
				if (f.getHeader().getText().equals(c)) {
					showConnnect(f,clone,"have");
				}
			}
		}
		
		shell2.open();
		return true;
	}

	private static void showConnnect(Figure figure, myUmlFigure clone,String relation) {
//		克隆找到的图形，保存到新图形中
		myUmlFigure copy = ((myUmlFigure) figure).clone();
		new MyMover(copy);
		showPrimary.add(copy, copy.getBounds());
//		新建连接图形，保存到连接图层中
		myUmlConnection connection = new myUmlConnection();
		connection.setSourceFigure(clone);
		connection.setEndFigure(copy);
		connection.setRelation(relation);
		conn.add(connection, connection.getBounds());
	}
	private void createMenuBar(Shell shell) {
//		新建菜单栏，并保存到窗体
		final Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
//		新建菜单项
		MenuItem zoomMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		zoomMenuItem.setText("Zoom");
//		新建菜单栏，并保存定义的菜单项
		Menu zoomMenu = new Menu(shell, SWT.DROP_DOWN);
		zoomMenuItem.setMenu(zoomMenu);

		createFixedZoomMenuItem(zoomMenu, "50%", 0.5);
		createFixedZoomMenuItem(zoomMenu, "100%", 1);
		createFixedZoomMenuItem(zoomMenu, "200%", 2);

		createScaleToFitMenuItem(zoomMenu);
	}

	private void createScaleToFitMenuItem(Menu menu) {
//		在菜单栏中新建菜单项
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
//		效果有问题
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
//		新建菜单栏的菜单项，设置菜单的标题，设置选择监听器
		MenuItem menuItem = new MenuItem(menu, SWT.NULL);
		menuItem.setText(text);
		menuItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				窗体被选择时设置根的Zoom
				root.setScale(scale);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
}
