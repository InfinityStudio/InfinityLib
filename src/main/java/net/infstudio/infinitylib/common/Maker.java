package net.infstudio.infinitylib.common;

/**
 * @author ci010
 */
public interface Maker<Input, Output>
{
	Output make(Input input);
}
