---
layout: gcn-layout
title:  "Home"
section: "home"
permalink: /
position: 0
---

{% capture dateAndKeys %}
  {% for tag in site.data.news %}
    {{ tag[1].date }}:{{ tag[0] }}
  {% endfor %}
{% endcapture %}

{% assign sortedComposite = dateAndKeys | split: ' ' | sort %}

{% capture sortedKeys %}
  {% for x in sortedComposite %}
    {{ x | split: ':' | last }}
  {% endfor %}
{% endcapture %}

{% assign newsKeyArray = sortedKeys | split: ' ' | reverse %}

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">Golf Clash Notebook</h1>

    {% if newsKeyArray != empty %}
      <div class="gcn-news-list">
        <div class="list-group">
          <div class="list-group-item">Recent News</div>
          {% for newsKey in newsKeyArray limit: 3 %}
            {% assign newsItem = site.data.news[newsKey] %}
            {% assign newsContent = newsItem.content | strip %}
            <div class="list-group-item">
              <h5 class="list-group-item-heading">{{ newsItem.title }}</h5>
              <div><small class="text-semi-muted">{{ newsItem.timestamp | date_to_long_string }}</small></div>
              <p class="list-group-item-text text-small">{{ newsContent }}</p>
            </div>
          {% endfor %}
        </div>
      </div>
    {% endif %}

    <p>
      An open source encyclopedia of sorts for Golf Clash. Here you'll find a compilation of guides,
      descriptions, tips and more for the game of Golf Clash that may help you along your way.
    </p>

    <p>
      This site was built because there is a lot of great content on the web to help you along
      your way but it's all pretty scattered. This site attempts to organize it all so you can
      spend a little less time searching and a little more time learning.
    </p>

    <p>
      This is very much a work in progress and wouldn't be possible without the great work already
      done by other golf clash players! Be sure to check out the <a href="/resources">Resources</a>
      page which provides links to other pages that have a lot of quality content. Also feel free
      to <a href="/contributing">contribute</a>, <a href="https://github.com/golf-clash-notebook/golf-clash-notebook.github.io/issues/new">create an issue</a>, or open a pull request at the <a href="https://github.com/golf-clash-notebook/golf-clash-notebook.github.io">GitHub</a> site.
    </p>

  </div>

</div>
