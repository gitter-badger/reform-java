package reform.stage.tooling.factory;

import reform.core.forms.ArcForm;
import reform.core.forms.CircleForm;
import reform.core.forms.LineForm;
import reform.core.forms.PieForm;
import reform.core.forms.RectangleForm;

public final class Builders {
	public static final FormFactory.Builder<LineForm> Line = LineForm::construct;

	public static final FormFactory.Builder<RectangleForm> Rectangle = RectangleForm::construct;

	public static final FormFactory.Builder<CircleForm> Circle = CircleForm::construct;

	public static final FormFactory.Builder<PieForm> Pie = PieForm::construct;

	public static final FormFactory.Builder<ArcForm> Arc = ArcForm::construct;
}
