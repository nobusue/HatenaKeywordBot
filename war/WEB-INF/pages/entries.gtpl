<% include '/WEB-INF/includes/header.gtpl' %>
<% import com.google.appengine.api.datastore.Query %>
<% import com.google.appengine.api.datastore.FetchOptions.Builder %>

<h1>Hatena Diary recent entries about Groovy</h1>

<%
	//def items = request.getAttribute('entries')
	def q = new Query('entry')
	def items = datastore.prepare(q).asList(
		Builder.withLimit(100).offset(0))
%>

<ul>
<% items.each{ %>
<li>${it.date} ${it.title}: ${it.link}
<% } %>
</ul>

<% include '/WEB-INF/includes/footer.gtpl' %>
