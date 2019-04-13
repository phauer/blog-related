package com.phauer.vaadin10sasscssrefresh;

import com.vaadin.flow.server.BootstrapListener;
import com.vaadin.flow.server.BootstrapPageResponse;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class CustomVaadinServiceListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        if (!event.getSource().getDeploymentConfiguration().isProductionMode()) {
            event.addBootstrapListener(new CustomBootstrapListener());
        }
    }
    static class CustomBootstrapListener implements BootstrapListener {
        @Override
        public void modifyBootstrapPage(BootstrapPageResponse response) {
            Element head = response.getDocument().head();
            head.append("<script type=\"text/javascript\" src=\"/js/cssrefresh.js\"></script>");
        }
    }
}

