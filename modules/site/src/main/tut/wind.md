---
layout: gcn-layout
title:  "Wind"
section: "wind"
permalink: /wind/
position: 60
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">Resources explaining how to account for wind.</h1>

    <br>

    <h4>Ring Method</h4>

    <p class="text-prototype">
      Help Wanted: {% lipsum 2 5 25 %}
    </p>

    <h4>Your method here!</h4>

    <p class="text-prototype">
      Help Wanted: {% lipsum 2 5 25 %}
    </p>

    <br>

    {% if site.data.wind.resources != undefined and site.data.wind.resources != empty %}
      <p class="lead text-center">Resources</p>
      <div class="list-group">
        {% for resource in site.data.wind.resources %}

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
    {% endif %}

  </div>

</div>
