---
layout: gcn-layout
title:  "Tours"
section: "tours"
permalink: /tours/
position: 10
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-sm-12">

    {% capture tourKeys %}
      {% for tag in site.data.tours %}
        {{ tag[0] }}
      {% endfor %}
    {% endcapture %}

    {% assign sortedKeys = tourKeys | split: ' ' | sort %}
    {% assign mod2 = sortedKeys | size | modulo: 2 %}
    {% assign mod3 = sortedKeys | size | modulo: 3 %}

    {% for tourKey in sortedKeys %}

      {% assign tour = site.data.tours[tourKey] %}

      {% capture columnClasses %}
        {% if mod3 == 1 and forloop.last %}
          col-md-4 col-md-offset-4 col-sm-6 col-xs-12
        {% elsif mod3 == 2 and forloop.rindex == 2 %}
          col-md-4 col-md-offset-2 col-sm-6 col-xs-12
        {% elsif mod3 == 2 and forloop.last %}
          col-md-4 col-md-offset-0 col-sm-6 col-sm-offset-3 col-xs-12
        {% elsif mod2 == 1 and forloop.last %}
          col-md-4 col-md-offset-0 col-sm-6 col-sm-offset-3 col-xs-12
        {% else %}
          col-md-4 col-sm-6 col-xs-12
        {% endif %}
      {% endcapture %}

      <div class="{{ columnClasses | strip }} text-center pad-16">
        <a href="/tours/{{ tour.number }}/">
          <img class="img-responsive img-center" src="/img/golfclash/tours/{{ tour.number }}-thumb.png">
        </a>
      </div>

    {% endfor %}

  </div>

</div>