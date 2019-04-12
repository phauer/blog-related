package com.phauer.vaadin10sasscssrefresh;

import com.vaadin.flow.server.BootstrapListener;
import com.vaadin.flow.server.BootstrapPageResponse;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.jsoup.nodes.Document;
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

        public void modifyBootstrapPage(BootstrapPageResponse response) {
            Document document = response.getDocument();
            Element cssRefreshScriptTag = document.createElement("script");
            cssRefreshScriptTag.attr("type", "text/javascript");
            cssRefreshScriptTag.attr("src", "/js/cssrefresh.js");
            document.head().appendChild(cssRefreshScriptTag);
        }

    }
}

