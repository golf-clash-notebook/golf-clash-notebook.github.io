---
layout: gcn-layout
title:  "Wind"
section: "wind"
permalink: /wind/
position: 65
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">Resources explaining how to account for wind.</h1>

    <br>

    <h4>Ring Method</h4>

    <p>
      While it's not the only way to account for the wind on your shots, the Ring Method is by far
      the most popular. Using the rings on your target cursor as a guide you can pretty accurately
      adjust for the wind and get your shot to land where you want it to. It boils down to
      calculating how many MPH each ring is worth based on the accuracy rating of the club you're
      using for your shot.
    </p>
    <p>
      I'm actually not going to go into much more detail than that because there are <em>a lot</em>
      of excellent guides out there that can teach you step by step how to learn and master this
      technique. If you don't already know about using the ring method then I would
      <strong><em>strongly</em></strong> encourage you to go through each resource at the bottom of
      the page and at least give it a shot. While it may take some time to learn, <strong><em>it
      simplifies the game once you get the hang of it</em></strong>.
    </p>
    <p>
      As a first step, check out
      <a href="https://docs.google.com/spreadsheets/d/17nrOcQWTqBV8cz7U3IHanBKc9DFkMma3WTP09QqkoVY/edit#gid=0" target="_blank" rel="noopener">
      this detailed guide</a> (not sure who created this but if it's you please let me know so I
      can give you credit). It touches on adjusting and time management, which are both key
      elements. If reading isn't the way you like to learn, then definitely check out the video
      resources at the bottom of the page. They're all worth at least one view.
    </p>

    <br>

    {% if site.data.wind.resources != undefined and site.data.wind.resources != empty %}
      <p class="lead text-center">Resources</p>
      <div class="list-group">
        {% for resource in site.data.wind.resources %}

          {% capture resourceIcon %}
            {% include resources/icon.liquid resource=resource iconSize='lg' %}
          {% endcapture %}

          <li class="list-group-item gcn-resource text-small"><a href="{{ resource.url }}" target="_blank" rel="noopener">{{ resourceIcon }} {{ resource.title }}</a></li>

        {% endfor %}
      </div>
    {% endif %}

  </div>

</div>
