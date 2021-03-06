package reform.core.pool;

public interface Pool<E>
{
	E take();

	void release();

	void release(PoolModifier<E> releaser);

	void clean(PoolModifier<E> cleaner);

	void eachActive(PoolModifier<E> modifier);

	int size();
}
