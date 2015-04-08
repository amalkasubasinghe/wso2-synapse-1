/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.synapse.transport.nhttp.util.dynamicconfigurations;

import org.apache.axis2.AxisFault;
import org.apache.axis2.description.ParameterInclude;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Profile re-loader for nhttp and pass-through SSL receivers
 */
public class ListenerProfileReloader extends DynamicProfileReloader {

    private static final Log LOG = LogFactory.getLog(ListenerProfileReloader.class);

    private SSLProfileLoader sslProfileLoader;
    private ParameterInclude transportInDescription;

    public ListenerProfileReloader(SSLProfileLoader profileLoader, ParameterInclude transportInDescription) {
        this.sslProfileLoader = profileLoader;
        this.transportInDescription = transportInDescription;
        registerListener(this.transportInDescription);
    }

    /**
     * Notification method which will be triggered by FileUpdateNotifier
     */
    public void notifyFileUpdate() {
        try {
            sslProfileLoader.reloadConfig(transportInDescription);
        } catch (AxisFault axisFault) {
            LOG.error("Error reloading dynamic SSL configurations for Listeners : New Configurations will not be applied " + axisFault.getMessage());
        }
    }

}