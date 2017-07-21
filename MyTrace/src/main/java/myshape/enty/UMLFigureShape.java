package myshape.enty;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.geometry.Dimension;

public class UMLFigureShape extends Panel {

	public UMLFigureShape() {
		super();
		setRequestFocusEnabled(true);
	}

	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
//		���ֹ����� �ڵ���  layoutʱ ���ã�����Ϊ���أ���ʾ�ؼ���
		/*if(isVisible()){
			if (getLayoutManager() != null) {
				Dimension d = getLayoutManager().getPreferredSize(this, wHint,
						hHint);
				if (d != null)
					return d;
			}
		}
		 return new Dimension(0,0);*/
		return super.getPreferredSize( wHint,  hHint);
	}

	@Override
	public void remove(IFigure figure) {
		@SuppressWarnings("unchecked")
		List<IFigure> list = getChildren();
		for (IFigure iFigure : list) {
			remove(iFigure);
		}
		super.remove(figure);
	}
	@Override
	public Dimension getMaximumSize() {
//		���ֹ����� �ڵ���  layoutʱ ���ã�����Ϊ���أ���ʾ�ؼ���
		if(isVisible())
			return super.getMaximumSize();
		return getSize();
	}

	@Override
	public Dimension getMinimumSize(int wHint, int hHint) {
//		���ֹ����� �ڵ���  layoutʱ ���ã�����Ϊ���أ���ʾ�ؼ���
		if(!isVisible())
			return getSize();
		return super.getMinimumSize(wHint, hHint);
	}

	public void appendMethodsName(Label label) {
		
	}

	public void appendFieldsName(Label label) {
		
	}

	
}
