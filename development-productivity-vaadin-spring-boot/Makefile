.PHONY: all watch

SHELL:=/bin/bash
BROWSERSYNC:=/usr/local/bin/browser-sync
all: watch

watch: $(BROWSERSYNC)
	browser-sync start --proxy 'localhost:8080' --files 'src/main/webapp/VAADIN/themes/mytheme/*.scss'

$(BROWSERSYNC):
	sudo npm install -g browser-sync
