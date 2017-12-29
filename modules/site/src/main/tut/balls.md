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

    <p class="text-content">
      Using the right balls is every bit as important as using the right clubs in Golf Clash. Using
      special balls too early will put you at a disadvantage later in the game, and using a ball
      with the wrong attributes will not only waste a ball that could be useful, but can also put
      you at a disadvantage for that hole. Learning these points of the game can give you an
      advantage and help save you some pain and aggravation.
    </p>

    <p class="text-content">
      Starting the game, you won't need to use any special balls. <strong>Let's repeat: "I will NOT
      use special balls before I hit Tour 5".</strong> Even then, being the Golf Clash God that you
      are, you can probably continue using basic balls through Tours 5 & 6. At most, you can use a
      Marlin here and there to deaden a head wind or get a little spin with a low level Extra Mile
      which lacks curl. This is the time to build a reserve of special balls that you will
      definitely need when you get to the higher tours.
    </p>

    <p class="text-content">
      It's fairly common to see Quasars and Navigators on Tour 7+ and that's the first time you
      should be using them. By that point, your free, pin and 1 vs. 1 chests will cover the cost of
      them in the shop. Only in the most extreme circumstances should you consider breaking out a
      Katana or Titan. You're much better off saving those for higher tours and
      tournaments.
    </p>

    <p class="text-content">
      Speaking of tournaments, that is the absolute best way to build the stockpile of balls that
      you'll need. Finishing in the top 10 or 25 in a couple tournaments will get you well on your
      way to where you can use the balls you've won to win more (and better) balls in the future.
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
            {% include resources/icon.liquid resource=resource iconSize='lg' %}
          {% endcapture %}

          <li class="list-group-item gcn-resource text-small"><a href="{{ resource.url }}" target="_blank" rel="noopener">{{ resourceIcon }} {{ resource.title }}</a></li>

        {% endfor %}
      </div>
    </div>
  </div>

{% endif %}
