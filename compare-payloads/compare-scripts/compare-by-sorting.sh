#!/usr/bin/env bash

if [[ ! -e "payload-output" ]]; then
    mkdir "payload-output"
fi

http http://localhost:8080/blogposts --pretty=none > payload-output/version1.json
http http://localhost:8080/blogposts2 --pretty=none > payload-output/version2.json
# heuristic: sort all characters of the payload alphanumerical. This leads to rubbish.
# But we can compare the rubbish to determine equality with a high probability.
version1Sorted=$(grep -o . "payload-output/version1.json" | sort | tr -d "\n")
version2Sorted=$(grep -o . "payload-output/version2.json" | sort | tr -d "\n")
if [ "$version1Sorted" = "$version2Sorted" ]; then
    echo "JSON payloads are equal."
else
    echo "!!!JSON payloads are NOT EQUAL!!!"
    meld payload-output/version1.json payload-output/version2.json
fi