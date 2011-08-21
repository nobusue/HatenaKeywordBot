import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.Query
import com.google.appengine.api.datastore.Query.FilterOperator
import com.google.appengine.api.datastore.FetchOptions.Builder
import twitter4j.*
import twitter4j.http.*

TwitterFactory factory = new TwitterFactory();
Twitter twitter = factory.getInstance();

URL url = new URL('http://k.hatena.ne.jp/keywordblog/Groovy')
def res = url.get(
	params:[mode:'rss'],
	headers:['User-Agent':'Mozilla/5.0']
)
if (res.statusCode != 200){
	log.warning "Unexpected status code: ${res.statusCode}"
	log.warning "Response: ${res.text}"
	return
}

def root = new XmlSlurper().parseText(res.text)
def items = root.item

if(localMode){
	items.each{
		log.info it.title.toString()
	}
}

items = filterItems(items)

def posted = 0
items.each{ it ->
	def q = new Query('entry')
	def item = datastore.prepare(
		q.addFilter("link", Query.FilterOperator.EQUAL, it.link.toString() ))
		.asList(Builder.withLimit(1).offset(0))
	if(!item) {
		//Tweet new entry
		if(!localMode){
			twitter.updateStatus("${it.title.toString()} ${it.link.toString()}")
		}
		posted++

		//Record tweeted entry
		def entry = new Entity('entry')
		entry.title = it.title.toString()
		entry.link = it.link.toString()
		entry.date = it.date.toString()
		entry.creator = it.creator.toString()
		entry.save()
	}
}

request.setAttribute 'posted', posted
//redirect '/entries.gtpl'
forward '/WEB-INF/pages/result.gtpl'

def filterItems(items){
	def excludes = ['レコード','バッグ','新入荷']
	excludes.each{word ->
		items = items.findAll{!(it =~ /${word}/)}
	}
	return items
}
