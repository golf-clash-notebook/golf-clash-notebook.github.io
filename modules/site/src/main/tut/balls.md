---
layout: gcn-layout
title:  "Balls"
section: "balls"
permalink: /balls/
position: 50
---

{% capture ballKeys %}
  {% for tag in site.data.balls %}
    {% comment %}Filter out ball resources...{% endcomment %}
    {% if tag[1].name != undefined %}
      {{ tag[0] }}
    {% endif %}
  {% endfor %}
{% endcapture %}

{% assign ballKeyArray = ballKeys | split: ' ' %}

<div class="row">
  <div class="col-md-5 col-md-offset-1 col-sm-12">
    <h1 class="gcn-page-header">Ball Guide</h1>
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 20 %}
    </p>
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 20 %}
    </p>
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 20 %}
    </p>

  </div>
  <div class="col-sm-12 visible-sm visible-xs">
    <hr>
  </div>
  <div class="col-md-5 col-sm-12">

    <div class="ball-chart-container">

      {% include balls/chart-template.liquid ballKeyArray=ballKeyArray %}

    </div>

  </div>
</div>

<br>

<p class="lead text-center">Individual Ball Details</p>

<div>

  {% assign numBalls = ballKeyArray | size %}

  {% for ballKey in ballKeyArray %}

    {% assign loopMod2 = forloop.index0 | modulo: 2 %}

    {% assign nextIx = forloop.index0 | plus: 1 %}

    {% assign nextBallKey = ballKeyArray[nextIx] %}

    {% assign ball = site.data.balls[ballKey] %}
    {% assign nextBall = site.data.balls[nextBallKey] %}

    {% if nextIx < numBalls and loopMod2 == 0 %}

      <div class="row">
        <a id="{{ ballKey }}"></a>
        <div class="col-md-5 col-md-offset-1 col-sm-12 col-flex" data-mh="ball-card">
          {% include balls/details-template.liquid ball=ball %}
        </div>
        <a id="{{ nextBallKey }}"></a>
        <div class="col-md-5 col-sm-12 col-flex" data-mh="ball-card">
          {% include balls/details-template.liquid ball=nextBall %}
        </div>
      </div>

    {% elsif loopMod2 == 0 %}

      <div class="row">
        <a id="{{ ballKey }}"></a>
        <div class="col-md-6 col-md-offset-3 col-sm-12 col-flex" data-mh="ball-card">
          {% include balls/details-template.liquid ball=ball %}
        </div>
      </div>

    {% else %}
    {% endif %}

  {% endfor %}

</div>

{% if site.data.balls.resources != undefined and site.data.balls.resources != empty %}

  <div class="row">
    <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
      <p class="lead text-center">Resources</p>
      <div class="list-group">
        {% for resource in site.data.balls.resources %}

          {% capture resourceIcon %}
            {% include resources/icon.liquid resource=resource %}
          {% endcapture %}

          <li class="list-group-item gcn-resource text-small"><a href="{{ resource.url }}" target="_blank" rel="noopener">{{ resourceIcon }} {{ resource.title }}</a></li>

        {% endfor %}
      </div>
    </div>
  </div>

{% endif %}
