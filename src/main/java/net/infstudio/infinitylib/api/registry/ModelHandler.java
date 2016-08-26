package net.infstudio.infinitylib.api.registry;

public interface ModelHandler<Type>
{
	boolean handle(Type target);
}
