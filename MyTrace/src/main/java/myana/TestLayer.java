package myana;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class TestLayer {
	private static FigureCanvas canvas;
	private static GridLayout gridLayout;
	private GridData layoutData;
	private Canvas canvas2;
	
	private ScalableFreeformLayeredPane root=null;
	private FreeformLayer primary=null;
	
	private IFigure getContents() {
		if(root!=null) {
			return root;
		}
		root=new ScalableFreeformLayeredPane();
//				总是需要给子类设置字体，以便label计算prefereed size
		root.setFont(canvas.getFont());
		
		primary=new FreeformLayer();
		primary.setLayoutManager(new FreeformLayout());
		
		primary.setFont(root.getFont());
		root.add(primary,"Primary");
		
//				将解析到的信息转化成图形并添加到primary
		addContent();
		return root;
	}
	private void addContent() {
		Figure figure = new Figure();
		figure.setFont(primary.getFont());
		
		Label label = new Label();
		label.setFont(figure.getFont());
		Label label2 = new Label();
		label2.setFont(figure.getFont());
		Label label3 = new Label();
		label3.setFont(figure.getFont());
		label.setText("Label11111111111111111111111111111111111111111111111111111111");
		label2.setText("Label2");
		label3.setText("Label3");
		
		label.setBackgroundColor(ColorConstants.black);
		label2.setBackgroundColor(ColorConstants.blue);
		label3.setBackgroundColor(ColorConstants.red);
		
		
		org.eclipse.draw2d.GridLayout layout = new org.eclipse.draw2d.GridLayout();
		ToolbarLayout layout2 = new ToolbarLayout();
		figure.setLayoutManager(layout2);
		new  org.eclipse.draw2d.GridData();
		figure.add(label,label.getText());
		figure.add(label2,label.getText());
		figure.add(label3,label.getText());
		figure.setMinimumSize(label.getPreferredSize().union(label2.getPreferredSize()).union(label3.getPreferredSize()));
		figure.setBackgroundColor(ColorConstants.red);
		primary.add(figure,new Rectangle(new Point(100,100),figure.getPreferredSize()));
	}
	public static void main(String[] args) {
		TestLayer view = new TestLayer();
		view.run();
	}


	private void run() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		gridLayout = new GridLayout();
		layoutData = new GridData(GridData.FILL_BOTH);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		shell.setFont(new Font(display,new FontData()));
		
		// 新建 画布 组件
		canvas2 = new Canvas(shell,SWT.DOUBLE_BUFFERED);
		canvas2.setFont(shell.getFont());
		// 为画布和父类组件设置布局管理器
//		canvas2.setLayoutData(layoutData);
		canvas2.setLayout(layout);
		
		
		
//		LightweightSystem canvas = new LightweightSystem(canvas2);
		
		canvas = new FigureCanvas(canvas2, SWT.V_SCROLL|SWT.DOUBLE_BUFFERED);
		canvas.setFont(canvas2.getFont());
		canvas.setViewport(new FreeformViewport());
		
		createMenuBar(shell);
		getContents();
		canvas.setContents(root);
		
//		canvas.setLayout(gridLayout);

		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
	private void createMenuBar(Shell shell) {
		final Menu menuBar=new Menu(shell,SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem zoomMenuItem = new MenuItem(menuBar,SWT.CASCADE);
		zoomMenuItem.setText("Zoom");
		Menu zoomMenu = new Menu(shell,SWT.DROP_DOWN);
		zoomMenuItem.setMenu(zoomMenu);
		
		createFixedZoomMenuItem(zoomMenu,"50%",0.5);
		createFixedZoomMenuItem(zoomMenu,"100%",1);
		createFixedZoomMenuItem(zoomMenu,"200%",2);
		
		createScaleToFitMenuItem(zoomMenu);
	}
	private void createScaleToFitMenuItem(Menu menu) {
		MenuItem menuItem = new MenuItem(menu,SWT.NULL);
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
		FreeformViewport viewport=(FreeformViewport)root.getParent();
		Rectangle viewArea = viewport.getClientArea();
		root.setScale(1);
		root.getFreeformExtent().union(0,0);
		double wScale=(double)viewArea.width;
		double hScale=(double)viewArea.height;
		double newScale = Math.min(wScale, hScale);
		
		root.setScale(newScale);
	}
	private void createFixedZoomMenuItem(Menu menu, String text, double scale) {
		MenuItem menuItem = new MenuItem(menu,SWT.NULL);
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
