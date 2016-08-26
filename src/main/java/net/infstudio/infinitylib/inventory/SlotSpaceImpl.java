package net.infstudio.infinitylib.inventory;

import net.infstudio.infinitylib.api.inventory.Inventory;
import net.infstudio.infinitylib.api.inventory.InventoryRule;
import net.infstudio.infinitylib.api.inventory.InventorySlot;
import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

/**
 * @author ci010
 */
public class SlotSpaceImpl implements InventorySlot
{
	private InventoryRule rule;
	private String name;
	private Inventory parent;
	private int index;

	public SlotSpaceImpl(Inventory parent, int index)
	{
		this.parent = parent;
		this.index = index;
	}

	void setName(String name)
	{
		this.name = name;
	}

	public InventoryRule getRule()
	{
		return rule;
	}

	@Override
	public Inventory parent()
	{
		return parent;
	}

	@Override
	public Optional<String> name()
	{
		return Optional.fromNullable(name);
	}

	@Override
	public int id()
	{
		return index;
	}

	public void setRule(InventoryRule rule)
	{
		this.rule = rule;
	}

	@Override
	public ItemStack getStackInSlot()
	{
		return parent.getStackInSlot(index);
	}

	@Override
	public ItemStack insertItem(ItemStack stack, boolean simulate)
	{
		return parent.insertItem(index, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int amount, boolean simulate)
	{
		return parent.extractItem(index, amount, simulate);
	}

	@Override
	public Iterator<ItemStack> iterator()
	{
		return Iterators.forArray(getStackInSlot());
	}
}
