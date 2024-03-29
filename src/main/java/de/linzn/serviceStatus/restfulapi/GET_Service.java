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

package de.linzn.serviceStatus.restfulapi;

import de.linzn.restfulapi.api.jsonapi.IRequest;
import de.linzn.restfulapi.api.jsonapi.RequestData;
import de.linzn.serviceStatus.ServiceStatusPlugin;
import org.json.JSONObject;

import java.util.Map;

public class GET_Service implements IRequest {
    private final ServiceStatusPlugin serviceStatusPlugin;

    public GET_Service(ServiceStatusPlugin serviceStatusPlugin) {
        this.serviceStatusPlugin = serviceStatusPlugin;
    }

    @Override
    public Object proceedRequestData(RequestData requestData) {
        if (requestData.getSubChannels().size() > 0) {
            String serviceID = requestData.getSubChannels().get(0);
            boolean status = serviceStatusPlugin.getServiceStatus(serviceID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", status);
            return jsonObject;
        } else {
            return genericData();
        }
    }

    @Override
    public Object genericData() {
        JSONObject jsonObject = new JSONObject();
        Map<String, Boolean> services = serviceStatusPlugin.getServices();
        for (String serviceID : services.keySet()) {
            boolean status = services.get(serviceID);
            jsonObject.put(serviceID, status);
        }
        return jsonObject;
    }

    @Override
    public String name() {
        return "service";
    }
}
