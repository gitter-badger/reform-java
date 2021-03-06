package reform.core.runtime.errors;

import reform.data.sheet.expression.Expression;

public class InvalidExpressionError implements RuntimeError
{
	private final Expression _expression;

	public InvalidExpressionError(final Expression expression)
	{
		_expression = expression;
	}

	public Expression getExpression()
	{
		return _expression;
	}

	@Override
	public String getMessage()
	{
		return "Invalid Expression";
	}
}
