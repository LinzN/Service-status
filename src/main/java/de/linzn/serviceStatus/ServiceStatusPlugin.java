/*
 * Copyright (C) 2019. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.serviceStatus;


import de.linzn.serviceStatus.callbacks.PlexServerStatus;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

public class ServiceStatusPlugin extends STEMPlugin {

    public static ServiceStatusPlugin serviceStatusPlugin;

    private PlexServerStatus plexServerStatus;

    @Override
    public void onEnable() {
        serviceStatusPlugin = this;
        plexServerStatus = new PlexServerStatus();
        STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(plexServerStatus, this);
    }

    @Override
    public void onDisable() {
        STEMSystemApp.getInstance().getCallBackService().unregisterCallbackListeners(this);
    }
}
