---
layout: gcn-layout
title:  "Resources"
section: "resources"
permalink: /resources/
position: 80
---

<div class="row">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <h1 class="gcn-page-header">More resources to look at in your quest for domination.</h1>
    <hr>
  </div>
</div>

<div class="row">
  <div class="col-md-6 col-md-offset-3 col-sm-12 text-center">
    <div class="resources-list list-group">
      {% for resource in site.data.resources %}

        {% capture resourceIcon %}
          {% include resources/icon.liquid resource=resource %}
        {% endcapture %}

        <li class="list-group-item gcn-resource text-small">
          <a href="{{ resource.url }}" target="_blank" rel="noopener">
            <h4><span>{{ resourceIcon }}</span> {{ resource.name }}</h4>
          </a>
        </li>
      {% endfor %}
    </div>
  </div>
</div>
