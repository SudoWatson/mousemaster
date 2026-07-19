package mousemaster.qt;

import io.qt.core.QObject;
import io.qt.core.QRect;
import io.qt.core.Qt;
import io.qt.gui.QColor;
import io.qt.gui.QPaintEvent;
import io.qt.gui.QPainter;
import io.qt.widgets.QWidget;

public class TransparentWindow extends QWidget {

    private QColor backgroundColor;
    private QRect backgroundRect;

    public TransparentWindow() {
        // FramelessWindowHint: no title bar or border.
        // X11BypassWindowManagerHint: bypass tiling WMs (e.g. Hyprland via XWayland) so the
        // window floats at the exact geometry we specify rather than being tiled.
        // WindowStaysOnTopHint: appear above all other windows.
        setWindowFlags(Qt.WindowType.FramelessWindowHint,
                Qt.WindowType.X11BypassWindowManagerHint,
                Qt.WindowType.WindowStaysOnTopHint);
        setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground);
    }

    public void setBackground(QColor color, QRect rect) {
        if (this.backgroundRect != null)
            this.backgroundRect.dispose();
        this.backgroundColor = color;
        this.backgroundRect = rect;
    }

    @Override
    protected void paintEvent(QPaintEvent event) {
        if (backgroundColor != null) {
            QPainter painter = new QPainter(this);
            painter.fillRect(backgroundRect, backgroundColor);
            painter.end();
            painter.dispose();
        }
    }

    public void hideChildren() {
        for (QObject child : children()) {
            if (child instanceof QWidget widget) {
                widget.hide();
            }
        }

    }

    public void clearWindow() {
        for (QObject child : children()) {
            if (child instanceof QWidget widget) {
                widget.setParent(null);
            }
        }
    }

}
