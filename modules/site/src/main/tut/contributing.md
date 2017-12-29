---
layout: gcn-layout
title:  "Contributing"
section: "contributing"
permalink: /contributing/
position: 100
---

<div class="row">

  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">

    <h1 class="gcn-page-header">Help Make Golf Clash Notebook Better!</h1>

    <br>

    <h4 class="text-center">How Can You Help?</h4>

    <p>
      There are a lot of different ways you can help improve the quality of this site. Any effort
      made by you is <em><strong>greatly</strong></em> appreciated. Fixing simple spelling mistakes,
      adding new hole guides, adding tour advice, etc. are all tasks that take time and the more
      people who can contribute, the better this site will become.
    </p>

    <p>
      The easiest way to help out it to send an e-mail to <span class="font-600 text-backwards">oi.koobetonhsalcflog@etubirtnoc</span>.
      I'll listen to any ideas, take any missing chests on the chart, club descriptions, etc. If
      you're used to using GitHub then you can
      <a href="https://github.com/golf-clash-notebook/golf-clash-notebook.github.io/issues/new">open an issue</a>
      and describe what problem(s) you see or what could be added that would improve the overall quality
      of the site. This will require a Github account. If you don't have one already, you can create one
      <a href="https://github.com/join">here</a>. Once you create an issue, I'll work on getting a response back to
      you about what can be done to fix the problem.
    </p>

    <p>
      Another place to start is search for the words 'Help Wanted' in the code base and see what you
      find. I've tried marking most simple issues with that phrase (e.g. club, ball and tour descriptions).
    </p>

  </div>

</div>

<br>

<div class="row">

  <a id="ContributorList"></a>

  <div class="col-xs-12">
    <h4 class="text-center margin-4">
      King Makers
    </h4>
    <div class="text-center text-semi-muted">
      Heroes among us who've made the site better through content and ideas!
    </div>
  </div>

  <div class="col-md-10 col-md-offset-1 col-xs-12">
    <hr>
  </div>

  {% assign contributorsMod2 = site.data.contributors | size | modulo: 2 %}

  {% for contributor in site.data.contributors %}

    {% assign loopMod2 = forloop.index0 | modulo: 2 %}

    {% capture columnClasses %}
      {% if contributorsMod2 == 1 and forloop.last %}
        col-sm-6 col-sm-offset-3
      {% elsif loopMod2 == 0 %}
        col-sm-5 col-sm-offset-1
      {% else %}
        col-sm-5
      {% endif %}
    {% endcapture %}

    <div class="{{ columnClasses | strip }} text-center">
      <div class="pad-8">
        <strong>{{ contributor.name }}</strong>
        <br>
        <span class="text-semi-muted">{{ contributor.contributions }}</span>
      </div>
    </div>
  {% endfor %}

  <div class="col-md-10 col-md-offset-1 col-xs-12">
    <hr>
  </div>

</div>

<div class="row">
  <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <h4 class="text-center">Site Progress</h4>
    {% include contributing/status.liquid %}
  </div>
</div>
