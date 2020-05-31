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
import de.linzn.serviceStatus.callbacks.ProxmoxStatus;
import de.linzn.serviceStatus.callbacks.SolarStatus;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

public class ServiceStatusPlugin extends STEMPlugin {

    public static ServiceStatusPlugin serviceStatusPlugin;

    private PlexServerStatus plexServerStatus;
    private ProxmoxStatus proxmoxStatus;
    private SolarStatus solarStatus;

    @Override
    public void onEnable() {
        serviceStatusPlugin = this;
        plexServerStatus = new PlexServerStatus();
        proxmoxStatus = new ProxmoxStatus();
        solarStatus = new SolarStatus();
        STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(plexServerStatus, this);
        STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(proxmoxStatus, this);
        STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(solarStatus, this);
    }

    @Override
    public void onDisable() {
        STEMSystemApp.getInstance().getCallBackService().unregisterCallbackListeners(this);
    }
}
