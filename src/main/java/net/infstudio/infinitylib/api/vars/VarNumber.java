package net.infstudio.infinitylib.api.vars;

/**
 * @author ci010
 */
public interface VarNumber<T extends Number> extends Var<T>
{
	T increment();

	T decrement();
}
