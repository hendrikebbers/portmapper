/*
 * Copyright (c) 2013-2016, Kasra Faghihi, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.offbynull.portmapper.upnpigd.externalmessages;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.lang3.Validate;

/**
 * Represents a UPnP probe response.
 * <p>
 * Specifications are documented at https://tools.ietf.org/html/draft-goland-http-udp-00 and
 * http://quimby.gnus.org/internet-drafts/draft-cai-ssdp-v1-03.txt
 * @author Kasra Faghihi
 */
public final class ServiceDiscoveryUpnpIgdResponse extends UpnpIgdHttpResponse {

    // http://quimby.gnus.org/internet-drafts/draft-cai-ssdp-v1-03.txt

//    // examples for Javadoc taken from http://www.upnp-hacks.org/upnp.html
//    /**
//     * Constructs a {@link ProbeResponse} object.
//     * @param location type of device to probe for (IPv4 or IPv6)
//     * @param server name of the device replying to the probe (can be {@code null} -- should be there but not required for identifying
//     * UPnP-IGD devices -- e.g. {@code "SpeedTouch 510 4.0.0.9.0 UPnP/1.0 (DG233B00011961)"})
//     * @param usn unique service name of the device replying to the probe (can be {@code null} -- should be there but not required for
//     * identifying UPnP-IGD devices -- e.g. {@code "uuid:UPnP-SpeedTouch510::urn:schemas-upnp-org:service:WANPPPConnection:1"})
//     * @param serviceType service type of the device replying to the probe (can be {@code null} -- should be there but not required for
//     * identifying UPnP-IGD devices (action names are used instead) -- e.g. {@code "urn:schemas-upnp-org:service:WANPPPConnection:1"})
//     * @throws NullPointerException if {@code location} is {@code null}
//     */
//    public ProbeResponse(String location, String server, String usn, String serviceType) {
//        super(generateHeaders(location, server, usn, serviceType), null);
//    }
//
//    private static Map<String, String> generateHeaders(String location, String server, String usn, String serviceType) {
//        Validate.notNull(location);
//        
//        Map<String, String> ret = new HashMap<>();
//        ret.put(LOCATION_KEY, location);
//        if (server != null) {
//            ret.put(ST_KEY, server);
//        }
//        if (usn != null) {
//            ret.put(ST_KEY, usn);
//        }
//        if (serviceType != null) {
//            ret.put(ST_KEY, serviceType);
//        }
//        
//        return ret;
//    }

    /**
     * Constructs a {@link ProbeResponse} object by parsing a buffer.
     * @param buffer buffer containing response data
     * @throws NullPointerException if any argument is {@code null}
     * @throws IllegalArgumentException if buffer is malformed (response is not 200 OK, or LOCATION header is missing)
     */
    public ServiceDiscoveryUpnpIgdResponse(byte[] buffer) {
        super(buffer);
        
        Validate.isTrue(isResponseSuccessful());
    }

    /**
     * Get location to access service.
     * @return location
     * @throws IllegalStateException if was not found or could not be interpreted
     */
    public URL getLocation() {
        String uriStr = getHeaderIgnoreCase("LOCATION");
        Validate.validState(uriStr != null);
        try {
            return new URL(uriStr);
        } catch (MalformedURLException urise) {
            throw new IllegalStateException(urise);
        }
    }

    /**
     * Get server description.
     * @return server description (may be {@code null})
     */
    public String getServer() {
        return getHeaderIgnoreCase("SERVER");
    }

    /**
     * Get unique service identifier.
     * @return unique service identifier
     * @throws IllegalStateException if was not found
     */
    public String getUsn() {
        String val = getHeaderIgnoreCase("USN");
        Validate.validState(val != null);
        return val;
    }

    /**
     * Get service type.
     * @return service type
     * @throws IllegalStateException if was not found
     */
    public String getServiceType() {
        String val = getHeaderIgnoreCase("ST");
        Validate.validState(val != null);
        return val;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServiceDiscoveryUpnpIgdResponse other = (ServiceDiscoveryUpnpIgdResponse) obj;
        if (!super.equals(obj)) {
            return false;
        }
        return true;
    }
}