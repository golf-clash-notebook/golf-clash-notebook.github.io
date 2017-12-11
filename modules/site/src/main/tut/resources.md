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

<div>

  {% for resource in site.data.resources %}

    {% assign loopMod2 = forloop.index0 | modulo: 2 %}

    {% assign currentRes = site.data.resources[forloop.index0] %}
    {% assign nextRes = site.data.resources[forloop.index] %}

    {% if loopMod2 == 0 and forloop.rindex > 1 %}
      <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-sm-12">
          <div class="col-md-6 col-sm-12 col-flex" data-mh="resource-card">
            {% include resources/card-template.liquid resource=currentRes %}
          </div>
          <div class="col-md-6 col-sm-12 col-flex" data-mh="resource-card">
            {% include resources/card-template.liquid resource=nextRes %}
          </div>
        </div>
      </div>
    {% elsif loopMod2 == 0 %}
      <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-sm-12">
          <div class="col-md-6 col-md-offset-3 col-sm-12 col-flex" data-mh="resource-card">
            {% include resources/card-template.liquid resource=resource %}
          </div>
        </div>
      </div>
    {% endif %}

  {% endfor %}

</div>
