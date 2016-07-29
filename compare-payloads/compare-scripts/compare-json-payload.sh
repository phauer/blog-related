#!/usr/bin/env bash

if [[ ! -e "payload-output" ]]; then
    mkdir "payload-output"
fi

http http://localhost:8080/blogposts --pretty=none | jq -S . > payload-output/version1.json
http http://localhost:8080/blogposts2 --pretty=none | jq -S . > payload-output/version2.json
meld payload-output/version1.json payload-output/version2.json
