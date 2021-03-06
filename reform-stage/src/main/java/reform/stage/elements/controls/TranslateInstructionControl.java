package reform.stage.elements.controls;

import reform.core.forms.Form;
import reform.core.forms.relations.ConstantDistance;
import reform.core.forms.relations.RelativeDistance;
import reform.core.procedure.instructions.single.TranslateInstruction;
import reform.core.runtime.relations.ReferencePoint;
import reform.core.runtime.relations.TranslationDistance;
import reform.identity.Identifier;
import reform.stage.elements.ControlPoint;
import reform.stage.elements.InstructionControl;
import reform.stage.elements.RubberBand;

import java.util.ArrayList;
import java.util.List;

public class TranslateInstructionControl implements InstructionControl
{
	private final TranslateInstruction _instruction;
	private final ArrayList<ControlPoint> _controlPoints = new ArrayList<>();

	private final ControlPoint _sourcePoint = new ControlPoint();
	private final ControlPoint _targetPoint = new ControlPoint();
	private final RubberBand _rubberBand = new RubberBand(_sourcePoint, _targetPoint);

	private boolean _canEdit = false;

	public TranslateInstructionControl(final TranslateInstruction instruction)
	{
		_controlPoints.add(_sourcePoint);
		_controlPoints.add(_targetPoint);
		_instruction = instruction;
	}

	public TranslateInstruction getInstruction()
	{
		return _instruction;
	}

	@Override
	public List<ControlPoint> getControlPoints()
	{
		return _controlPoints;
	}

	@Override
	public RubberBand getRubberBand()
	{
		return _rubberBand;
	}

	@Override
	public void updateForRuntime(final reform.core.runtime.Runtime runtime)
	{
		final TranslationDistance distance = _instruction.getDistance();

		final Identifier<? extends Form> formId = _instruction.getFormId();
		final Form form = runtime.get(formId);
		if (form != null)
		{

			if (distance instanceof ConstantDistance)
			{
				final double baseX = runtime.getSize().x / 2.5;
				final double baseY = runtime.getSize().y / 2.7;

				_sourcePoint.setError(false);
				_sourcePoint.updatePosition(baseX, baseY);

				_targetPoint.setError(false);
				_targetPoint.updatePosition(baseX + distance.getXValueForRuntime
						                            (runtime), baseY + distance
						.getYValueForRuntime(
						                            runtime));

				_canEdit = true;
			}
			else if (distance instanceof RelativeDistance)
			{
				final RelativeDistance rel = (RelativeDistance) distance;
				final ReferencePoint source = rel.getReferenceA();
				final ReferencePoint target = rel.getReferenceB();

				if (source.isValidFor(runtime))
				{
					_sourcePoint.setError(false);
					_sourcePoint.updatePosition(source.getXValueForRuntime(runtime),
					                            source.getYValueForRuntime(runtime));
				}
				else
				{
					final double baseX = runtime.getSize().x / 2.2;
					final double baseY = runtime.getSize().y / 2.3;

					_sourcePoint.setError(true);
					_sourcePoint.updatePosition(baseX, baseY);
				}

				if (target.isValidFor(runtime))
				{
					_targetPoint.setError(false);
					_sourcePoint.updatePosition(source.getXValueForRuntime(runtime),
					                            source.getYValueForRuntime(runtime));
				}
				else
				{
					final double baseX = runtime.getSize().x / 2.2;
					final double baseY = runtime.getSize().y / 2.3;

					_targetPoint.setError(true);
					_targetPoint.updatePosition(baseX, baseY);
				}
				_canEdit = true;
			}
			else
			{
				_canEdit = false;
			}
		}
		else
		{
			_canEdit = false;
		}
	}

	public boolean canEdit()
	{
		return _canEdit;
	}

}
