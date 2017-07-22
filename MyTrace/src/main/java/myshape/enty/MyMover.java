package myshape.enty;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import myshape.enty.adapter.ConnectionAdapter;

public class MyMover implements MouseListener, MouseMotionListener {
	private final Figure figure;
	private boolean flage = true;
	Dimension preferredSize;
	private boolean draggerFlage = false;

	public MyMover(Figure figure) {
		this.figure = figure;
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		rewrite(event);
		draggerFlage = true;
	}

	private void rewrite(MouseEvent event) {
		if (location == null)
			return;
		Point newLocation = event.getLocation();
		if (newLocation == null) {
			return;
		}
		Dimension offset = newLocation.getDifference(location);
		if (offset.width == 0 && offset.height == 0) {
			return;
		}
		location = newLocation;
		UpdateManager updateManager = figure.getUpdateManager();
		LayoutManager layoutManager = figure.getParent().getLayoutManager();

		Rectangle bounds = figure.getBounds();
		updateManager.addDirtyRegion(figure.getParent(), bounds);
		// Ϊ�˱��ⲻ�ܻ�ӭ��Ӱ�죬��ȡ����
		bounds.getCopy().translate(offset.width, offset.height);
		layoutManager.setConstraint(figure, bounds);
		figure.translate(offset.width, offset.height);
		updateManager.addDirtyRegion(figure.getParent(), bounds);
		// consume���Ա���������¼���͸��
		event.consume();
	}

	@Override
	public void mouseEntered(MouseEvent event) {

	}

	@Override
	public void mouseExited(MouseEvent event) {

	}

	@Override
	public void mouseHover(MouseEvent event) {

	}

	@Override
	public void mouseMoved(MouseEvent event) {
	}
	
	@Override
	public void mouseDoubleClicked(MouseEvent event) {
		doubleClick=true;
		if (figure instanceof myUmlFigure) {
			 ConnectionAdapter.showConnection(figure);
		}
	}
	private Point location;

	@Override
	public void mousePressed(MouseEvent event) {
		location = event.getLocation();
		event.consume();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (location == null)
			return;
		location = null;
		if (!draggerFlage&&!doubleClick) {
			UpdateManager updateManager = figure.getUpdateManager();
			LayoutManager layoutManager = figure.getParent().getLayoutManager();
			Rectangle bounds = figure.getBounds();
			if (figure instanceof myUmlFigure) {
				myUmlFigure myfigure = (myUmlFigure) figure;
				if (flage) {
					// ((myUmlFigure) figure).showDetail();
					flage = false;
					preferredSize = myfigure.getPreferredSize();
					Dimension size = myfigure.getHeader().getPreferredSize();
					updateManager.addDirtyRegion(figure.getParent(), bounds);
					bounds.getCopy().resize(size);
					// Ϊ�˱��ⲻ�ܻ�ӭ��Ӱ�죬��ȡ����
					layoutManager.setConstraint(figure, bounds);
					figure.setPreferredSize(size);
					updateManager.addDirtyRegion(figure.getParent(), bounds);
					// consume���Ա���������¼���͸��
					event.consume();
				} else {
					// ((myUmlFigure) figure).closeDetal();
					flage = true;
					updateManager.addDirtyRegion(figure.getParent(), bounds);

					bounds.getCopy().resize(preferredSize);
					// Ϊ�˱��ⲻ�ܻ�ӭ��Ӱ�죬��ȡ����
					layoutManager.setConstraint(figure, bounds);
					figure.setPreferredSize(preferredSize);
					updateManager.addDirtyRegion(figure.getParent(), bounds);
					// consume���Ա���������¼���͸��
					event.consume();
				}
			}
		}
		event.consume();
		draggerFlage = false;
		doubleClick=false;
	}
boolean doubleClick=false;
}
