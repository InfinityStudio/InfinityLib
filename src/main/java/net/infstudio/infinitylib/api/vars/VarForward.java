package net.infstudio.infinitylib.api.vars;

/**
 * @author ci010
 */
public interface VarForward<T> extends Var<T>
{
	boolean isPresent();

	void delegate(Var<T> var);

	Var<T> delegate();
}
