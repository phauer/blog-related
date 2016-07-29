#!/usr/bin/env bash

if [[ ! -e "payload-output" ]]; then
    mkdir "payload-output"
fi

http http://localhost:8080/blogposts > payload-output/version1.json
http http://localhost:8080/blogposts2 > payload-output/version2.json
# sort all character of the payload alphanumerical. This leads to rubbish. But a rubbish we can compare to determine equality.
version1Sorted=$(grep -o . "payload-output/version1.json" | sort | tr -d "\n")
version2Sorted=$(grep -o . "payload-output/version2.json" | sort | tr -d "\n")
if [ "$version1Sorted" = "$version2Sorted" ]; then
    echo "JSON payloads are equal."
else
    echo "!!!JSON payloads are NOT EQUAL!!!"
    meld payload-output/version1.json payload-output/version2.json
fi