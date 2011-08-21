<% include '/WEB-INF/includes/header.gtpl' %>
<% import com.google.appengine.api.datastore.Query %>
<% import com.google.appengine.api.datastore.FetchOptions.Builder %>

<h1>Hatena Diary recent entries check result</h1>

<p>
    Tweeted <%= request.getAttribute('posted') %> new Hatena entries.
</p>

<p>
    Click <a href="/entries.gtpl">here</a> to view Hatena diary groovy entries.
</p>

<% include '/WEB-INF/includes/footer.gtpl' %>
