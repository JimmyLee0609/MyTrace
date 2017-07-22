package myshape.enty;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class UMLFigureShape extends Panel {

	public UMLFigureShape() {
		super();
		setRequestFocusEnabled(true);
	}

	@Override
	public void handleMouseDoubleClicked(MouseEvent event) {
		System.out.println("double click");
		super.handleMouseDoubleClicked(event);
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

}
