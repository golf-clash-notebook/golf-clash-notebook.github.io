---
layout: gcn-layout
title:  "Balls"
section: "balls"
permalink: /balls/
position: 50
---

{% assign windImportance = 0.35 %}
{% assign sideSpinImportance = 0.15 %}
{% assign powerImportance = 0.50 %}

{% capture costAndKeys %}
  {% for tag in site.data.balls %}
    {% comment %}Filter out ball resources...{% endcomment %}
    {% if tag[1].name != undefined %}
      {% assign weightedWindRating = tag[1].wind-resistance | times: windImportance %}
      {% assign weightedSideSpinRating = tag[1].side-spin | times: sideSpinImportance %}
      {% assign weightedPowerRating = tag[1].power | times: powerImportance %}
      {% assign rating = weightedWindRating | plus: weightedSideSpinRating | plus: weightedPowerRating | round: 3 %}
      {{ rating }}:{{ tag[0] }}
    {% endif %}
  {% endfor %}
{% endcapture %}

{% assign sortedComposite = costAndKeys | split: ' ' | sort %}

{% capture sortedKeys %}
  {% for x in sortedComposite %}
    {{ x | split: ':' | last }}
  {% endfor %}
{% endcapture %}

{% assign ballKeyArray = sortedKeys | split: ' ' %}

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

    {% include_relative balls/ball-chart-template.liquid ballKeyArray=ballKeyArray %}

    <p class="text-center text-small text-semi-muted">
      This chart <strong><em>attempts</em></strong> to rank the balls based on wind resistance, side spin and power. Of course, every hole is different so a given ball may be better for a certain set of circumstances.
    </p>

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
          {% include_relative balls/ball-details-template.liquid ball=ball %}
        </div>
        <a id="{{ nextBallKey }}"></a>
        <div class="col-md-5 col-sm-12 col-flex" data-mh="ball-card">
          {% include_relative balls/ball-details-template.liquid ball=nextBall %}
        </div>
      </div>

    {% elsif loopMod2 == 0 %}

      <div class="row">
        <a id="{{ ballKey }}"></a>
        <div class="col-md-6 col-md-offset-3 col-sm-12 col-flex" data-mh="ball-card">
          {% include_relative balls/ball-details-template.liquid ball=ball %}
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
            {% if resource.url contains 'facebook' %}
              <i class="fa fa-facebook-official text-facebook" aria-hidden="true"></i>
            {% elsif resource.url contains 'reddit' %}
              <i class="fa fa-reddit text-reddit" aria-hidden="true"></i>
            {% elsif resource.url contains 'twitch' %}
              <i class="fa fa-twitch text-twitch" aria-hidden="true"></i>
            {% elsif resource.url contains 'twitter' %}
              <i class="fa fa-twitter text-twitter" aria-hidden="true"></i>
            {% elsif resource.url contains 'youtube' or resource.url contains 'youtu.be' %}
              <i class="fa fa-youtube-play text-youtube" aria-hidden="true"></i>
            {% else %}
              <i class="fa fa-book" aria-hidden="true"></i>
            {% endif %}
          {% endcapture %}

          <li class="list-group-item gcn-resource text-small"><a href="{{ resource.url }}" target="_blank" rel="noopener">{{ resourceIcon }} {{ resource.title }}</a></li>

        {% endfor %}
      </div>
    </div>
  </div>

{% endif %}
