
get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/entries.gtpl", forward: "/WEB-INF/pages/entries.gtpl"

get "/datetime", forward: "/datetime.groovy"
get "/getEntries", forward: "/getEntries.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"
