package net.infstudio.infinitylib.api.network;

public abstract class AbstractBiMessage<T> extends AbstractMessage<T>
{
	public AbstractBiMessage(MessageCoder<T> coder)
	{
		super(coder);
	}
}
