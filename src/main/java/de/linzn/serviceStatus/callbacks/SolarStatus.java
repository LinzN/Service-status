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
import de.stem.stemSystem.AppLogger;
import de.stem.stemSystem.taskManagment.AbstractCallback;
import de.stem.stemSystem.taskManagment.CallbackTime;
import de.stem.stemSystem.taskManagment.operations.OperationOutput;
import de.stem.stemSystem.utils.Color;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SolarStatus extends AbstractCallback {

    private static boolean solarStatus = false;
    private final FileConfiguration fileConfiguration;

    public SolarStatus() {
        fileConfiguration = YamlConfiguration.loadConfiguration(new File(ServiceStatusPlugin.serviceStatusPlugin.getDataFolder(), "solarBerry.yml"));
        fileConfiguration.get("hostname", "10.10.10.10");
        fileConfiguration.get("port", 32400);
        fileConfiguration.save();
    }

    public static boolean getSolarStatus() {
        return solarStatus;
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
        solarStatus = (boolean) operationOutput.getData();
        WebStatusOperation webStatusOperation = (WebStatusOperation) operationOutput.getAbstractOperation();
        AppLogger.debug("SOLARBERRY " + webStatusOperation.getStatusHost() + ":" + webStatusOperation.getStatusPort() + " status " + (solarStatus ? Color.GREEN + "ONLINE" : Color.RED + "OFFLINE"));
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 1, TimeUnit.MINUTES);
    }

}
