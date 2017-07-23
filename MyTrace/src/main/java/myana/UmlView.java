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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
	public static MessageBox msgb;
//	private String path = "C:\\Users\\cobbl\\git\\MyTrace\\MyTrace\\target";
	private String path = "C:\\Users\\cobbl\\Desktop\\�����ļ�\\�鼮\\Eclipse\\draw2d\\org.eclipse.draw2d_3.10.100.201606061308\\org\\eclipse";

	public UmlView() {
	}

	private IFigure getContents() {
		if (root != null) {
			return root;
		}
//		�½�����壬��������
		root = new ScalableFreeformLayeredPane();
		// ������Ҫ�������������壬�Ա�label����prefereed size
		root.setFont(canvas.getFont());
//		�½�ͼ�εĲ㣬������ʽ���֣�����
		primary = new FreeformLayer();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHorizontal(true);
		flowLayout.setMajorSpacing(20);
		flowLayout.setMinorSpacing(20);
		primary.setLayoutManager(flowLayout);
		primary.setFont(root.getFont());
		
		// ������������Ϣת����ͼ�β���ӵ�primary
		addAllClass();
		
//		��ͼ�β㱣�浽���
		root.add(primary, "Primary");
		return root;
	}
	Context context;
	private void addAllClass() {
//		�½�һ��������
		context= new Context();
//		����·���е��ļ�
		context.analyse(path);
//		�������������ļ�
		List<ClassDetail_StringMode> detail = context.getClassDetail();
		for (ClassDetail_StringMode classDetail : detail) {
//			��ȥ������������
			String className = classDetail.getClassName();
			if (!className.contains("$")) {
//				�½����Ӧ��ͼ�Σ��������壬����
				myUmlFigure figure = new myUmlFigure();
				new MyMover(figure);
				figure.setFont(primary.getFont());
				figure.setClassName(className);
//				�����������ֶ���Ϣ��ӵ�ͼ����
				ArrayList<FieldDetail> fields = classDetail.getFields();
				figure.appendFieldsName("=======fields======");
				for (FieldDetail ctField : fields) {
					figure.appendFieldsName(ctField.toString());
				}
//				���������Ĺ�������ӵ�ͼ����
				figure.appendMethodsName("=======constructors=======");
				ArrayList<ConstructorDetail> constructors = classDetail.getConstructors();
				for (ConstructorDetail constructorDetail : constructors) {
					figure.appendMethodsName(constructorDetail.toString());
				}
//				���������ķ���������ӵ�ͼ����
				figure.appendMethodsName("=======method=======");
				 ArrayList<MethodDetail> methodDetails = classDetail.getMethodDetails();
				for (MethodDetail ctMethod : methodDetails) {
					figure.appendMethodsName(ctMethod.toString());
				}
//				���ڲ��ౣ�浽ͼƬ��
				figure.appendInnerClass("======innerClass=======");
				ArrayList<String> innerClazz = classDetail.getInnerClazz();
				for (String ctClass : innerClazz) {
					figure.appendInnerClass(ctClass);
				}
//				ͼ��ͼ�����ͼ��
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
//		�½����壬���ò��ֺ�����
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		Shell shell2=new Shell(display);
		
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		shell.setFont(new Font(display, new FontData()));
		
//		�½��������������壬������
		canvas = new FigureCanvas(shell, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		canvas.setFont(shell.getFont());
		FreeformViewport viewport = new FreeformViewport();
		canvas.setViewport(viewport);
//		�½�ͼ�㣬ͼ����Ϣ
		getContents();
//		�½�һ�������������ڼ��µĴ�����װ��ѡ���ͼ�κ��������ͼ��
		new ConnectionAdapter(shell2,context,primary);
//		�������ݣ��½��˵���
		canvas.setContents(root);
		createMenuBar(shell);
		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
	
	 Button button;
	 Text text;
	 Shell shell23;

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
		MenuItem find = new MenuItem(menuBar, SWT.CASCADE);
//		�������Ҳ˵�������ʽ�Ĵ��ڣ�
		find.setText("����");
		find.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display default1 = Display.getDefault();
				shell23= new Shell(default1);
				createFind(shell23);
			}
			
			private void createFind(Composite shell) {
//				�½����壬����ı�����򣬺Ͱ�ť
				shell.setLayout(new FillLayout());
				shell.setSize(300, 70);
				text= new Text(shell,SWT.SEARCH|SWT.ICON_SEARCH|SWT.ICON_CANCEL);
				button= new Button(shell,SWT.NATIVE);
				button.setText("button");
				button.setVisible(true);
				text.setVisible(true);
				text.setSize(100, 100);
				button.setSize(100, 100);
//				��Ӱ�ťѡ���¼�
				button.addSelectionListener(new SelectionListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void widgetSelected(SelectionEvent e) {
//						����ťѡ��ʱ�����ı����е����ݶ�ȡ������Ȼ����ҳ���Ӧ��ͼ��
						String text2 = text.getText();
						if(text2==null||text2=="") return;
						List<myUmlFigure> children = primary.getChildren();
						for (myUmlFigure myUmlFigure : children) {
							String text3 = myUmlFigure.getHeader().getText();
							int indexOf = text3.lastIndexOf(".");
							if(text2.equalsIgnoreCase(text3)) {
								myUmlFigure.requestFocus();
							}else if(indexOf>0) {
								if(text2.equalsIgnoreCase(text3.substring(indexOf+1, text3.length()))) {
									myUmlFigure.requestFocus();
								}
							}
						}
					}
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});
				shell23.open();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
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
