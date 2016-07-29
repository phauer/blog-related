#!/usr/bin/env bash

if [ -z "$1" ]; then
    fileName=$(basename "$0")
    printf "Paths are not provided! Pattern: \n./$fileName \"http://localhost:8080/blogposts\" \"http://localhost:8080/blogposts2\"\n"
    exit
fi

outputFolder="payload-output"
if [[ ! -e "$outputFolder" ]]; then
    mkdir "$outputFolder"
fi

resource1="$1"
resource2="$2"

compare-payloads(){
    format="$1"
    payload1="$outputFolder/payload1.$format"
    payload2="$outputFolder/payload2.$format"

     if [ "$format" = "json" ]; then
        http "$resource1" Accept:application/json --pretty=none | jq -S . > "$payload1"
        http "$resource2" Accept:application/json --pretty=none | jq -S . > "$payload2"
    else
        http "$resource1" Accept:application/xml --pretty=none | xmllint -c14n - | xmllint --format - > "$payload1"
        http "$resource2" Accept:application/xml --pretty=none | xmllint -c14n - | xmllint --format - > "$payload2"
    fi
    if [ "$(cat "$payload1")" = "$(cat "$payload2")" ]; then
        echo "$format payloads are equal."
    else
        echo "!!!$format payloads are NOT EQUAL!!!"
        # if the payloads are not equals, show create a diff and show it to the developer. Let's him decide.
        meld "$payload1" "$payload2"
    fi
}
#TODO pipe output of http to variable, so this can be outside of the if-body. this also prevents redundant echos for the requests. "calling ...".
#TODO xml nodes are not sorted!
#TODO compare files... using cat?

compare-payloads "json"
compare-payloads "xml"