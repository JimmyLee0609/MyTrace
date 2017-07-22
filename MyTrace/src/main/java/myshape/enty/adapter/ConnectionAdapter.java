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
//		����ԭ����¼ͼ���ͼ��,�ʹ���������ĵ�ַ
		ConnectionAdapter.context = context;
		ConnectionAdapter.primary = primary;
		
//		�½��������ò���
		Display display = Display.getDefault();
		shell2 = new Shell(display);
		shell2.setLayout(new FillLayout());
//		�½������������壬����
		FigureCanvas figureCanvas = new FigureCanvas(shell2, SWT.DOUBLE_BUFFERED);
		figureCanvas.setFont(shell2.getFont());
		figureCanvas.setViewport(new FreeformViewport());
		
		root = new ScalableFreeformLayeredPane();
		root.setFont(figureCanvas.getFont());
//		�½�����ͼ��,�����ߵ�����·��
		conn=new ConnectionLayer();
		conn.setConnectionRouter( new FanRouter());
		
//		�½�����ͼ�εĲ��������壬���ò���
		showPrimary = new FreeformLayer();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHorizontal(true);
		flowLayout.setMajorSpacing(20);
		flowLayout.setMinorSpacing(20);
		showPrimary.setLayoutManager(flowLayout);
		showPrimary.setFont(root.getFont());
//		Ŀǰû������
		new ShortestPathConnectionRouter(showPrimary);
//		����������		
		root.add(showPrimary, "Primary");
		root.add(conn, "conn");
//		������ʾ�����ĸ�
		figureCanvas.setContents(root);
		createMenuBar(shell2);
		createFind(shell2);
	}

	private void createFind(Shell shell) {
		final Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem findMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		findMenuItem.setText("����");
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
//		�������ͼ�ν�������ת��������ȡͷ������
		myUmlFigure temp=(myUmlFigure) figure;
		String text = temp.getHeader().getText();
//		��ȡͼ����ص������Ϣ
		ClassDetail find = null;
		List<ClassDetail> list = context.getClassDetail();
		for (ClassDetail detail : list) {
			if (detail.getClassName().equals(text)) {
				find = detail;
			}
		}
//		��ȡ����������Ϣ��  ���࣬  �ӿڣ� �ֶ���Ϣ
		String superClassName = find.getSuperClassName();
		List<CtClass> interfaceNames = find.getInterfaceNames();
		Set<String> assiation = null;
		try {
			assiation = find.getAssiation();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		��¡�����ͼ�β������µ�ͼ��ͼ����
		myUmlFigure clone = temp.clone();
		new MyMover(clone);
		showPrimary.add(clone, "origin");
		
//		��ȡԭ��ͼ���ͼ����Ϣ�������������ҵ�������Ϣ���бȶԣ����ϵľʹ�����ͼ���У��������ǵĹ�ϵ�½�����ͼ�α��浽����ͼ��
		List<myUmlFigure> children = primary.getChildren();
//		�Ҹ���
		if(null!=superClassName) {
			for (myUmlFigure f : children) {
				if (f.getHeader().getText().equals(superClassName)) {
					showConnnect(f, clone,"extends");
				}
			}
		}
//		�ҽӿ�
		for(CtClass in:interfaceNames) {
			String c = in.getName();
			for (myUmlFigure f : children) {
				if (f.getHeader().getText().equals(c)) {
					showConnnect(f, clone,"implements");
				}
			}
		}
//		���ֶ�
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
//		��¡�ҵ���ͼ�Σ����浽��ͼ����
		myUmlFigure copy = ((myUmlFigure) figure).clone();
		new MyMover(copy);
		showPrimary.add(copy, copy.getBounds());
//		�½�����ͼ�Σ����浽����ͼ����
		myUmlConnection connection = new myUmlConnection();
		connection.setSourceFigure(clone);
		connection.setEndFigure(copy);
		connection.setRelation(relation);
		conn.add(connection, connection.getBounds());
	}
	private void createMenuBar(Shell shell) {
//		�½��˵����������浽����
		final Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
//		�½��˵���
		MenuItem zoomMenuItem = new MenuItem(menuBar, SWT.CASCADE);
		zoomMenuItem.setText("Zoom");
//		�½��˵����������涨��Ĳ˵���
		Menu zoomMenu = new Menu(shell, SWT.DROP_DOWN);
		zoomMenuItem.setMenu(zoomMenu);

		createFixedZoomMenuItem(zoomMenu, "50%", 0.5);
		createFixedZoomMenuItem(zoomMenu, "100%", 1);
		createFixedZoomMenuItem(zoomMenu, "200%", 2);

		createScaleToFitMenuItem(zoomMenu);
	}

	private void createScaleToFitMenuItem(Menu menu) {
//		�ڲ˵������½��˵���
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
//		Ч��������
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
//		�½��˵����Ĳ˵�����ò˵��ı��⣬����ѡ�������
		MenuItem menuItem = new MenuItem(menu, SWT.NULL);
		menuItem.setText(text);
		menuItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				���屻ѡ��ʱ���ø���Zoom
				root.setScale(scale);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
}
