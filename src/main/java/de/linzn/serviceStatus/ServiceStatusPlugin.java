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


import de.linzn.serviceStatus.callbacks.UniversalCallback;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceStatusPlugin extends STEMPlugin {

    public static ServiceStatusPlugin serviceStatusPlugin;

    private HashMap<String, UniversalCallback> list;

    @Override
    public void onEnable() {
        serviceStatusPlugin = this;
        this.list = new HashMap<>();
        readServices();
    }

    @Override
    public void onDisable() {
        STEMSystemApp.getInstance().getCallBackService().unregisterCallbackListeners(this);
        this.list.clear();
    }

    public boolean getServiceStatus(String serviceID) {
        if (this.list.containsKey(serviceID.toLowerCase())) {
            return this.list.get(serviceID.toLowerCase()).getStatus();
        }
        STEMSystemApp.LOGGER.ERROR("No service status available for " + serviceID);
        return false;
    }

    private void readServices() {
        if (!this.getDefaultConfig().contains("services") || this.getDefaultConfig().getStringList("services").isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add("TEST");
            this.getDefaultConfig().get("services", list);
            this.getDefaultConfig().save();
        }

        for (String serviceID : this.getDefaultConfig().getStringList("services")) {
            UniversalCallback universalCallback = new UniversalCallback(serviceID.toLowerCase());
            STEMSystemApp.getInstance().getCallBackService().registerCallbackListener(universalCallback, this);
            this.list.put(serviceID.toLowerCase(), universalCallback);
            STEMSystemApp.LOGGER.INFO("Register new service status " + serviceID);
        }
    }
}
