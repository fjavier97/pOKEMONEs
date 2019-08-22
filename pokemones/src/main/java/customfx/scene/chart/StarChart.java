package customfx.scene.chart;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;


public class StarChart extends Chart
{

	/* data */
	private final ObservableList<Data> data;

	/**************************************************/
	/* ---------------- propiedades ----------------- */
	/**************************************************/

	// show labels for data ---------------------------------------------------------------
	private final double spacing = 3;

	private final BooleanProperty showLabels = new SimpleBooleanProperty(true);

	public void setShowLabels(final boolean show)
	{
		showLabels.set(show);
	}

	public boolean isShowLabels()
	{
		return showLabels.get();
	}

	public BooleanProperty showLabels()
	{
		return showLabels;
	}

	// Max value ---------------------------------------------------------------------------
	private final DoubleProperty maxValueProperty = new SimpleDoubleProperty(0);

	public void resetMaxValue()
	{
		setMaxValue(0);
	}

	public void setMaxValue(final double maxValue)
	{
		maxValueProperty.set(maxValue);
	}

	public double getMaxValue()
	{
		return maxValueProperty.get();
	}

	public DoubleProperty maxValueProperty()
	{
		return maxValueProperty;
	}

	// Auto adjust overflowing new values to be the max ------------------------------------
	private final BooleanProperty autoAdjustOverflow = new SimpleBooleanProperty(true);

	public void setAutoAdjustOverflow(final boolean autoAdjust)
	{
		autoAdjustOverflow.set(autoAdjust);
	}

	public boolean isAutoAdjustOverflow()
	{
		return autoAdjustOverflow.get();
	}

	public BooleanProperty autoAdjustOverflow()
	{
		return autoAdjustOverflow;
	}

	// start angle -------------------------------------------------------------------------
	private final DoubleProperty startAngleProperty = new SimpleDoubleProperty(270);

	public void setStartAngle(final double startAngle)
	{
		startAngleProperty.set(startAngle % 360);
	}

	public double getStartAngle()
	{
		return startAngleProperty.get();
	}

	public DoubleProperty startAngleProperty()
	{
		return startAngleProperty;
	}

	// display background grid -------------------------------------------------------------
	private final IntegerProperty showGridProperty = new SimpleIntegerProperty(5);

	public void setShowGrid(final int showGrid)
	{
		showGridProperty.set(showGrid);
	}

	public int getShowGrid()
	{
		return showGridProperty.get();
	}

	public IntegerProperty showGridProperty()
	{
		return showGridProperty;
	}

	// display axis ------------------------------------------------------------------------
	private final BooleanProperty showAxisProperty = new SimpleBooleanProperty(true);

	public void setShowAxis(final boolean showAxis)
	{
		showAxisProperty.set(showAxis);
	}

	public boolean getShowAxis()
	{
		return showAxisProperty.get();
	}

	public BooleanProperty showAxisProperty()
	{
		return showAxisProperty;
	}

	// color of the area -------------------------------------------------------------------
	private final ObjectProperty<Color> areaColorProperty = new SimpleObjectProperty<>(Color.GRAY);

	public void setAreaColor(final Color color)
	{
		areaColorProperty.set(color);
	}

	public Color getAreaColor()
	{
		return areaColorProperty.get();
	}

	public ObjectProperty<Color> areaColorProperty()
	{
		return areaColorProperty;
	}

	//color of the grid area ---------------------------------------------------------------
	private final ObjectProperty<Color> gridAreaColorProperty = new SimpleObjectProperty<>(Color.WHITE);

	public void setGridAreaColor(final Color color)
	{
		gridAreaColorProperty.set(color);
	}

	public Color getGridAreaColor()
	{
		return gridAreaColorProperty.get();
	}

	public ObjectProperty<Color> gridAreaColorProperty()
	{
		return gridAreaColorProperty;
	}

	//color of the grid --------------------------------------------------------------------
	private final ObjectProperty<Color> gridColorProperty = new SimpleObjectProperty<>(Color.BLACK);

	public void setGridColor(final Color color)
	{
		gridColorProperty.set(color);
	}

	public Color getGridColor()
	{
		return gridColorProperty.get();
	}

	public ObjectProperty<Color> gridColorProperty()
	{
		return gridColorProperty;
	}


	/**************************************************/
	/* --------------- constructores ---------------- */
	/**************************************************/

	/**
	 *
	 */
	public StarChart()
	{
		this.data = FXCollections.observableArrayList();
		data.addListener((ListChangeListener<Data>) c -> {
			c.next();
			if (isAutoAdjustOverflow() && c.wasAdded())
			{
				for (final Data item : c.getAddedSubList())
				{
					if (item.getValue() > getMaxValue())
					{
						setMaxValue(item.getValue());
					}
				}
			}
			requestChartLayout();
		});
	}

	/**
	 * @return
	 */
	public ObservableList<Data> getData()
	{
		return data;
	}


	protected @Override void layoutChartChildren(final double top, final double left, final double width, final double height)
	{
		final ObservableList<Node> chartchildren = FXCollections.observableArrayList();

		if (data.size() < 3)
		{
			final Text l = new Text("not enough data to draw representation");
			l.setLayoutX((width / 2) + left - (l.getBoundsInLocal().getWidth() / 2));
			l.setLayoutY((height / 2) + top + (l.getBoundsInLocal().getHeight() / 2));
			getChartChildren().setAll(l);
			return;
		}
		/* ----------------------------------- calcular medidas clave -------------------------------------------- */
		final Point2D origen = new Point2D((width / 2) + left, (height / 2) + top);

		final double angulo = 360 / data.size();

		final double labelspace = getLongestTextLenght() + spacing;

		final double radio = (Math.min(height, width) / 2) - (isShowLabels() ? labelspace : 0);

		/* ------------------ dibujar centro, margen maximo, grid(opcional) y eje(opcinal) ----------------------- */
		/* centro */
		final Circle c = new Circle(origen.getX(), origen.getY(), 0.5);
		c.setStroke(getGridColor());
		c.setStrokeWidth(0.5);
		chartchildren.add(c);

		/* margen maximo */
		final double[] puntosTotales = new double[data.size() * 2];
		for (int i = 0, j = 0; j < data.size(); j++)
		{
			final Point2D punto = getPointOnCircle(origen, radio, (getStartAngle() + (angulo * j)) % 360);
			//			chartchildren.add(new Circle(punto.getX(), punto.getY(), 1.0));
			/* X */puntosTotales[i++] = punto.getX();
			/* Y */puntosTotales[i++] = punto.getY();
			/* eje */
			if (getShowAxis())
			{
				final Line l = new Line(origen.getX(), origen.getY(), punto.getX(), punto.getY());
				l.setStrokeWidth(0.1);
				l.setStroke(getGridColor().brighter());
				chartchildren.add(l);
			}
		}
		final Polygon b = new Polygon(puntosTotales);
		b.setStrokeWidth(0.3);
		b.setStroke(getGridColor());
		b.setFill(getGridAreaColor());
		chartchildren.add(b);
		b.toBack();

		/* grid */
		if (getShowGrid() > 0)
		{
			final double[][] puntosGrid = new double[getShowGrid() - 1][data.size() * 2];
			for (int k = 1; k < getShowGrid(); k++)
			{
				for (int i = 0, j = 0; j < data.size(); j++)
				{
					final int x = i++;
					final int y = i++;
					final Point2D extremo = new Point2D(puntosTotales[x], puntosTotales[y]);
					// distancia real = (origen.distance(extremo) / getShowGrid()) * k
					// distancia perc = 100 / getShowGrid()) * k
					final Point2D punto = getPointOnSegment(origen, extremo, (getMaxValue() / getShowGrid()) * k);
					/* X */puntosGrid[k - 1][x] = punto.getX();
					/* Y */puntosGrid[k - 1][y] = punto.getY();
				}
				final Polygon gi = new Polygon(puntosGrid[k - 1]);
				gi.setStrokeWidth(0.1);
				gi.setStroke(getGridColor().brighter());
				gi.setFill(Color.TRANSPARENT);
				chartchildren.add(gi);
			}
		}


		/* ----------------------------------- dibujar los datos -------------------------------------------- */

		final double[] puntosReales = new double[data.size() * 2];
		for (int i = 0, j = 0; j < data.size(); j++)
		{
			final int x = i++;
			final int y = i++;
			final Point2D punto = getPointOnSegment(origen, new Point2D(puntosTotales[x], puntosTotales[y]), data.get(j).getValue());
			chartchildren.add(new Circle(punto.getX(), punto.getY(), 1.0));
			/* X */puntosReales[x] = punto.getX();
			/* Y */puntosReales[y] = punto.getY();

			/* label */
			if (isShowLabels())
			{
				final Text t = new Text(data.get(j).getText());
				final Point2D layout = getPointOnSegment(origen, new Point2D(puntosTotales[x], puntosTotales[y]), getMaxValue() + 3);
				t.setLayoutX(layout.getX() - (t.getBoundsInLocal().getWidth() / 2));
				t.setLayoutY(layout.getY() + (t.getBoundsInLocal().getHeight() / 2));
				chartchildren.add(t);
			}
		}
		final Polygon d = new Polygon(puntosReales);
		d.setFill(getAreaColor());
		chartchildren.add(d);
		d.toBack();

		getChartChildren().setAll(chartchildren);
		for (final Node n : chartchildren)
		{
			if (n instanceof Circle)
			{
				n.toFront();
			}
			if (n instanceof Polygon && ((Polygon) n).getFill().equals(Color.TRANSPARENT))
			{
				n.toFront();
			}
			if (n instanceof Line)
			{
				n.toFront();
			}
			if (n instanceof Text)
			{
				n.toFront();
			}
		}

	}


	private Point2D getPointOnSegment(final Point2D origin, final Point2D end, final double distance_from_origin)
	{
		final Point2D v = end.subtract(origin);
		return origin.add(v.multiply(distance_from_origin / getMaxValue()));
	}

	private Point2D getPointOnCircle(final Point2D origin, final double radius, final double angle)
	{
		// Convert from degrees to radians via multiplication by PI/180
		return new Point2D(radius * Math.cos(angle * Math.PI / 180F) + origin.getX(),
				radius * Math.sin(angle * Math.PI / 180F) + origin.getY());

	}

	private double getLongestTextLenght()
	{
		String longest = getData().get(0).getText();
		for (final Data item : getData())
		{
			final String nuevo = item.getText();
			longest = longest.length() < nuevo.length() ? nuevo : longest;
		}

		return new Text(longest).getBoundsInLocal().getWidth();
	}

	public static class Data
	{
		private double value;
		private String text;

		public Data()
		{

		}

		public Data(final double value, final String text)
		{
			super();
			this.value = value;
			this.text = text;
		}

		/**
		 * @return the value
		 */
		public double getValue()
		{
			return value;
		}

		/**
		 * @param value
		 *           the value to set
		 */
		public void setValue(final double value)
		{
			this.value = value;
		}

		/**
		 * @return the text
		 */
		public String getText()
		{
			return text;
		}

		/**
		 * @param text
		 *           the text to set
		 */
		public void setText(final String text)
		{
			this.text = text;
		}
	}

}
