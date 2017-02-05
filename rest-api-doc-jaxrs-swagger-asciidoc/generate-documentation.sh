#!/usr/bin/env bash
# Convenience script for development to generate only the HTML Documentation without executing the whole Maven lifecycle.

set -e

case "$1" in
    '')
        cat <<HEREDOC
Generating everything (swagger spec, asciidoc, html).
Please mind to force recompilation of your resource classes upfront (Ctrl+Shift+F9 in IDEA).
HEREDOC
        mvn exec:java@generate-swagger-and-asciidoc asciidoctor:process-asciidoc@output-html
       ;;
    'only-html')
        cat <<HEREDOC
Only generating html.
Assuming that only the asciidoc has changed.
HEREDOC
        mvn asciidoctor:process-asciidoc@output-html
        ;;
    *)
        cat <<HEREDOC
Unknown argument: $1
Usage: $0 [only-html]
HEREDOC
        exit 1
        ;;
esac

echo "Result: file://$PWD/target/classes/index.html"
