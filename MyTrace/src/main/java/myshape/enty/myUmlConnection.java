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
//		�½�һ�����Ǽ�ͷ
		decoration = new PolygonDecoration();
//		��ͷ�Ĵ�С
		decoration.setScale(10, 6);
		

		switch(relation){
			case "extends":
//				���ü�ͷ������
				decoration.setLineStyle(SWT.LINE_SOLID);
//				��ͷ�Ƿ�ʵ��
				decoration.setFill(true);
//				Ϊ�����������Ǽ�ͷ���������ߵ��������ͣ�����Ǵ�targetָ��source
				this.setTargetDecoration(decoration);
				this.setLineStyle(SWT.LINE_SOLID);
				decoration.setForegroundColor(ColorConstants.darkBlue);
				break;
			case"implements":
//				���ü�ͷ������
				decoration.setLineStyle(SWT.LINE_SOLID);
//				��ͷ�Ƿ�ʵ��
				decoration.setFill(false);
//				Ϊ�����������Ǽ�ͷ���������ߵ��������ͣ�����Ǵ�targetָ��source
				this.setTargetDecoration(decoration);
				this.setLineStyle(SWT.LINE_DOT);
				break;
			case "have":
				decoration.setLineStyle(SWT.LINE_SOLID);
				decoration.setFill(false);
				this.setSourceDecoration(decoration);
				this.setLineStyle(SWT.LINE_SOLID);
//				��Ϲ�ϵ�����Ǻ�ҪСһ�㣬����ɫ
				decoration.setScale(8, 5);
				decoration.setFill(true);
				decoration.setForegroundColor(ColorConstants.green);
				break;
				
			default:
				throw new RuntimeException();
		}
	}

	public myUmlConnection setEndFigure(myUmlFigure end) {
//		����ͼ�����ã�����ê�㣬Ϊ���������ê��
		this.end = end;
		endAnchor = new ChopboxAnchor(end);
		this.setTargetAnchor(endAnchor);
		return this;
	}

	public myUmlConnection setSourceFigure(myUmlFigure source) {
//		����ͼ�����ã�����ê�㣬Ϊ���������ê��
		this.source = source;
		sourceaAnchor = new ChopboxAnchor(source);
		this.setSourceAnchor(sourceaAnchor);
		return this;
	}
}
