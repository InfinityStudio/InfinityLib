package net.infstudio.infinitylib.api;

/**
 * @author ci010
 */
public interface Callback<T>
{
	void onChange(T t);

	interface Container<T> extends Iterable<Callback<T>>
	{
		void add(Callback<T> callBack);

		void remove(Callback<T> callBack);
	}
}
