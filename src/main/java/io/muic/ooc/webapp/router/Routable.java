package io.muic.ooc.webapp.router;

import io.muic.ooc.webapp.service.SecurityService;

public interface Routable {

    String getMapping();

    void setSecurityService(SecurityService securityService);
}
