package net.infstudio.infinitylib.api.registry;

import net.infstudio.infinitylib.api.entity.EntityHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation register the {@link EntityHandler}.
 *
 * @author ci010
 * @see EntityHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value =
		{ElementType.TYPE})
public @interface ModEntityHandler
{
}
