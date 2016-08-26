package net.infstudio.infinitylib.time;

import com.google.common.collect.ImmutableList;
import net.infstudio.infinitylib.api.coremod.ClassPatch;
import net.infstudio.infinitylib.api.coremod.Transformer;

/**
 * @author ci010
 */
public class TimeTransformer extends Transformer
{
	@Override
	protected void buildClassPatch(ImmutableList.Builder<ClassPatch> builder)
	{
//		builder.add(new ClassPatch()
//		{
//			{
//				this.patchMethod(new MethodPatch("getGrassColor", "(DD)I")
//				{
//					@Override
//					public void visitInsn(int opcode)
//					{
//						if (opcode == IRETURN)
//							super.visitMethodInsn(INVOKESTATIC, Hook.CLASS, Hook.GETCOLOR, Hook.GETCOLOR_DES, false);
//						super.visitInsn(opcode);
//					}
//				});
//			}
//
//			@Override
//			public String patchClass()
//			{
//				return "net.minecraft.world.ColorizerGrass";
//			}
//		});
	}
}
