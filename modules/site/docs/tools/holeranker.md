---
layout: gcn-layout
title:  "Hole Ranker"
section: "tools"
permalink: /tools/holeranker/
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">
      Hole Ranker
      <a class="btn btn-link text-semi-muted pad-0" href="#WhatIsThis">
        <i class="far fa-question-circle"></i>
      </a>
    </h1>

    <hr class="hr-text text-large" data-content="Which Hole Is More Difficult?">

  </div>

</div>

<div class="hole-ranker-scenario-container">
  <div id="busy-spinner" class="row hidden">
    <div class="col-xs-12">
      <div class="orbit-spinner spinner-centered">
        <div class="orbit"></div>
        <div class="orbit"></div>
        <div class="orbit"></div>
      </div>
    </div>
  </div>
  <button id="skip-scenario-btn" type="button" class="btn btn-default btn-slim">Not Sure...</button>
  <div id="scenario-content"></div>
</div>

<div class="row">

  <hr class="hr-text text-large" data-content="Hole Difficulty Ratings">

  {% assign currentRatings = site.data.holeratings.currentratings | sort:'rating' | reverse %}
  {% assign previousRatings = site.data.holeratings.previousratings %}

  {% assign minHoleRating = currentRatings | first %}
  {% assign maxHoleRating = currentRatings | last %}
  {% assign minRating = minHoleRating.rating %}
  {% assign maxRating = maxHoleRating.rating %}
  {% assign ratingRange = maxRating | minus: minRating %}

  {% for holeRating in currentRatings %}
    {% for courseHash in site.data.courses %}

      {% assign course = courseHash[1] %}

      {% for hole in course.holes %}

        {% if hole.id == holeRating.holeId %}

          {% assign coursePath = course.courseName | remove: " " %}
          {% assign holeDifficulty = holeRating.rating | minus: minRating | divided_by: ratingRange %}

          {% capture previousRating %}
            {% for previousRating in previousRatings %}
              {% if previousRating.holeId == holeRating.holeId %}
                {{ previousRating.rating }}
              {% endif %}
            {% endfor %}
          {% endcapture %}

          {% assign previousRating = previousRating | strip | plus: 0 %}

          {% capture ratingChange %}
            {% if previousRating == empty %}
              +0.0
            {% elsif previousRating < holeRating.rating %}
              +{{ holeRating.rating | minus: previousRating | round: 1 }}
            {% else %}
              {{ holeRating.rating | minus: previousRating | round: 1 }}
            {% endif %}
          {% endcapture %}

          {% capture ratingColor %}
            {% if holeDifficulty > 0.9 %}
              #007D00;
            {% elsif holeDifficulty > 0.8 %}
              #019501;
            {% elsif holeDifficulty > 0.7 %}
              #6EAE00;
            {% elsif holeDifficulty > 0.6 %}
              #C5C800;
            {% elsif holeDifficulty > 0.5 %}
              #FFDE00;
            {% elsif holeDifficulty > 0.4 %}
              #FFDF00;
            {% elsif holeDifficulty > 0.3 %}
              #FFDE00;
            {% elsif holeDifficulty > 0.2 %}
              #FFA800;
            {% elsif holeDifficulty > 0.1 %}
              #FF7000;
            {% else %}
              #FD3900;
            {% endif %}
          {% endcapture %}

          <a href="/courses/{{ coursePath }}/{{ hole.number }}/">
            <div class="col-md-4 col-sm-6 col-xs-12">
              <div class="hole-rating-card pad-12 {{ ratingTextClass | strip }}" style="border-left: 3px solid {{ ratingColor | strip }}">
                <div class="col hole-thumb">
                  <img class="img-inline img-responsive" src="/img/golfclash/courses/{{ coursePath }}/{{ hole.number}}-thumb.png" >
                </div>
                <div class="col hole-description">
                  <h4 class="text-semi-muted">{{ course.courseName }}</h4>
                  <h5 class="text-semi-muted margin-left-8">Hole {{ hole.number }} - Par {{ hole.par }}</h5>
                  <h5 class="text-semi-muted margin-left-8">
                    <strong> {{ holeRating.rating | round: 2 }}</strong>
                    <small class="text-semi-muted">({{ ratingChange | strip }})</small>
                  </h5>
                </div>
              </div>
            </div>
          </a>
        {% endif %}
      {% endfor %}
    {% endfor %}
  {% endfor %}

</div>

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <a id="WhatIsThis"></a>
    <hr class="hr-text text-large" data-content="What is This?">

    <p>
      Ever wonder which holes are the easiest and which are the most difficult? That's what this
      tool aims to answer once and for all! You'll be given a series of 2 different holes and all
      you have to do is click the hole you believe is more difficult overall. Don't worry about
      which tee, par, winds, clubs, etc. Pretty easy right? The idea is that once enough votes are
      gathered we can get a list of all the holes, sorted by difficulty. The key is to get as many
      votes as possible so if you have some time to kill while you're waiting for your chests to
      open, go ahead and make your choices. The more input the better!
    </p>
    <p class="text-small text-semi-muted">
      If you're interested in the nerdy details, have a look at
      <a href="https://youtu.be/D0-mwicFMyI" target="_blank" rel="noopener">this video</a> which
      lays out the general idea behind how this works. You can also check out the
      <a href="https://en.wikipedia.org/wiki/Elo_rating_system" target="_blank" rel="noopener">Wikipedia page on Elo Ratings.</a>
    </p>

  </div>

</div>
