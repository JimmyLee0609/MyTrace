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
//		��¡������ķ���
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
//		��ý���ʱ���ñ�����ɫ
		super.handleFocusGained(event);
		this.setBackgroundColor(focusC);
	}

	@Override
	public void handleFocusLost(FocusEvent event) {
//		ʧȥ����ʱ������ɫ
		super.handleFocusLost(event);
		this.setBackgroundColor(color);
	}


	public myUmlFigure() {
		this(new ToolbarLayout());
	}
	public myUmlFigure(ToolbarLayout layout) {
		this.layout=layout;
//		�½�һ�����������֣��Զ���ķֿ��߿�
		separatorBorder= new SeparatorBorder();
//		�½�ͷ��ǩ����������ͱ߽�
		header=new Label();
		header.setFont(getFont());
		header.setBorder(new MarginBorder(3, 5, 3, 5));
		
//		�½��ֶ�������ͷ���������������ǵ����壬���ò��ֺͱ߿�
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
		
//		��ӱ���Ա�ǩ
		fields.add(new Label("---fields---"),"---fields---");
		methods.add(new Label("---methods---"),"---methods---");
		layout.setStretchMinorAxis(true);
		
//		��ͷ���ֶΣ��ͷ�����ӵ������
		add(header,"head");
		add(fields,"fields");
		add(methods,"methods");
		
//		���ñ���������岼�ֺͱ߿���Ӹ���������ñ�����ɫ��
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
//		�Զ���ָ��߽�
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
//		�½���ǩ��������ķ���д�룬����ӵ�������
		Label label = new Label(string);
		label.setFont(getFont());
		methods.add(label,label.getText());
	}

	public void setClassName(String string) {
//		������д�뵽ͷlabel
		header.setText(string);
	}

	public void appendFieldsName(String string) {
//		�½���ǩ������������ֶ���д�룬����ӵ��ֶ���
		Label label = new Label(string);
		label.setFont(getFont());
		fields.add(label,label.getText());
	}

}
