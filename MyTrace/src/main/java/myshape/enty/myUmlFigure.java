package myshape.enty;

import java.util.List;

import org.eclipse.draw2d.FocusEvent;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class myUmlFigure extends Panel implements Cloneable{
	ToolbarLayout layout;
	UMLFigureShape fields;
	UMLFigureShape methods;
	Label header;
	public Label getHeader() {
		return header;
	}
	public myUmlFigure clone() {
//		克隆本组件的方法
		myUmlFigure umlFigure = new myUmlFigure();
		
		umlFigure.setClassName(this.header.getText());
		
		List<Label> fieldC = fields.getChildren();
		for(Label f:fieldC) {
			umlFigure.appendFieldsName(f.getText());
		}
		List<Label> mc = methods.getChildren();
		for(Label m:mc) {
			umlFigure.appendMethodsName(m.getText());
		}
		return umlFigure;
	}
	public UMLFigureShape getFields() {
		return fields;
	}

	public UMLFigureShape getMethods() {
		return methods;
	}

	Color color = new Color(Display.getCurrent(), 255, 255, 6);
	Color focusC=new Color(Display.getCurrent(),255,0,200);
	SeparatorBorder separatorBorder;
	boolean flag=false;
	LineBorder lineBorder=new LineBorder();
	boolean fieldsFlag=false;
	boolean methodsFlag=false;
	
	@Override
	public void handleFocusGained(FocusEvent event) {
//		获得焦点时设置背景颜色
		super.handleFocusGained(event);
		this.setBackgroundColor(focusC);
	}

	@Override
	public void handleFocusLost(FocusEvent event) {
//		失去焦点时设置颜色
		super.handleFocusLost(event);
		this.setBackgroundColor(color);
	}


	public myUmlFigure() {
		this(new ToolbarLayout());
	}
	public myUmlFigure(ToolbarLayout layout) {
		this.layout=layout;
//		新建一个工具栏布局，自定义的分开边框
		separatorBorder= new SeparatorBorder();
//		新建头标签，设置字体和边界
		header=new Label();
		header.setFont(getFont());
		header.setBorder(new MarginBorder(3, 5, 3, 5));
		
//		新建字段组件，和方法组件，设置它们的字体，设置布局和边框
		fields = new UMLFigureShape();
		methods = new UMLFigureShape();
		fields.setFont(getFont());
		methods.setFont(getFont());
		fields.setBorder(separatorBorder);
		methods.setBorder(separatorBorder);
		ToolbarLayout layout2 = new ToolbarLayout();
		ToolbarLayout layout3 = new ToolbarLayout();
		fields.setLayoutManager(layout2);
		methods.setLayoutManager(layout3);
		
//		添加标记性标签
		fields.add(new Label("---fields---"),"---fields---");
		methods.add(new Label("---methods---"),"---methods---");
		layout.setStretchMinorAxis(true);
		
//		将头，字段，和方法添加到组件中
		add(header,"head");
		add(fields,"fields");
		add(methods,"methods");
		
//		设置本组件的整体布局和边框，添加个组件，设置背景颜色。
		setBorder(lineBorder);
		setLayoutManager(layout);
		setBackgroundColor(color);
		setRequestFocusEnabled(true);
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

	class SeparatorBorder extends MarginBorder {
//		自定义分隔边界
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
//		新建标签并将传入的方法写入，并添加到方法区
		Label label = new Label(string);
		label.setFont(getFont());
		methods.add(label,label.getText());
	}

	public void setClassName(String string) {
//		将类名写入到头label
		header.setText(string);
	}

	public void appendFieldsName(String string) {
//		新建标签，并将传入的字段名写入，并添加到字段区
		Label label = new Label(string);
		label.setFont(getFont());
		fields.add(label,label.getText());
	}

}
