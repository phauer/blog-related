#!/usr/bin/env bash

if [[ ! -e "payload-output" ]]; then
    mkdir "payload-output"
fi

http http://localhost:8080/blogposts Accept:application/xml --pretty=none | xmllint -c14n - | xmllint --format - > payload-output/version1.xml
http http://localhost:8080/blogposts2 Accept:application/xml --pretty=none | xmllint -c14n - | xmllint --format - > payload-output/version2.xml
meld payload-output/version1.xml payload-output/version2.xml
#TODO xml nodes are not sorted!
#TODO difference between canonical format 1.0 and 1.1