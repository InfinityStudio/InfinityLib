package net.infstudio.infinitylib.api.utils;

import java.lang.annotation.*;

/**
 * @author ci010
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Inherited
public @interface Nullable
{
}
