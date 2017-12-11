---
layout: gcn-layout
title:  "Tournaments"
section: "tournaments"
permalink: /tournaments/
position: 10
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-sm-12">

    {% capture dateAndKeys %}
      {% for tag in site.data.tournaments %}
        {{ tag[1].date }}:{{ tag[0] }}
      {% endfor %}
    {% endcapture %}

    {% assign sortedComposite = dateAndKeys | split: ' ' | sort %}

    {% capture sortedKeys %}
      {% for x in sortedComposite %}
        {{ x | split: ':' | last }}
      {% endfor %}
    {% endcapture %}

    {% assign keyArray = sortedKeys | split: ' ' | reverse %}
    {% assign mod2 = keyArray | size | modulo: 2 %}

    {% for key in keyArray %}

      {% capture columnClasses %}
        {% if mod2 == 1 and forloop.last %}
          col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-12
        {% else %}
          col-md-6 col-xs-12
        {% endif %}
      {% endcapture %}

      <div class="{{ columnClasses | strip }} text-center pad-16">
        <a href="/tournaments/{{ key }}/">
          <img class="img-responsive img-center" src="/img/golfclash/tournaments/titles/{{ key }}.png">
        </a>
      </div>

    {% endfor %}

  </div>

</div>

<hr>

<div class="row">

  <div class="col-md-4 col-md-offset-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Rules.png" class="img-center img-responsive" style="margin: 20px 0;">
  </div>

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <p class="text-prototype">
      Help Wanted: {% lipsum 3 5 10 %}
    </p>
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 20 %}
    </p>
  </div>

  <div class="col-lg-12"><hr></div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Qualifying.png" class="img-responsive">
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 15 %}
    </p>
  </div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Opening.png" class="img-responsive">
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 15 %}
    </p>
  </div>

  <div class="col-md-4 col-sm-12">
    <img src="/img/golfclash/tournaments/rules/Weekend.png" class="img-responsive">
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 15 %}
    </p>
  </div>

  <div class="col-lg-12"><hr></div>


  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <p class="text-prototype">
      Help Wanted: {% lipsum 2 10 20 %}
    </p>
  </div>

</div>
