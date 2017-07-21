package myshape.enty;

import org.eclipse.draw2d.FocusEvent;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class myUmlFigure extends Panel {
	ToolbarLayout layout;
	UMLFigureShape fields;
	UMLFigureShape methods;
	UMLFigureShape header;
	Color color = new Color(Display.getCurrent(), 255, 255, 6);
	Color focusC=new Color(Display.getCurrent(),255,0,200);
	SeparatorBorder separatorBorder;
	boolean flag=false;
	LineBorder lineBorder=new LineBorder();
	boolean fieldsFlag=false;
	boolean methodsFlag=false;
	
	@Override
	public void handleFocusGained(FocusEvent event) {
		super.handleFocusGained(event);
		this.setBackgroundColor(focusC);
	}

	@Override
	public void handleFocusLost(FocusEvent event) {
		super.handleFocusLost(event);
		this.setBackgroundColor(color);
	}

	@Override
	protected void layout() {
		super.layout();
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
	}

	public myUmlFigure() {
		this(new ToolbarLayout());
	}
	public myUmlFigure(ToolbarLayout layout) {
		this.layout=layout;
//		新建一个工具栏布局，自定义的分开边框

		separatorBorder= new SeparatorBorder();
//		新建组件的头部，设置布局和边框
		header = new UMLFigureShape();
		header.setFont(getFont());
		header.setLayoutManager(layout);
		header.setBorder(new MarginBorder(3, 5, 3, 5));
//		新建字段组件，和方法组件，设置布局和边框
		fields = new UMLFigureShape();
		methods = new UMLFigureShape();
		fields.setFont(getFont());
		methods.setFont(getFont());
		GridData data = new GridData();
		GridLayout gridLayout = new GridLayout(1,false);
		GridLayout gridLayout2 = new GridLayout(1,false);
		fields.setBorder(separatorBorder);
		methods.setBorder(separatorBorder);
		fields.setLayoutManager(gridLayout);
		methods.setLayoutManager(gridLayout2);
		layout.setStretchMinorAxis(false);

//		设置本组件的整体布局和边框，添加个组件，设置背景颜色。
		setBorder(lineBorder);
		setLayoutManager(layout);
		add(header);
		add(fields);
		add(methods);
		setBackgroundColor(color);
		setRequestFocusEnabled(true);
		header.addMouseListener(getHindMouseListener());
		/*fields.setVisible(false);
		methods.setVisible(false);*/
//		fields.setOpaque(false);
	}

	private MouseListener getHindMouseListener() {
		return new MouseListener(){

			@Override
			public void mousePressed(MouseEvent me) {
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if(flag)
					myUmlFigure.this.showDetail();
				else myUmlFigure.this.closeDetal();
			}
			@Override
			public void mouseDoubleClicked(MouseEvent me) {
			}
			
		};
	}
	@Override
	public void remove(IFigure figure) {
		remove(header);
		remove(fields);
		remove(methods);
		color.dispose();
		focusC.dispose();
		layout=null;
		separatorBorder=null;
		lineBorder=null;
		super.remove(figure);
	}
	protected void closeDetal() {
		fields.setVisible(false);
		methods.setVisible(false);
		layout();
		repaint();
		flag=true;
	}

	protected void showDetail() {
		fields.setVisible(true);
		methods.setVisible(true);
		fields.setOpaque(true);
		layout();
		repaint();
		flag=false;
	}

	class SeparatorBorder extends MarginBorder {
		public SeparatorBorder() {
			super(3, 5, 3, 5);
		}
		@Override
		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			Rectangle rectangle = getPaintRectangle(figure, insets);
			graphics.drawLine(rectangle.getTopLeft(), rectangle.getTopRight());
		}

	}


	public void appendMethodsName(String string) {
		Label label = new Label(string);
		label.setLayoutManager(layout);
		methods.add(label);
	}

	public void setClassName(String string) {
		Label label = new Label(string);
		label.setLayoutManager(layout);
		header.add(label);
	}

	public void appendFieldsName(String string) {
		Label label = new Label(string);
		label.setLayoutManager(layout);
		fields.add(label);
	}

}
