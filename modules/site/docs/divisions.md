---
layout: gcn-layout
title:  "Divisions"
section: "divisions"
permalink: /divisions/
position: 60
---

{% capture levelAndKeys %}
  {% for tag in site.data.divisions %}
    {{ tag[1].level }}:{{ tag[0] }}
  {% endfor %}
{% endcapture %}

{% assign sortedComposite = levelAndKeys | split: ' ' | sort %}

{% capture sortedKeys %}
  {% for x in sortedComposite %}
    {{ x | split: ':' | last }}
  {% endfor %}
{% endcapture %}

{% assign keyArray = sortedKeys | split: ' ' %}

<h1 class="gcn-page-header">Divisions</h1>

<div class="row">

  <div class="col-lg-10 col-lg-offset-1 col-md-12 table-responsive">

    <div class="row">
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Master3'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Master2'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Master1'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Expert3'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Expert2'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Expert1'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Professional3'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Professional2'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Professional1'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Rookie3'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Rookie2'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
      <div class="col-sm-4 col-xs-12">
        {% assign division = site.data.divisions['Rookie1'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
    </div>
    <div class="row">
      <div class="col-sm-4 col-sm-offset-4 col-xs-12">
        {% assign division = site.data.divisions['Beginner'] %}
        {% include divisions/individual-template.liquid division=division %}
      </div>
    </div>

  </div>

</div>
