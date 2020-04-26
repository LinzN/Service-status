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

import de.stem.stemSystem.taskManagment.operations.AbstractOperation;
import de.stem.stemSystem.taskManagment.operations.OperationOutput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WebStatusOperation extends AbstractOperation {

    private String statusHost;
    private int statusPort;

    @Override
    public OperationOutput runOperation() {
        OperationOutput operationOutput = new OperationOutput(this);
        boolean status = checkOnline();
        operationOutput.setExit(status ? 0 : 1);
        operationOutput.setData(status);
        return operationOutput;
    }


    private boolean checkOnline() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(statusHost, statusPort), 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getStatusHost() {
        return statusHost;
    }

    public void setStatusHost(String statusHost) {
        this.statusHost = statusHost;
    }

    public int getStatusPort() {
        return statusPort;
    }

    public void setStatusPort(int statusPort) {
        this.statusPort = statusPort;
    }
}
