package net.infstudio.infinitylib.api.remote.capabilities;

import net.infstudio.infinitylib.api.remote.Syncable;
import net.infstudio.infinitylib.api.vars.VarSyncFactory;

/**
 * @author ci010
 */
@CapabilityInjectInterface(Syncable.class)
public interface ISyncPortal
{
	/**
	 * This method provide a VarFactory that all the vars it produced are auto sync between client and server.
	 *
	 * @param varFactory
	 */
	void buildVars(VarSyncFactory varFactory);
}
