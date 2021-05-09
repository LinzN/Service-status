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

package de.linzn.serviceStatus.callbacks;

import de.linzn.serviceStatus.ServiceStatusPlugin;
import de.linzn.serviceStatus.WebStatusOperation;
import de.linzn.simplyConfiguration.FileConfiguration;
import de.linzn.simplyConfiguration.provider.YamlConfiguration;
import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.taskManagment.AbstractCallback;
import de.stem.stemSystem.taskManagment.CallbackTime;
import de.stem.stemSystem.taskManagment.operations.OperationOutput;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class UniversalCallback extends AbstractCallback {

    private final FileConfiguration fileConfiguration;

    private final String serviceID;

    private boolean status;

    public UniversalCallback(String serviceID) {
        this.serviceID = serviceID;
        this.fileConfiguration = YamlConfiguration.loadConfiguration(new File(ServiceStatusPlugin.serviceStatusPlugin.getDataFolder(), serviceID + ".yml"));
        this.fileConfiguration.get("hostname", "10.10.10.10");
        this.fileConfiguration.get("port", 1234);
        this.fileConfiguration.save();
    }

    @Override
    public void operation() {
        String hostname = fileConfiguration.getString("hostname");
        int port = fileConfiguration.getInt("port");

        WebStatusOperation webStatusOperation = new WebStatusOperation();
        webStatusOperation.setStatusHost(hostname);
        webStatusOperation.setStatusPort(port);
        addOperationData(webStatusOperation);
    }

    @Override
    public void callback(OperationOutput operationOutput) {
        status = (boolean) operationOutput.getData();
        WebStatusOperation webStatusOperation = (WebStatusOperation) operationOutput.getAbstractOperation();
        STEMSystemApp.LOGGER.DEBUG(serviceID + " " + webStatusOperation.getStatusHost() + ":" + webStatusOperation.getStatusPort() + " status " + (status ? "ONLINE" : "OFFLINE"));
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 1, TimeUnit.MINUTES);
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getServiceID() {
        return serviceID;
    }
}
