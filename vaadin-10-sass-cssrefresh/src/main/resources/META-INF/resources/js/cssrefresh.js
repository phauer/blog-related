(function () {
    function cssRefresh(link) {
        let href = link.href + '?x=' + Math.random();

        fetch(href, {
            method: 'HEAD'
        })
            .then(response => {
                let lastModified = new Date(response.headers.get('last-modified'));
                if (response.ok && (lastModified > link.lastModified || !link.lastModified)) {
                    link.lastModified = lastModified;
                    link.element.setAttribute('href', href);
                }

                setTimeout(() => {
                    cssRefresh(link);
                }, 1000);
            })
    }

    Array.prototype.slice.call(document.querySelectorAll('link'))
        .filter(link => link.rel === 'stylesheet' && link.href)
        .map(link => {
            return {
                element: link,
                href: link.getAttribute('href').split('?')[0],
                lastModified: false
            };
        })
        .forEach(function (link) {
            cssRefresh(link);
        });
})();