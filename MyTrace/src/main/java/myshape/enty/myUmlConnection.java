package myshape.enty;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FocusEvent;
import org.eclipse.draw2d.FocusListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GraphicsSource;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class myUmlConnection extends PolylineConnection {
	private myUmlFigure source;
	private myUmlFigure end;
	private ChopboxAnchor sourceaAnchor;
	private ChopboxAnchor endAnchor;
	private String relation;
	private PolygonDecoration decoration;
	private boolean flag=false;
	Color color = new Color(Display.getCurrent(), 255, 255, 6);
	Color focusC=new Color(Display.getCurrent(),255,0,200);
	public myUmlConnection() {
		super();
		addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClicked(MouseEvent me) {
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if(myUmlConnection.this.flag){
					myUmlConnection.this.source.setBackgroundColor(color);;
					myUmlConnection.this.end.setBackgroundColor(focusC);;
					flag=!flag;
				}else{
					myUmlConnection.this.source.setBackgroundColor(focusC);;
					myUmlConnection.this.end.setBackgroundColor(color);;
					flag=!flag;
				}
			}
			@Override
			public void mouseReleased(MouseEvent me) {
			}
			
		});
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent fe) {
				myUmlConnection.this.setForegroundColor(ColorConstants.red);
			}
			
			@Override
			public void focusGained(FocusEvent fe) {
				myUmlConnection.this.setForegroundColor(ColorConstants.black);
			}
		});
		setLineWidth(2);
	}

	public myUmlConnection(myUmlFigure source, myUmlFigure end,String relation) {
		this();
		this.source = source;
		this.end = end;
		this.relation=relation;
		setCon(relation);
	}

	public myUmlFigure getEndFigure() {
		return end;
	}

	public String getRelation() {
		return relation;
	}

	public myUmlFigure getSourceFigure() {
		return source;
	}

	public void setCon(String relation) {
//		新建一个三角箭头
		decoration = new PolygonDecoration();
//		箭头的大小
		decoration.setScale(10, 6);
		

		switch(relation){
			case "extends":
//				设置箭头的线条
				decoration.setLineStyle(SWT.LINE_SOLID);
//				箭头是否实心
				decoration.setFill(true);
//				为线条设置三角箭头，设置连线的线条类型，这个是从target指向source
				this.setTargetDecoration(decoration);
				this.setLineStyle(SWT.LINE_SOLID);
				decoration.setForegroundColor(ColorConstants.darkBlue);
				break;
			case"implements":
//				设置箭头的线条
				decoration.setLineStyle(SWT.LINE_SOLID);
//				箭头是否实心
				decoration.setFill(false);
//				为线条设置三角箭头，设置连线的线条类型，这个是从target指向source
				this.setTargetDecoration(decoration);
				this.setLineStyle(SWT.LINE_DOT);
				break;
			case "have":
				decoration.setLineStyle(SWT.LINE_SOLID);
				decoration.setFill(false);
				this.setSourceDecoration(decoration);
				this.setLineStyle(SWT.LINE_SOLID);
//				组合关系的三角号要小一点，有颜色
				decoration.setScale(8, 5);
				decoration.setFill(true);
				decoration.setForegroundColor(ColorConstants.green);
				break;
				
			default:
				throw new RuntimeException();
		}
	}

	public myUmlConnection setEndFigure(myUmlFigure end) {
//		保存图形引用，创建锚点，为本连接添加锚点
		this.end = end;
		endAnchor = new ChopboxAnchor(end);
		this.setTargetAnchor(endAnchor);
		return this;
	}

	public myUmlConnection setSourceFigure(myUmlFigure source) {
//		保存图形引用，创建锚点，为本连接添加锚点
		this.source = source;
		sourceaAnchor = new ChopboxAnchor(source);
		this.setSourceAnchor(sourceaAnchor);
		return this;
	}
}
