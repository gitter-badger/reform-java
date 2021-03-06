package reform.playground.presenter;

import reform.core.analyzer.Analyzer;
import reform.playground.renderers.*;
import reform.rendering.canvas.Canvas;
import reform.stage.Stage;
import reform.stage.tooling.FormSelection;
import reform.stage.tooling.ToolState;
import reform.stage.tooling.cursor.Cursor;

import javax.swing.*;
import java.awt.*;

public class StagePresenter
{
	private final Stage _stage;
	private final Canvas _canvas;
	private final StageRenderer _stageRenderer;
	private final BackgroundRenderer _backgroundRenderer;
	private final SelectionRenderer _selectionRenderer;
	private final CropRenderer _cropRenderer;
	private final SnapPointRenderer _snapPointRenderer;
	private final EntityPointRenderer _entityPointRenderer;
	private final HandleRenderer _handleRenderer;
	private final PivotRenderer _pivotRenderer;
	private final GuideRenderer _guideRenderer;
	private final InstructionControlRenderer _instructionControlRenderer;
	private final ToolStateDescriptionRenderer _toolStateDescriptionRenderer;

	public StagePresenter(final Stage stage, final FormSelection formSelection, final
	ToolState toolState, final Analyzer analyzer, final Cursor cursor)
	{
		_stage = stage;
		_canvas = new Canvas();

		final ToolTipRenderer toolTipRenderer = new ToolTipRenderer();

		_backgroundRenderer = new BackgroundRenderer(toolState, Color.GRAY,
		                                             Color.DARK_GRAY);
		_stageRenderer = new StageRenderer(stage, toolState);
		_selectionRenderer = new SelectionRenderer(stage, formSelection, toolState);
		_cropRenderer = new CropRenderer(stage, toolState);
		_snapPointRenderer = new SnapPointRenderer(stage, toolState);
		_entityPointRenderer = new EntityPointRenderer(stage, toolState);
		_handleRenderer = new HandleRenderer(stage, toolState);
		_pivotRenderer = new PivotRenderer(stage, toolState);
		_guideRenderer = new GuideRenderer(stage, toolState);
		_instructionControlRenderer = new InstructionControlRenderer(stage, toolState);
		_toolStateDescriptionRenderer = new ToolStateDescriptionRenderer(stage,
		                                                                 toolState,
		                                                                 formSelection,
		                                                                 analyzer,
		                                                                 toolTipRenderer,
		                                                                 cursor);


		_canvas.addRenderer(_backgroundRenderer);
		_canvas.addRenderer(_stageRenderer);
		_canvas.addRenderer(_selectionRenderer);
		_canvas.addRenderer(_guideRenderer);
		_canvas.addRenderer(_cropRenderer);
		_canvas.addRenderer(_snapPointRenderer);
		_canvas.addRenderer(_entityPointRenderer);
		_canvas.addRenderer(_handleRenderer);
		_canvas.addRenderer(_pivotRenderer);
		_canvas.addRenderer(_instructionControlRenderer);
		_canvas.addRenderer(_toolStateDescriptionRenderer);

		toolState.addListener(new ToolStateListener(this));

		stage.addListener(new StageListener(this));
	}

	public JComponent getView()
	{
		return _canvas;
	}

	public void update()
	{
		_canvas.revalidate();
		_canvas.repaint();
	}

	public void updateSize()
	{
		_canvas.setPreferredSize(
				new Dimension(_stage.getSize().x + 30, _stage.getSize().y + 30));
		_canvas.revalidate();
		_canvas.repaint();
	}

	public void setPreview(final boolean b)
	{
		_backgroundRenderer.setPreview(b);
		_stageRenderer.setPreview(b);
		_selectionRenderer.setPreview(b);
		_snapPointRenderer.setPreview(b);
		_entityPointRenderer.setPreview(b);
		_handleRenderer.setPreview(b);
		_pivotRenderer.setPreview(b);
		_guideRenderer.setPreview(b);

		_canvas.repaint();
	}

	private static class StageListener implements Stage.Listener
	{

		private final StagePresenter _presenter;

		public StageListener(final StagePresenter presenter)
		{
			_presenter = presenter;
		}

		@Override
		public void onStageComplete(final Stage stage)
		{
			_presenter.update();
		}

	}

	private static class ToolStateListener implements reform.stage.tooling
			.ToolStateListener
	{

		private final StagePresenter _stagePresenter;

		public ToolStateListener(final StagePresenter stagePresenter)
		{
			_stagePresenter = stagePresenter;
		}

		@Override
		public void onToolStateChange(final ToolState state)
		{
			_stagePresenter.update();
		}

	}
}
